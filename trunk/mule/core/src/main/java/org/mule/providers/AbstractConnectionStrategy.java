/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers;

import org.mule.MuleManager;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.impl.internal.notifications.ManagerNotificationListener;
import org.mule.impl.internal.notifications.NotificationException;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.provider.UMOConnectable;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageReceiver;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO document
 */
public abstract class AbstractConnectionStrategy implements ConnectionStrategy
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    private volatile boolean doThreading = false;
    
    private volatile boolean isConnecting = false;
  
    private ThreadLocal isInitialThread = new ThreadLocal()
    {
      protected synchronized Object initialValue()
      {
          return new Boolean(true);
      }
    };
    
    private final Object reconnectLock = new Object();

    public final void connect(UMOConnectable connectable) throws FatalConnectException
    {
        if (doThreading && !isConnecting)
        {
            connectAfterServerStartup(connectable);
        }
        else if (!((Boolean) isInitialThread.get()).booleanValue() || !isConnecting)
        {
            connectImmediately(connectable);
        }
    }

    public boolean isDoThreading()
    {
        return doThreading;
    }

    public void setDoThreading(boolean doThreading)
    {
        this.doThreading = doThreading;
    }

    protected abstract void doConnect(UMOConnectable connectable) throws FatalConnectException;

    /**
     * Resets any state stored in the retry strategy
     */
    public abstract void resetState();

    protected String getDescription(UMOConnectable connectable)
    {
        if (connectable instanceof UMOMessageReceiver)
        {
            return ((UMOMessageReceiver) connectable).getEndpointURI().toString();
        }
        else
        {
            return connectable.toString();
        }
    }
    
    protected void connectAfterServerStartup(final UMOConnectable connectable) throws FatalConnectException
    {
        isConnecting = true;
        doThreading = false;
        
        try
        {
            MuleManager.getInstance().registerListener(new ManagerNotificationListener()
            {
                public void onNotification(UMOServerNotification notification)
                {
                    if (notification.getAction() == ManagerNotification.MANAGER_STARTED)
                    {
                        try
                        {
                            MuleManager.getInstance().getWorkManager().scheduleWork(new Work()
                            {
                                public void release()
                                {
                                    // ignore
                                }
    
                                public void run()
                                {
                                    isInitialThread.set(new Boolean(false));
    
                                    try
                                    {
                                        synchronized (reconnectLock)
                                        {
                                            doConnect(connectable);
                                        }
                                    }
                                    catch (FatalConnectException e)
                                    {
                                        synchronized (reconnectLock)
                                        {
                                            resetState();
                                        }
                                        // TODO should really extract an interface for
                                        // classes capable of handling an exception
                                        if (connectable instanceof UMOConnector)
                                        {
                                            ((UMOConnector) connectable).handleException(e);
                                        }
                                        // TODO: this cast is evil
                                        else if (connectable instanceof AbstractMessageReceiver)
                                        {
                                            ((AbstractMessageReceiver) connectable).handleException(e);
                                        }
                                        // if it's none of the above, it's not handled and Mule just sits doing nothing
                                    }
                                    finally
                                    {
                                        isConnecting = false;
                                    }
                                }
                            });
                        }
                        catch (WorkException e)
                        {
                            synchronized (reconnectLock)
                            {
                                resetState();
                            }
                        }
                    }
                }
            });
        }
        catch (NotificationException e)
        {
            synchronized (reconnectLock)
            {
                resetState();
            }
            throw new FatalConnectException(e, connectable);
        }
    }
    
    protected void connectImmediately(UMOConnectable connectable) throws FatalConnectException
    {
        try
        {
            synchronized (reconnectLock)
            {
                doConnect(connectable);
            }
        }
        finally
        {
            synchronized (reconnectLock)
            {
                resetState();
            }
        }
    }
}

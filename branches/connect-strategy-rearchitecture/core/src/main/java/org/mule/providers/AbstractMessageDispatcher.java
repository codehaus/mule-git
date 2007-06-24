/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers;

import org.mule.MuleRuntimeException;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.RequestContext;
import org.mule.impl.retry.RetryTemplate;
import org.mule.impl.internal.notifications.ConnectionNotification;
import org.mule.impl.internal.notifications.MessageNotification;
import org.mule.impl.internal.notifications.SecurityNotification;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.TransactionException;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOTransaction;
import org.mule.umo.retry.UMORetryTemplate;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.umo.provider.DispatchException;
import org.mule.umo.provider.ReceiveException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.util.ClassUtils;
import org.mule.umo.retry.UMORetryCallback;
import org.mule.umo.retry.RetryContext;

import java.beans.ExceptionListener;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p/> <code>AbstractMessageDispatcher</code> provides a default dispatch (client)
 * support for handling threads lifecycle and validation.
 */
public abstract class AbstractMessageDispatcher implements UMOMessageDispatcher, ExceptionListener
{
    /** logger used by this class */
    protected transient Log logger = LogFactory.getLog(getClass());

    /** Thread pool of Connector sessions */
    protected UMOWorkManager workManager = null;

    protected final UMOImmutableEndpoint endpoint;
    protected final AbstractConnector connector;

    protected boolean disposed = false;

    protected UMORetryTemplate connectionStrategy;

    protected volatile boolean connecting = false;
    protected volatile boolean connected = false;

    public AbstractMessageDispatcher(UMOImmutableEndpoint endpoint)
    {
        this.endpoint = endpoint;
        this.connector = (AbstractConnector) endpoint.getConnector();

        if(endpoint.getRetryPolicyFactory()!=null)
        {
            connectionStrategy = new RetryTemplate(endpoint.getRetryPolicyFactory(), new ConnectNotifier());
        }
        else
        {
            connectionStrategy = connector.getConnectionStrategy();
        }

        if (isDoThreading())
        {
            try
            {
                workManager = connector.getDispatcherWorkManager();
            }
            catch (UMOException e)
            {
                dispose();
                throw new MuleRuntimeException(CoreMessages.failedToStart("WorkManager"), e);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.umo.provider.UMOMessageDispatcher#dispatch(org.mule.umo.UMOEvent)
     */
    public final void dispatch(UMOEvent event) throws DispatchException
    {
        event.setSynchronous(false);
        event.getMessage().setProperty(MuleProperties.MULE_ENDPOINT_PROPERTY,
                event.getEndpoint().getEndpointURI().toString());
        RequestContext.setEvent(event);

        // Apply Security filter if one is set
        UMOImmutableEndpoint endpoint = event.getEndpoint();
        if (endpoint.getSecurityFilter() != null)
        {
            try
            {
                endpoint.getSecurityFilter().authenticate(event);
            }
            catch (org.mule.umo.security.SecurityException e)
            {
                // TODO MULE-863: Do we need this warning?
                logger.warn("Outbound Request was made but was not authenticated: " + e.getMessage(), e);
                connector.fireNotification(new SecurityNotification(e,
                        SecurityNotification.ADMIN_EVENT_ACTION_START_RANGE));
                connector.handleException(e);
                return;
            }
            catch (UMOException e)
            {
                dispose();
                throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
            }
        }
        // the security filter may update the payload so we need to get the
        // latest event again
        final UMOEvent dispatchEvent = RequestContext.getEvent();

        try
        {
            UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
            Worker worker = new Worker(dispatchEvent);
            if (isDoThreading() && !dispatchEvent.isSynchronous() && tx == null)
            {
                workManager.scheduleWork(worker, WorkManager.INDEFINITE, null, connector);
            }
            else
            {
                //Execute within this thread
                worker.run();
            }
        }
        catch (Exception e)
        {
            dispose();
            throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
        }
    }

    public final UMOMessage send(UMOEvent event) throws DispatchException
    {
        // No point continuing if the component has rolledback the transaction
        if (isTransactionRollback())
        {
            return event.getMessage();
        }

        event.setSynchronous(true);
        event.getMessage().setProperty(MuleProperties.MULE_ENDPOINT_PROPERTY,
                event.getEndpoint().getEndpointURI().toString());
        RequestContext.setEvent(event);

        // Apply Security filter if one is set
        UMOImmutableEndpoint endpoint = event.getEndpoint();
        if (endpoint.getSecurityFilter() != null)
        {
            try
            {
                endpoint.getSecurityFilter().authenticate(event);
            }
            catch (org.mule.umo.security.SecurityException e)
            {
                logger.warn("Outbound Request was made but was not authenticated: " + e.getMessage(), e);
                connector.fireNotification(new SecurityNotification(e,
                        SecurityNotification.SECURITY_AUTHENTICATION_FAILED));
                connector.handleException(e);
                return event.getMessage();
            }
            catch (UMOException e)
            {
                dispose();
                throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
            }
        }

        // the security filter may update the payload so we need to get the
        // latest event again
        final UMOEvent currentEvent = RequestContext.getEvent();
        try
        {
            RetryContext context = connectionStrategy.execute(new UMORetryCallback()
            {
                public void doWork(RetryContext context) throws Exception
                {
                    // Make sure we are connected
                    connect();

                    UMOMessage result = doSend(currentEvent);
                    if (connector.isEnableMessageEvents())
                    {
                        String component = null;
                        if (currentEvent.getComponent() != null)
                        {
                            component = currentEvent.getComponent().getDescriptor().getName();
                        }
                        connector.fireNotification(new MessageNotification(currentEvent.getMessage(), currentEvent.getEndpoint(),
                                component, MessageNotification.MESSAGE_SENT));
                    }

                    // Once a dispatcher has done its work we need to remove this property
                    // so that it is not propagated to the next request
                    if (result != null)
                    {
                        result.removeProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY);
                        context.addReturnMessage(result);
                    }
                }

                public String getWorkDescription()
                {
                    return getConnectionDescription();
                }
            });
            return context.getFirstReturnMessage();

        }
        catch (Exception e)
        {
            dispose();
            throw new DispatchException(event.getMessage(), event.getEndpoint(), e);
        }
    }

    /**
     * Make a specific request to the underlying transport
     *
     * @param timeout the maximum time the operation should block before returning.
     *                The call should return immediately if there is data available. If
     *                no data becomes available before the timeout elapses, null will be
     *                returned
     * @return the result of the request wrapped in a UMOMessage object. Null will be
     *         returned if no data was avaialable
     * @throws Exception if the call to the underlying protocal cuases an exception
     */
    public final UMOMessage receive(final long timeout) throws Exception
    {

        try
        {
            RetryContext context = connectionStrategy.execute(new UMORetryCallback()
            {
                public void doWork(RetryContext context) throws Exception
                {
                    // Make sure we are connected
                    connect();
                    UMOMessage result = doReceive(timeout);
                    if (result != null && connector.isEnableMessageEvents())
                    {
                        connector.fireNotification(new MessageNotification(result, endpoint, null,
                                MessageNotification.MESSAGE_RECEIVED));
                    }
                    context.setReturnMessages(new UMOMessage[]{result});
                }

                public String getWorkDescription()
                {
                    return getWorkDescription();
                }
            });
            return context.getFirstReturnMessage();

        }
        catch (Exception e)
        {
            dispose();
            throw new ReceiveException(endpoint, timeout, e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.util.ExceptionListener#onException(java.lang.Throwable)
     */
    public void exceptionThrown(Exception e)
    {
        try
        {
            getConnector().handleException(e);
        }
        finally
        {
            dispose();
        }
    }

    public boolean validate()
    {
        // by default a dispatcher can be used unless disposed
        return !disposed;
    }

    public void activate()
    {
        // nothing to do by default
    }

    public void passivate()
    {
        // nothing to do by default
    }

    /** Template method to destroy any resources held by the Message Dispatcher */
    public final synchronized void dispose()
    {
        if (!disposed)
        {
            try
            {
                try
                {
                    this.disconnect();
                }
                catch (Exception e)
                {
                    // TODO MULE-863: What should we really do?
                    logger.warn(e.getMessage(), e);
                }

                this.doDispose();

                if (workManager != null)
                {
                    workManager.dispose();
                }
            }
            finally
            {
                disposed = true;
            }
        }
    }

    public UMOConnector getConnector()
    {
        return connector;
    }

    public UMOImmutableEndpoint getEndpoint()
    {
        return endpoint;
    }

    /**
     * RemoteSync causes the message dispatch to wait for a response to an event on a
     * response channel after it sends the event. The following rules apply to
     * RemoteSync 1. The connector has to support remoteSync. Some transports do not
     * have the notion of a response channel 2. Check if the endpoint has been
     * configured for remoteSync 3. Check if the REMOTE_SYNC message header has been
     * set 4. Finally, if the current component has a response router configured,
     * that the router will handle the response channel event and we should not try
     * and receive a response in the Message dispatcher If remotesync should not be
     * used we must remove the REMOTE_SYNC header Note the MuleClient will
     * automatically set the REMOTE_SYNC header when client.send(..) is called so
     * that results are returned from remote invocations too.
     *
     * @param event the current event
     * @return true if a response channel should be used to get a resposne from the
     *         event dispatch.
     */
    protected boolean useRemoteSync(UMOEvent event)
    {
        boolean remoteSync = false;
        if (event.getEndpoint().getConnector().isRemoteSyncEnabled())
        {
            remoteSync = event.getEndpoint().isRemoteSync()
                    || event.getMessage().getBooleanProperty(
                    MuleProperties.MULE_REMOTE_SYNC_PROPERTY, false);
            if (remoteSync)
            {
                // component will be null for client calls
                if (event.getComponent() != null)
                {
                    remoteSync = event.getComponent().getDescriptor().getResponseRouter() == null;
                }
            }
        }
        if (!remoteSync)
        {
            event.getMessage().removeProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY);
        }
        return remoteSync;
    }

    public synchronized void connect() throws Exception
    {
        if (disposed)
        {
            throw new IllegalStateException("Dispatcher has been disposed; cannot connect to resource");
        }

        if (connected)
        {
            return;
        }

        doConnect();
        connected = true;
    }

    public synchronized void disconnect() throws Exception
    {
        if (!connected)
        {
            return;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Disconnecting: " + this);
        }

        this.doDisconnect();
        connected = false;

        logger.info("Disconnected: " + this);

        connector.fireNotification(new ConnectionNotification(this, getConnectEventId(endpoint),
                ConnectionNotification.CONNECTION_DISCONNECTED));
    }

    protected String getConnectEventId(UMOImmutableEndpoint endpoint)
    {
        return connector.getName() + ".dispatcher(" + endpoint.getEndpointURI() + ")";
    }

    public final boolean isConnected()
    {
        return connected;
    }

    protected boolean isDoThreading()
    {
        return connector.getDispatcherThreadingProfile().isDoThreading();
    }

    /**
     * Returns a string identifying the underlying resource
     *
     * @return
     */
    public String getConnectionDescription()
    {
        return "endpoint." + endpoint.getEndpointURI().toString();
    }

    public synchronized void reconnect() throws Exception
    {
        disconnect();
        connect();
    }

    protected abstract void doDispose();

    protected abstract void doDispatch(UMOEvent event) throws Exception;

    protected abstract UMOMessage doSend(UMOEvent event) throws Exception;

    protected abstract void doConnect() throws Exception;

    protected abstract void doDisconnect() throws Exception;

    /**
     * Make a specific request to the underlying transport
     *
     * @param timeout the maximum time the operation should block before returning.
     *                The call should return immediately if there is data available. If
     *                no data becomes available before the timeout elapses, null will be
     *                returned
     * @return the result of the request wrapped in a UMOMessage object. Null will be
     *         returned if no data was avaialable
     * @throws Exception if the call to the underlying protocal cuases an exception
     */
    protected abstract UMOMessage doReceive(long timeout) throws Exception;

    private class Worker implements Work
    {
        private final UMOEvent event;

        public Worker(UMOEvent event)
        {
            this.event = event;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Runnable#run()
         */
        public void run()
        {
            try
            {
                connectionStrategy.execute(new UMORetryCallback()
                {

                    public void doWork(RetryContext context) throws Exception
                    {

                        RequestContext.setEvent(event);
                        // Make sure we are connected
                        connect();
                        AbstractMessageDispatcher.this.doDispatch(event);

                        if (connector.isEnableMessageEvents())
                        {
                            String component = null;
                            if (event.getComponent() != null)
                            {
                                component = event.getComponent().getDescriptor().getName();
                            }

                            connector.fireNotification(new MessageNotification(event.getMessage(), event
                                    .getEndpoint(), component, MessageNotification.MESSAGE_DISPATCHED));
                        }
                    }

                    public String getWorkDescription()
                    {
                        return getConnectionDescription();
                    }
                }

                );


            }
            catch (Exception e)
            {
                AbstractMessageDispatcher.this.getConnector().handleException(e);
            }
        }

        public void release()
        {
            // nothing to do
        }
    }

    /**
     * Checks to see if the current transaction has been rolled back
     *
     * @return
     */
    protected boolean isTransactionRollback()
    {
        try
        {
            UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
            if (tx != null && tx.isRollbackOnly())
            {
                return true;
            }
        }
        catch (TransactionException e)
        {
            // TODO MULE-863: What should we really do?
            logger.warn(e.getMessage());
        }
        return false;
    }

    //  @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer(80);
        sb.append(ClassUtils.getSimpleName(this.getClass()));
        sb.append("{this=").append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(", endpoint=").append(endpoint.getEndpointURI());
        sb.append('}');
        return sb.toString();
    }
}

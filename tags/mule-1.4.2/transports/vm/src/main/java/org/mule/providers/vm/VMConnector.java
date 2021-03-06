/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.vm;

import org.mule.MuleManager;
import org.mule.config.QueueProfile;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.MuleMessage;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.AbstractConnector;
import org.mule.routing.filters.WildcardFilter;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.MessagingException;
import org.mule.umo.TransactionException;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOTransaction;
import org.mule.umo.endpoint.EndpointException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.MessageTypeNotSupportedException;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.umo.provider.UMOMessageReceiver;
import org.mule.util.ClassUtils;
import org.mule.util.queue.QueueManager;
import org.mule.util.queue.QueueSession;

import java.util.Iterator;

/**
 * <code>VMConnector</code> is a simple endpoint wrapper to allow a Mule component
 * to be accessed from an endpoint
 */
public class VMConnector extends AbstractConnector
{
    private boolean queueEvents = false;
    private QueueProfile queueProfile;
    private Class adapterClass = null;
    private int queueTimeout = 1000;

    protected void doInitialise() throws InitialisationException
    {
        if (queueEvents)
        {
            if (queueProfile == null)
            {
                queueProfile = MuleManager.getConfiguration().getQueueProfile();
            }
        }

        try
        {
            adapterClass = ClassUtils.loadClass(serviceDescriptor.getMessageAdapter(), getClass());
        }
        catch (ClassNotFoundException e)
        {
            throw new InitialisationException(
                CoreMessages.failedToLoad("Message Adapter: " + serviceDescriptor.getMessageAdapter()), e);
        }
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doConnect() throws Exception
    {
        // template method
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    protected void doStart() throws UMOException
    {
        // template method
    }

    protected void doStop() throws UMOException
    {
        // template method
    }

    public UMOMessageReceiver createReceiver(UMOComponent component, UMOEndpoint endpoint) throws Exception
    {
        if (queueEvents)
        {
            queueProfile.configureQueue(endpoint.getEndpointURI().getAddress());
        }
        return serviceDescriptor.createMessageReceiver(this, component, endpoint);
    }

    public UMOMessageAdapter getMessageAdapter(Object message) throws MessagingException
    {
        if (message == null)
        {
            throw new MessageTypeNotSupportedException(null, adapterClass);
        }
        else if (message instanceof MuleMessage)
        {
            return ((MuleMessage)message).getAdapter();
        }
        else if (message instanceof UMOMessageAdapter)
        {
            return (UMOMessageAdapter)message;
        }
        else
        {
            throw new MessageTypeNotSupportedException(message, adapterClass);
        }
    }

    public String getProtocol()
    {
        return "VM";
    }

    public boolean isQueueEvents()
    {
        return queueEvents;
    }

    public void setQueueEvents(boolean queueEvents)
    {
        this.queueEvents = queueEvents;
    }

    public QueueProfile getQueueProfile()
    {
        return queueProfile;
    }

    public void setQueueProfile(QueueProfile queueProfile)
    {
        this.queueProfile = queueProfile;
    }

    VMMessageReceiver getReceiver(UMOEndpointURI endpointUri) throws EndpointException
    {
        return (VMMessageReceiver)getReceiverByEndpoint(endpointUri);
    }

    QueueSession getQueueSession() throws InitialisationException
    {
        QueueManager qm = MuleManager.getInstance().getQueueManager();
        UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
        if (tx != null)
        {
            if (tx.hasResource(qm))
            {
                if (logger.isTraceEnabled())
                {
                    logger.trace("Retrieving queue session from current transaction");
                }
                return (QueueSession)tx.getResource(qm);
            }
        }

        if (logger.isTraceEnabled())
        {
            logger.trace("Retrieving new queue session from queue manager");
        }

        QueueSession session = qm.getQueueSession();
        if (tx != null)
        {
            logger.debug("Binding queue session to current transaction");
            try
            {
                tx.bindResource(qm, session);
            }
            catch (TransactionException e)
            {
                throw new RuntimeException("Could not bind queue session to current transaction", e);
            }
        }
        return session;
    }

    protected UMOMessageReceiver getReceiverByEndpoint(UMOEndpointURI endpointUri) throws EndpointException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Looking up vm receiver for address: " + endpointUri.toString());
        }

        UMOMessageReceiver receiver;
        // If we have an exact match, use it
        receiver = (UMOMessageReceiver)receivers.get(endpointUri.getAddress());
        if (receiver != null)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Found exact receiver match on endpointUri: " + endpointUri);
            }
            return receiver;
        }

        // otherwise check each one against a wildcard match
        for (Iterator iterator = receivers.values().iterator(); iterator.hasNext();)
        {
            receiver = (UMOMessageReceiver)iterator.next();
            String filterAddress = receiver.getEndpointURI().getAddress();
            WildcardFilter filter = new WildcardFilter(filterAddress);
            if (filter.accept(endpointUri.getAddress()))
            {
                receiver.getEndpoint().setEndpointURI(new MuleEndpointURI(endpointUri, filterAddress));

                if (logger.isDebugEnabled())
                {
                    logger.debug("Found receiver match on endpointUri: " + receiver.getEndpointURI()
                                 + " against " + endpointUri);
                }
                return receiver;
            }
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("No receiver found for endpointUri: " + endpointUri);
        }
        return null;
    }

    // @Override
    public boolean isRemoteSyncEnabled()
    {
        return true;
    }

    public int getQueueTimeout()
    {
        return queueTimeout;
    }

    public void setQueueTimeout(int queueTimeout)
    {
        this.queueTimeout = queueTimeout;
    }

}

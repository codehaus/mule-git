/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport;

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleSession;
import org.mule.OptimizedRequestContext;
import org.mule.ResponseOutputStream;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.config.MuleProperties;
import org.mule.api.context.WorkManager;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.service.Service;
import org.mule.api.transaction.Transaction;
import org.mule.api.transport.Connector;
import org.mule.api.transport.MessageReceiver;
import org.mule.processor.ChainMessageProcessorBuilder;
import org.mule.transaction.TransactionCoordination;
import org.mule.transport.inbound.processor.InboundEndpointDecoratorMessageProcessor;
import org.mule.transport.inbound.processor.InboundExceptionDetailsMessageProcessor;
import org.mule.transport.inbound.processor.InboundFilterMessageProcessor;
import org.mule.transport.inbound.processor.InboundLoggingMessageProcessor;
import org.mule.transport.inbound.processor.InboundNotificationMessageProcessor;
import org.mule.transport.inbound.processor.InboundResponseTransformerMessageProcessor;
import org.mule.transport.inbound.processor.InboundSecurityFilterMessageProcessor;
import org.mule.util.ClassUtils;

import java.io.OutputStream;

/**
 * <code>AbstractMessageReceiver</code> provides common methods for all Message
 * Receivers provided with Mule. A message receiver enables an endpoint to receive a
 * message from an external system.
 */
public abstract class AbstractMessageReceiver extends AbstractConnectable implements MessageReceiver
{
    /**
     * The Service with which this receiver is associated with
     */
    protected Service service = null;

    /**
     * {@link MessageProcessor} chain used to process messages once the transport
     * specific {@link MessageReceiver} has received transport message and created
     * the {@link MuleMessage}
     */
    protected MessageProcessor receiverMessageProcessorChain;

    /**
     * Stores the key to this receiver, as used by the Connector to store the
     * receiver.
     */
    protected String receiverKey = null;

    /**
     * Stores the endpointUri that this receiver listens on. This enpoint can be
     * different to the endpointUri in the endpoint stored on the receiver as
     * endpoint endpointUri may get rewritten if this endpointUri is a wildcard
     * endpointUri such as jms.*
     */
    private EndpointURI endpointUri;

    /**
     * Creates the Message Receiver
     * 
     * @param connector the endpoint that created this listener
     * @param service the service to associate with the receiver. When data is
     *            received the service <code>dispatchEvent</code> or
     *            <code>sendEvent</code> is used to dispatch the data to the relevant
     *            Service.
     * @param endpoint the provider contains the endpointUri on which the receiver
     *            will listen on. The endpointUri can be anything and is specific to
     *            the receiver implementation i.e. an email address, a directory, a
     *            jms destination or port address.
     * @see Service
     * @see InboundEndpoint
     */
    public AbstractMessageReceiver(Connector connector, Service service, InboundEndpoint endpoint)
        throws CreateException
    {
        super(endpoint);

        if (service == null)
        {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.service = service;

        receiverMessageProcessorChain = createReceiverMessageProcessorChain();
    }

    /**
     * Method used to perform any initialisation work. If a fatal error occurs during
     * initialisation an <code>InitialisationException</code> should be thrown,
     * causing the Mule instance to shutdown. If the error is recoverable, say by
     * retrying to connect, a <code>RecoverableException</code> should be thrown.
     * There is no guarantee that by throwing a Recoverable exception that the Mule
     * instance will not shut down.
     * 
     * @throws org.mule.api.lifecycle.InitialisationException if a fatal error occurs
     *             causing the Mule instance to shutdown
     * @throws org.mule.api.lifecycle.RecoverableException if an error occurs that
     *             can be recovered from
     */
    @Override
    public final void initialise() throws InitialisationException
    {
        super.initialise();

        endpointUri = endpoint.getEndpointURI();

        doInitialise();
    }

    @Override
    public final synchronized void dispose()
    {
        super.dispose();
        try
        {
            doDispose();
        }
        finally
        {
            disposed.set(true);
        }
    }

    /**
     * <p>
     * Create a default {@link MessageProcessor} chain suitable for and used by most
     * transports.
     * </p>
     * //TODO This construction of this chain should be external to MessageReciver
     * with the chain that is built being injected into MessageReceiver as a listener
     */
    protected MessageProcessor createReceiverMessageProcessorChain()
    {
        ChainMessageProcessorBuilder builder = new ChainMessageProcessorBuilder();
        builder.chain(new InboundNotificationMessageProcessor())
            .chain(new InboundLoggingMessageProcessor())
            .chain(new InboundFilterMessageProcessor())
            .chain(new InboundSecurityFilterMessageProcessor());

        if (isResponseReceiver())
        {
            builder.chain(service.getResponseRouter());
        }
        else
        {
            builder.chain(new InboundEndpointDecoratorMessageProcessor())
                .chain(service.getInboundRouter())
                .chain(new InboundExceptionDetailsMessageProcessor())
                .chain(new InboundResponseTransformerMessageProcessor());
        }

        customizeMessageProcessorBuilder(builder);
        return builder.build();
    }

    protected boolean isResponseReceiver()
    {
        return service.getResponseRouter() != null
               && service.getResponseRouter().getEndpoints().contains(endpoint);
    }

    /**
     * Create a default {@link MessageProcessor} chain suitable for and used by most
     * transports.
     */
    protected void customizeMessageProcessorBuilder(ChainMessageProcessorBuilder builder)
    {
        // Template method
    }

    // TODO BL-46 Service dependency
    public Service getService()
    {
        return service;
    }

    public final MuleMessage routeMessage(MuleMessage message) throws MuleException
    {
        return routeMessage(message, (endpoint.isSynchronous() || TransactionCoordination.getInstance()
            .getTransaction() != null));
    }

    public final MuleMessage routeMessage(MuleMessage message, boolean synchronous) throws MuleException
    {
        Transaction tx = TransactionCoordination.getInstance().getTransaction();
        return routeMessage(message, tx, tx != null || synchronous, null);
    }

    public final MuleMessage routeMessage(MuleMessage message, Transaction trans, boolean synchronous)
        throws MuleException
    {
        return routeMessage(message, trans, synchronous, null);
    }

    public final MuleMessage routeMessage(MuleMessage message,
                                          Transaction trans,
                                          boolean synchronous,
                                          OutputStream outputStream) throws MuleException
    {

        // Enforce a sync endpoint if remote sync is set
        // TODO MULE-4622
        if (message.getBooleanProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY, false))
        {
            synchronous = true;
        }

        MuleEvent muleEvent = createMuleEvent(message, synchronous, outputStream);
        muleEvent = OptimizedRequestContext.unsafeSetEvent(muleEvent);

        MuleEvent resultEvent = receiverMessageProcessorChain.process(muleEvent);

        return resultEvent != null ? resultEvent.getMessage() : null;

    }

    protected MuleEvent createMuleEvent(MuleMessage message, boolean synchronous, OutputStream outputStream)
        throws MuleException
    {
        ResponseOutputStream ros = null;
        if (outputStream != null)
        {
            if (outputStream instanceof ResponseOutputStream)
            {
                ros = (ResponseOutputStream) outputStream;
            }
            else
            {
                ros = new ResponseOutputStream(outputStream);
            }
        }
        MuleSession session = connector.getSessionHandler().retrieveSessionInfoFromMessage(message);
        if (session != null)
        {
            session.setService(service);
        }
        else
        {
            session = new DefaultMuleSession(service, connector.getMuleContext());
        }
        return new DefaultMuleEvent(message, endpoint, session, synchronous, ros);
    }

    public EndpointURI getEndpointURI()
    {
        return endpointUri;
    }

    @Override
    public String getConnectionDescription()
    {
        return endpoint.getEndpointURI().toString();
    }

    protected String getConnectEventId()
    {
        return connector.getName() + ".receiver (" + endpoint.getEndpointURI() + ")";
    }

    // TODO MULE-4871 Receiver key should not be mutable
    public void setReceiverKey(String receiverKey)
    {
        this.receiverKey = receiverKey;
    }

    public String getReceiverKey()
    {
        return receiverKey;
    }

    @Override
    public InboundEndpoint getEndpoint()
    {
        return (InboundEndpoint) super.getEndpoint();
    }

    // TODO MULE-4871 Endpoint should not be mutable
    public void setEndpoint(InboundEndpoint endpoint)
    {
        super.setEndpoint(endpoint);
    }

    @Override
    protected WorkManager getWorkManager()
    {
        try
        {
            return connector.getReceiverWorkManager("receiver");
        }
        catch (MuleException e)
        {
            logger.error(e);
            return null;
        }
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer(80);
        sb.append(ClassUtils.getSimpleName(this.getClass()));
        sb.append("{this=").append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(", receiverKey=").append(receiverKey);
        sb.append(", endpoint=").append(endpoint.getEndpointURI());
        sb.append('}');
        return sb.toString();
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.component;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.component.Component;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.LifecycleTransitionResult;
import org.mule.api.service.Service;
import org.mule.api.transport.ReplyToHandler;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.MessageFactory;
import org.mule.management.stats.ComponentStatistics;
import org.mule.transport.AbstractConnector;

import javax.resource.spi.work.Work;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract {@link Component} to be used by all {@link Component} implementations.
 */
public abstract class AbstractComponent implements Component
{

    /** logger used by this class */
    protected final Log logger = LogFactory.getLog(this.getClass());

    protected Service service;

    protected ComponentStatistics statistics = null;

    public AbstractComponent()
    {
        statistics = new ComponentStatistics();
    }

    public Object onCall(MuleEvent event) throws MuleException
    {
        if (logger.isTraceEnabled())
        {
            logger.trace(this.getClass().getName() + ": sync call for Mule Component " + service.getName());
        }
        if (!(event.getEndpoint() instanceof InboundEndpoint))
        {
            throw new IllegalStateException(
                "Unable to process outbound event, components only process incoming events.");
        }

        return doOnCall(event);
    }

    public void onEvent(MuleEvent event)
    {
        if (logger.isTraceEnabled())
        {
            logger.trace(this.getClass().getName() + ": async call for Mule Component " + service.getName());
        }
        if (!(event.getEndpoint() instanceof InboundEndpoint))
        {
            throw new IllegalStateException(
                "Unable to process outbound event, components only process incoming events.");
        }
        doOnEvent(event);
    }

    protected abstract Object doOnCall(MuleEvent event);

    protected abstract void doOnEvent(MuleEvent event);

    /**
     * When an exception occurs this method can be called to invoke the configured
     * UMOExceptionStrategy on the UMO
     * 
     * @param exception If the UMOExceptionStrategy implementation fails
     */
    public void handleException(Exception exception)
    {
        service.getExceptionListener().exceptionThrown(exception);
    }

    public String toString()
    {
        return "proxy for: " + service.toString();
    }

    protected ReplyToHandler getReplyToHandler(MuleMessage message, InboundEndpoint endpoint)
    {
        Object replyTo = message.getReplyTo();
        ReplyToHandler replyToHandler = null;
        if (replyTo != null)
        {
            replyToHandler = ((AbstractConnector) endpoint.getConnector()).getReplyToHandler();
            // Use the response transformer for the event if one is set
            if (endpoint.getResponseTransformers() != null)
            {
                replyToHandler.setTransformers(endpoint.getResponseTransformers());
            }
        }
        return replyToHandler;
    }

    public void release()
    {
        // nothing to do
    }

    public ComponentStatistics getStatistics()
    {
        return statistics;
    }

    public Work getWorker(MuleEvent event)
    {
        return new ComponentWorker(event);
    }

    public void setService(Service service)
    {
        this.service = service;
    }

    public Service getService()
    {
        return service;
    }

    public LifecycleTransitionResult initialise() throws InitialisationException
    {
        // Default implementation. Implementations should override and call
        // ensuring they call super.initialise()
        if (service == null)
        {
            throw new InitialisationException(
                MessageFactory.createStaticMessage("Component has not been initialized properly, no service."), this);
        }
        return LifecycleTransitionResult.OK;
    }

    public void dispose()
    {
        // Default implementation. Implementations should override if needed.
        try
        {
            stop();
        }
        catch (MuleException e)
        {
            logger.error(CoreMessages.failedToStop(toString()));
        }
    }

    public LifecycleTransitionResult stop() throws MuleException
    {
        // Default implementation. Implementations should override if needed.
        return LifecycleTransitionResult.OK;
    }

    public LifecycleTransitionResult start() throws MuleException
    {
        // Default implementation. Implementations should override if needed.
        return LifecycleTransitionResult.OK;
    }

    private class ComponentWorker implements Work
    {
        private MuleEvent event;

        public ComponentWorker(MuleEvent event)
        {
            this.event = event;
        }

        public void run()
        {
            onEvent(event);
        }

        public void release()
        {
            // no-op
        }
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.inbound.processor;

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleSession;
import org.mule.RequestContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.processor.AbstractInterceptingMessageProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InboundFilterMessageProcessor extends AbstractInterceptingMessageProcessor
{
    protected transient Log logger = LogFactory.getLog(getClass());

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        MuleMessage message = event.getMessage();
        InboundEndpoint endpoint = (InboundEndpoint) event.getEndpoint();

        // Apply the endpoint filter if one is configured
        if (endpoint.getFilter() != null)
        {
            if (!endpoint.getFilter().accept(message))
            {
                // TODO RM* This ain't pretty, we don't yet have an event context
                // since the message hasn't gone to the
                // message listener yet. So we need to create a new context so
                // that EventAwareTransformers can be applied
                // to response messages where the filter denied the message
                // Maybe the filter should be checked in the MessageListener...
                message = handleUnacceptedFilter(message, endpoint);

                MuleEvent result = new DefaultMuleEvent(message, endpoint,
                    new DefaultMuleSession(endpoint.getMuleContext()), event.isSynchronous());
                RequestContext.setEvent(result);
                return result;
            }
        }
        return next.process(event);
    }

    protected MuleMessage handleUnacceptedFilter(MuleMessage message, InboundEndpoint endpoint)
    {
        String messageId;
        messageId = message.getUniqueId();

        if (logger.isDebugEnabled())
        {
            logger.debug("Message " + messageId + " failed to pass filter on endpoint: " + endpoint
                         + ". Message is being ignored");
        }

        return message;
    }

}

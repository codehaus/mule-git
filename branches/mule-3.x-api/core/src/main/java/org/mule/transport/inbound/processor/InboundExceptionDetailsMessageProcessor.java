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

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.transport.Connector;
import org.mule.config.ExceptionHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InboundExceptionDetailsMessageProcessor implements MessageProcessor
{

    private static final Log logger = LogFactory.getLog(InboundExceptionDetailsMessageProcessor.class);

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        if (event != null)
        {
            InboundEndpoint endpoint = (InboundEndpoint) event.getEndpoint();
            MuleMessage resultMessage = event.getMessage();
            if (resultMessage != null)
            {
                if (resultMessage.getExceptionPayload() != null)
                {
                    setExceptionDetails(resultMessage, endpoint.getConnector(),
                        resultMessage.getExceptionPayload().getException());
                }
            }
        }
        return event;
    }

    /**
     * This method is used to set any additional aand possibly transport specific
     * information on the return message where it has an exception payload.
     * 
     * @param message
     * @param exception
     */
    protected void setExceptionDetails(MuleMessage message, Connector connector, Throwable exception)
    {
        String propName = ExceptionHelper.getErrorCodePropertyName(connector.getProtocol());
        // If we dont find a error code property we can assume there are not
        // error code mappings for this connector
        if (propName != null)
        {
            String code = ExceptionHelper.getErrorMapping(connector.getProtocol(), exception.getClass());
            if (logger.isDebugEnabled())
            {
                logger.debug("Setting error code for: " + connector.getProtocol() + ", " + propName + "="
                             + code);
            }
            message.setProperty(propName, code);
        }
    }

}

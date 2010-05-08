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

import org.mule.RequestContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.security.SecurityException;
import org.mule.api.service.Service;
import org.mule.api.transport.MessageReceiver;
import org.mule.context.notification.SecurityNotification;
import org.mule.processor.AbstractInterceptingMessageProcessor;
import org.mule.transport.AbstractConnector;

public class InboundSecurityFilterMessageProcessor extends AbstractInterceptingMessageProcessor
{
    public MuleEvent process(MuleEvent event) throws MuleException
    {
        InboundEndpoint endpoint = (InboundEndpoint) event.getEndpoint();
        AbstractConnector connector = (AbstractConnector) endpoint.getConnector();
        Service service = event.getService();
        MessageReceiver receiver = ((AbstractConnector) connector).getReceiver(service, endpoint);
        MuleEvent resultEvent = null;

        // Apply Security filter if one is set
        boolean authorised = false;
        if (endpoint.getSecurityFilter() != null)
        {
            try
            {
                endpoint.getSecurityFilter().authenticate(event);
                authorised = true;
            }
            catch (SecurityException e)
            {
                logger.warn("Request was made but was not authenticated: " + e.getMessage(), e);
                ((AbstractConnector) endpoint.getConnector()).fireNotification(new SecurityNotification(e,
                    SecurityNotification.SECURITY_AUTHENTICATION_FAILED));
                connector.handleException(e, receiver);
                resultEvent = RequestContext.getEvent();
                resultEvent.getMessage().setPayload(e.getLocalizedMessage());
            }
        }
        else
        {
            authorised = true;
        }

        if (authorised)
        {
            resultEvent = next.process(event);
        }
        return resultEvent;
    }
}

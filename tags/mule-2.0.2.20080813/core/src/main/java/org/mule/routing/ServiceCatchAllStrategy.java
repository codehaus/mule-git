/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing;

import org.mule.DefaultMuleEvent;
import org.mule.RequestContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.routing.RoutingException;
import org.mule.api.routing.ServiceRoutingException;

/**
 * <code>ServiceCatchAllStrategy</code> is used to catch any events and forward the
 * events to the service as is.
 */
public class ServiceCatchAllStrategy extends AbstractCatchAllStrategy
{
    public void setEndpoint(OutboundEndpoint endpoint)
    {
        throw new UnsupportedOperationException("The endpoint cannot be set on this catch all");
    }

    public OutboundEndpoint getEndpoint()
    {
        return null;
    }

    public synchronized MuleMessage catchMessage(MuleMessage message, MuleSession session, boolean synchronous)
        throws RoutingException
    {
        MuleEvent event = RequestContext.getEvent();
        logger.debug("Catch all strategy handling event: " + event);
        try
        {
            logger.info("MuleEvent being routed from catch all strategy for endpoint: " + event.getEndpoint());
            event = new DefaultMuleEvent(message, event.getEndpoint(), session.getService(), event);
            if (synchronous)
            {
                statistics.incrementRoutedMessage(event.getEndpoint());
                return session.getService().sendEvent(event);
            }
            else
            {
                statistics.incrementRoutedMessage(event.getEndpoint());
                session.getService().dispatchEvent(event);
                return null;
            }
        }
        catch (MuleException e)
        {
            throw new ServiceRoutingException(event.getMessage(), event.getEndpoint(),
                session.getService(), e);
        }
    }
}

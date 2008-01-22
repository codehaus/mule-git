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

import org.mule.MuleEvent;
import org.mule.api.Event;
import org.mule.api.MuleMessage;
import org.mule.api.Session;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.routing.ComponentRoutingException;
import org.mule.api.routing.RoutingException;
import org.mule.config.i18n.CoreMessages;

/**
 * <code>ForwardingCatchAllStrategy</code> acts as a catch and forward router for
 * any events not caught by the router this strategy is associated with. Users can
 * assign an endpoint to this strategy to forward all events to. This can be used as
 * a dead letter/error queue.
 */

public class ForwardingCatchAllStrategy extends AbstractCatchAllStrategy
{
    private boolean sendTransformed = false;

    public MuleMessage catchMessage(MuleMessage message, Session session, boolean synchronous)
        throws RoutingException
    {
        if (getEndpoint() == null)
        {
            throw new ComponentRoutingException(CoreMessages.noCatchAllEndpointSet(), message,
                getEndpoint(), session.getComponent());
        }
        try
        {
            ImmutableEndpoint endpoint = getEndpoint();
            if (sendTransformed && endpoint.getTransformers() != null)
            {
                message.applyTransformers(endpoint.getTransformers());
            }

            Event newEvent = new MuleEvent(message, endpoint, session, synchronous);

            if (synchronous)
            {
                MuleMessage result = endpoint.send(newEvent);
                if (statistics != null)
                {
                    statistics.incrementRoutedMessage(getEndpoint());
                }
                return result;
            }
            else
            {
                endpoint.dispatch(newEvent);
                if (statistics != null)
                {
                    statistics.incrementRoutedMessage(getEndpoint());
                }
                return null;
            }
        }
        catch (Exception e)
        {
            throw new RoutingException(message, getEndpoint(), e);

        }
    }

    public boolean isSendTransformed()
    {
        return sendTransformed;
    }

    public void setSendTransformed(boolean sendTransformed)
    {
        this.sendTransformed = sendTransformed;
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.usecases.routing;

import org.mule.api.Event;
import org.mule.api.MuleMessage;
import org.mule.api.Session;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.routing.ComponentRoutingException;
import org.mule.api.routing.RoutingException;
import org.mule.impl.MuleEvent;
import org.mule.impl.DefaultMuleMessage;
import org.mule.impl.RequestContext;
import org.mule.impl.config.i18n.CoreMessages;
import org.mule.impl.routing.AbstractCatchAllStrategy;

public class InboundTransformingForwardingCatchAllStrategy extends AbstractCatchAllStrategy
{
    public MuleMessage catchMessage(MuleMessage message, Session session, boolean synchronous)
        throws RoutingException
    {
        ImmutableEndpoint endpoint = this.getEndpoint();

        if (endpoint == null)
        {
            throw new ComponentRoutingException(
                CoreMessages.noCatchAllEndpointSet(), message, this.getEndpoint(), session.getComponent());
        }
        try
        {
            message = new DefaultMuleMessage(RequestContext.getEventContext().transformMessage(), message);
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
            throw new RoutingException(message, endpoint, e);
        }
    }
}

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

import org.mule.api.UMOEvent;
import org.mule.api.UMOMessage;
import org.mule.api.UMOSession;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.routing.ComponentRoutingException;
import org.mule.api.routing.RoutingException;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.RequestContext;
import org.mule.impl.routing.AbstractCatchAllStrategy;
import org.mule.imple.config.i18n.CoreMessages;

public class InboundTransformingForwardingCatchAllStrategy extends AbstractCatchAllStrategy
{
    public UMOMessage catchMessage(UMOMessage message, UMOSession session, boolean synchronous)
        throws RoutingException
    {
        UMOImmutableEndpoint endpoint = this.getEndpoint();

        if (endpoint == null)
        {
            throw new ComponentRoutingException(
                CoreMessages.noCatchAllEndpointSet(), message, this.getEndpoint(), session.getComponent());
        }
        try
        {
            message = new MuleMessage(RequestContext.getEventContext().transformMessage(), message);
            UMOEvent newEvent = new MuleEvent(message, endpoint, session, synchronous);

            if (synchronous)
            {
                UMOMessage result = endpoint.send(newEvent);
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

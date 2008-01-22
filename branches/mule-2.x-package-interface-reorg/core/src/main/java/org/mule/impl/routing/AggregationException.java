/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.routing;

import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.routing.RoutingException;
import org.mule.impl.DefaultMuleMessage;
import org.mule.impl.config.i18n.Message;
import org.mule.impl.routing.inbound.EventGroup;
import org.mule.impl.transport.NullPayload;

/**
 * TODO document
 *
 */
public class AggregationException extends RoutingException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 1276049971165761454L;

    private EventGroup eventGroup = null;

    public AggregationException(EventGroup eventGroup, ImmutableEndpoint endpoint)
    {
        super(new DefaultMuleMessage(NullPayload.getInstance()), endpoint);
        this.eventGroup = eventGroup;
    }

    public AggregationException(EventGroup eventGroup, ImmutableEndpoint endpoint, Throwable cause)
    {
        super(new DefaultMuleMessage(NullPayload.getInstance()), endpoint, cause);
        this.eventGroup = eventGroup;
    }

    public AggregationException(Message message, EventGroup eventGroup, ImmutableEndpoint endpoint)
    {
        super(message, new DefaultMuleMessage(NullPayload.getInstance()), endpoint);
        this.eventGroup = eventGroup;
    }

    public AggregationException(Message message,
                                EventGroup eventGroup,
                                ImmutableEndpoint endpoint,
                                Throwable cause)
    {
        super(message, new DefaultMuleMessage(NullPayload.getInstance()), endpoint, cause);
        this.eventGroup = eventGroup;
    }

    public EventGroup getEventGroup()
    {
        return eventGroup;
    }
}

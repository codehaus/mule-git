/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.routing.inbound;

import org.mule.api.AbstractMuleException;
import org.mule.api.Event;
import org.mule.api.MessagingException;
import org.mule.api.Session;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.DispatchException;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleSession;
import org.mule.impl.NullSessionHandler;
import org.mule.impl.RequestContext;

/**
 * An inbound router that can forward every message to another destination as defined
 * in the "endpoint" property. This can be a logical destination of a URI. <p/> A
 * filter can be applied to this router so that only events matching a criteria will
 * be tapped.
 */
public class WireTap extends SelectiveConsumer
{
    private volatile ImmutableEndpoint tap;

    public boolean isMatch(Event event) throws MessagingException
    {
        if (tap != null)
        {
            return super.isMatch(event);
        }
        else
        {
            logger.warn("No endpoint identifier is set on this wire tap");
            return false;
        }
    }

    public Event[] process(Event event) throws MessagingException
    {
        RequestContext.setEvent(null);
        try
        {
            //We have to create a new session for this dispatch, since the session may get altered
            //using this call, changing the behaviour of the request
            Session session = new MuleSession(event.getMessage(), new NullSessionHandler());
            tap.dispatch(new MuleEvent(event.getMessage(), tap, session, false));
        }
        catch (MessagingException e)
        {
            throw e;
        }
        catch (AbstractMuleException e)
        {
            throw new DispatchException(event.getMessage(), tap, e);
        }
        return super.process(event);
    }

    public ImmutableEndpoint getEndpoint()
    {
        return tap;
    }

    public void setEndpoint(ImmutableEndpoint endpoint) throws AbstractMuleException
    {
        this.tap = endpoint;
    }
}

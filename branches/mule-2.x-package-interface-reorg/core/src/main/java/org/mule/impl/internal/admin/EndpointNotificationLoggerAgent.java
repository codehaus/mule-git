/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.internal.admin;

import org.mule.api.Event;
import org.mule.api.MuleMessage;
import org.mule.api.Session;
import org.mule.api.context.ServerNotification;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.impl.DefaultMuleMessage;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleSession;
import org.mule.impl.NullSessionHandler;
import org.mule.impl.config.i18n.CoreMessages;
import org.mule.impl.transport.NullPayload;

import java.util.Map;

/**
 * <code>EndpointAbstractEventLoggerAgent</code> will forward server notifications
 * to a configurered endpoint uri.
 */
public class EndpointNotificationLoggerAgent extends AbstractNotificationLoggerAgent
{

    private String endpointAddress;
    private ImmutableEndpoint logEndpoint = null;
    private Session session;


    public EndpointNotificationLoggerAgent()
    {
        super("Endpoint Logger Agent");
    }

    protected void doInitialise() throws InitialisationException
    {
        // first see if we're logging notifications to an endpoint
        try
        {
            if (endpointAddress != null)
            {
                logEndpoint = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(endpointAddress);
            }
            else
            {
                throw new InitialisationException(
                    CoreMessages.propertiesNotSet("endpointAddress"), this);
            }
            // Create a session for sending notifications
            session = new MuleSession(new DefaultMuleMessage(NullPayload.getInstance(), (Map) null), new NullSessionHandler());
        }
        catch (Exception e)
        {
            throw new InitialisationException(e, this);
        }
    }

    protected void logEvent(ServerNotification e)
    {
        if (logEndpoint != null)
        {
            try
            {
                MuleMessage msg = new DefaultMuleMessage(e.toString(), (Map) null);
                Event event = new MuleEvent(msg, logEndpoint, session, false);
                logEndpoint.dispatch(event);
            }
            catch (Exception e1)
            {
                // TODO MULE-863: If this is an error, do something better than this
                logger.error("Failed to dispatch event: " + e.toString() + " over endpoint: " + logEndpoint
                             + ". Error is: " + e1.getMessage(), e1);
            }
        }
    }

    /**
     * Should be a 1 line description of the agent
     * 
     * @return
     */
    public String getDescription()
    {
        StringBuffer buf = new StringBuffer();
        buf.append(getName()).append(": ");
        if (endpointAddress != null)
        {
            buf.append("Forwarding notifications to: " + endpointAddress);
        }
        return buf.toString();
    }

    public String getEndpointAddress()
    {
        return endpointAddress;
    }

    public void setEndpointAddress(String endpointAddress)
    {
        this.endpointAddress = endpointAddress;
    }
}

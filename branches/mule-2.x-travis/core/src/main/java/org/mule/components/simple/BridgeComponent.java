/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.components.simple;

import org.mule.config.ConfigurationException;
import org.mule.impl.UMOComponentAware;
import org.mule.routing.inbound.ForwardingConsumer;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.lifecycle.Callable;
import org.mule.umo.routing.UMOInboundRouter;

import java.util.Iterator;

/**
 * The BridgeComponent is a standard Mule component that enables a bridge between an inbound and outbound endpoints.
 * Transformers can be used on the endpoints to convert the data being received in order to 'bridge' from one
 * endpoint transport to another.  When the BridgeComponent is used, it configures itself so that it will
 * not actually be invoked, instead it tells Mule to bypass invocation of the component, which has a slight performance
 * improvement. Note that because the component is never actually invoked any interceptors configured on the component
 * will not be invoked either.
 */
public class BridgeComponent implements UMOComponentAware, Callable
{
    public void setComponent(UMOComponent component) throws ConfigurationException
    {
        // Adding a forwarding consumer will cause the inbound routing to
        // directly invoke the outbound router, bypassing the component

        // first check there isn't one already registered
        boolean registered = false;
        for (Iterator iterator = component.getInboundRouter().getRouters().iterator(); iterator.hasNext();)
        {
            UMOInboundRouter router = (UMOInboundRouter) iterator.next();
            if (router instanceof ForwardingConsumer)
            {
                registered = true;
            }

        }
        if (!registered)
        {
            component.getInboundRouter().addRouter(new ForwardingConsumer());
        }
        // Make sure if other routers on the inbound router, they are honoured
        component.getInboundRouter().setMatchAll(true);
    }

    public Object onCall(UMOEventContext context) throws Exception
    {
        throw new UnsupportedOperationException(
            "A bridge should not ever receive an event, instead the event should be directly dispatched from the inbound endpoint to the outbound router. Component is: "
                            + context.getComponent().getName());
    }
}

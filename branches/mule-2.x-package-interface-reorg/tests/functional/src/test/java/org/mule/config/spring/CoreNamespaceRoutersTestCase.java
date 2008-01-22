/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.spring;

import org.mule.api.UMOComponent;
import org.mule.api.routing.UMORouter;
import org.mule.impl.routing.inbound.ForwardingConsumer;
import org.mule.impl.routing.inbound.IdempotentSecureHashReceiver;
import org.mule.impl.routing.inbound.InboundPassThroughRouter;
import org.mule.impl.routing.inbound.SelectiveConsumer;
import org.mule.tck.FunctionalTestCase;

import java.util.List;

public class CoreNamespaceRoutersTestCase extends FunctionalTestCase
{
    public String getConfigResources()
    {
        return "core-namespace-routers.xml";
    }

    public void testForwardingRouter() throws Exception
    {
        UMORouter r = lookupInboundRouterFromService("ForwardingRouter");
        assertTrue(r instanceof ForwardingConsumer);
    }

    public void testIdempotentSecureHashReceiverRouter() throws Exception
    {
        UMORouter r = lookupInboundRouterFromService("IdempotentSecureHashReceiverRouter");
        assertTrue(r instanceof IdempotentSecureHashReceiver);
    }

    public void testInboundPassThroughRouter() throws Exception
    {
        UMORouter r = lookupInboundRouterFromService("InboundPassThroughRouter");
        assertTrue(r instanceof InboundPassThroughRouter);
    }

    public void testSelectiveConsumerRouter() throws Exception
    {
        UMORouter r = lookupInboundRouterFromService("SelectiveConsumerRouter");
        assertTrue(r instanceof SelectiveConsumer);
        assertFalse(((SelectiveConsumer) r).isTransformFirst());
    }

    protected UMORouter lookupInboundRouterFromService(String serviceName) throws Exception
    {
        UMOComponent c = muleContext.getRegistry().lookupComponent(serviceName);
        assertNotNull(c);
        List routers = c.getInboundRouter().getRouters();
        assertEquals(1, routers.size());
        assertTrue(routers.get(0) instanceof UMORouter);
        return (UMORouter) routers.get(0);
    }
}

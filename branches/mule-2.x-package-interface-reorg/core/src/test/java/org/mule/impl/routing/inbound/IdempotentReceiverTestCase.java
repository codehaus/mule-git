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

import org.mule.api.UMOComponent;
import org.mule.api.UMOEvent;
import org.mule.api.UMOMessage;
import org.mule.api.UMOSession;
import org.mule.api.endpoint.UMOEndpoint;
import org.mule.api.routing.UMOInboundRouterCollection;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.routing.LoggingCatchAllStrategy;
import org.mule.impl.routing.inbound.IdempotentReceiver;
import org.mule.impl.routing.inbound.InboundRouterCollection;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.tck.testmodels.fruit.Apple;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

public class IdempotentReceiverTestCase extends AbstractMuleTestCase
{

    public void testIdempotentReceiver() throws Exception
    {
        IdempotentReceiver router = new IdempotentReceiver();

        Mock session = MuleTestUtils.getMockSession();
        UMOComponent testComponent = getTestComponent("test", Apple.class);

        UMOInboundRouterCollection messageRouter = new InboundRouterCollection();

        messageRouter.addRouter(router);
        messageRouter.setCatchAllStrategy(new LoggingCatchAllStrategy());

        UMOMessage message = new MuleMessage("test event");

        UMOEndpoint endpoint = getTestEndpoint("Test1Provider", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        UMOEvent event = new MuleEvent(message, endpoint, (UMOSession) session.proxy(), false);
        // called by idempotent receiver as this is the fist event it will try
        // and initialize the id store
        session.expectAndReturn("getComponent", testComponent);

        assertTrue(router.isMatch(event));

        session.expect("dispatchEvent", C.eq(event));
        // called by Inbound message router
        session.expectAndReturn("getComponent", testComponent);

        // called by idempotent receiver
        session.expectAndReturn("getComponent", testComponent);
        messageRouter.route(event);

        session.verify();
        message = new MuleMessage("test event");
        event = new MuleEvent(message, endpoint, (UMOSession) session.proxy(), true);

        session.expectAndReturn("sendEvent", C.eq(event), message);
        // called by idempotent receiver
        session.expectAndReturn("getComponent", testComponent);
        // called by Inbound message router
        session.expectAndReturn("getComponent", testComponent);
        UMOMessage result = messageRouter.route(event);
        assertNotNull(result);
        assertEquals(message, result);
        session.verify();

        session.expect("toString");
        // called by idempotent receiver
        session.expectAndReturn("getComponent", testComponent);

        event = new MuleEvent(message, endpoint, (UMOSession) session.proxy(), false);
        // we've already received this message
        assertTrue(!router.isMatch(event));

        messageRouter.route(event);
        session.verify();
    }

}

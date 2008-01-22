/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule;

import org.mule.api.MuleException;
import org.mule.api.UMOException;
import org.mule.api.context.ManagerException;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.routing.RoutingException;
import org.mule.imple.config.i18n.MessageFactory;
import org.mule.tck.AbstractMuleTestCase;

public class ExceptionsTestCase extends AbstractMuleTestCase
{

    public void testExceptionChaining()
    {
        String rootMsg = "Root Test Exception Message";
        String msg = "Test Exception Message";

        Exception e = new ManagerException(MessageFactory.createStaticMessage(msg), new MuleException(
            MessageFactory.createStaticMessage(rootMsg)));

        assertEquals(rootMsg, e.getCause().getMessage());
        assertEquals(msg, e.getMessage());
        assertEquals(e.getClass().getName() + ": " + msg, e.toString());
    }

    public final void testRoutingExceptionNullUMOMessageNullUMOImmutableEndpoint() throws UMOException
    {
        RoutingException rex = new RoutingException(null, null);
        assertNotNull(rex);
    }

    public final void testRoutingExceptionNullUMOMessageValidUMOImmutableEndpoint() throws UMOException
    {
        UMOImmutableEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint("test://outbound");
        assertNotNull(endpoint);

        RoutingException rex = new RoutingException(null, endpoint);
        assertNotNull(rex);
        assertSame(endpoint, rex.getEndpoint());
    }

}

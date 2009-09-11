/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.routing.outbound;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;

public class ExceptionBasedRouterTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "org/mule/test/integration/routing/outbound/exception-based-router.xml";
    }

    public void testStaticEndpoints() throws Exception
    {
        MuleClient client = new MuleClient();
        
        MuleMessage reply = client.send("vm://in1", "request", null);
        assertNotNull(reply);
        assertEquals("success", reply.getPayload());
    }

    public void testDynamicEndpoints() throws Exception
    {
        MuleClient client = new MuleClient();
        
        Map props = new HashMap();
        props.put("recipients", "service1,service2,service3");
        MuleMessage reply = client.send("vm://in2", "request", props);
        assertNotNull(reply);
        assertEquals("success", reply.getPayload());
    }
}



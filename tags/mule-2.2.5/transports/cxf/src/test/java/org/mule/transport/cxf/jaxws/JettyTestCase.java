/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.jaxws;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.BusFactory;
import org.apache.hello_world_soap_http.GreeterImpl;

public class JettyTestCase extends FunctionalTestCase
{

    @Override
    protected void suitePreSetUp() throws Exception
    {
        BusFactory.setDefaultBus(null);
        super.suitePreSetUp();
    }

    private GreeterImpl getGreeter() throws Exception
    {
        Object instance = getComponent("greeterService");
        
        return (GreeterImpl) instance;
    }

    public void testClientWithMuleClient() throws Exception
    {
        MuleClient client = new MuleClient();
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("operation", "greetMe");
        MuleMessage result = client.send("clientEndpoint", "Dan", props);
        assertEquals("Hello Dan", result.getPayload());
        
        GreeterImpl impl = getGreeter();
        
        Thread.sleep(3000);
        
        assertEquals(1, impl.getInvocationCount());
    }
    
    protected String getConfigResources()
    {
        return "jetty-conf.xml";
    }

}
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.transport.cxf;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.Properties;

public class WebServiceWrapperWithCxfTestCase extends FunctionalTestCase
{
    private String testString = "test";
    
    public void testWsCall() throws Exception
    {
        MuleClient client = new MuleClient();
        MuleMessage result = client.send("vm://testin", new DefaultMuleMessage(testString));
        assertNotNull(result.getPayload());
        assertEquals("Payload", testString, result.getPayloadAsString());
    }
    
    public void testWsCallWithUrlFromMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();
        props.setProperty("ws.service.url", "cxf:http://localhost:65081/services/TestUMO?method=onReceive");
        MuleMessage result = client.send("vm://testin2", testString, props);
        assertNotNull(result.getPayload());
        assertEquals("Payload", testString, result.getPayloadAsString());
    }
    
    protected String getConfigResources()
    {
        return "mule-ws-wrapper-config.xml";
    }
}

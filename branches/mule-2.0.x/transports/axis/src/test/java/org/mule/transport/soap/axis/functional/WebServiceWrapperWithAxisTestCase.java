/*
 * $Id$
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.transport.soap.axis.functional;


import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;


import java.util.Properties;

public class WebServiceWrapperWithAxisTestCase extends FunctionalTestCase
{
    private String testString = "test";
    
    public void testWsCall() throws Exception
    {
        MuleClient client = new MuleClient();
        MuleMessage result = client.send("vm://testin?connector=VMConnector", new DefaultMuleMessage(testString));
        assertNotNull(result.getPayload());
        assertEquals("Payload", "Received: "+ testString, result.getPayloadAsString());
    }
    
    public void testWsCallWithUrlFromMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();
        props.setProperty("ws.service.url", "axis:http://localhost:65081/services/TestUMO?method=receive");
        MuleMessage result = client.send("vm://testin2?connector=VMConnector", testString, props);
        assertNotNull(result.getPayload());
        assertEquals("Payload", "Received: "+ testString, result.getPayloadAsString());
    }
    
    public void testWsCallWithComplexParameters() throws Exception
    {
        MuleClient client = new MuleClient();
        client.dispatch("vm://queue.in?connector=VMQueue", new Object[]{new Long(3), new Long(3)},null);
        MuleMessage result = client.request("vm://queue.out?connector=VMQueue", 500000);
        assertNotNull(result.getPayload());
        assertTrue(result.getPayload() instanceof Long);
        assertEquals("Payload", 6, ((Long)result.getPayload()).intValue());
    }
    
    protected String getConfigResources()
    {
        return "mule-ws-wrapper-config.xml";
    }
}

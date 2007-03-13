/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.xfire;

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.util.IOUtils;

import org.custommonkey.xmlunit.XMLUnit;

public class XFireBasicTestCase extends FunctionalTestCase
{
    private String echoWsdl;

    protected void doPostFunctionalSetUp() throws Exception
    {
        super.doPostFunctionalSetUp();
        echoWsdl = IOUtils.getResourceAsString("xfire-echo-service.wsdl", getClass());
        XMLUnit.setIgnoreWhitespace(true);
    }

    public void testEchoService() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage result = client.send("xfire:http://localhost:10081/services/echoService?method=echo", "Hello!", null);
        assertEquals("Hello!", result.getPayload());
    }

    public void testEchoWsdl() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage result = client.receive("http://localhost:10081/services/echoService?wsdl", 5000);
        assertNotNull(result.getPayload());
        XMLUnit.compareXML(echoWsdl, result.getPayload().toString());
    }

    public void testEchoWsdl2() throws Exception
    {
        MuleClient client = new MuleClient();
    	UMOMessage result = client.send("xfire:http://127.0.0.1:10081/services/echoService?wsdl&method=echo", "Hello!", null);
        System.out.println(result.toString());
        System.out.println(result.getPayload());
    }

    protected String getConfigResources()
    {
        return "xfire-basic-conf.xml";
    }

}


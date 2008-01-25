/*
 * $$Id$$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.components.rest;

import org.mule.extras.client.MuleClient;
import org.mule.providers.NullPayload;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

public class RestErrorExpressionTestCase extends FunctionalTestCase
{
    protected static String TEST_MESSAGE = "Test Http Request";

    public static class EchoComponent
    {	
        public String Echo(String echo) throws Exception
        {	
            return echo;
        }
    }

    protected String getConfigResources()
    {
        return "http-rest-error-expression-functional-test.xml";
    }

    public void testErrorExpressionOnRegexFilterFail() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage result = client.send("restServiceEndpoint", TEST_MESSAGE, null);
        assertTrue(result.getPayload() instanceof NullPayload);
    }

    public void testErrorExpressionOnRegexFilterPass() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage result = client.send("restServiceEndpoint2", TEST_MESSAGE, null);
        assertEquals("echo=" + TEST_MESSAGE,result.getPayloadAsString());
    }
}

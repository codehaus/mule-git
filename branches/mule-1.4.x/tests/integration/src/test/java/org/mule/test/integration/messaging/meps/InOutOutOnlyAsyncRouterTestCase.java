/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.integration.messaging.meps;

import org.mule.tck.FunctionalTestCase;
import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;

// START SNIPPET: full-class
public class InOutOutOnlyAsyncRouterTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "org/mule/test/integration/messaging/meps/pattern_In-Out_Out-Only-Async-Router.xml";
    }

    public void testExchange() throws Exception
    {
        MuleClient client = new MuleClient();

        UMOMessage result = client.send("inboundEndpoint", "some data", null);
        assertNotNull(result);
        //The FTC is not so functional in Mule 1.x so we get this long result
        assertEquals("some data Received In-Out_Out-Only-Async-Service Received Mock-External-Service", result.getPayloadAsString());
    }
}
// END SNIPPET: full-class

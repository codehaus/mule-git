/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp.issues;

import org.mule.tck.FunctionalTestCase;
import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;

import java.util.Map;
import java.util.HashMap;

public class KeepSendSocketOpenMule1491TestCase  extends FunctionalTestCase 
{

    protected static String TEST_MESSAGE = "Test TCP Request";

    public KeepSendSocketOpenMule1491TestCase()
    {
        setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "tcp-keep-send-socket-open.xml";
    }

    public void testSend() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();
        UMOMessage result = client.send("clientEndpoint", TEST_MESSAGE, props);
        assertEquals(TEST_MESSAGE + " Received", result.getPayloadAsString());
        // try an extra message in case it's a problem on repeat
        result = client.send("clientEndpoint", TEST_MESSAGE, props);
        assertEquals(TEST_MESSAGE + " Received", result.getPayloadAsString());
    }

}

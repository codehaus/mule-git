/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.usecases.sync;

import org.mule.api.MuleMessage;
import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class TcpJmsResponseBridgeTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "org/mule/test/usecases/sync/tcp-jms-response-bridge.xml";
    }

    public void testSyncResponse() throws Exception
    {
        MuleClient client = new MuleClient();
        MuleMessage message = client.send("tcp://localhost:4444", "request", null);
        assertNotNull(message);
        assertEquals("Received: request", message.getPayloadAsString());
    }
}

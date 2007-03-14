/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.util.StringUtils;

import java.util.Arrays;

public class TcpSyncTestCase extends FunctionalTestCase
{

    public TcpSyncTestCase()
    {
        setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "tcp-sync.xml";
    }

    public void testSendString() throws Exception
    {
        MuleClient client = new MuleClient();

        UMOMessage message = client.send("clientEndpoint", "data", null);
        assertNotNull(message);
        String response = message.getPayloadAsString();
        assertEquals("data", response);
    }

    public void testSyncResponseOfBufferSize() throws Exception
    {
        MuleClient client = new MuleClient();

        TcpConnector tcp = (TcpConnector)MuleManager.getInstance().lookupConnector("tcpConnector");
        tcp.setSendBufferSize(1024 * 16);
        byte[] data = StringUtils.repeat("0123456789", tcp.getSendBufferSize() / 10).getBytes();
        UMOMessage message = client.send("clientEndpoint", data, null);
        assertNotNull(message);
        byte[] response = message.getPayloadAsBytes();

        assertEquals(data.length, response.length);
        assertTrue(Arrays.equals(data, response));
    }

    public void testSyncResponseVeryBig() throws Exception
    {
        MuleClient client = new MuleClient();
        byte[] data = StringUtils.repeat("0123456789", 10000).getBytes();

        UMOMessage message = client.send("clientEndpoint", data, null);
        assertNotNull(message);
        byte[] response = message.getPayloadAsBytes();
        assertEquals(data.length, response.length);
        assertTrue(Arrays.equals(data, response));
    }

}

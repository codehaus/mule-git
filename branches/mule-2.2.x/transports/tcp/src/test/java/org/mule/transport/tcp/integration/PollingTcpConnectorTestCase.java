/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.tcp.integration;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Integration test for <code>PollingTcpConnector</code> 
 * 
 * @author esteban.robles
 */
public class PollingTcpConnectorTestCase extends FunctionalTestCase
{

    /**
     * Starts a TCP server and sends 2 messages through it when a client requests it.
     * The PollingTCPConnector acts as a TCP client polling data every second
     * We verify that the data is received in the outbound
     * 
     * @throws Exception
     */
    public void testPolling() throws Exception
    {
        MuleClient client = new MuleClient();
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(65466);

            sendAndTest(client, serverSocket, "hi");
            sendAndTest(client, serverSocket, "hi 3");
        }
        catch (IOException e)
        {
            System.out.println("Could not listen on port: 65466");
            System.exit(-1);
        }
        finally
        {
            try
            {
                if (serverSocket != null)
                {
                    serverSocket.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Utility method to send messages through the server and verify that the data 
     * is correctly received in the outbound
     */
    private void sendAndTest(MuleClient client, ServerSocket serverSocket, String stringMessage)
        throws IOException, MuleException, Exception
    {
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        out.print(stringMessage);
        out.close();
        clientSocket.close();

        MuleMessage message = client.request("vm://out?connector=queue", 10000);
        assertEquals(stringMessage, message.getPayloadAsString());
    }

    /* (non-Javadoc)
     * @see org.mule.tck.FunctionalTestCase#getConfigResources()
     */
    @Override
    protected String getConfigResources()
    {
        return "tcp-polling-config.xml";
    }
}
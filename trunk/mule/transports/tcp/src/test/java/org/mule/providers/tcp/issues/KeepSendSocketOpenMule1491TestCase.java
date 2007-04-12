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

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

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

    private void useServer(String endpoint, int port, int count) throws Exception
    {
        SimpleServerSocket server = new SimpleServerSocket(port);
        try
        {
            new Thread(server).start();
            MuleClient client = new MuleClient();
            client.send(endpoint, "Hello", null);
            client.send(endpoint, "world", null);
            assertEquals(count, server.getCount());
        }
        finally
        {
            server.close();
        }
    }

    public void testOpen() throws Exception
    {
        useServer("tcp://localhost:60197?connector=openConnector", 60197, 1);
    }

    public void testClose() throws Exception
    {
        useServer("tcp://localhost:60196?connector=openConnector", 60196, 1);
    }

    private class SimpleServerSocket implements Runnable
    {
        
        private ServerSocket server;
        AtomicInteger count = new AtomicInteger(0);

        public SimpleServerSocket(int port) throws Exception
        {
            server = new ServerSocket();
            logger.debug("starting server");
            server.bind(new InetSocketAddress("localhost", port));
        }

        public int getCount()
        {
            return count.get();
        }

        public void run()
        {
            try
            {
                while (true)
                {
                    Socket socket = server.accept();
                    count.incrementAndGet();
                    logger.debug("have connection " + count);
                    socket.getOutputStream().write("shaddup".getBytes());
                    InputStream in = socket.getInputStream();
                    while (in.read() > -1)
                    {
                        logger.debug("read character");
                    }
                    socket.close();
                }
            }
            catch(Exception e)
            {
                // can get a socket close error if client closes first
                // we don't care - we're only interested in connections here
                // and without any real protocol this is the simplest way
                logger.debug(e);
            }
        }

        public void close()
        {
            try
            {
                server.close();
            }
            catch (Exception e)
            {
                // no-op
            }
        }
    }

}

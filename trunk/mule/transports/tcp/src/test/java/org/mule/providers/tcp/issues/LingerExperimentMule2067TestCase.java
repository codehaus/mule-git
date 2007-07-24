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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Are the "adress already in use" errors coming from lingering sockets?
 */
public class LingerExperimentMule2067TestCase extends TestCase
{

    private static final int PORT = 65432;

    private Log logger = LogFactory.getLog(getClass());

    public void testDefault() throws IOException
    {
        openCloseServer(1000, PORT); // ok
        openCloseClientServer(1000, PORT); // ok
        repeatOpenCloseClientServer(1000, 10, PORT); // address already in use
    }

    protected void openCloseServer(int numberOfSockets, int port) throws IOException
    {
        for (int i = 0; i < numberOfSockets; i++)
        {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
        }
    }

    protected void repeatOpenCloseClientServer(int numberOfRepeats, int numberOfConnections, int port) throws IOException
    {
        for (int i = 0; i < numberOfRepeats; i++)
        {
            openCloseClientServer(numberOfConnections, port);
        }
    }

    protected void openCloseClientServer(int numberOfConnections, int port) throws IOException
    {
        Server server = new Server(port);
        new Thread(server).start();
        for (int i = 0; i < numberOfConnections; i++)
        {
            logger.debug("opening socket " + i);
            Socket client = new Socket("localhost", port);
            client.close();
        }
        server.close();
    }

    protected static class Server implements Runnable
    {

        private Log logger = LogFactory.getLog(getClass());
        private ServerSocket server;

        public Server(int port) throws IOException
        {
            server = new ServerSocket();
            server.bind(new InetSocketAddress("localhost", port));
        }

        public void run()
        {
            try
            {
                while (true)
                {
                    Socket socket = server.accept();
                    socket.close();
                }
            }
            catch (Exception e)
            {
                logger.debug("Expected - direty closedown: " + e);
            }
        }

        public void close() throws IOException
        {
            server.close();
            server = null;
        }
    }

}

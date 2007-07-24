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
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Can we avoid the "address already in use" errors by using SO_REUSEADDR?
 */
public class ReuseExperimentMule2067TestCase extends TestCase
{

    private static final int NO_WAIT = -1;
    private static final int PORT = 65432;
    private static boolean NO_REUSE = false;
    private static boolean REUSE = true;

    private Log logger = LogFactory.getLog(getClass());

    public void testReuse() throws IOException
    {
        repeatOpenCloseClientServer(1000, 10, PORT, 1, REUSE, false); // fails, but less often?
        repeatOpenCloseClientServer(100, 10, PORT, 1, NO_REUSE, false); // intermittent
    }

    public void testMeasureImprovement() throws IOException
    {
        measureMeanRunLength(10, 100, 10, PORT, 100, NO_REUSE);
        measureMeanRunLength(10, 100, 10, PORT, 100, REUSE);
        measureMeanRunLength(10, 100, 10, PORT, 10, NO_REUSE);
        measureMeanRunLength(10, 100, 10, PORT, 10, REUSE);
        measureMeanRunLength(10, 100, 10, PORT, 1, NO_REUSE);
        measureMeanRunLength(10, 100, 10, PORT, 1, REUSE);
    }

    protected void measureMeanRunLength(int sampleSize, int numberOfRepeats, int numberOfConnections,
                                        int port, long pause,  boolean reuse)
            throws IOException
    {
        logger.info("Measuring average run length for " + numberOfRepeats + " repeats " +
                (reuse ? "with" : "without") + " reuse and a pause of " + pause + " ms");
        int totalLength = 0;
        long totalLengthSquared = 0;
        for (int i = 0; i < sampleSize; ++i)
        {
            int length = repeatOpenCloseClientServer(numberOfRepeats, numberOfConnections, port, pause, reuse, true);
            totalLength += length;
            totalLengthSquared += length * length;
            pause(100);
        }
        double mean = totalLength / (double) sampleSize;
        double sd = Math.sqrt(totalLengthSquared / (double) sampleSize - mean * mean);
        logger.info("Average run length: " + mean + " +/- " + sd);
        pause(2000);
    }

    protected int repeatOpenCloseClientServer(int numberOfRepeats, int numberOfConnections, int port,
                                              long pause, boolean reuse, boolean noFail)
            throws IOException
    {
        String message = "Repeating openCloseClientServer with pauses of " + pause + " ms "
                    + (reuse ? "with" : "without") + " reuse";
        if (noFail)
        {
            logger.debug(message);
        }
        else
        {
            logger.info(message);
        }
        for (int i = 0; i < numberOfRepeats; i++)
        {
            if (0 != i)
            {
                pause(pause);
            }
            try
            {
                openCloseClientServer(numberOfConnections, port, reuse);
            }
            catch (BindException e)
            {
                if (noFail && e.getMessage().indexOf("Address already in use") > -1)
                {
                    return i;
                }
                throw e;
            }
        }
        return numberOfRepeats;
    }

    protected void openCloseClientServer(int numberOfConnections, int port, boolean reuse)
            throws IOException
    {
        Server server = new Server(port, reuse);
        try {
            new Thread(server).start();
            for (int i = 0; i < numberOfConnections; i++)
            {
                logger.debug("opening socket " + i);
                Socket client = new Socket("localhost", port);
                client.close();
            }
        }
        finally
        {
            server.close();
        }
    }

    protected void pause(long pause)
    {
        if (pause != NO_WAIT)
        {
            try
            {
                synchronized(this)
                {
                    if (pause > 0)
                    {
                        this.wait(pause);
                    }
                }
            }
            catch (InterruptedException e)
            {
                // ignore
            }
        }
    }

    protected static class Server implements Runnable
    {

        private Log logger = LogFactory.getLog(getClass());
        private ServerSocket server;

        public Server(int port, boolean reuse) throws IOException
        {
            server = new ServerSocket();
            server.setReuseAddress(reuse);
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
                logger.debug("Expected - dirty closedown: " + e);
            }
        }

        public void close() throws IOException
        {
            server.close();
            server = null;
        }
    }

}
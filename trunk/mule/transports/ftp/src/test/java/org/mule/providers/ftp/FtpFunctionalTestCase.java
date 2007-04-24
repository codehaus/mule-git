/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ftp;

import org.mule.extras.client.MuleClient;
import org.mule.providers.ftp.server.Server;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import java.util.HashMap;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

public class FtpFunctionalTestCase extends FunctionalTestCase
{

    private String TEST_MESSAGE = "Test FTP message";
    private static AtomicInteger port = new AtomicInteger(60198);
    private long TIMEOUT = 10000;
    private Server server;

    public FtpFunctionalTestCase()
    {
        setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "ftp-functional-test.xml";
    }

    protected void doPostFunctionalSetUp() throws Exception
    {
        server = new Server(port.incrementAndGet(), 1);
        server.waitToStart(TIMEOUT);
    }

    protected void doFunctionalTearDown() throws Exception
    {
        // stop the server
        if (null != server)
        {
            server.stop();
        }
    }

    public void testSendAndReceive() throws Exception
    {
        Map properties = new HashMap();
        MuleClient client = new MuleClient();
        client.dispatch("ftp://anonymous:email@localhost:" + port.get(), TEST_MESSAGE, properties);
        server.awaitAndReset(TIMEOUT, 1);
        assertNotNull(server.getPayload());
        assertEquals(TEST_MESSAGE, new String(server.getPayload()));
        logger.info("received message OK!");
        UMOMessage retrieved = client.receive("ftp://anonymous:email@localhost:" + port.get(), TIMEOUT);
        assertNotNull(retrieved);
        assertEquals(retrieved.getPayloadAsString(), TEST_MESSAGE);
    }

}

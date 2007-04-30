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

import org.mule.providers.ftp.server.NamedPayload;
import org.mule.extras.client.MuleClient;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalStreamingTestComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOSession;
import org.mule.umo.model.UMOModel;
import org.mule.MuleManager;
import org.mule.impl.model.streaming.StreamingComponent;

import java.util.HashMap;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicReference;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

/**
 * We don't have an integrated ftp server (yet), and synchronous return doesn't work
 * with streaming, as far as i can tell, so the best we can do here is dispatch
 * a stream (which is testing the stream adapter, not the streaming model) to the
 * test server, then pull it back again through a streaming model.
 */
public class FtpStreamingTestCase extends BaseServerTestCase
{

    private static int PORT = 60197;

    public FtpStreamingTestCase()
    {
        super(PORT);
    }

    protected String getConfigResources()
    {
        return "ftp-streaming-test.xml";
    }

    public void testSendAndReceive() throws Exception
    {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference message = new AtomicReference();
        final AtomicInteger loopCount = new AtomicInteger(0);

        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component)
            {
                try
                {
                    logger.warn("called " + loopCount.incrementAndGet() + " times");
                    FunctionalStreamingTestComponent ftc = (FunctionalStreamingTestComponent) component;
                    // without this we may have problems with the many repeats
                    if (1 == latch.getCount())
                    {
                        message.set(ftc.getSummary());
                        latch.countDown();
                    }
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                }
            }
        };

        MuleClient client = new MuleClient();

        UMOModel model = (UMOModel) MuleManager.getInstance().getModels().get("main");
        UMOSession session = model.getComponentSession("testComponent");
        StreamingComponent component = (StreamingComponent) session.getComponent();
        FunctionalStreamingTestComponent ftc = (FunctionalStreamingTestComponent) component.getComponent();

        ftc.setEventCallback(callback, TEST_MESSAGE.length());

        // send out to FTP server
//        client.dispatchStream("ftp://anonymous:email@localhost:" + PORT,
//                new StreamMessageAdapter(new ByteArrayInputStream(TEST_MESSAGE.getBytes())),
//                getTimeout());
//        NamedPayload payload = awaitUpload();
//        assertNotNull(payload);
//        logger.info("received message: " + payload);
//        assertEquals(TEST_MESSAGE, new String(payload.getPayload()));

        // send out to FTP server via streaming model
        client.dispatch("tcp://localhost:60196", TEST_MESSAGE, new HashMap());
        NamedPayload payload = awaitUpload();
        assertNotNull(payload);
        logger.info("received message: " + payload);
        assertEquals(TEST_MESSAGE, new String(payload.getPayload()));

        // poll and pull back through test component
        latch.await(getTimeout(), TimeUnit.MILLISECONDS);
        assertEquals("Received stream; length: 16; 'Test...sage'", message.get());
    }

}
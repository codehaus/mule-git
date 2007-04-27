/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp.integration;

import org.mule.extras.client.MuleClient;
import org.mule.providers.streaming.StreamMessageAdapter;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.model.UMOModel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StreamingTestCase  extends FunctionalTestCase
{

    private static final Log logger = LogFactory.getLog(StreamingTestCase.class);
    public static final int TIMEOUT = 3000;
    public static final String TEST_MESSAGE = "Test TCP Request";

    public StreamingTestCase()
    {
        setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "tcp-streaming-test.xml";
    }

    public void testSend() throws Exception
    {
        final CountDownLatch latch = new CountDownLatch(1);

        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component)
            {
                try
                {
                    logger.debug("woot - event received");
                    logger.debug("context: " + context);
                    logger.debug("component: " + component);
                    latch.countDown();
                }
                catch (Exception e)
                {
                    // counter will not be incremented
                    logger.error(e.getMessage(), e);
                }
            }
        };

        MuleClient client = new MuleClient();
        UMOModel model = (UMOModel) client.getManager().getModels().get("echo");
        FunctionalTestComponent ftc =
                (FunctionalTestComponent) model.getComponent("testComponent").getInstance();
        ftc.setEventCallback(callback);

        Object result = client.sendStream("tcp://localhost:65432",
                        new StreamMessageAdapter(
                                new ByteArrayInputStream(TEST_MESSAGE.getBytes())),
                TIMEOUT);
        assertNotNull(result);
        assertTrue(result instanceof StreamMessageAdapter);
        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(((StreamMessageAdapter) result).getInputStream()));
        String message = in.readLine();
        logger.debug("this is useless: " + message);

        latch.await(2, TimeUnit.SECONDS);
    }

}

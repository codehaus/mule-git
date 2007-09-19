/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp.integration;

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalStreamingTestComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.model.UMOModel;

import java.util.HashMap;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This test is more about testing the streaming model than the TCP provider, really.
 */
public class StreamingTestCase extends FunctionalTestCase
{

    private static final Log logger = LogFactory.getLog(StreamingTestCase.class);
    public static final int TIMEOUT = 3000;
    public static final String TEST_MESSAGE = "Test TCP Request";
    public static final String RESULT = "Received stream; length: 16; 'Test...uest'";

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
        MuleClient client = new MuleClient();

        UMOMessage result = client.send("tcp://localhost:65432", TEST_MESSAGE, new HashMap());

        String strResult = result.getPayloadAsString();
        assertEquals(TEST_MESSAGE, strResult);
    }

}

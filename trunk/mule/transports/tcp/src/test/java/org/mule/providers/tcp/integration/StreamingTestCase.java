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
import org.mule.umo.provider.UMOStreamMessageAdapter;
import org.mule.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class StreamingTestCase  extends FunctionalTestCase
{

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
        MuleClient client = new MuleClient();
        UMOStreamMessageAdapter result =
                client.sendStream("tcp://localhost:65432",
                        new StreamMessageAdapter(
                                new ByteArrayInputStream(TEST_MESSAGE.getBytes())),
                        TIMEOUT);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        IOUtils.copy(result.getInputStream(), buffer);
        assertEquals(TEST_MESSAGE, buffer.toString());
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.components.script;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalStreamingTestComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.model.UMOModel;

import java.util.HashMap;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicReference;

public class StreamingScriptComponentTestCase extends FunctionalTestCase
{
    public static final int TIMEOUT = 3000;

    public static final String TEST_MESSAGE = "World";

    public static final String RESULT = "Received stream; length: 11; 'Hell...orld'";

    public StreamingScriptComponentTestCase()
    {
        setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "groovy-streaming-test.xml";
    }

    public void testFunctionBehaviour() throws Exception
    {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference message = new AtomicReference();

        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component)
            {
                try
                {
                    FunctionalStreamingTestComponent ftc = (FunctionalStreamingTestComponent) component;
                    // without this we may have problems with the many repeats
                    if (1 == latch.getCount())
                    {
                        message.set(ftc.getSummary());
                        assertEquals(RESULT, message.get());
                        latch.countDown();
                    }
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                }
            }
        };

        UMOModel model = (UMOModel) MuleManager.getInstance().getModels().get("streaming-model");
        FunctionalStreamingTestComponent ftc = (FunctionalStreamingTestComponent) model.getComponent(
            "testComponent").getInstance();
        assertNotNull(ftc);
        ftc.setEventCallback(callback, TEST_MESSAGE.length());

        MuleClient client = new MuleClient();
        client.dispatch("tcp://localhost:65432", TEST_MESSAGE, new HashMap());

        latch.await(10, TimeUnit.SECONDS);
        assertEquals(RESULT, message.get());
    }
}

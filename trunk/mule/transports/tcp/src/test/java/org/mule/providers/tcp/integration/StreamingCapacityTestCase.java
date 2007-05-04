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

import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalStreamingTestComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOSession;
import org.mule.umo.provider.UMOStreamMessageAdapter;
import org.mule.umo.model.UMOModel;
import org.mule.extras.client.MuleClient;
import org.mule.MuleManager;
import org.mule.providers.streaming.StreamMessageAdapter;
import org.mule.impl.model.streaming.StreamingComponent;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicReference;

/**
 * This will happily send 1GB while running in significantly less memory, but it takes some time.
 * Since I'd like this to run in CI wwill set at 100MB and test memory delta.  But since memory usage
 * could be around that anyway, this is may be a little unreliable.  We'll see...
 *
 * IMPORTANT - DO NOT RUN THIS TEST IN AN IDE WITH LOG LEVEL OF DEBUG.  USE INFO TO SEE
 * DIAGNOSTICS.  OTHERWISE THE CONSOLE OUTPUT WILL BE SIMILAR SIZE TO DATA TRANSFERRED,
 * CAUSING CONFUSNG AND PROBABLY FATAL MEMORY USE.
 */
public class StreamingCapacityTestCase  extends FunctionalTestCase
{

    private static final Log logger = LogFactory.getLog(StreamingTestCase.class);
    public static final int TIMEOUT = 3000;
    public static final long ONE_KB = 1024;
    public static final long ONE_MB = ONE_KB * ONE_KB;
    public static final long ONE_GB = ONE_KB * ONE_MB;
    public static final long SIZE = 100 * ONE_MB;
    public static final int MESSAGES = 21;

    public StreamingCapacityTestCase()
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
        final AtomicReference message = new AtomicReference();

        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component)
            {
                try
                {
                    FunctionalStreamingTestComponent ftc = (FunctionalStreamingTestComponent) component;
                    message.set(ftc.getSummary());
                    latch.countDown();
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                }
            }
        };

        MuleClient client = new MuleClient();

        UMOModel model = (UMOModel) MuleManager.getInstance().getModels().get("echo");
        UMOSession session = model.getComponentSession("testComponent");
        StreamingComponent component = (StreamingComponent) session.getComponent();
        FunctionalStreamingTestComponent ftc = (FunctionalStreamingTestComponent) component.getComponent();

        ftc.setEventCallback(callback, SIZE);

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // i know, i know...
        long freeStart = runtime.freeMemory();
        long maxStart = runtime.maxMemory();

        BigInputStream stream = new BigInputStream(SIZE, MESSAGES);
        UMOStreamMessageAdapter adapter = new StreamMessageAdapter(stream);
        client.dispatchStream("tcp://localhost:65432", adapter);

        // if we assume 1MB/sec then we need at least...
        int pause = (int) Math.max(SIZE / ONE_MB, 10);
        logger.info("Waiting for up to " + pause + " seconds");

        latch.await(pause, TimeUnit.SECONDS);
        assertEquals(stream.summary(), message.get());

        // neither of these memory tests are really reliable, but if we stay with 1.4 i don't
        // know of anything better.
        // if these fail in practice i guess we just remove them.

        long freeEnd = runtime.freeMemory();
        long delta = freeStart - freeEnd;
        double usePercent = 100.0 * delta / ((double) SIZE);
        logger.info("Memory delta " + delta + " = " + usePercent + "%");
        assertTrue("Memory used too high", usePercent < 10);

        long maxEnd = runtime.maxMemory();
        assertEquals("Max memory shifted", 0,  maxEnd - maxStart);
    }

    private static class BigInputStream extends InputStream
    {

        private static final int SUMMARY_SIZE = 4;
        private static final  MessageFormat FORMAT  =
            new MessageFormat("Sent {0,number,#} bytes, {1,number,###.##}% (free {2,number,#}/{3,number,#})");
        private long size;
        private int messages;

        private long sent = 0;
        private byte[] data;
        private int dataIndex = 0;
        private int printedMessages = 0;
        private int nextMessage = 0;


        public BigInputStream(long size, int messages)
        {
            this.size = size;
            this.messages = messages;
            data = ("This message is repeated for " + size + " bytes. ").getBytes();
        }

        /**
         * @return String matching {@link org.mule.tck.functional.FunctionalStreamingTestComponent}
         */
        public String summary()
        {

            byte[] tail = new byte[SUMMARY_SIZE];
            for (int i = 0; i < SUMMARY_SIZE; ++i)
            {
                tail[i] = data[(int) ((sent - SUMMARY_SIZE + i) % data.length)];
            }
            return "Received stream; length: " + sent + "; '" +
                    new String(data, 0, 4) + "..." + new String(tail) +
                    "'";
        }

        public int read() throws IOException
        {
            if (sent == size)
            {
                return -1;
            }
            else
            {
                if (++sent > nextMessage)
                {
                    double percent = 100l * sent / ((double) size);
                    Runtime runtime = Runtime.getRuntime();
                    logger.info(FORMAT.format(new Object[]{
                            new Long(sent), new Double(percent),
                            new Long(runtime.freeMemory()), new Long(runtime.maxMemory())}));
                    nextMessage = ++printedMessages *
                            ((int) Math.floor(((double) size) / (messages - 1)) - 1);
                }
                if (dataIndex == data.length)
                {
                    dataIndex = 0;
                }
                return data[dataIndex++];
            }
        }

    }

}


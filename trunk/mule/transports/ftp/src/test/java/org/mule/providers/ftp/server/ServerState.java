/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ftp.server;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class ServerState
{

    private byte[] payload;
    private CountDownLatch started = new CountDownLatch(1);
    private CountDownLatch complete;

    public ServerState(int count)
    {
        complete = new CountDownLatch(count);
    }

    public byte[] getPayload()
    {
        return payload;
    }

    public void setPayload(byte[] payload)
    {
        this.payload = payload;
    }

    public void registerStarted()
    {
        started.countDown();
    }

    public void registerCompletion()
    {
        complete.countDown();
    }

    /**
     * WARNING - intended only for sequencing tests.  This is not general.
     * In particular, countdowns may be "lost".
     * The only use case supported is waiting for a known number of threads to complete.
     * So "resetting" to a new initial count is done in a sequential context.
     *
     * @param ms Wait period (ms)
     * @param count New count value
     */
    public void awaitAndReset(long ms, int count) throws InterruptedException
    {
        complete.await(ms, TimeUnit.MILLISECONDS);
        complete = new CountDownLatch(count);
    }

    public void waitToStart(long ms) throws InterruptedException
    {
        started.await(ms, TimeUnit.MILLISECONDS);
    }

}

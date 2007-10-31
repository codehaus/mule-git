/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.config;

import org.mule.config.ThreadingProfile;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.util.concurrent.NamedThreadFactory;

import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionHandler;
import edu.emory.mathcs.backport.java.util.concurrent.ThreadFactory;
import edu.emory.mathcs.backport.java.util.concurrent.ThreadPoolExecutor;

public class ThreadingProfileTestCase extends AbstractMuleTestCase
{

    public void testCustomRejectedExecutionHandler()
    {
        RejectedExecutionHandler handler = new RejectedExecutionHandler()
        {
            public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1)
            {
                // nothing to do here
            }
        };

        ThreadingProfile profile = new ThreadingProfile();
        profile.setRejectedExecutionHandler(handler);
        ThreadPoolExecutor pool = profile.createPool("testPool");
        assertSame(handler, pool.getRejectedExecutionHandler());
    }

    public void testPoolExhaustedActionStrings()
    {
        ThreadingProfile tp = new ThreadingProfile();

        tp.setPoolExhaustedActionString(null);
        assertEquals(ThreadingProfile.DEFAULT_POOL_EXHAUST_ACTION, tp.getPoolExhaustedAction());

        tp.setPoolExhaustedActionString("BAZ");
        assertEquals(ThreadingProfile.DEFAULT_POOL_EXHAUST_ACTION, tp.getPoolExhaustedAction());

        tp.setPoolExhaustedActionString("WHEN_EXHAUSTED_WAIT");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_WAIT, tp.getPoolExhaustedAction());
        tp.setPoolExhaustedActionString("WAIT");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_WAIT, tp.getPoolExhaustedAction());

        tp.setPoolExhaustedActionString("WHEN_EXHAUSTED_DISCARD");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_DISCARD, tp.getPoolExhaustedAction());
        tp.setPoolExhaustedActionString("DISCARD");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_DISCARD, tp.getPoolExhaustedAction());

        tp.setPoolExhaustedActionString("WHEN_EXHAUSTED_DISCARD_OLDEST");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_DISCARD_OLDEST, tp.getPoolExhaustedAction());
        tp.setPoolExhaustedActionString("DISCARD_OLDEST");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_DISCARD_OLDEST, tp.getPoolExhaustedAction());

        tp.setPoolExhaustedActionString("WHEN_EXHAUSTED_ABORT");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_ABORT, tp.getPoolExhaustedAction());
        tp.setPoolExhaustedActionString("ABORT");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_ABORT, tp.getPoolExhaustedAction());

        tp.setPoolExhaustedActionString("WHEN_EXHAUSTED_RUN");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_RUN, tp.getPoolExhaustedAction());
        tp.setPoolExhaustedActionString("RUN");
        assertEquals(ThreadingProfile.WHEN_EXHAUSTED_RUN, tp.getPoolExhaustedAction());
    }

    public void testDefaultThreadFactory()
    {
        ThreadingProfile profile = new ThreadingProfile();
        ThreadPoolExecutor pool = profile.createPool();
        ThreadFactory returnedFactory = pool.getThreadFactory();
        assertTrue(returnedFactory.getClass().isInstance(Executors.defaultThreadFactory()));
    }

    public void testDefaultNamedThreadFactory()
    {
        ThreadingProfile profile = new ThreadingProfile();
        ThreadPoolExecutor pool = profile.createPool("myThreadPool");
        ThreadFactory returnedFactory = pool.getThreadFactory();
        assertTrue(returnedFactory instanceof NamedThreadFactory);
    }

    public void testCustomThreadFactory()
    {
        ThreadingProfile profile = new ThreadingProfile();

        ThreadFactory configuredFactory = new ThreadFactory()
        {
            public Thread newThread(Runnable r)
            {
                return null;
            }
        };

        profile.setThreadFactory(configuredFactory);
        ThreadPoolExecutor pool = profile.createPool();
        ThreadFactory returnedFactory = pool.getThreadFactory();
        assertSame(configuredFactory, returnedFactory);
    }

}

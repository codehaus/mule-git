/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.config;

import org.mule.api.context.WorkManager;
import org.mule.config.ImmutableThreadingProfile;

import java.util.Map;

import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionHandler;
import edu.emory.mathcs.backport.java.util.concurrent.ThreadFactory;
import edu.emory.mathcs.backport.java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * Mule uses a few different pools i.e. for service threads and message dispatchers. This interface
 * makes it easier to configure the pool.  Pools are created via
 * {@link ImmutableThreadingProfile#createPool(String, ThreadingProfile)} (which
 * should really be a separate factory).
 *
 * <p>{@link ImmutableThreadingProfile} is a simple read-only implementation that
 * makes a local copy of any {@link org.mule.api.config.ThreadingProfile} passed to a consructor.</p>
 *
 * <p>{@link org.mule.config.ChainedThreadingProfile} is a mutable implementation that can take
 * default values from an existing {@link org.mule.api.config.ThreadingProfile}.  The default values
 * can be either dynamic (read whenever the value is queried) or static (a local copy of the
 * default is made when the profile is first constructed).</p>
 */
public interface ThreadingProfile
{

    /**
     * Default value for MAX_THREADS_ACTIVE
     */
    int DEFAULT_MAX_THREADS_ACTIVE = 16;

    /**
     * Default value for MAX_THREADS_IDLE
     */
    int DEFAULT_MAX_THREADS_IDLE = 1;

    /**
     * Default value for MAX_BUFFER_SIZE
     */
    int DEFAULT_MAX_BUFFER_SIZE = 0;

    /**
     * Default value for MAX_THREAD_TTL
     */
    long DEFAULT_MAX_THREAD_TTL = 60000;

    /**
     * Default value for DEFAULT_THREAD_WAIT_TIMEOUT
     */
    long DEFAULT_THREAD_WAIT_TIMEOUT = 30000L;

    /**
     * Default value for do threading
     */
    boolean DEFAULT_DO_THREADING = true;

    /**
     * Actions to perform on pool exhaustion
     */
    int WHEN_EXHAUSTED_WAIT = 0;
    int WHEN_EXHAUSTED_DISCARD = 1;
    int WHEN_EXHAUSTED_DISCARD_OLDEST = 2;
    int WHEN_EXHAUSTED_ABORT = 3;
    int WHEN_EXHAUSTED_RUN = 4;

    /**
     * Default action to perform on pool exhaustion
     */
    int DEFAULT_POOL_EXHAUST_ACTION = WHEN_EXHAUSTED_RUN;

    // map pool exhaustion strings to their respective values
    Map POOL_EXHAUSTED_ACTIONS = new CaseInsensitiveMap()
    {
        private static final long serialVersionUID = 1L;

        // initializer
        {
            Integer value = new Integer(WHEN_EXHAUSTED_WAIT);
            this.put("WHEN_EXHAUSTED_WAIT", value);
            this.put("WAIT", value);

            value = new Integer(WHEN_EXHAUSTED_DISCARD);
            this.put("WHEN_EXHAUSTED_DISCARD", value);
            this.put("DISCARD", value);

            value = new Integer(WHEN_EXHAUSTED_DISCARD_OLDEST);
            this.put("WHEN_EXHAUSTED_DISCARD_OLDEST", value);
            this.put("DISCARD_OLDEST", value);

            value = new Integer(WHEN_EXHAUSTED_ABORT);
            this.put("WHEN_EXHAUSTED_ABORT", value);
            this.put("ABORT", value);

            value = new Integer(WHEN_EXHAUSTED_RUN);
            this.put("WHEN_EXHAUSTED_RUN", value);
            this.put("RUN", value);
        }
    };

    ThreadingProfile DEFAULT_THREADING_PROFILE =
            new ImmutableThreadingProfile(
                    DEFAULT_MAX_THREADS_ACTIVE,
                    DEFAULT_MAX_THREADS_IDLE,
                    DEFAULT_MAX_BUFFER_SIZE,
                    DEFAULT_MAX_THREAD_TTL,
                    DEFAULT_THREAD_WAIT_TIMEOUT,
                    DEFAULT_POOL_EXHAUST_ACTION,
                    DEFAULT_DO_THREADING,
                    null,
                    null
                    );

    int getMaxThreadsActive();

    int getMaxThreadsIdle();

    long getThreadTTL();

    long getThreadWaitTimeout();

    int getPoolExhaustedAction();

    RejectedExecutionHandler getRejectedExecutionHandler();

    ThreadFactory getThreadFactory();

    void setMaxThreadsActive(int maxThreadsActive);

    void setMaxThreadsIdle(int maxThreadsIdle);

    void setThreadTTL(long threadTTL);

    void setThreadWaitTimeout(long threadWaitTimeout);

    void setPoolExhaustedAction(int poolExhaustPolicy);

    void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler);

    void setThreadFactory(ThreadFactory threadFactory);

    int getMaxBufferSize();

    void setMaxBufferSize(int maxBufferSize);

    WorkManagerFactory getWorkManagerFactory();

    void setWorkManagerFactory(WorkManagerFactory workManagerFactory);

    WorkManager createWorkManager(String name);

    ThreadPoolExecutor createPool();

    ThreadPoolExecutor createPool(String name);

    boolean isDoThreading();

    void setDoThreading(boolean doThreading);

    interface WorkManagerFactory
    {
        WorkManager createWorkManager(ThreadingProfile profile, String name);
    }

}

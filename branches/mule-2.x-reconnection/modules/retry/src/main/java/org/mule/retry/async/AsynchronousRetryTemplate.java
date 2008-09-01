/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.retry.async;

import org.mule.api.context.WorkManager;
import org.mule.api.retry.RetryCallback;
import org.mule.api.retry.RetryContext;
import org.mule.api.retry.RetryNotifier;
import org.mule.api.retry.RetryPolicy;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.transport.FatalConnectException;
import org.mule.util.concurrent.Latch;

import javax.resource.spi.work.WorkException;

/**
 * The AsynchronousRetryTemplate is a wrapper for a {@link org.mule.api.retry.RetryTemplate} and will execute
 * any work within a separate thread.
 * A {@link org.mule.util.concurrent.Latch} can be passed into this template, in this scenario the execution of the
 * template will only occur once the latch is released.
 *
 */
public class AsynchronousRetryTemplate implements RetryPolicyTemplate
{
    private final WorkManager workManager;
    private final RetryPolicyTemplate delegate;
    private final Latch startLatch;

    public AsynchronousRetryTemplate(RetryPolicyTemplate delegate, WorkManager workManager)
    {
        this(delegate, workManager, null);
    }

    public AsynchronousRetryTemplate(RetryPolicyTemplate delegate, WorkManager workManager, Latch startLatch)
    {
        this.delegate = delegate;
        this.workManager = workManager;
        this.startLatch = startLatch;
    }

    public RetryContext execute(RetryCallback callback) throws FatalConnectException
    {
        RetryWorker worker = new RetryWorker(delegate, callback, startLatch);
        FutureRetryContext context = worker.getRetryContext();
        try
        {
            workManager.scheduleWork(worker);
        }
        catch (WorkException e)
        {
            throw new FatalConnectException(e, null);
        }
        return context;
    }

    public RetryPolicy createRetryInstance()
    {
        return delegate.createRetryInstance();
    }
    
    public RetryNotifier getNotifier()
    {
        return delegate.getNotifier();
    }

    public void setNotifier(RetryNotifier retryNotifier)
    {
        delegate.setNotifier(retryNotifier);
    }

    public boolean isConnectAsynchronously()
    {
        return true;
    }
}

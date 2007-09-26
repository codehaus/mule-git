/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.retry.async;

import org.mule.providers.FatalConnectException;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.umo.retry.UMOPolicyFactory;
import org.mule.umo.retry.UMORetryCallback;
import org.mule.umo.retry.UMORetryContext;
import org.mule.umo.retry.UMORetryNotifier;
import org.mule.umo.retry.UMORetryTemplate;
import org.mule.util.concurrent.Latch;

import javax.resource.spi.work.WorkException;

/**
 * The AsynchronousRetryTemplate is a wrapper for a {@link org.mule.umo.retry.UMORetryTemplate} and will execute
 * any work within a separate thread.
 * A {@link org.mule.util.concurrent.Latch} can be passed into this template, in this scenario the execution of the
 * template will only occur once the latch is released.
 *
 */
public class AsynchronousRetryTemplate implements UMORetryTemplate
{
    private UMOWorkManager workManager;
    private UMORetryTemplate delegate;
    private Latch startLatch;

    public AsynchronousRetryTemplate(UMORetryTemplate delegate, UMOWorkManager workManager)
    {
        this(delegate, workManager, null);
    }

    public AsynchronousRetryTemplate(UMORetryTemplate delegate, UMOWorkManager workManager, Latch startLatch)
    {
        this.delegate = delegate;
        this.workManager = workManager;
        this.startLatch = startLatch;
    }

    public UMORetryContext execute(UMORetryCallback callback) throws FatalConnectException
    {
        RetryWorker worker = new RetryWorker(delegate, callback, startLatch);
        FutureRetryContext context = worker.getRetryContext();
        try
        {
            workManager.scheduleWork(worker);
        }
        catch (WorkException e)
        {
            throw new FatalConnectException(e, this);
        }
        return context;
    }

    public UMOPolicyFactory getPolicyFactory()
    {
        return delegate.getPolicyFactory();
    }

    public UMORetryNotifier getRetryNotifier()
    {
        return delegate.getRetryNotifier();
    }
}

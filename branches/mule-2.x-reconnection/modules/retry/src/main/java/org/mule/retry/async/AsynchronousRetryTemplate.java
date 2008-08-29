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

import org.mule.api.MuleRuntimeException;
import org.mule.api.context.WorkManager;
import org.mule.api.retry.RetryCallback;
import org.mule.api.retry.RetryContext;
import org.mule.api.retry.RetryTemplate;
import org.mule.api.retry.RetryTemplateFactory;
import org.mule.config.i18n.MessageFactory;
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
public class AsynchronousRetryTemplate implements RetryTemplate, RetryTemplateFactory
{
    private final WorkManager workManager;
    private final RetryTemplate delegate;
    private final Latch startLatch;

    public RetryTemplate create()
    {
        if (workManager == null)
        {
            throw new MuleRuntimeException(MessageFactory.createStaticMessage("Asynchronous connections specified but no work manager provided"));
        }
        return new AsynchronousRetryTemplate(delegate, workManager, startLatch);
    }
    
    public AsynchronousRetryTemplate(RetryTemplate delegate, WorkManager workManager)
    {
        this(delegate, workManager, null);
    }

    public AsynchronousRetryTemplate(RetryTemplate delegate, WorkManager workManager, Latch startLatch)
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
            throw new FatalConnectException(e, this);
        }
        return context;
    }

    public boolean isRetryEnabled()
    {
        return delegate.isRetryEnabled();
    }
}

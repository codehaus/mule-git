/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.retry;

import org.mule.api.retry.RetryCallback;
import org.mule.api.retry.RetryContext;
import org.mule.api.retry.RetryNotifier;
import org.mule.api.retry.RetryPolicy;
import org.mule.api.retry.RetryPolicyFactory;
import org.mule.api.retry.RetryTemplate;
import org.mule.api.retry.RetryTemplateFactory;
import org.mule.config.i18n.CoreMessages;
import org.mule.retry.policies.NoRetryPolicyFactory;
import org.mule.transport.FatalConnectException;

import java.io.InterruptedIOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A RetryTemplate can be used to invoke actions that may need to be retried i.e. connecting to an external process,
 * or dispatching an event. How retries are made is dictated by the {@link org.mule.api.retry.RetryPolicyFactory}. Policies
 * are stategies that define what happens between retries.
 * Also a {@link org.mule.api.retry.RetryNotifier} that can be used to invoke actions between Retries for tracking and
 * notifications.
 *
 * @see org.mule.api.retry.RetryNotifier
 * @see RetryCallback
 * @see org.mule.api.retry.RetryPolicyFactory
 */
public class DefaultRetryTemplate implements RetryTemplate, RetryTemplateFactory
{
    protected transient final Log logger = LogFactory.getLog(DefaultRetryTemplate.class);
    private final RetryPolicyFactory policyFactory;
    private final RetryNotifier notifier;

    public RetryTemplate create()
    {
        return new DefaultRetryTemplate(policyFactory, notifier);
    }
    
    public DefaultRetryTemplate(RetryPolicyFactory policyFactory)
    {
        this(policyFactory, null);
    }

    public DefaultRetryTemplate(RetryPolicyFactory policyFactory, RetryNotifier notifier)
    {
        if (policyFactory == null)
        {
            throw new IllegalArgumentException(CoreMessages.objectIsNull("policyFactory").getMessage());
        }
        this.policyFactory = policyFactory;
        this.notifier = notifier;
    }

    public RetryContext execute(RetryCallback callback) throws FatalConnectException
    {
        PolicyStatus status = null;
        RetryPolicy policy = policyFactory.create();
        DefaultRetryContext context = new DefaultRetryContext(callback.getWorkDescription());
        try
        {
            do
            {
                Throwable cause;
                try
                {
                    callback.doWork(context);
                    if (notifier != null)
                    {
                        notifier.sucess(context);
                    }

                    break;
                }
                catch (Exception e)
                {
                    cause = e;
                    if (notifier != null)
                    {
                        notifier.failed(context, e);
                    }
                    if (e instanceof InterruptedException || e instanceof InterruptedIOException)
                    {
                        logger.error("Process was interrupted (InterruptedException), ceasing process");
                        break;
                    }
                }
                status = policy.applyPolicy(cause);
            }
            while (status.isOk());

            if(status==null || status.isOk())
            {
                return context;
            }
            else
            {
                throw new FatalConnectException(
                        CoreMessages.failedToConnect(context.getDescription(), policyFactory),
                        status.getThrowable(), this);
            }
        }
        finally
        {
            if (status != null && status.getThrowable() != null)
            {
                if (logger.isDebugEnabled())
                {
                    logger.error(status.getThrowable());
                }
            }
        }

    }

    /**
     * @return true if a policy is configured which will actually retry
     */
    public boolean isRetryEnabled()
    {
        return policyFactory != null && !(policyFactory instanceof NoRetryPolicyFactory);
    }
}

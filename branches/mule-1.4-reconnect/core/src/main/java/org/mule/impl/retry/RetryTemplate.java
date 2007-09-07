/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.retry;

import org.mule.providers.FatalConnectException;
import org.mule.umo.retry.UMORetryTemplate;
import org.mule.umo.retry.PolicyStatus;
import org.mule.umo.retry.UMOPolicyFactory;
import org.mule.umo.retry.UMORetryNotifier;
import org.mule.umo.retry.UMORetryCallback;
import org.mule.umo.retry.UMOTemplatePolicy;
import org.mule.umo.retry.RetryContext;
import org.mule.config.i18n.CoreMessages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A RetryTemplate can be used to invoke actions that may need to be retried i.e. connecting to an external process,
 * or dispatching an event. How reties are made is dicatated by the {@link org.mule.umo.retry.UMOPolicyFactory}. Policies
 * are stategies that define what happens between retries.
 * Also a {@link org.mule.umo.retry.UMORetryNotifier} that can be used to invoke actions between Retries for tracking and
 * notifications.
 *
 * @see org.mule.umo.retry.UMORetryNotifier
 * @see UMORetryCallback
 * @see org.mule.umo.retry.UMOPolicyFactory
 */
public class RetryTemplate implements UMORetryTemplate
{
    /**
     * logger used by this class
     */
    protected transient final Log logger = LogFactory.getLog(RetryTemplate.class);
    private final UMOPolicyFactory policyFactory;
    private UMORetryNotifier notifier;

    public RetryTemplate(UMOPolicyFactory policyFactory)
    {
        this(policyFactory, null);
    }

    public RetryTemplate(UMOPolicyFactory policyFactory, UMORetryNotifier notifier)
    {
        if (policyFactory == null)
        {
            throw new IllegalArgumentException(CoreMessages.objectIsNull("policyFactory").getMessage());
        }
        this.policyFactory = policyFactory;
        this.notifier = notifier;
    }

    public RetryContext execute(UMORetryCallback callback) throws FatalConnectException
    {
        PolicyStatus status = null;
        UMOTemplatePolicy policy = policyFactory.create();
        RetryContext context = new RetryContext(callback.getWorkDescription());
        try
        {
            do
            {
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
                    if (notifier != null)
                    {
                        notifier.failed(context, e);
                    }
                    if (e instanceof InterruptedException)
                    {
                        logger.error("Process was interrupted (InterruptedException), ceasing process");
                        break;
                    }
                }
                status = policy.applyPolicy();
            }
            while (status.isOk());
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
        return context;

    }

    public UMOPolicyFactory getPolicyFactory()
    {
        return policyFactory;
    }

    public UMORetryNotifier getRetryNotifier()
    {
        return notifier;
    }
}

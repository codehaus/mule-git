/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.retry;

import org.mule.transport.FatalConnectException;

/**
 * A RetryTemplate can be used to invoke actions that may need to be retried i.e. connecting to an external process, 
 * or dispatching an event. How reties are made is dicatated by the {@link org.mule.umo.retry.PolicyFactory}. Policies
 * are stategies that define what happens between retries.
 * Also a {@link org.mule.umo.retry.RetryNotifier} that can be used to invoke actions between Retries for tracking and
 * notifications.
 *
 * @see org.mule.umo.retry.RetryNotifier
 * @see RetryCallback
 * @see org.mule.umo.retry.PolicyFactory
 */
public interface RetryTemplate
{
    RetryContext execute(RetryCallback callback) throws FatalConnectException;

    PolicyFactory getPolicyFactory();

    RetryNotifier getRetryNotifier();
}

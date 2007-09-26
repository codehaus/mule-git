/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.umo.retry;

import org.mule.providers.FatalConnectException;

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
public interface UMORetryTemplate
{
    UMORetryContext execute(UMORetryCallback callback) throws FatalConnectException;

    UMOPolicyFactory getPolicyFactory();

    UMORetryNotifier getRetryNotifier();
}

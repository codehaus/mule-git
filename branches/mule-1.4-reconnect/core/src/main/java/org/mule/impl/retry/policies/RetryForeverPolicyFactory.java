/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.retry.policies;

import org.mule.umo.retry.UMOTemplatePolicy;

/**
 * This policy allows the user to configure how namy times a retry should be attempted and
 * how much time to wait between retries.
 */
public class RetryForeverPolicyFactory extends SimpleRetryPolicyFactory
{

    public RetryForeverPolicyFactory()
    {
        super();
    }

    public RetryForeverPolicyFactory(long frequency)
    {
        this.frequency = frequency;
    }

    public UMOTemplatePolicy create()
    {
        return new SimpleRetryPolicy(frequency, RETRY_COUNT_FOREVER);
    }

}

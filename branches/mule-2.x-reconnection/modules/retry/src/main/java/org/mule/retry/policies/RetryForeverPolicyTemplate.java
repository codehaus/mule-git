/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.retry.policies;

import org.mule.api.retry.RetryPolicy;

/**
 * This policy is the same as {@link SimpleRetryPolicyTemplate} but will retry an infinite amount of times.
 * @see SimpleRetryPolicyTemplate
 */
public class RetryForeverPolicyTemplate extends SimpleRetryPolicyTemplate
{
    public RetryForeverPolicyTemplate()
    {
        super();
    }

    public RetryForeverPolicyTemplate(long frequency)
    {
        this.frequency = frequency;
    }

    public RetryPolicy createRetryInstance()
    {
        return new SimpleRetryPolicy(frequency, RETRY_COUNT_FOREVER);
    }

    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append("RetryForeverPolicy");
        sb.append("{frequency=").append(frequency);
        sb.append(", connectAsync=").append(isConnectAsynchronously());
        sb.append('}');
        return sb.toString();
    }
}

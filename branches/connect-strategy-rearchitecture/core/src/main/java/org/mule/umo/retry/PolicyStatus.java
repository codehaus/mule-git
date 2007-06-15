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

/**
 * When a policy is executed, it can be in two states -
 * - ok: The policy is active
 * - exhausted: The policy has run through the actions for the policy
 *
 * For example, a RetryPolicy may have a RetryCount - how many times the policy can be invoked.
 * Once the retryCount has been reached, the policy is exhausted and cannot be used again.
 */
public class PolicyStatus
{
    private boolean exhausted = false;
    private boolean ok = false;
    private Throwable trowable;

    public static PolicyStatus policyExhaused(Throwable t)
    {
        return new PolicyStatus(true, t);
    }

    public static PolicyStatus policyOk()
    {
        return new PolicyStatus();
    }

    protected PolicyStatus()
    {
        this.ok = true;
    }

    protected PolicyStatus(boolean exhausted, Throwable trowable)
    {
        this.exhausted = exhausted;
        this.trowable = trowable;
    }

    public boolean isExhausted()
    {
        return exhausted;
    }

    public boolean isOk()
    {
        return ok;
    }

    public Throwable getThrowable()
    {
        return trowable;
    }
}

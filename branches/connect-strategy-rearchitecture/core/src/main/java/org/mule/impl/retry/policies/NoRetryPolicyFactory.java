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

import org.mule.umo.retry.PolicyStatus;
import org.mule.umo.retry.UMOPolicyFactory;
import org.mule.umo.retry.UMOTemplatePolicy;

/**
 * This polciy does what it says on the tin.  It will allow a {@link UMORetryTemplate} to execute
 * once and then stop.
 */
public class NoRetryPolicyFactory implements UMOPolicyFactory
{
    public UMOTemplatePolicy create()
    {
        return new NoRetryPolicy();
    }

    protected static class NoRetryPolicy implements UMOTemplatePolicy
    {
        public PolicyStatus applyPolicy()
        {
            return PolicyStatus.policyOk();
        }
    }
}

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
 * This is the main Reptry SPI. Any code executed in the {@link #doWork} method will be subject to any Retry Policies
 * associated with the {@link org.mule.umo.retry.UMORetryTemplate}. If {@link #doWork} throws an exception the operation
 * will be attempted again until the Retry Policy has been exhausted. Note that Reties cn be wrapped in a transaction to ensure
 * the work is automic.
 *
 * @see org.mule.umo.retry.UMORetryTemplate
 * @see org.mule.umo.retry.UMOTemplatePolicy
 *
 */

public interface UMORetryCallback
{
    public void doWork() throws Exception;

    public String getWorkDescription();
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.models;

import org.mule.umo.Invocation;
import org.mule.umo.UMOException;
import org.mule.umo.UMOInterceptor;
import org.mule.umo.UMOMessage;
import org.mule.umo.lifecycle.Disposable;

/**
 * This interceptor detects when it has been prematurely disposed (i.e., intercept is
 * called after disposal); in this case, it throws an exception.
 */
public class TestInterceptor implements Disposable, UMOInterceptor
{
    private boolean disposed = false;

    public UMOMessage intercept(Invocation invocation) throws UMOException
    {
        // Pause to give the SedaComponent thread that scheduled this work
        // time to perform its (incorrect) finalization code.
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            // ignore
        }

        if (disposed)
        {
            throw new IllegalStateException("TestInterceptor has already been disposed.");
        }

        UMOMessage ret = invocation.execute();
        return ret;
    }

    public void dispose()
    {
        disposed = true;
    }
}

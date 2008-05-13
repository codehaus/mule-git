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

/**
 * This component simulates work during the reception of an Integer message and
 * provides a method to determine whether it was actually called.
 */
public class TestComponent
{
    private boolean called = false;

    public void call(Integer in)
    {
        // Simulate work delay.
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            // ignore
        }

        called = true;
    }

    public boolean isCalled()
    {
        return called;
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport;

import org.mule.api.MuleException;

import org.apache.commons.lang.Validate;

/**
 * This is just a {@link RuntimeException} that will encapsulate a
 * {@link MuleException} so it can be thrown in places where only
 * {@link RuntimeException RuntimeExceptions} can be thrown. It has a
 * {@link #getOriginalMuleException() getter method} that allows the recovery of the
 * original exception.
 * <p>
 * Example of intended usage:
 * 
 * <pre>
 * try
 * {
 *     WaitableBoolean connected = new WaitableBoolean(false);
 *     ...
 *     // some other thread controls the value of the WaitableBoolean variable
 *     ... 
 *     connected.whenTrue(new Runnable()
 *     {
 *         public void run()
 *         {
 *             try
 *             {
 *                 doSomethingThatWillThrowAMuleException();
 *             }
 *             catch (MuleException e)
 *             {
 *                 throw new MuleExceptionAsRuntimeException(e);
 *             }
 *         }
 *     });
 * }
 * catch (MuleExceptionAsRuntimeException e)
 * {
 *     throw e.getOriginalMuleException();
 * }
 * </pre>
 */
@SuppressWarnings("serial")
public class MuleExceptionAsRuntimeException extends RuntimeException
{
    /**
     * Reference to the original encapsulated exception.
     */
    private final MuleException originalMuleException;

    /**
     * @param originalMuleException the {@link MuleException} that will be
     *            encapsulated.
     */
    public MuleExceptionAsRuntimeException(MuleException originalMuleException)
    {
        super(originalMuleException);
        Validate.notNull(originalMuleException, "The encapsulated originalMuleException cannot be null");
        this.originalMuleException = originalMuleException;
    }

    /**
     * @return the original {@link MuleException} that was used to create this one.
     */
    public MuleException getOriginalMuleException()
    {
        return originalMuleException;
    }
}

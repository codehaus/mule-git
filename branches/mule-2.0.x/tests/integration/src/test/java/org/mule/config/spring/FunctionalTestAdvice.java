/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring;

import org.mule.util.concurrent.Latch;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class FunctionalTestAdvice implements MethodBeforeAdvice
{

    private Latch latch = new Latch();
    private Object[] args;

    public void before(Method method, Object[] args, Object target) throws Throwable
    {
        this.args = args;
        latch.countDown();
    }

    public Object[] getArgs(long ms) throws InterruptedException
    {
        latch.await(ms, TimeUnit.MILLISECONDS);
        return args;
    }

}

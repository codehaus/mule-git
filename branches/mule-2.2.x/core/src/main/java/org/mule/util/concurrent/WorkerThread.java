/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util.concurrent;


/**
 * Simple thread which does some work, then stores an optional result object and any exception which occurred.
 */
public abstract class WorkerThread extends Thread
{
    private Object result;
    private Exception exception;
    
    @Override
    public void run() 
    {            
        try
        {
            doWork();
        }
        catch (Exception e)
        {
            exception = e;
        }
    }
    
    public Object getResult()
    {
        return result;
    }

    protected void setResult(Object result)
    {
        this.result = result;
    }

    public Exception getException()
    {
        return exception;
    }

    /**
     * @throws an exception which will be retrievable after the thread has run
     */
    abstract protected void doWork() throws Exception;
}



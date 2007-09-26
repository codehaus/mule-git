/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.retry.async;

import org.mule.umo.UMOMessage;
import org.mule.umo.retry.UMORetryContext;

import java.util.Map;

/**
 * An implementation of {@link org.mule.umo.retry.UMORetryContext} used when a {@link org.mule.umo.retry.UMORetryTemplate}
 * is execute in a separate thread using the {@link org.mule.impl.retry.async.AsynchronousRetryTemplate}. A FutureRetryContext
 * is a proxy to a real {@link org.mule.umo.retry.UMORetryContext} that provides access to the real context once it
 * becomes available.
 */
public class FutureRetryContext implements UMORetryContext
{
    private UMORetryContext delegate;

    public FutureRetryContext()
    {
    }

    void setDelegateContext(UMORetryContext context)
    {
        this.delegate = context;
    }

    public boolean isReady()
    {
        return delegate!=null;
    }

    protected void checkState()
    {
        if(!isReady())
        {
            throw new IllegalStateException("Cannot perform operations on a FutureRetryContext until isReady() returns true");
        }
    }

    //@java.lang.Override
    public void addReturnMessage(UMOMessage result)
    {
        checkState();
        delegate.addReturnMessage(result);
    }

    //@java.lang.Override
    public String getDescription()
    {
        checkState();
        return delegate.getDescription();
    }

    //@java.lang.Override
    public UMOMessage getFirstReturnMessage()
    {
        checkState();
        return delegate.getFirstReturnMessage();
    }

    //@java.lang.Override
    public Map getMetaInfo()
    {
        checkState();
        return delegate.getMetaInfo();
    }

    //@java.lang.Override
    public UMOMessage[] getReturnMessages()
    {
        checkState();
        return delegate.getReturnMessages();
    }

    //@java.lang.Override
    public void setMetaInfo(Map metaInfo)
    {
        checkState();
        delegate.setMetaInfo(metaInfo);
    }

    //@java.lang.Override
    public void setReturnMessages(UMOMessage[] returnMessages)
    {
        checkState();
        delegate.setReturnMessages(returnMessages);
    }
}

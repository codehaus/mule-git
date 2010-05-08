/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.processor;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Can be used for simple {@link MessageProcessor} that use the {@link MuleEvent} to
 * log or send notifications etc. But don't modify it any way or do anything with
 * message flow.
 */
public abstract class AbstractMessageObserver implements MessageProcessor
{

    protected Log logger = LogFactory.getLog(getClass());

    public MuleEvent process(MuleEvent event) throws MuleException
    {
        // Really need to make event/message immutable before invoking observe
        observe(event);
        return event;
    }

    public abstract void observe(MuleEvent event);
}

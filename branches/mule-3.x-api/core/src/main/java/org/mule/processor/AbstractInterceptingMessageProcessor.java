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

import org.mule.api.processor.InterceptingMessageProcessor;
import org.mule.api.processor.MessageProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractInterceptingMessageProcessor implements InterceptingMessageProcessor
{
    protected Log logger = LogFactory.getLog(getClass());

    protected MessageProcessor next;

    public void setNext(MessageProcessor next)
    {
        this.next = next;
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.processor;

import org.mule.api.MuleEvent;

/**
 * <p>
 * Processes {@link MuleEvent}'s intercepting another {@link MessageProcessor}. It is
 * the InterceptingMessageProcessor's responsibility to invoke the next
 * {@link MessageProcessor}.
 * </p>
 * Although not normal, it is valid for the <i>next</i> MessageProcessor to be
 * <i>null</i> and implementations should handle this case.
 * 
 * @since 3.0
 */
public interface InterceptingMessageProcessor extends MessageProcessor
{
    /**
     * Configures {@link MessageProcessor} that this MessageProcessor intercepts
     * 
     * @param next
     */
    void setNext(MessageProcessor next);
}

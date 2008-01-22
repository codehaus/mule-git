/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.usecases;

import org.mule.api.EventContext;
import org.mule.api.lifecycle.Callable;

public class ReceiverComponent implements Callable
{
    public Object onCall(EventContext eventContext) throws Exception
    {
        return "Received: " + eventContext.getMessageAsString();
    }
}

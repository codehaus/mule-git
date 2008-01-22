/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.component.simple;

import org.mule.api.EventContext;
import org.mule.api.component.EchoService;

/**
 * <code>EchoComponent</code> will log the message and return the payload back as
 * the result.
 */
public class EchoComponent extends LogComponent implements EchoService
{

    public Object onCall(EventContext context) throws Exception
    {
        super.onCall(context);
        return context.transformMessage();
    }

    public String echo(String echo)
    {
        return echo;
    }

}

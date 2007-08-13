/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.providers.jms;

import org.mule.tck.functional.EventCallback;
import org.mule.umo.UMOEventContext;

/**
 * Comment
 */
public class JmsEventRollback implements EventCallback
{

    public void eventReceived(UMOEventContext context, Object component) throws Exception
    {
        throw new IllegalStateException("Rollback");
    }
}

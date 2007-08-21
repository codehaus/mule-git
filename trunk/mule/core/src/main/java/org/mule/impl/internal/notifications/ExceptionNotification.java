/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.internal.notifications;

import org.mule.umo.manager.UMOServerNotification;

public class ExceptionNotification extends UMOServerNotification
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -43091546451476239L;
    public static final int EXCEPTION_ACTION = EXCEPTION_EVENT_ACTION_START_RANGE + 1;

    private static final transient String[] ACTIONS = new String[] {"exception"};

    private Throwable exception;

    public ExceptionNotification(Throwable exception)
    {
        super(exception, EXCEPTION_ACTION);
        this.exception = exception;
    }

    public Throwable getException()
    {
        return this.exception;
    }

    protected String getActionName(int action)
    {
        int i = action - EXCEPTION_EVENT_ACTION_START_RANGE;
        if (i - 1 > ACTIONS.length)
        {
            return String.valueOf(action);
        }
        return ACTIONS[i - 1];
    }

    public String getType()
    {
        return TYPE_ERROR;
    }
}

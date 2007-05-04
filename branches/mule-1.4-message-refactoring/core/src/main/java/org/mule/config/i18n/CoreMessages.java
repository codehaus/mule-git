/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.i18n;

import org.mule.util.DateUtils;

import java.util.Date;

public class CoreMessages extends Object
{
    private static final String BUNDLE_NAME = "core";
    
    public static Message shutdownNormally(Date date)
    {
        return MessageFactory.createMessage(BUNDLE_NAME, 7, date);
    }

    public static Object serverWasUpForDuration(long duration)
    {
        String formattedDuration = DateUtils.getFormattedDuration(duration);
        return MessageFactory.createMessage(BUNDLE_NAME, 8, formattedDuration);
    }
    
}



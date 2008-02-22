/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http.functional;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import junit.framework.Assert;

public class CookieTestComponent extends Object implements Callable
{

    public Object onCall(MuleEventContext context) throws Exception
    {
        Assert.assertNotNull(context.getMessage().getProperty("customCookie"));
        return "success";
    }
    
}



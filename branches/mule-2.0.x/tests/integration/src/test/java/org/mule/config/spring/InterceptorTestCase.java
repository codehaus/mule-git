/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring;

import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.module.client.MuleClient;
import org.mule.api.MuleException;
import org.mule.api.MuleEvent;
import org.mule.api.MuleEventContext;

public class InterceptorTestCase extends FunctionalTestCase
{

    public static final String MESSAGE = "boo";

    protected String getConfigResources()
    {
        return "org/mule/config/spring/interceptor-test.xml";
    }

    public void testInterceptor() throws MuleException, InterruptedException
    {
        MuleClient client = new MuleClient();
        client.send("vm://in", MESSAGE, null);
        FunctionalTestAdvice advice = (FunctionalTestAdvice) muleContext.getRegistry().lookupObject("advice");
        assertNotNull("Cannot find advice", advice);
        Object[] args = advice.getArgs(RECEIVE_TIMEOUT);
        assertNotNull("Null args", args);
        assertEquals("Single argument", 1, args.length);
        MuleEventContext context = (MuleEventContext) args[0];
        // by the time the message is delivered to the component, it is transformed
        assertEquals("Bad message", FunctionalTestComponent.received(MESSAGE), context.getMessageAsString());
    }

}

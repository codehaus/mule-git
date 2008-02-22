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

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class HttpCookieTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "http-cookie-test.xml";
    }

    /**
     * Dispatches a request to an endpoint configured with Cookies. The component that receives
     * the request asserts that the proper cookies were sent.
     */
    public void testCookies() throws Exception
    {
        MuleClient client = new MuleClient();
        MuleMessage message = client.send("vm://vm-in", "foobar", null);
        assertNotNull(message);
        assertNull(message.getExceptionPayload());
    }

}

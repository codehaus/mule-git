/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.wssecurity.testcases;

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import java.util.Properties;

public class AxisWsSecurityOnInboundTestCase extends FunctionalTestCase
{
    public void testGoodUserNameEncrypted () throws Exception
    {
        MuleClient client = new MuleClient(); 
        Properties props = new Properties();

        UMOMessage m = client.send("Test1", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    public void testBadUserNameEncrypted () throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage m = null;
        try
        {
            m = client.send("Test2", "Test", null);
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
        assertNull(m);
    }

    protected String getConfigResources ()
    {
        return "axis-wssecurity-mule-config.xml";
    }
}
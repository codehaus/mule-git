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

import org.apache.ws.security.handler.WSHandlerConstants;

public class XfireWsSecurityOnOutboundTestCase extends FunctionalTestCase
{
    public void testGoodUserNameTokenAuthentication () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // User name to send
        props.setProperty(WSHandlerConstants.USER, "gooduser");

        UMOMessage m = client.send("vm://testin", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    public void testBadUserNameTokenAuthentication () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // User name to send
        props.setProperty(WSHandlerConstants.USER, "baduser");

        UMOMessage m = null;
        try
        {
            m = client.send("vm://testin", "Test", props);
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
        assertNull(m);
    }

    public void testGoodUserNameEncrypted () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // User name to send
        props.setProperty(WSHandlerConstants.USER, "mulealias");

        UMOMessage m = client.send("vm://testin2", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    public void testBadUserNameEncrypted () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // User name to send
        props.setProperty(WSHandlerConstants.USER, "myBadAlias");

        UMOMessage m = null;
        try
        {
            m = client.send("vm://testin2", "Test", props);
        }
        catch (Exception e)
        {
            assertNotNull(e);
        }
        assertNull(m);
    }

    public void testSignedSoapMessage () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // User in keystore
        props.setProperty(WSHandlerConstants.USER, "mulealias");

        UMOMessage m = client.send("vm://testin3", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    protected String getConfigResources ()
    {
        return "wssecurity-mule-config-for-outbound.xml";
    }
}
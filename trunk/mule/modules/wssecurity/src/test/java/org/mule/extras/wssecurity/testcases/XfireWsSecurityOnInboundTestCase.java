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

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

public class XfireWsSecurityOnInboundTestCase extends FunctionalTestCase
{
    public void testGoodUserNameTokenAuthentication () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // Password type : text or digest
        props.setProperty(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // User name to send
        props.setProperty(WSHandlerConstants.USER, "gooduser");

        UMOMessage m = client.send("Test1", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    public void testBadUserNameTokenAuthentication () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // Password type : text or digest
        props.setProperty(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // User name to send
        props.setProperty(WSHandlerConstants.USER, "baduser");

        UMOMessage m = null;
        try
        {
            m = client.send("xfire:http://localhost:8282/MySecuredUMO?connector=WsSecurity&method=echo", "Test", props);
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
        // Property file containing the Encryption properties
        props.setProperty(WSHandlerConstants.ENC_PROP_FILE, "out-encrypted-security.properties");

        UMOMessage m = client.send("Test3", "Test", props);
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
        // Property file containing the Encryption properties
        props.setProperty(WSHandlerConstants.ENC_PROP_FILE, "out-encrypted-security.properties");

        UMOMessage m = null;
        try
        {
            m = client.send("xfire:http://localhost:8282/MySecuredUMO?method=echo", "Test", props);
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
        // Configuration for accessing private key in keystore
        props.setProperty(WSHandlerConstants.SIG_PROP_FILE, "out-signed-security.properties");

        UMOMessage m = client.send("Test5", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    protected String getConfigResources ()
    {
        return "wssecurity-mule-config-for-inbound.xml";
    }
}
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

public class XfireSamlTestCase extends FunctionalTestCase
{
    // The test cases have been suppressed because for JDK 1.4, the Xerces parser
    // must be in an endorsed file for SAML to work. Everything works fine on JDK 1.5

    public void testBogus () throws Exception
    {
        // no test
    }

    public void _testGoodUnsignedSamlTokenAuthentication () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // saml configuration file
        props.setProperty(WSHandlerConstants.SAML_PROP_FILE, "saml.properties");
        
        UMOMessage m = client.send("unsignedAddr", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    public void _testGoodSignedSamlTokenAuthentication () throws Exception
    {
        MuleClient client = new MuleClient();
        Properties props = new Properties();

        // saml configuration file
        props.setProperty(WSHandlerConstants.SAML_PROP_FILE, "saml.properties");
        
        UMOMessage m = client.send("signedAddr", "Test", props);
        assertNotNull(m);
        assertTrue(m.getPayload() instanceof String);
        assertTrue(m.getPayload().equals("Test"));
    }

    protected String getConfigResources ()
    {
        return "wssecurity-mule-saml-config.xml";
    }
}
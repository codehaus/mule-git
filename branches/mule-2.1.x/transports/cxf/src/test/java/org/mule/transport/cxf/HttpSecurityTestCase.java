/*
 * $Id: HttpSecurityTestCase.java 11449 2008-03-20 12:27:50Z dandiep $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.cxf;

import org.mule.api.MuleContext;
import org.mule.api.security.tls.TlsConfiguration;
import org.mule.tck.FunctionalTestCase;
import org.mule.util.IOUtils;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;


public class HttpSecurityTestCase extends FunctionalTestCase 
{
    
    private static String soapRequest = 
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:unk=\"http://unknown.namespace/\">" +
           "<soapenv:Header/>" +
           "<soapenv:Body>" +
              "<unk:echo>" +         
                 "<arg0>asdf</arg0>" +
              "</unk:echo>" +
           "</soapenv:Body>" +
        "</soapenv:Envelope>";
    
    /**
     * This test doesn't work in Maven because Mule can't load the keystores from the jars
     * @throws Exception
     */
    public void testBasicAuth() throws Exception
    {
        HttpClient client = new HttpClient();
        Credentials credentials = new UsernamePasswordCredentials("admin", "admin");
        client.getState().setCredentials(AuthScope.ANY, credentials);
        client.getState().setAuthenticationPreemptive(true);

        PostMethod method = new PostMethod("https://localhost:60443/services/Echo");
        method.setDoAuthentication(true);
        StringRequestEntity requestEntity = new StringRequestEntity(soapRequest, "text/plain", "UTF-8");
        method.setRequestEntity(requestEntity);
        
        int result = client.executeMethod(method);

        assertEquals(200, result);
        System.out.println(method.getResponseBodyAsString());

        credentials = new UsernamePasswordCredentials("admin", "adminasd");
        client.getState().setCredentials(AuthScope.ANY, credentials);
        client.getState().setAuthenticationPreemptive(true);

        result = client.executeMethod(method);

        assertEquals(401, result);
    }

//    public void testBasicAuthWithCxfClient() throws Exception
//    {
//    	MuleClient client = new MuleClient();
//    	
//    	String payload = "Hello";
//    	MuleMessage result = client.send("cxfOutbound", new DefaultMuleMessage(payload));    	
//    	assertEquals(payload, result.getPayloadAsString());
//    }

    @Override
    protected String getConfigResources()
    {
        return "http-security-conf.xml";
    }   
    
}   

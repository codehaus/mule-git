/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.http.transformers;

import org.mule.config.MuleProperties;
import org.mule.impl.RequestContext;
import org.mule.providers.NullPayload;
import org.mule.providers.http.HttpConnector;
import org.mule.providers.http.HttpConstants;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOEvent;

import org.apache.commons.httpclient.HttpMethod;

public class ObjectToHttpClientMethodRequestTestCase extends AbstractMuleTestCase
{
    
    private void setupRequestContext(String url) throws Exception
    {
        UMOEvent event = getTestEvent("test");
        event.getMessage().setStringProperty(HttpConnector.HTTP_METHOD_PROPERTY, HttpConstants.METHOD_GET);
        event.getMessage().setStringProperty(MuleProperties.MULE_ENDPOINT_PROPERTY, url);
        RequestContext.setEvent(event);
    }

    // @Override
    protected void doTearDown() throws Exception
    {
        RequestContext.setEvent(null);
    }

    public void testUrlWithoutQuery() throws Exception
    {
        setupRequestContext("http://localhost:8080/services");

        ObjectToHttpClientMethodRequest transformer = new ObjectToHttpClientMethodRequest();
        // transforming NullPayload will make sure that no body=xxx query is added
        Object response = transformer.transform(NullPayload.getInstance());
        
        assertTrue(response instanceof HttpMethod);
        HttpMethod httpMethod = (HttpMethod) response;
        
        assertEquals(null, httpMethod.getQueryString());
    }
    
    public void testUrlWithQuery() throws Exception
    {
        setupRequestContext("http://localhost:8080/services?method=echo");
        
        ObjectToHttpClientMethodRequest transformer = new ObjectToHttpClientMethodRequest();
        // transforming NullPayload will make sure that no body=xxx query is added
        Object response = transformer.transform(NullPayload.getInstance());
        
        assertTrue(response instanceof HttpMethod);
        HttpMethod httpMethod = (HttpMethod) response;
        
        assertEquals("method=echo", httpMethod.getQueryString());
    }

    public void testUrlWithUnescapedQuery() throws Exception
    {
        setupRequestContext("http://mycompany.com/test?fruits=apple%20orange");
        
        ObjectToHttpClientMethodRequest transformer = new ObjectToHttpClientMethodRequest();
        // transforming NullPayload will make sure that no body=xxx query is added
        Object response = transformer.transform(NullPayload.getInstance());
        
        assertTrue(response instanceof HttpMethod);
        HttpMethod httpMethod = (HttpMethod) response;
        
        assertEquals("fruits=apple%20orange", httpMethod.getQueryString());
    }
    
    public void testAppendedUrl() throws Exception
    {
        setupRequestContext("http://mycompany.com/test?fruits=apple%20orange");
        
        ObjectToHttpClientMethodRequest transformer = new ObjectToHttpClientMethodRequest();
        // transforming a payload here will add it as body=xxx query parameter
        Object response = transformer.transform("test");
        
        assertTrue(response instanceof HttpMethod);
        HttpMethod httpMethod = (HttpMethod) response;
        
        assertEquals("fruits=apple%20orange&body=test", httpMethod.getQueryString());
    }
}



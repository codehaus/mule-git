/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.transport.AbstractMuleMessageFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;

public class HttpMuleMessageFactory extends AbstractMuleMessageFactory
{

    private boolean enableCookies;
    private String cookieSpec;

    public HttpMuleMessageFactory(MuleContext context)
    {
        super(context);
    }

    @Override
    protected Class<?>[] getSupportedTransportMessageTypes()
    {
        return new Class[]{HttpRequest.class};
    }

    @Override
    protected Object extractPayload(Object transportMessage, String encoding) throws Exception
    {
        HttpRequest httpRequest = (HttpRequest) transportMessage;
        Object body = httpRequest.getBody();
        if (body == null)
        {
            return httpRequest.getRequestLine().getUri();
        }
        else
        {
            return body;
        }
    }

    @Override
    protected void addProperties(MuleMessage message, Object transportMessage) throws Exception
    {
        super.addProperties(message, transportMessage);
        HttpRequest httpRequest = (HttpRequest) transportMessage;

        Map<String, Object> headers = convertHeadersToMap(httpRequest.getHeaders());
        rewriteConnectionAndKeepAliveHeaders(headers);

        RequestLine requestLine = httpRequest.getRequestLine();

        for (Iterator rhi = httpRequest.getHeaderIterator(); rhi.hasNext();)
        {
            Header header = (Header) rhi.next();
            String headerName = header.getName();
            Object headerValue = header.getValue();

            // fix Mule headers?
            if (headerName.startsWith("X-MULE"))
            {
                headerName = headerName.substring(2);
            }
            // Parse cookies?
            else if (headerName.equals(HttpConnector.HTTP_COOKIES_PROPERTY))
            {
                if (enableCookies)
                {
                    Cookie[] cookies = CookieHelper.parseCookies(header, cookieSpec);
                    if (cookies.length > 0)
                    {
                        // yum!
                        headerValue = cookies;
                    }
                    else
                    {
                        // bad cookies?!
                        continue;
                    }
                }
                else
                {
                    // no cookies for you!
                    continue;
                }
            }

            // accept header & value
            headers.put(headerName, headerValue);
        }

        headers.put(HttpConnector.HTTP_METHOD_PROPERTY, requestLine.getMethod());
        headers.put(HttpConnector.HTTP_REQUEST_PROPERTY, requestLine.getUri());
        headers.put(HttpConnector.HTTP_VERSION_PROPERTY, requestLine.getHttpVersion().toString());
        headers.put(HttpConnector.HTTP_COOKIE_SPEC_PROPERTY, cookieSpec);

        message.addProperties(headers);
    }

    private Map<String, Object> convertHeadersToMap(Header[] headers)
    {
        Map<String, Object> headersMap = new HashMap<String, Object>();
        for (int i = 0; i < headers.length; i++)
        {
            headersMap.put(headers[i].getName(), headers[i].getValue());
        }
        return headersMap;
    }

    private void rewriteConnectionAndKeepAliveHeaders(Map<String, Object> headers)
    {
        // rewrite Connection and Keep-Alive headers based on HTTP version
        String headerValue = null;
        if (!isHttp11(headers))
        {
            String connection = (String) headers.get(HttpConstants.HEADER_CONNECTION);
            if ((connection != null) && connection.equalsIgnoreCase("close"))
            {
                headerValue = "false";
            }
            else
            {
                headerValue = "true";
            }
        }
        else
        {
            headerValue = (headers.get(HttpConstants.HEADER_CONNECTION) != null ? "true" : "false");
        }

        headers.put(HttpConstants.HEADER_CONNECTION, headerValue);
        headers.put(HttpConstants.HEADER_KEEP_ALIVE, headerValue);
    }

    private boolean isHttp11(Map<String, Object> headers)
    {
        String httpVersion = (String) headers.get(HttpConnector.HTTP_VERSION_PROPERTY);
        if (HttpConstants.HTTP10.equalsIgnoreCase(httpVersion))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setEnableCookies(boolean enableCookies)
    {
        this.enableCookies = enableCookies;
    }

    public void setCookieSpec(String cookieSpec)
    {
        this.cookieSpec = cookieSpec;
    }

}

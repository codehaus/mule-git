/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.servlet;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.transport.AbstractMuleMessageFactory;
import org.mule.transport.http.HttpConstants;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletMuleMessageFactory extends AbstractMuleMessageFactory
{    
    public ServletMuleMessageFactory(MuleContext context)
    {
        super(context);
    }

    @Override
    protected Class<?>[] getSupportedTransportMessageTypes()
    {
        return new Class[] { HttpServletRequest.class };
    }

    @Override
    protected Object extractPayload(Object transportMessage, String encoding) throws Exception
    {
        HttpServletRequest request = (HttpServletRequest) transportMessage;
        
        String method = request.getMethod();
        if (HttpConstants.METHOD_GET.equalsIgnoreCase(method))
        {
            return queryString(request);
        }
        else
        {
            return request.getInputStream();
        }
    }

    protected String queryString(HttpServletRequest request)
    {
        StringBuilder buf = new StringBuilder(request.getRequestURI());
        
        String queryString = request.getQueryString();
        if (queryString != null)
        {
            buf.append("?");
            buf.append(queryString);
        }
        
        return buf.toString();
    }

    @Override
    protected void addProperties(MuleMessage message, Object transportMessage) throws Exception
    {
        HttpServletRequest request = (HttpServletRequest) transportMessage;

        setupRequestParameters(request, message);
        setupEncoding(request, message);
        setupUniqueId(request, message);
        setupContentType(request, message);
        setupCharacterEncoding(request, message);
        setupMessageProperties(request, message);
    }

    @SuppressWarnings("unchecked")
    protected void setupRequestParameters(HttpServletRequest request, MuleMessage message)
    {
        Enumeration<String> parameterNames = request.getParameterNames();            
        if (parameterNames != null)
        {
            Map<String, Object> parameterProperties = new HashMap<String, Object>();
            while (parameterNames.hasMoreElements())
            {
                String name = parameterNames.nextElement();            
                String key = ServletConnector.PARAMETER_PROPERTY_PREFIX + name;
                parameterProperties.put(key, request.getParameterValues(name)[0]);
            }
            
            ((DefaultMuleMessage) message).addInboundProperties(parameterProperties);
        }
    }
    
    protected void setupEncoding(HttpServletRequest request, MuleMessage message)
    {
        String contentType = request.getContentType();
        if (contentType != null)
        {
            int index = contentType.indexOf("charset");
            if (index > -1)
            {
                int semicolonIndex = contentType.lastIndexOf(";");
                if (semicolonIndex > index)
                {
                    message.setEncoding(contentType.substring(index + 8, semicolonIndex));
                }
                else
                {
                    message.setEncoding(contentType.substring(index + 8));
                }
            }
        }
    }

    protected void setupUniqueId(HttpServletRequest request, MuleMessage message)
    {
        try
        {
            // We wrap this call as on some App Servers (Websfear) it can cause an NPE
            HttpSession session = request.getSession(false);
            ((DefaultMuleMessage) message).setUniqueId(session.getId());
        }
        catch (Exception e)
        {
            // MuleMessage's default is good enough in this case
        }
    }
    
    protected void setupContentType(HttpServletRequest request, MuleMessage message)
    {
        String contentType = request.getContentType();
        
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ServletConnector.CONTENT_TYPE_PROPERTY_KEY, contentType);
     
        ((DefaultMuleMessage) message).addInboundProperties(properties);
    }
    
    protected void setupCharacterEncoding(HttpServletRequest request, MuleMessage message)
    {
        String characterEncoding = request.getCharacterEncoding();
        
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ServletConnector.CHARACTER_ENCODING_PROPERTY_KEY, characterEncoding);
     
        ((DefaultMuleMessage) message).addInboundProperties(properties);
    }

    private void setupMessageProperties(HttpServletRequest request, MuleMessage message)
    {
        Map<String, Object> messageProperties = new HashMap<String, Object>();
        
        Map<String, Object> parameters = buildParameterMap(request);
        messageProperties.put(ServletConnector.PARAMETER_MAP_PROPERTY_KEY, parameters);
        
        Map<String, Object> attributes = buildAttributeMap(request);
        messageProperties.put(ServletConnector.ATTRIBUTE_MAP_PROPERTY_KEY, attributes);
        
        Map<String, Object> headers = copyHeaders(request);
        messageProperties.put(ServletConnector.HEADER_MAP_PROPERTY_KEY, headers);
        
        ((DefaultMuleMessage) message).addInboundProperties(messageProperties);
    }

    protected Map<String, Object> buildParameterMap(HttpServletRequest request)
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        Map<?, ?> parameterMap = request.getParameterMap();
        if (parameterMap != null && parameterMap.size() > 0)
        {
            for (Map.Entry<?, ?> entry : parameterMap.entrySet())
            {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if (value != null)
                {
                    if (value.getClass().isArray() && ((Object[]) value).length == 1)
                    {
                        value = ((Object[]) value)[0];
                    }
                }
                
                parameters.put(key, value);
            }
        }
        
        return parameters;
    }

    protected Map<String, Object> buildAttributeMap(HttpServletRequest request)
    {
        Map<String, Object> attributes = new HashMap<String, Object>();
        
        for (Enumeration<?> e = request.getAttributeNames(); e.hasMoreElements();)
        {
            String key = (String) e.nextElement();
            attributes.put(key, request.getAttribute(key));
        }
        
        return attributes;
    }
    
    private Map<String, Object> copyHeaders(HttpServletRequest request)
    {        
        Map<String, Object> headers = new HashMap<String, Object>();        
        
        for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements();)
        {
            String key = (String)e.nextElement();
            String realKey = key;
            
            if (key.startsWith(HttpConstants.X_PROPERTY_PREFIX))
            {
                realKey = key.substring(2);
            }

            // Workaround for containers that strip the port from the Host header.
            // This is needed so Mule components can figure out what port they're on.
            String value = request.getHeader(key);
            if (HttpConstants.HEADER_HOST.equalsIgnoreCase(key)) 
            {
                realKey = HttpConstants.HEADER_HOST;
                int port = request.getLocalPort();
                if (!value.contains(":") && port != 80 && port != 443)
                {
                    value = value + ":" + port;
                }
            }
            
            headers.put(realKey, value);
        }
        
        return headers;
    }
}

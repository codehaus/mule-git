/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.soap;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.context.MuleContextAware;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.Callable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.i18n.CoreMessages;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebServiceWrapperComponent implements Callable, Initialisable, MuleContextAware
{
    protected transient Log logger = LogFactory.getLog(getClass());
    
    public static final String WS_SERVICE_URL = "ws.service.url";
    
    private String webServiceUrl;
    private boolean urlFromMessage = false;
    private Map wrapperProperties;
    private Map soapMethods;
    
    private MuleContext muleContext;

    public Object onCall(MuleEventContext eventContext) throws Exception
    {
        String tempUrl;
        if (urlFromMessage)
        {
            tempUrl = eventContext.getMessage().getStringProperty(WS_SERVICE_URL, null);
            if (tempUrl == null)
            {
                throw new IllegalArgumentException(
                    CoreMessages.propertyIsNotSetOnEvent(WS_SERVICE_URL).toString());
            }
        }
        else
        {
            tempUrl = webServiceUrl;
        }
        MuleMessage message = new DefaultMuleMessage(eventContext.transformMessage());
        OutboundEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getOutboundEndpoint(tempUrl);
        
        setPropertiesOnEndpoint(endpoint);
        
        if (soapMethods != null)
        {
            message.addProperties(soapMethods);
        }
        
        logger.info("Invoking Web Service: " + tempUrl);
        
        MuleMessage result = eventContext.sendEvent(message, endpoint);
        return result;
    }

    public void initialise() throws InitialisationException
    {
        if (webServiceUrl == null && !urlFromMessage)
        {
            throw new InitialisationException(CoreMessages.objectIsNull("webServiceUrl"), this);
        }
    }
    
    private void setPropertiesOnEndpoint(ImmutableEndpoint endpoint) throws Exception
    {
        if (wrapperProperties != null)
        {    
            endpoint.getProperties().putAll(wrapperProperties);
        }
    }
    
    public Map getWrapperProperties()
    {
        return wrapperProperties;
    }
    
    public void setWrapperProperties(Map wrapperProperties)
    {
        this.wrapperProperties = wrapperProperties;
    }

    public String getWebServiceUrl()
    {
        return webServiceUrl;
    }

    public void setWebServiceUrl(String webServiceUrl)
    {
        this.webServiceUrl = webServiceUrl;
    }

    public boolean isUrlFromMessage()
    {
        return urlFromMessage;
    }

    public void setUrlFromMessage(boolean urlFromMessage)
    {
        this.urlFromMessage = urlFromMessage;
    }

    public Map getSoapMethods()
    {
        return soapMethods;
    }

    public void setSoapMethods(Map soapMethods)
    {
        this.soapMethods = soapMethods;
    }

    public void setMuleContext(MuleContext context)
    {
        muleContext = context;
    }
}

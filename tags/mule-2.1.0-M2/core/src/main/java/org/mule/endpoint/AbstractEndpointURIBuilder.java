/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.endpoint;

import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.EndpointURIBuilder;
import org.mule.api.endpoint.MalformedEndpointException;
import org.mule.util.PropertiesUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * {@link UrlEndpointURIBuilder} is the default endpointUri strategy suitable for
 * most connectors
 */

public abstract class AbstractEndpointURIBuilder implements EndpointURIBuilder
{
    protected String address;
    protected String endpointName;
    protected String connectorName;
    protected String transformers;
    protected String responseTransformers;
    protected String userInfo;

    public EndpointURI build(URI uri) throws MalformedEndpointException
    {
        Properties props = getPropertiesForURI(uri);
        String replaceAddress = null;
        //If the address has been set as a parameter on the URI, then we must ensure that that value is used
        //for the address. We still call the setEndpoint() method so that other information on the URI
        //is still processed
        if (address != null)
        {
            replaceAddress = address;
            setEndpoint(uri, props);
            address = replaceAddress;
        }
        else
        {
            setEndpoint(uri, props);
        }

        EndpointURI ep = new MuleEndpointURI(address, endpointName, connectorName, transformers,
            responseTransformers, props, uri, userInfo);
        address = null;
        endpointName = null;
        connectorName = null;
        transformers = null;
        responseTransformers = null;
        return ep;
    }

    protected abstract void setEndpoint(URI uri, Properties props) throws MalformedEndpointException;

    protected Properties getPropertiesForURI(URI uri) throws MalformedEndpointException
    {
        Properties properties = PropertiesUtils.getPropertiesFromQueryString(uri.getQuery());

        String tempEndpointName = (String) properties.get(EndpointURI.PROPERTY_ENDPOINT_NAME);
        if (tempEndpointName != null)
        {
            this.endpointName = tempEndpointName;
        }
        // override the endpointUri if set
        String endpoint = (String) properties.get(EndpointURI.PROPERTY_ENDPOINT_URI);
        if (endpoint != null)
        {
            this.address = endpoint;
            address = decode(address, uri);
        }

        String cnnName = (String) properties.get(EndpointURI.PROPERTY_CONNECTOR_NAME);
        if (cnnName != null)
        {
            this.connectorName = cnnName;
        }

        transformers = (String) properties.get(EndpointURI.PROPERTY_TRANSFORMERS);
        if (transformers != null)
        {
            transformers = transformers.replaceAll(" ", ",");
        }
        responseTransformers = (String) properties.get(EndpointURI.PROPERTY_RESPONSE_TRANSFORMERS);
        if (responseTransformers != null)
        {
            responseTransformers = responseTransformers.replaceAll(" ", ",");
        }
        userInfo = uri.getUserInfo();
        return properties;
    }

    private String decode(String string, URI uri) throws MalformedEndpointException
    {
        try
        {
            //TODO RM* URGENT:
            return URLDecoder.decode(string, "UTF-8" /*RegistryContext.getConfiguration().getDefaultEncoding()*/);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new MalformedEndpointException(uri.toString(), e);
        }
    }
}

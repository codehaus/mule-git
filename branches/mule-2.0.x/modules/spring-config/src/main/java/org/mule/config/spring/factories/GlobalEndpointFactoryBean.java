/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.factories;

import org.mule.api.config.ConfigurationException;
import org.mule.api.endpoint.EndpointException;
import org.mule.api.endpoint.EndpointFactory;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.config.i18n.MessageFactory;
import org.mule.endpoint.EndpointURIEndpointBuilder;

/**
 * Spring FactoryBean used to create concrete instances of inbound endpoints
 */
public class GlobalEndpointFactoryBean extends AbstractEndpointFactoryBean
{

    public GlobalEndpointFactoryBean(EndpointURIEndpointBuilder global) throws EndpointException
    {
        super(global);
    }

    public GlobalEndpointFactoryBean()
    {
        super();
    }

    public Class getObjectType()
    {
        return EndpointURIEndpointBuilder.class;
    }

    public Object doGetObject() throws Exception
    {
        return this;
    }

}
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.email.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.generic.ParentDefinitionParser;
import org.mule.config.spring.parsers.specific.tls.TrustStoreDefinitionParser;
import org.mule.config.spring.parsers.specific.tls.ClientKeyStoreDefinitionParser;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.email.ImapsConnector;

/**
 * Reigsters a Bean Definition Parser for handling <code><tcp:connector></code> elements.
 *
 */
public class ImapsNamespaceHandler extends AbstractMuleNamespaceHandler
{
    public void init()
    {
        registerStandardTransportEndpoints(ImapsConnector.IMAPS, URIBuilder.USERHOST_ATTRIBUTES);
        registerConnectorDefinitionParser(ImapsConnector.class);
        registerBeanDefinitionParser("tls-trust-store", new TrustStoreDefinitionParser());
        registerBeanDefinitionParser("tls-client", new ClientKeyStoreDefinitionParser());
    }
}
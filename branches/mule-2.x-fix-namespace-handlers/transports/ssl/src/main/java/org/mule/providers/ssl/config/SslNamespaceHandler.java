/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.ssl.config;

import org.mule.config.spring.handlers.AbstractIgnorableNamespaceHandler;
import org.mule.providers.ssl.SslConnector;

/**
 * Registers a Bean Definition Parser for handling <code><ssl:connector></code> elements.
 */
public class SslNamespaceHandler extends AbstractIgnorableNamespaceHandler
{
    public void init()
    {
        // All nested elements are now in this parser's postProcess()...
        registerBeanDefinitionParser("connector", new SslDefinitionParser(SslConnector.class, true));
        //registerBeanDefinitionParser("ssl-key-store", new CompoundElementDefinitionParser());
        //registerBeanDefinitionDecorator("ssl-client", new CompoundElementDefinitionDecorator());
        //registerBeanDefinitionParser("ssl-client", new CompoundElementDefinitionParser());
        //registerBeanDefinitionParser("ssl-server", new CompoundElementDefinitionParser());
        //registerBeanDefinitionParser("ssl-protocol-handler", new CompoundElementDefinitionParser());

        /*
            These MUST be excluded, as they don't follow a 1:1 element:bean convention. The parser
            handles them in postProcess().

            I wonder if we could enforce the postProcess() & registerIgnoredElement() binding...
         */
        registerIgnoredElement("ssl-key-store");
        registerIgnoredElement("ssl-client");
        registerIgnoredElement("ssl-server");
        registerIgnoredElement("ssl-protocol-handler");
    }
    
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extras.spring.config.handlers;

import org.mule.extras.spring.config.parsers.ConnectionStrategyDefinitionParser;
import org.mule.extras.spring.config.parsers.CustomElementDefinitionParser;
import org.mule.extras.spring.config.parsers.EndpointDefinitionParser;
import org.mule.extras.spring.config.parsers.ManagementContextDefinitionParser;
import org.mule.extras.spring.config.parsers.ModelDefinitionParser;
import org.mule.extras.spring.config.parsers.ServiceDescriptorDefinitionParser;
import org.mule.extras.spring.config.parsers.ServiceOverridesDefinitionParser;
import org.mule.extras.spring.config.parsers.SimpleChildDefinitionParser;
import org.mule.extras.spring.config.parsers.SourceTypeDefinitionParser;
import org.mule.extras.spring.config.parsers.ThreadingProfileDefinitionParser;
import org.mule.extras.spring.config.parsers.KnownTypeElementDefinitionParser;
import org.mule.model.CallableEntryPointResolver;
import org.mule.routing.inbound.InboundMessageRouter;
import org.mule.routing.outbound.OutboundMessageRouter;
import org.mule.routing.response.ResponseMessageRouter;
import org.mule.impl.container.RmiContainerContext;
import org.mule.impl.container.PropertiesContainerContext;
import org.mule.impl.container.JndiContainerContext;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class MuleNamespaceHandler extends AbstractHierarchicalNamespaceHandler
{

    public void init()
    {
        //Common elements
        registerBeanDefinitionParser("mule-configuration", new ManagementContextDefinitionParser());
        registerBeanDefinitionParser("threading-profile", new ThreadingProfileDefinitionParser());
        registerBeanDefinitionParser("exception-strategy", new SimpleChildDefinitionParser("execptionStrategy", null));

        //Connector elements
        registerBeanDefinitionParser("dispatcher-threading-profile", new ThreadingProfileDefinitionParser());
        registerBeanDefinitionParser("receiver-threading-profile", new ThreadingProfileDefinitionParser());
        registerBeanDefinitionParser("dispatcher-connection-straqtegy", new ConnectionStrategyDefinitionParser());
        registerBeanDefinitionParser("receiver-connection-straqtegy", new ConnectionStrategyDefinitionParser());
        registerBeanDefinitionParser("service-overrides", new ServiceOverridesDefinitionParser());
        registerBeanDefinitionParser("custom-connector", new CustomElementDefinitionParser());

        //Transformer elements
        registerBeanDefinitionParser("custom-transformer", new CustomElementDefinitionParser());
        registerBeanDefinitionParser("source-type", new SourceTypeDefinitionParser());

        //Endpoint elements
        registerBeanDefinitionParser("endpoint", new EndpointDefinitionParser());

        //Container contexts
        registerBeanDefinitionParser("custom-container", new CustomElementDefinitionParser());
        registerBeanDefinitionParser("rmi-container", new KnownTypeElementDefinitionParser(RmiContainerContext.class));
        registerBeanDefinitionParser("jndi-container", new KnownTypeElementDefinitionParser(JndiContainerContext.class));
        registerBeanDefinitionParser("properties-container", new KnownTypeElementDefinitionParser(PropertiesContainerContext.class));

        //Model Elements
        registerBeanDefinitionParser("model-seda", new ModelDefinitionParser("seda"));
        registerBeanDefinitionParser("model-seda-optimised", new ModelDefinitionParser("seda-optimised"));
        registerBeanDefinitionParser("model-simple", new ModelDefinitionParser("simple"));
        registerBeanDefinitionParser("model-pipeline", new ModelDefinitionParser("pipeline"));
        registerBeanDefinitionParser("custom-model", new ModelDefinitionParser("custom"));

        registerBeanDefinitionParser("custom-lifecycle-adaptor", new SimpleChildDefinitionParser("lifecycleAdapater", null));
        registerBeanDefinitionParser("callable-entrypoint-resolver", new SimpleChildDefinitionParser("entrypointResolver", CallableEntryPointResolver.class));
        registerBeanDefinitionParser("custom-entrypoint-resolver", new SimpleChildDefinitionParser("entrypointResolver", null));
        //registerBeanDefinitionParser("method-entrypoint-resolver", new SimpleChildDefinitionParser("entrypointResolver", MethodEntryPointResolver.class));
        //registerBeanDefinitionParser("reflection-entrypoint-resolver", new SimpleChildDefinitionParser("entrypointResolver", MethodEntryPointResolver.class));
        //registerBeanDefinitionParser("non-void-entrypoint-resolver", new SimpleChildDefinitionParser("entrypointResolver", NonVoidEntryPointResolver.class));

        //Service Elements
        registerBeanDefinitionParser("service", new ServiceDescriptorDefinitionParser());
        registerBeanDefinitionParser("inbound-router", new SimpleChildDefinitionParser("inboundRouter", InboundMessageRouter.class));
        registerBeanDefinitionParser("outbound-router", new SimpleChildDefinitionParser("outboundRouter", OutboundMessageRouter.class));
        registerBeanDefinitionParser("response-router", new SimpleChildDefinitionParser("responseRouter", ResponseMessageRouter.class));


    }
}

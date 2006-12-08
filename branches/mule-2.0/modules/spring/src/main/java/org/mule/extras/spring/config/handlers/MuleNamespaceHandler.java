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

import org.mule.ManagementContext;
import org.mule.config.MuleProperties;
import org.mule.config.ThreadingProfile;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.extras.spring.config.AbstractChildDefinitionParser;
import org.mule.extras.spring.config.AbstractMuleSingleBeanDefinitionParser;
import org.mule.impl.DefaultExceptionStrategy;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.SimpleRetryConnectionStrategy;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.util.ClassUtils;
import org.mule.util.StringUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.1 $
 */
public class MuleNamespaceHandler extends NamespaceHandlerSupport
{


    public void init()
    {
        registerBeanDefinitionParser("mule-configuration", new MuleBeanDefinitionParser());
        registerBeanDefinitionParser("threading-profile", new ThreadingProfileDefinitionParser());
        registerBeanDefinitionParser("exception-strategy", new ExceptionStrategyDefinitionParser());
        registerBeanDefinitionParser("dispatcher-threading-profile", new ThreadingProfileDefinitionParser());
        registerBeanDefinitionParser("receiver-threading-profile", new ThreadingProfileDefinitionParser());
        registerBeanDefinitionParser("dispatcher-connection-straqtegy", new ConnectionStrategyDefinitionParser());
        registerBeanDefinitionParser("receiver-connection-straqtegy", new ConnectionStrategyDefinitionParser());
        registerBeanDefinitionParser("service-overrides", new ServiceOverridesDefinitionParser());
        registerBeanDefinitionParser("endpoint", new EndpointDefinitionParser());
        registerBeanDefinitionParser("custom-connector", new CustomConnectorDefinitionParser());
        //todo registerBeanDefinitionParser("source-type", new TransformerSourceTypeDefinitionParser());

    }

    public static class MuleBeanDefinitionParser extends AbstractMuleSingleBeanDefinitionParser
    {


        protected Class getBeanClass(Element element)
        {
            return ManagementContext.class;
        }
    }

    public static class ThreadingProfileDefinitionParser extends AbstractChildDefinitionParser
    {

        public ThreadingProfileDefinitionParser() {
            registerAttributeMapping("poolExhaustedAction", "poolExhaustedActionString");
        }

        protected Class getBeanClass(Element element) {
            return ThreadingProfile.class;
        }

        protected String getPropertyName(Element e) {
            String name = e.getLocalName();
            if ("receiver-threading-profile".equals(name)) {
                return "receiverThreadingProfile";
            } else if ("dispatcher-threading-profile".equals(name)) {
                return "dispatcherThreadingProfile";
            } else {
                return "threadingProfile";
            }
        }
    }

    public static class ExceptionStrategyDefinitionParser extends AbstractChildDefinitionParser {

        protected Class getBeanClass(Element element) {
            return DefaultExceptionStrategy.class;
        }

        protected String getPropertyName(Element e) {
            return "exceptionListener";
        }
    }

    public static class ConnectionStrategyDefinitionParser extends AbstractChildDefinitionParser {

        public static final Class DEFAULT_CONNECTION_STRATEGY = SimpleRetryConnectionStrategy.class;

        protected Class getBeanClass(Element element) {
            //generateBeanNameIfNotSet(element, JdmkAgent.class);
            String clazz = element.getAttribute("class");
            if (clazz != null) {
                try {
                    return ClassUtils.loadClass(clazz, getClass());
                } catch (ClassNotFoundException e) {
                    throw new FatalBeanException(new Message(Messages.CLASS_X_NOT_FOUND, clazz).getMessage(), e);
                }
            }
            return DEFAULT_CONNECTION_STRATEGY;
        }

        protected String getPropertyName(Element e) {
            String name = e.getLocalName();
            if ("receiver-connection-strategy".equals(name)) {
                return "receiverConnectionStrategy";
            } else if ("dispatcher-connection-strategy".equals(name)) {
                return "dispatcherConnectionStrategy";
            } else {
                return "connectionStrategy";
            }
        }
    }

    public static class ServiceOverridesDefinitionParser extends AbstractChildDefinitionParser
    {

        protected Class getBeanClass(Element element)
        {
            return ManagementContext.class;
        }


        protected String getPropertyName(Element e)
        {
            return "serviceOverrides";
        }


        protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
        {
            Map overrides = new HashMap();
            addOverride(overrides, element, "messageReceiver", MuleProperties.CONNECTOR_MESSAGE_RECEIVER_CLASS);
            addOverride(overrides, element, "transactedessageReceiver", MuleProperties.CONNECTOR_TRANSACTED_MESSAGE_RECEIVER_CLASS);
            addOverride(overrides, element, "dispatcherFactory", MuleProperties.CONNECTOR_DISPATCHER_FACTORY);
            addOverride(overrides, element, "messageAdapter", MuleProperties.CONNECTOR_MESSAGE_ADAPTER);
            addOverride(overrides, element, "streamMessageAdapter", MuleProperties.CONNECTOR_STREAM_MESSAGE_ADAPTER);
            addOverride(overrides, element, "inboundTransformer", MuleProperties.CONNECTOR_INBOUND_TRANSFORMER);
            addOverride(overrides, element, "outboundTransformer", MuleProperties.CONNECTOR_OUTBOUND_TRANSFORMER);
            addOverride(overrides, element, "responseTransformer", MuleProperties.CONNECTOR_RESPONSE_TRANSFORMER);
            addOverride(overrides, element, "endpointBuilder", MuleProperties.CONNECTOR_ENDPOINT_BUILDER);
            addOverride(overrides, element, "serviceFinder", MuleProperties.CONNECTOR_SERVICE_FINDER);
            builder.setSource(overrides);
        }

        protected void postProcess(RootBeanDefinition beanDefinition, Element element) {
            String parentBean = ((Element) element.getParentNode()).getAttribute("id");
            beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("id", parentBean + "-" + element.getNodeName()));
        }

        protected void addOverride(Map overrides, Element e, String attributeName, String overrideName) {
            String value = e.getAttribute(attributeName);
            if (!StringUtils.isBlank(value)) {
                overrides.put(overrideName, value);
            }
        }
    }

    public static class EndpointDefinitionParser extends AbstractMuleSingleBeanDefinitionParser
    {

        /**
         * Parse the specified {@link org.w3c.dom.Element} and register resulting <code>BeanDefinitions</code>
         * with the supplied {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}.
         */
        public BeanDefinition doParse(Element element) {

            String address = element.getAttribute("address");

            MutablePropertyValues mpvs = new MutablePropertyValues();

            NamedNodeMap attributes = element.getAttributes();
            for(int x = 0; x < attributes.getLength(); x++) {
                Attr attribute = (Attr) attributes.item(x);
                String name = attribute.getLocalName();

                if("address".equals(name)) {
                    try {
                        mpvs.addPropertyValue("endpointURI", new MuleEndpointURI(address));
                    } catch (MalformedEndpointException e) {
                        throw new BeanCreationException(new Message(Messages.ENPOINT_X_IS_MALFORMED, address).getMessage(), e);
                    }
                } else {
                    String n = attribute.getLocalName();
                    if(n==null) n = attribute.getName();
                    n = getAttributeMapping(n);
                    String v = attribute.getValue();
                    mpvs.addPropertyValue(n, v);
                }
            }

            if(StringUtils.isBlank(element.getAttribute("id"))) {
                mpvs.addPropertyValue(new PropertyValue("id", address));
                element.setAttribute("id", address);
            }
            //todo parseProperties(element, element.getAttribute("id"), mpvs, true);

            RootBeanDefinition def = new RootBeanDefinition(MuleEndpoint.class, mpvs);
            postProcess(def, element);
            return def;
        }

        protected void postProcess(RootBeanDefinition beanDefinition, Element element) {
            String parentBean = ((Element) element.getParentNode()).getAttribute("id");
            if(StringUtils.isBlank(parentBean)) {
                return;
            }
            BeanDefinition parent = registry.getBeanDefinition(parentBean);
            PropertyValue pv = parent.getPropertyValues().getPropertyValue("endpoints");
            if(pv==null) {
                pv = new PropertyValue("endpoints", new ManagedList());
                parent.getPropertyValues().addPropertyValue(pv);
            }
            ((List)pv.getValue()).add(beanDefinition);

            //parent.getPropertyValues().addPropertyValue(new PropertyValue(getPropertyName(element), beanDefinition));

        }


        protected Class getBeanClass(Element element) {
            return MuleEndpoint.class;
        }
    }

    public static class CustomConnectorDefinitionParser extends AbstractSingleBeanDefinitionParser
    {
        protected Class getBeanClass(Element element)
        {
            String cls = element.getAttribute("class");
            try
            {
                return ClassUtils.loadClass(cls, getClass());
            }
            catch (ClassNotFoundException e)
            {
                return null;
            }
        }
    }

}
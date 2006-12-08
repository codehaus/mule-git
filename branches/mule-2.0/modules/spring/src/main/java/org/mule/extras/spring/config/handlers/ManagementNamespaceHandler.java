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

import org.mule.impl.internal.admin.EndpointNotificationLoggerAgent;
import org.mule.impl.internal.admin.Log4jNotificationLoggerAgent;
import org.mule.management.agents.JdmkAgent;
import org.mule.management.agents.JmxAgent;
import org.mule.management.agents.Log4jAgent;
import org.mule.management.agents.Mx4jAgent;
import org.mule.management.agents.RmiRegistryAgent;
import org.mule.util.ClassUtils;
import org.mule.util.StringUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * todo document
 *
 */
public class ManagementNamespaceHandler extends NamespaceHandlerSupport
{

    public void init()
    {
        registerBeanDefinitionParser("jmx-server", new JmxServerBeanDefinitionParser());
        registerBeanDefinitionParser("jmx-log4j", new JmxLog4jBeanDefinitionParser());
        registerBeanDefinitionParser("jmx-mx4j-adaptor", new JmxMx4jBeanDefinitionParser());
        registerBeanDefinitionParser("jmx-notifications", new JmxNotificationsBeanDefinitionParser());
        registerBeanDefinitionParser("jmx-default-configuration", new JmxDefaultConfigurationBeanDefinitionParser());
        registerBeanDefinitionParser("chainsaw-notifications", new ChainsawNotificationsBeanDefinitionParser());
        registerBeanDefinitionParser("log4j-notifications", new Log4jNotificationsBeanDefinitionParser());
        registerBeanDefinitionParser("publish-notifications", new EndpointNotificationsBeanDefinitionParser());
        registerBeanDefinitionParser("rmi-server", new RmiRegistryBeanDefinitionParser());
    }


    public static class JmxJmdkBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
    {
        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, JdmkAgent.class);
            return JdmkAgent.class;
        }
    }

    public static class JmxMx4jBeanDefinitionParser extends AbstractSingleBeanDefinitionParser
    {
        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, Mx4jAgent.class);
            return Mx4jAgent.class;
        }
    }

    public static class JmxServerBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
        public static final String CONNECTOR_SERVER = "connector-server";

        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, JmxAgent.class);
            return JmxAgent.class;
        }

        protected void postProcess(RootBeanDefinition definition, Element element) {
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if (CONNECTOR_SERVER.equals(node.getLocalName())) {
                    definition.getPropertyValues().addPropertyValue("connectorServerUrl", ((Element) node).getAttribute("url"));
                    String rebind = ((Element) node).getAttribute("rebind");
                    if(!StringUtils.isEmpty(rebind)) {
                        Map csProps = new HashMap();
                        csProps.put("jmx.remote.jndi.rebind", rebind);
                        definition.getPropertyValues().addPropertyValue("connectorServerProperties", csProps);
                    }
                }
            }
        }
    }

    public static class JmxLog4jBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, Log4jAgent.class);
            return Log4jAgent.class;
        }
    }

    public static class JmxNotificationsBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
        protected Class getBeanClass(Element element) {
            return null; //todo JmxNotificationAgent.class;
        }
    }

        public static class JmxDefaultConfigurationBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
        protected Class getBeanClass(Element element) {
            return null; //todo JmxDefaultConfigurationAgent.class;
        }
    }


    public static class EndpointNotificationsBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, EndpointNotificationLoggerAgent.class);
            return EndpointNotificationLoggerAgent.class;
        }
    }

    public static class Log4jNotificationsBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, Log4jNotificationLoggerAgent.class);
            return Log4jNotificationLoggerAgent.class;
        }
    }

    public static class RmiRegistryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, RmiRegistryAgent.class);
            return RmiRegistryAgent.class;
        }
    }

    public static class ChainsawNotificationsBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        public static final String LEVEL_MAPPINGS = "levelMappings";

        protected Class getBeanClass(Element element) {
            generateBeanNameIfNotSet(element, Log4jNotificationLoggerAgent.class);
            return Log4jNotificationLoggerAgent.class;
        }

        protected void postProcess(RootBeanDefinition definition, Element element) {
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if (LEVEL_MAPPINGS.equals(node.getLocalName())) {
                    definition.getPropertyValues().addPropertyValue("jndiEnvironment", DomUtils.getTextValue((Element) node));
                }
            }
        }
    }

    public static void generateBeanNameIfNotSet(Element e, Class clazz) {
        String id = e.getAttribute("id");
        if(StringUtils.isBlank(id)) {
            id = e.getAttribute("name");
        }
        if(StringUtils.isBlank(id)) id = ClassUtils.getClassName(clazz);
        e.setAttribute("name", id);
    }
}
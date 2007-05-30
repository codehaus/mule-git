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

import org.mule.config.spring.parsers.AbstractChildBeanDefinitionParser;
import org.mule.util.ClassUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SslDefinitionParser extends AbstractChildBeanDefinitionParser
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    private Class beanClass;


    public SslDefinitionParser(Class beanClass, boolean singleton)
    {
        this.beanClass = beanClass;
        this.singleton = singleton;
    }

    protected Class getBeanClass(Element element)
    {
        if (beanClass == null)
        {
            String cls = element.getAttribute("class");
            try
            {
                //TODO TC: probably need to use OSGi Loader here
                beanClass = ClassUtils.loadClass(cls, getClass());
            }
            catch (ClassNotFoundException e)
            {
                logger.error("could not load class: " + cls, e);
            }
        }
        element.removeAttribute("class");
        return beanClass;
    }

    protected void postProcess(final BeanDefinitionBuilder builder, final Element element)
    {
        AbstractBeanDefinition parent = builder.getBeanDefinition();
        final NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node node = childNodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
            {
                // process only elements, not whitespaces or xml instructions
                continue;
            }

            Element child = (Element) node;

            /*

                THIS APPROACH IGNORES ELEMENT NAMES. The way to handle it is to
                route via the

                     public String getPropertyName(Element element)

                below.
             */
            NamedNodeMap attrs = child.getAttributes();
            for (int k = 0; k < attrs.getLength(); k++)
            {
                Attr attr = (Attr) attrs.item(k);
                // careful not to go beyond the top element
                if (hasParent(child))
                {
                    parent.getPropertyValues().addPropertyValue(attr.getNodeName(), attr.getNodeValue());
                }
            }

            //if ("ssl-client".equals(node.getLocalName()))
            //{
            //    mutablePropertyValues.addPropertyValue("clientKeyStore", ((Element) node).getAttribute("clientKeystore"));
            //    mutablePropertyValues.addPropertyValue("clientKeyStorePassword", ((Element) node).getAttribute("clientKeyStorePassword"));
            //}

        }

    }

    public String getPropertyName(Element element)
    {
        // ok, I see now this would map XML-time user-friendly attribute to a JavaBean property
        return null;
    }
}
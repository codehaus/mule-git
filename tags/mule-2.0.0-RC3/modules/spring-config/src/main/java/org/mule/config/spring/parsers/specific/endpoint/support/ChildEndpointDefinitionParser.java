/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.specific.endpoint.support;

import org.mule.config.spring.parsers.AbstractMuleBeanDefinitionParser;
import org.mule.config.spring.parsers.generic.AutoIdUtils;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.util.StringUtils;
import org.mule.endpoint.AbstractEndpointBuilder;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * A parser for "embedded" endpoints - ie inbound, outbound and response endpoints.
 * Because we have automatic String -> MuleEnpointURI conversin via property editors
 * this can be used in a variety of ways.  It should work directly with a simple String
 * address attribute or, in combination with a child element (handled by
 * {@link ChildAddressDefinitionParser},
 * or embedded in
 * {@link AddressedEndpointDefinitionParser}
 * for a more compact single-eleent approach.
 *
 * <p>This class does support references to other endpoints.</p>
 * TODO - check that references are global!
 */
public class ChildEndpointDefinitionParser extends ChildDefinitionParser
{

    public ChildEndpointDefinitionParser(Class endpoint)
    {
        super("endpoint", endpoint);
        addIgnored(AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF);
        EndpointUtils.addProperties(this);
        EndpointUtils.addPostProcess(this);
    }

    // @Override
    public BeanDefinitionBuilder createBeanDefinitionBuilder(Element element, Class beanClass)
    {
        BeanDefinitionBuilder builder = super.createBeanDefinitionBuilder(element, beanClass);
        String global = element.getAttribute(AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF);
        if (StringUtils.isNotBlank(global))
        {
            builder.addConstructorArgReference(global);
            builder.addDependsOn(global);
        }
        return builder;
    }

    // @Override
    public String getBeanName(Element element)
    {
        if (null != element.getAttributeNode(AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF))
        {
            return AutoIdUtils.uniqueValue("ref:" + element.getAttribute(AbstractMuleBeanDefinitionParser.ATTRIBUTE_REF));
        }
        else
        {
            return super.getBeanName(element);
        }
    }
    
    //@Override
    protected void parseChild(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
    {
        // Not sure if this is required. Adding for now for backwards compatability
        if (element.getParentNode().getNodeName().equals("chaining-router")
            || element.getParentNode().getNodeName().equals("exception-based-router"))
        {
            builder.addPropertyValue(AbstractEndpointBuilder.PROPERTY_REMOTE_SYNC, Boolean.TRUE);
        }

        super.parseChild(element, parserContext, builder);
    }

}

/*
 * $Header: /opt/cvsroot/mule2/mule-core/src/main/java/org/mule/spring/config/handlers/FilterNamespaceHandler.java,v 1.1 2006/02/01 19:42:11 rossmason Exp $
 * $Revision: 1.1 $
 * $Date: 2006/02/01 19:42:11 $
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.extras.spring.config.handlers;

import org.mule.extras.spring.config.AbstractChildDefinitionParser;
import org.mule.routing.filters.ExceptionTypeFilter;
import org.mule.routing.filters.MessagePropertyFilter;
import org.mule.routing.filters.PayloadTypeFilter;
import org.mule.routing.filters.RegExFilter;
import org.mule.routing.filters.WildcardFilter;
import org.mule.routing.filters.logic.AndFilter;
import org.mule.routing.filters.logic.OrFilter;
import org.mule.routing.filters.logic.NotFilter;
import org.mule.routing.filters.xml.JXPathFilter;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.w3c.dom.Element;

/**
 * todo document
 */
public class FilterNamespaceHandler extends NamespaceHandlerSupport
{


    public void init()
    {
        FilterDefinitionParser parser = new FilterDefinitionParser();
        registerBeanDefinitionParser("and", parser);
        registerBeanDefinitionParser("or", parser);
        registerBeanDefinitionParser("not", parser);
        registerBeanDefinitionParser("regex", parser);
        registerBeanDefinitionParser("jxpath", parser);
        registerBeanDefinitionParser("message-property", parser);
        registerBeanDefinitionParser("payload-type", parser);
        registerBeanDefinitionParser("wildcard", parser);
        //todo
        registerBeanDefinitionParser("ognl", parser);
        //todo
        //registerBeanDefinitionParser("xpath", parser);
        //registerBeanDefinitionParser("xquery", parser);
    }

    public static class FilterDefinitionParser extends AbstractChildDefinitionParser
    {

        protected Class getBeanClass(Element element)
        {
            if (element.getLocalName().equals("and"))
            {
                return AndFilter.class;
            }
            else if (element.getLocalName().equals("or"))
            {
                return NotFilter.class;
            }
            else if (element.getLocalName().equals("not"))
            {
                return OrFilter.class;
            }
            else if (element.getLocalName().equals("regex"))
            {
                return RegExFilter.class;
                //} else if(element.getLocalName().equals("xpath")) {
                //   return XPathFilter.class;
            }
            else if (element.getLocalName().equals("jxpath"))
            {
                return JXPathFilter.class;
            }
            else if (element.getLocalName().equals("message-property"))
            {
                return MessagePropertyFilter.class;
            }
            else if (element.getLocalName().equals("payload-type"))
            {
                return PayloadTypeFilter.class;
            }
            else if (element.getLocalName().equals("exception-type"))
            {
                return ExceptionTypeFilter.class;
            }
            else if (element.getLocalName().equals("wildcard"))
            {
                return WildcardFilter.class;
            }
            //Todo
//            else if (element.getLocalName().equals("ognl"))
//            {
//                return OGNLFilter.class;
//            }
            return null;
        }

        protected String getPropertyName(Element e)
        {
            return "filter";
        }
    }
}

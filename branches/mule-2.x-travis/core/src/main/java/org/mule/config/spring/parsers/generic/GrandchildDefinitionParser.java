/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.spring.parsers.generic;

import org.springframework.beans.factory.config.BeanDefinition;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An extension to {@line ChildDefinitionParser} which recurses up the DOM
 * tree until it finds a named parent.
 */
public class GrandchildDefinitionParser extends ChildDefinitionParser
{
    public GrandchildDefinitionParser(String setterMethod, Class clazz)
    {
        super(setterMethod, clazz);
    }

    protected String getParentBeanName(Element element)
    {
        Node parent = element.getParentNode();
        if (parent == null)
        {
            logger.error("No parent node found for element " + element);
            return null;
        }
        Node grandparent = parent.getParentNode();
        if (grandparent == null)
        {
            logger.error("No parent node found for element " + element);
            return null;
        }
        Node grandparentNameAttribute = grandparent.getAttributes().getNamedItem("name");
        if (grandparentNameAttribute == null)
        {
            logger.error("Grandparent node has no 'name' attribute: " + grandparent);
            return null;
        }
        return grandparentNameAttribute.getNodeValue();
    }

    public void forceParent(BeanDefinition parent)
    {
        // TODO Auto-generated method stub
        super.forceParent(parent);
    }

}

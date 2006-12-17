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

import org.mule.extras.spring.config.AbstractChildBeanDefinitionParser;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractHierarchicalNamespaceHandler implements NamespaceHandler
{

    /**
     * Stores the {@link org.springframework.beans.factory.xml.BeanDefinitionParser} implementations keyed by the
     * local name of the {@link Element Elements} they handle.
     */
    private final Map parsers = new HashMap();

    /**
     * Stores the {@link org.springframework.beans.factory.xml.BeanDefinitionDecorator} implementations keyed by the
     * local name of the {@link Element Elements} they handle.
     */
    private final Map decorators = new HashMap();

    /**
     * Stores the {@link org.springframework.beans.factory.xml.BeanDefinitionParser} implementations keyed by the local
     * name of the {@link Attr Attrs} they handle.
     */
    private final Map attributeDecorators = new HashMap();

    /**
     * Decorates the supplied {@link org.w3c.dom.Node} by delegating to the {@link org.springframework.beans.factory.xml.BeanDefinitionDecorator} that
     * is registered to handle that {@link org.w3c.dom.Node}.
     */
    public final BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definition, ParserContext parserContext)
    {
        return findDecoratorForNode(node).decorate(node, definition, parserContext);
    }

    /**
     * Parses the supplied {@link Element} by delegating to the {@link org.springframework.beans.factory.xml.BeanDefinitionParser} that is
     * registered for that {@link Element}.
     */
    public final BeanDefinition parse(Element element, ParserContext parserContext)
    {

        //Todo RM* Hierarchical impl, with child definition parses
        BeanDefinition root = findParserForElement(element).parse(element, parserContext);
        parseNext(root, element, parserContext);
        return root;
    }

    public final void parseNext(BeanDefinition parentBean, Element parentElement, ParserContext parserContext)
        {
             NodeList list = parentElement.getElementsByTagNameNS(parentElement.getNamespaceURI(), "*");

        for (int i = 0; i < list.getLength(); i++)
        {
            Element element = (Element) list.item(i);
            BeanDefinitionParser parser = findParserForElement(element);
            if (parser == null)
            {
                throw new BeanCreationException(element.getNodeName(), "Failed to find parser for element");
            }
            else if (!(parser instanceof AbstractChildBeanDefinitionParser))
            {
                //TODO RM*
                throw new BeanCreationException(element.getNodeName(), "Definition parser for element does not implement: " + AbstractChildBeanDefinitionParser.class.getName() +
                " yet the element is contained within a parent element");
            }
            else
            {
                AbstractChildBeanDefinitionParser childParser = (AbstractChildBeanDefinitionParser)parser;
                BeanDefinition child = childParser.parse(element, parserContext);
                //String propertyName = childParser.getPropertyName(element);
                //parentBean.getPropertyValues().addPropertyValue(propertyName, child);
                //parseNext(child, element, parserContext);
            }

        }
     }
    /**
     * Locates the {@link org.springframework.beans.factory.xml.BeanDefinitionParser} from the register implementations using
     * the local name of the supplied {@link Element}.
     */
    protected final BeanDefinitionParser findParserForElement(Element element)
    {
        BeanDefinitionParser parser = (BeanDefinitionParser) this.parsers.get(element.getLocalName());

        if (parser == null)
        {
            throw new IllegalArgumentException("Cannot locate BeanDefinitionParser for element [" +
                    element.getLocalName() + "].");
        }

        return parser;
    }

    /**
     * Locates the {@link BeanDefinitionParser} from the register implementations using
     * the local name of the supplied {@link Node}. Supports both {@link Element Elements}
     * and {@link Attr Attrs}.
     */
    protected final BeanDefinitionDecorator findDecoratorForNode(Node node)
    {
        BeanDefinitionDecorator decorator = null;
        if (node instanceof Element)
        {
            decorator = (BeanDefinitionDecorator) this.decorators.get(node.getLocalName());
        }
        else if (node instanceof Attr)
        {
            decorator = (BeanDefinitionDecorator) this.attributeDecorators.get(node.getLocalName());
        }
        else
        {
            throw new IllegalArgumentException("Cannot decorate based on Nodes of type '" + node.getClass().getName() + "'");
        }

        if (decorator == null)
        {
            throw new IllegalArgumentException("Cannot locate BeanDefinitionDecorator for "
                    + (node instanceof Element ? "element" : "attribute") + " [" +
                    node.getLocalName() + "].");
        }

        return decorator;
    }

    /**
     * Subclasses can call this to register the supplied {@link BeanDefinitionParser} to
     * handle the specified element. The element name is the local (non-namespace qualified)
     * name.
     */
    protected final void registerBeanDefinitionParser(String elementName, BeanDefinitionParser parser)
    {
        this.parsers.put(elementName, parser);
    }

    /**
     * Subclasses can call this to register the supplied {@link BeanDefinitionDecorator} to
     * handle the specified element. The element name is the local (non-namespace qualified)
     * name.
     */
    protected final void registerBeanDefinitionDecorator(String elementName, BeanDefinitionDecorator decorator)
    {
        this.decorators.put(elementName, decorator);
    }

    /**
     * Subclasses can call this to register the supplied {@link BeanDefinitionDecorator} to
     * handle the specified attribute. The attribute name is the local (non-namespace qualified)
     * name.
     */
    protected final void registerBeanDefinitionDecoratorForAttribute(String attributeName, BeanDefinitionDecorator decorator)
    {
        this.attributeDecorators.put(attributeName, decorator);
    }
}
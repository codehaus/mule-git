/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parsers.specific;

import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.component.DefaultJavaComponent;
import org.mule.object.AbstractObjectFactory;
import org.mule.object.SingletonObjectFactory;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Used to parse shortcut elements for simple built-in components such as
 * {@link org.mule.component.simple.BridgeComponent},
 * {@link import org.mule.component.simple.EchoComponent} and
 * {@link import org.mule.component.simple.LogComponent}. This allows shortcuts like
 * for example <i>&lt;mule:bridge-service/&gt;</i> to be used instead of having to
 * use the <i>&lt;mule:service/&gt;</i> element and specify the class name (and
 * scope) for built-in components that don't require configuration. <p/> <b>This
 * DefinitionParser should only be used for state-less components.</b> <p/> In order
 * to further customize components and use serviceFactory properties the
 * &lt;mule:service/&gt; element should be used.
 */
public class SimpleComponentDefinitionParser extends ComponentDefinitionParser
{
    private static Class OBJECT_FACTORY_TYPE = SingletonObjectFactory.class;
    private Class componentInstanceClass;

    public SimpleComponentDefinitionParser(Class component, Class componentInstanceClass)
    {
        super(DefaultJavaComponent.class);
        this.componentInstanceClass = componentInstanceClass;
    }

    protected void parseChild(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
    {
        Element parent = (Element) element.getParentNode();
        String serviceName = parent.getAttribute(ATTRIBUTE_NAME);
        builder.addPropertyReference("service", serviceName);

        // Create a BeanDefinition for the nested object factory and set it a
        // property value for the component
        AbstractBeanDefinition objectFactoryBeanDefinition = new GenericBeanDefinition();
        objectFactoryBeanDefinition.setBeanClass(OBJECT_FACTORY_TYPE);
        objectFactoryBeanDefinition.getPropertyValues().addPropertyValue(AbstractObjectFactory.ATTRIBUTE_OBJECT_CLASS,
            componentInstanceClass);
        objectFactoryBeanDefinition.setInitMethodName(Initialisable.PHASE_NAME);
        objectFactoryBeanDefinition.setDestroyMethodName(Disposable.PHASE_NAME);

        builder.addPropertyValue("objectFactory", objectFactoryBeanDefinition);

        super.parseChild(element, parserContext, builder);
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.spring.parser.specific;

import org.mule.config.annotations.Service;
import org.mule.config.spring.parsers.specific.PojoComponentDefinitionParser;
import org.mule.impl.annotations.ScopedObjectFactory;
import org.mule.util.object.AbstractObjectFactory;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Always creates a {@link org.mule.impl.annotations.ScopedObjectFactory} component, then when passing
 * the Xml definition, it will set the 'scope' property based no the annotations on the class.
 */
public class AnnotatedPojoComponentDefinitionParser extends PojoComponentDefinitionParser
{
    public AnnotatedPojoComponentDefinitionParser()
    {
        super(ScopedObjectFactory.class);
    }

    public AnnotatedPojoComponentDefinitionParser(String setterMethod)
    {
        super(ScopedObjectFactory.class, setterMethod);
    }

    @Override
    protected void parseChild(Element element, ParserContext parserContext, BeanDefinitionBuilder builder)
    {
        super.parseChild(element, parserContext, builder);
        String serviceName = ((Element)element.getParentNode()).getAttribute(ATTRIBUTE_NAME);
        builder.addDependsOn(serviceName);

        //The next bit of code is superfluous. This is handled in the AnnotatedServicebuilder, but for some reason the
        //ObjectFactory is getting initialised before service object (even though a depenency is set).
        try
        {
            Class cls = getPojoClass(builder);
            builder.addPropertyValue(AbstractObjectFactory.ATTRIBUTE_OBJECT_CLASS, cls);
            if (cls.isAnnotationPresent(Service.class))
            {
                Service service = (Service) cls.getAnnotation(Service.class);
                builder.addPropertyValue("scope", service.scope());
            }
        }
        catch (ClassNotFoundException e)
        {
            logger.warn(e);
        }
    }
}
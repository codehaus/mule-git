/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.spring.factories;

import org.mule.impl.ManagementContextAware;
import org.mule.impl.annotations.AnnotatedServiceBuilder;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOManagementContext;

import java.util.Map;

import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * A factory bean used to create a {@link org.mule.umo.UMOComponent} object from an annotated service
 */
public class AnnotatedServiceFactoryBean extends AbstractFactoryBean implements ManagementContextAware
{
    private Class componentClass;
    private String modelName;
    private Map properties;
    private UMOManagementContext managementContext;
    private UMOComponent component;

    public void setManagementContext(UMOManagementContext context)
    {
        managementContext = context;
     }

    public String getModelName()
    {
        return modelName;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public Class getComponentClass()
    {
        return componentClass;
    }

    public void setComponentClass(Class componentClass)
    {
        this.componentClass = componentClass;
    }

    public Map getProperties()
    {
        return properties;
    }

    public void setProperties(Map properties)
    {
        this.properties = properties;
    }

    protected Object createInstance() throws Exception
    {
        AnnotatedServiceBuilder builder = new AnnotatedServiceBuilder(getComponentClass(), managementContext);
        builder.setModelName(getModelName());
        builder.setProperties(getProperties());
        component = builder.createService();
        return component;
    }

    public boolean isSingleton()
    {
        return true;
    }

    public Class getObjectType()
    {
        return UMOComponent.class;
    }

    //@java.lang.Override
    public void afterPropertiesSet() throws Exception
    {
        super.afterPropertiesSet();
        component.initialise();
    }

}

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
import org.mule.impl.annotations.ScopedObjectFactory;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.model.UMOModel;
import org.mule.util.object.ObjectFactory;

import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * A factory bean used to create a {@link org.mule.umo.UMOComponent} object from an annotated service
 */
public class AnnotatedServiceFactoryBean extends AbstractFactoryBean implements ManagementContextAware
{
    private ScopedObjectFactory serviceFactory;
    private UMOModel model;
    private String name;
    private UMOManagementContext managementContext;
    private UMOComponent component;

    public void setManagementContext(UMOManagementContext context)
    {
        managementContext = context;
     }

    public UMOModel getModel()
    {
        return model;
    }

    public void setModel(UMOModel model)
    {
        this.model = model;
    }

    public ScopedObjectFactory getServiceFactory()
    {
        return serviceFactory;
    }

    public void setServiceFactory(ScopedObjectFactory serviceFactory)
    {
        this.serviceFactory = serviceFactory;
    }


    protected Object createInstance() throws Exception
    {
        AnnotatedServiceBuilder builder = new AnnotatedServiceBuilder(getServiceFactory(), managementContext);
        builder.setModel(getModel());
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
        component.setManagementContext(managementContext);
        component.initialise();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

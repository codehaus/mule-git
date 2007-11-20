/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.annotations;

import org.mule.config.MuleProperties;
import org.mule.config.annotations.Service;
import org.mule.impl.ManagementContextAware;
import org.mule.impl.model.resolvers.AnnotatedEntryPointResolver;
import org.mule.impl.model.resolvers.LegacyEntryPointResolverSet;
import org.mule.impl.model.seda.SedaModel;
import org.mule.impl.registry.ObjectProcessor;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.model.UMOModel;
import org.mule.util.BeanUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * This object processor allows users to register annotated services directly to the registry
 * and have them configured correctly.
 * It will look for a non-system {@link org.mule.umo.UMOModel} registered with the Registry.
 * If one is not found a default  Seda Model will be created
 * Finally, the processor will register the service with the Registry and return null.
 */
public class AnnotatedServiceObjectFactory implements ObjectProcessor, ManagementContextAware
{
    private UMOManagementContext context;

    public void setManagementContext(UMOManagementContext context)
    {
        this.context = context;
    }

    public Object process(Object object)
    {
        if (!object.getClass().isAnnotationPresent(Service.class))
        {
            return object;
        }
        
        Class aClass;
        if (object instanceof Class)
        {
            aClass = (Class) object;
        }
        else
        {
            aClass = object.getClass();
        }
        try
        {
            UMOModel model = null;
            Collection<UMOModel> models = context.getRegistry().lookupObjects(UMOModel.class);
            for (Iterator<UMOModel> iterator = models.iterator(); iterator.hasNext();)
            {
                UMOModel m = iterator.next();
                if(!m.getName().equals(MuleProperties.OBJECT_SYSTEM_MODEL))
                {
                    model = m;
                    break;
                }
            }
            if(model==null)
            {
                //Create a new Model and add the Annotations EPR to the list
                model = new SedaModel();
                LegacyEntryPointResolverSet resolverSet = new LegacyEntryPointResolverSet();
                resolverSet.addEntryPointResolver(new AnnotatedEntryPointResolver());
                model.setEntryPointResolverSet(resolverSet);
                context.getRegistry().registerModel(model, context);
            }

            ScopedObjectFactory serviceFactory = new ScopedObjectFactory();
            serviceFactory.setObjectClass(aClass);
            Map props = BeanUtils.describe(object);
            serviceFactory.setProperties(props);
            AnnotatedServiceBuilder builder = new AnnotatedServiceBuilder(serviceFactory, context);
            builder.setModel(model);
            UMOComponent service = builder.createService();
            context.getRegistry().registerComponent(service, context);
            return null;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }


}

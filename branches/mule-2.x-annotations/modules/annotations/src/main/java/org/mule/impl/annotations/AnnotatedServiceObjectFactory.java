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

import org.mule.config.annotations.Service;
import org.mule.impl.ManagementContextAware;
import org.mule.impl.annotations.AnnotatedServiceBuilder;
import org.mule.impl.annotations.AnnotationsModel;
import org.mule.impl.registry.ObjectProcessor;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.model.UMOModel;

import java.util.Collection;
import java.util.Iterator;

/**
 * This object processor allows users to register annotated services directly to the registry
 * and have them configured correctly.
 * It will look for an {@link org.mule.impl.annotations.AnnotationsModel} registered with the Registry.
 * If one is not found a default will be created called {@link org.mule.impl.annotations.AnnotationsModel.DEFAULT_MODEL_NAME}
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
                if(m instanceof AnnotationsModel)
                {
                    model = m;
                    break;
                }
            }
            if(model==null)
            {
                model = new AnnotationsModel();
                context.getRegistry().registerModel(model, context);
            }
            AnnotatedServiceBuilder builder = new AnnotatedServiceBuilder(aClass, context);
            builder.setModelName(model.getName());
            UMOComponent service = builder.createService();
            context.getRegistry().registerComponent(service, context);
            return null;
        }
        catch (UMOException e)
        {
            throw new RuntimeException(e);
        }

    }


}

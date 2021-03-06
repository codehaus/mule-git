/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.model;

import org.mule.RegistryContext;
import org.mule.registry.ServiceDescriptorFactory;
import org.mule.registry.ServiceException;
import org.mule.umo.model.UMOModel;

/**
 * Will locate the model service using the model type as the key and construct the model.
 */
public final class ModelFactory
{
    public static final String DEFAULT_MODEL_NAME = "main";

    public static UMOModel createModel(String type) throws ServiceException
    {
        return createModel(type, DEFAULT_MODEL_NAME);
    }

    public static UMOModel createModel(String type, String name) throws ServiceException
    {
        ModelServiceDescriptor sd = (ModelServiceDescriptor)
            RegistryContext.getRegistry().lookupServiceDescriptor(ServiceDescriptorFactory.MODEL_SERVICE_TYPE, type, null);
        if (sd != null)
        {
            UMOModel model = sd.createModel();
            model.setName(name);
            return model;
        }
        else return null;
    }
    
    public static Class getModelClass(String type) throws ServiceException
    {
        ModelServiceDescriptor sd = (ModelServiceDescriptor) 
            RegistryContext.getRegistry().lookupServiceDescriptor(ServiceDescriptorFactory.MODEL_SERVICE_TYPE, type, null);
        if (sd != null)
        {
            return sd.getModelClass();
        }
        else return null;
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl;

import org.mule.config.QueueProfile;
import org.mule.config.ThreadingProfile;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.model.UMOEntryPointResolverSet;
import org.mule.umo.routing.UMOInboundRouterCollection;
import org.mule.umo.routing.UMONestedRouterCollection;
import org.mule.umo.routing.UMOOutboundRouterCollection;
import org.mule.umo.routing.UMOResponseRouterCollection;

import java.beans.ExceptionListener;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>MuleDescriptor</code> describes all the properties for a Mule UMO. New
 * Mule UMOs can be initialised as needed from their descriptor.
 */
public class MuleDescriptor extends ImmutableMuleDescriptor implements UMODescriptor
{

    /** logger used by this class */
    private static Log logger = LogFactory.getLog(MuleDescriptor.class);

    public MuleDescriptor(String name)
    {
        super();
        this.name = name;
    }

    public MuleDescriptor(MuleDescriptor descriptor)
    {
        super(descriptor);
    }

    /**
     * Default constructor. Initalises common properties for the MuleConfiguration
     * object
     *
     * @see org.mule.config.MuleConfiguration
     */
    public MuleDescriptor()
    {
        super();
    }

    public void setThreadingProfile(ThreadingProfile threadingProfile)
    {
        this.threadingProfile = threadingProfile;
    }

    public void setQueueProfile(QueueProfile queueProfile)
    {
        this.queueProfile = queueProfile;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.umo.UMODescriptor#setExceptionListener(org.mule.umo.UMOExceptionStrategy)
     */
    public void setExceptionListener(ExceptionListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Exception Strategy cannot be null");
        }
        this.exceptionListener = listener;
        logger.debug("Using exception strategy: " + listener.getClass().getName());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.umo.UMODescriptor#setName(java.lang.String)
     */
    public void setName(String newName)
    {
        if (newName == null)
        {
            throw new IllegalArgumentException("Name cannot be null");
        }
        name = newName;
    }

    /*
     * @deprecated MULE-1933 Properties for the underlying service should be set on the ServiceFactory instead.
     */
    public void setProperties(Map props)
    {
        properties = props;
    }

    /** Factory which creates an instance of the actual service object. */
//    public void setServiceFactory(ObjectFactory serviceFactory)
//    {
//        if (serviceFactory == null)
//        {
//            throw new IllegalArgumentException("ServiceFactory cannot be null");
//        }
//        this.serviceFactory = serviceFactory;
//    }

    public void setInboundRouter(UMOInboundRouterCollection router)
    {
        this.inboundRouter = router;
    }

    public void setOutboundRouter(UMOOutboundRouterCollection router)
    {
        outboundRouter = router;
    }

    public void setNestedRouter(UMONestedRouterCollection router)
    {
        nestedRouter = router;
    }

    public void addInitialisationCallback(InitialisationCallback callback)
    {
        initialisationCallbacks.add(callback);
    }

    /**
     * Response Routers control how events are returned in a request/response call.
     * It cn be use to aggregate response events before returning, thus acting as a
     * Join in a forked process. This can be used to make request/response calls a
     * lot more efficient as independent tasks can be forked, execute concurrently
     * and then join before the request completes
     *
     * @param router the response router for this component
     * @see org.mule.umo.routing.UMOResponseRouterCollection
     */
    public void setResponseRouter(UMOResponseRouterCollection router)
    {
        this.responseRouter = router;
    }

    /**
     * Sets the initial state of this component
     *
     * @param state the initial state of this component
     */
    public void setInitialState(String state)
    {
        this.initialState = state;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public void setManagementContext(UMOManagementContext context)
    {
        this.managementContext = context;
    }

    /**
     * A descriptor can have a custom entrypoint resolver for its own object.
     * By default this is null. When set this resolver will override the resolver on the model
     *
     * @param resolverSet theresolver set to use when resolving entry points
     *                    on this component
     */
    public void setEntryPointResolverSet(UMOEntryPointResolverSet resolverSet)
    {
        this.entryPointResolverSet = resolverSet;
    }

}

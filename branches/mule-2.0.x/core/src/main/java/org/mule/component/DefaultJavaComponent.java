/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.component;

import org.mule.api.MuleException;
import org.mule.api.component.JavaComponent;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.LifecycleAdapter;
import org.mule.api.lifecycle.LifecycleTransitionResult;
import org.mule.api.model.EntryPointResolverSet;
import org.mule.api.model.ModelException;
import org.mule.api.routing.NestedRouterCollection;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.MessageFactory;
import org.mule.util.object.ObjectFactory;
import org.mule.util.object.SingletonObjectFactory;

/**
 * Default implementation of {@link JavaComponent}. Component lifecycle is
 * propagated to the component object instance via the {@link LifecycleAdapter}.
 */
public class DefaultJavaComponent extends AbstractJavaComponent
{

    protected LifecycleAdapter singletonComponentLifecycleAdapter;

    public DefaultJavaComponent()
    {
        // For spring only
        super();
    }

    public DefaultJavaComponent(ObjectFactory objectFactory)
    {
        super(objectFactory);
    }

    public DefaultJavaComponent(ObjectFactory objectFactory,
                                EntryPointResolverSet entryPointResolverSet,
                                NestedRouterCollection nestedRouterCollection)
    {
        super(objectFactory, entryPointResolverSet, nestedRouterCollection);
    }

    public LifecycleTransitionResult start() throws MuleException
    {
        checkDisposed();
        super.start();

        // If this component is using a SingletonObjectFactory we should create
        // LifecycleAdaptor wrapper just once now and not on each event. This also
        // allows start/stop life-cycle methods to be propagated to singleton
        // component instances.
        if (objectFactory != null && objectFactory instanceof SingletonObjectFactory)
        {
            // On first call, create and initialise singleton instance
            try
            {
                if (singletonComponentLifecycleAdapter == null)
                {
                    singletonComponentLifecycleAdapter = createLifeCycleAdaptor();
                    singletonComponentLifecycleAdapter.initialise();
                }
            }
            catch (Exception e)
            {
                throw new InitialisationException(
                    MessageFactory.createStaticMessage("Unable to create instance of POJO service"), e, this);

            }
            // On all calls, start if not started.
            if (!singletonComponentLifecycleAdapter.isStarted())
            {
                try
                {
                    return singletonComponentLifecycleAdapter.start();
                }
                catch (Exception e)
                {
                    throw new ModelException(CoreMessages.failedToStart("Service '" + service.getName() + "'"), e);
                }
            }
        }
        return LifecycleTransitionResult.OK;
    }

    public LifecycleTransitionResult stop() throws MuleException
    {
        // It only makes sense to propagate this life-cycle to singleton component
        // implementations
        if (singletonComponentLifecycleAdapter != null)
        {
            checkDisposed();
            if (singletonComponentLifecycleAdapter.isStarted())
            {
                try
                {
                    return singletonComponentLifecycleAdapter.stop();
                }
                catch (Exception e)
                {
                    throw new ModelException(CoreMessages.failedToStop("Service '" + service.getName() + "'"), e);
                }
            }
        }
        return LifecycleTransitionResult.OK;
    }

    public void dispose()
    {
        // It only makes sense to propagating this life-cycle to singleton component
        // implementations
        if (singletonComponentLifecycleAdapter != null)
        {
            if (singletonComponentLifecycleAdapter.isStarted())
            {
                try
                {
                    singletonComponentLifecycleAdapter.stop();
                }
                catch (MuleException e)
                {
                    logger.error(CoreMessages.failedToStop("Service '" + service.getName() + "'"), e);
                }
            }
            singletonComponentLifecycleAdapter.dispose();
        }
    }

    private void checkDisposed()
    {
        if (singletonComponentLifecycleAdapter != null && singletonComponentLifecycleAdapter.isDisposed())
        {
            throw new IllegalStateException("Service has already been disposed of");
        }
    }

    protected LifecycleAdapter borrowComponentLifecycleAdaptor() throws Exception
    {
        LifecycleAdapter componentLifecycleAdapter;
        if (singletonComponentLifecycleAdapter != null)
        {
            componentLifecycleAdapter = singletonComponentLifecycleAdapter;
        }
        else
        {
            componentLifecycleAdapter = createLifeCycleAdaptor();
            componentLifecycleAdapter.initialise();
            componentLifecycleAdapter.start();

        }
        return componentLifecycleAdapter;
    }

    protected void returnComponentLifecycleAdaptor(LifecycleAdapter lifecycleAdapter) throws Exception
    {
        if (singletonComponentLifecycleAdapter == null)
        {
            lifecycleAdapter.stop();
            lifecycleAdapter.dispose();
        }
    }

}

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
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.LifecycleAdapter;
import org.mule.api.lifecycle.LifecycleTransitionResult;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.config.PoolingProfile;
import org.mule.util.object.CommonsPoolObjectPool;
import org.mule.util.object.LifecyleEnabledObjectPool;
import org.mule.util.object.ObjectFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.PoolableObjectFactory;

/**
 * A LifecyleEnabledObjectPool implementation for pooling {@link LifecycleAdapter}
 * instances for implementations of {@link JavaComponent} that require
 * {@link LifecycleAdapter} pooling such as {@link PooledJavaComponent}.
 * 
 * @see PooledJavaComponent
 */
public class LifecycleAdaptorPool extends CommonsPoolObjectPool implements LifecyleEnabledObjectPool
{
    /**
     * logger used by this class
     */
    protected static final Log logger = LogFactory.getLog(LifecycleAdaptorPool.class);

    protected AtomicBoolean started;

    private List items = new LinkedList();

    /**
     * @param objectFactory The object factory that should be used to create new
     *            {@link LifecycleAdapter} instance for the pool
     * @param poolingProfile The pooling progile ot be used to configure pool
     */
    public LifecycleAdaptorPool(ObjectFactory objectFactory, PoolingProfile poolingProfile)
    {
        super(objectFactory, poolingProfile);
    }

    protected PoolableObjectFactory getPooledObjectFactory()
    {
        return new PooledLifecycleAdaptorObjectFactory();
    }

    public LifecycleTransitionResult start() throws MuleException
    {
        synchronized (items)
        {
            for (Iterator i = items.iterator(); i.hasNext();)
            {
                ((Startable) i.next()).start();
            }
        }
        return LifecycleTransitionResult.OK;
    }

    public LifecycleTransitionResult stop() throws MuleException
    {
        synchronized (items)
        {
            for (Iterator i = items.iterator(); i.hasNext();)
            {
                ((Stoppable) i.next()).stop();
            }
        }
        return LifecycleTransitionResult.OK;
    }

    /**
     * Wraps org.mule.object.ObjectFactory with commons-pool PoolableObjectFactory
     */
    class PooledLifecycleAdaptorObjectFactory implements PoolableObjectFactory
    {

        public void activateObject(Object obj) throws Exception
        {
            // nothing to do
        }

        public void destroyObject(Object obj) throws Exception
        {
            if (obj instanceof Disposable)
            {
                ((Disposable) obj).dispose();
            }
            synchronized (items)
            {
                items.remove(obj);
            }
        }

        public Object makeObject() throws Exception
        {
            Object object = objectFactory.getInstance();
            synchronized (items)
            {
                items.add(object);
            }
            return object;
        }

        public void passivateObject(Object obj) throws Exception
        {
            // nothing to do
        }

        public boolean validateObject(Object obj)
        {
            return true;
        }
    }
}

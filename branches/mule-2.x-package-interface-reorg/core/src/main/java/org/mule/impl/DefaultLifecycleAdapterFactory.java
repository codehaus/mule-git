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

import org.mule.api.AbstractMuleException;
import org.mule.api.Component;
import org.mule.api.lifecycle.LifecycleAdapter;
import org.mule.api.lifecycle.LifecycleAdapterFactory;
import org.mule.api.model.EntryPointResolverSet;

/**
 * <code>DefaultLifecycleAdapterFactory</code> creates a DefaultLifeCycleAdapter.  Users can
 * implement their own LifeCycleAdapter factories to control lifecycle events on their services such
 * as introduce other lifecycle events that are controlled by external changes.
 *
 * @see org.mule.api.lifecycle.LifecycleAdapter
 * @see org.mule.api.lifecycle.LifecycleAdapterFactory
 * @see org.mule.impl.DefaultLifecycleAdapter
 * @see org.mule.impl.DefaultLifecycleAdapterFactory
 */
public class DefaultLifecycleAdapterFactory implements LifecycleAdapterFactory
{
    public DefaultLifecycleAdapterFactory()
    {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.lifecycle.LifecycleAdapterFactory#create(java.lang.Object,
     *      org.mule.api.UMODescriptor)
     */
    public LifecycleAdapter create(Object pojoService,
                                      Component component,
                                      EntryPointResolverSet resolver) throws AbstractMuleException
    {
        return new DefaultLifecycleAdapter(pojoService, component, resolver);
    }

}

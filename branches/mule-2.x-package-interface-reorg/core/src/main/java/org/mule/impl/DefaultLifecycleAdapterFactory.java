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

import org.mule.api.UMOComponent;
import org.mule.api.UMOException;
import org.mule.api.lifecycle.UMOLifecycleAdapter;
import org.mule.api.lifecycle.UMOLifecycleAdapterFactory;
import org.mule.api.model.UMOEntryPointResolverSet;

/**
 * <code>DefaultLifecycleAdapterFactory</code> creates a DefaultLifeCycleAdapter.  Users can
 * implement their own LifeCycleAdapter factories to control lifecycle events on their services such
 * as introduce other lifecycle events that are controlled by external changes.
 *
 * @see org.mule.api.lifecycle.UMOLifecycleAdapter
 * @see org.mule.api.lifecycle.UMOLifecycleAdapterFactory
 * @see org.mule.impl.DefaultLifecycleAdapter
 * @see org.mule.impl.DefaultLifecycleAdapterFactory
 */
public class DefaultLifecycleAdapterFactory implements UMOLifecycleAdapterFactory
{
    public DefaultLifecycleAdapterFactory()
    {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.lifecycle.UMOLifecycleAdapterFactory#create(java.lang.Object,
     *      org.mule.api.UMODescriptor)
     */
    public UMOLifecycleAdapter create(Object pojoService,
                                      UMOComponent component,
                                      UMOEntryPointResolverSet resolver) throws UMOException
    {
        return new DefaultLifecycleAdapter(pojoService, component, resolver);
    }

}

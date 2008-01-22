/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.testmodels.mule;

import org.mule.api.UMOComponent;
import org.mule.api.UMOException;
import org.mule.api.lifecycle.UMOLifecycleAdapter;
import org.mule.api.lifecycle.UMOLifecycleAdapterFactory;
import org.mule.api.model.UMOEntryPointResolverSet;


public class TestDefaultLifecycleAdapterFactory implements UMOLifecycleAdapterFactory
{
    public TestDefaultLifecycleAdapterFactory()
    {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.lifecycle.UMOLifecycleAdapterFactory#create(java.lang.Object,
     *      org.mule.api.UMODescriptor, org.mule.api.model.UMOEntryPointResolver)
     */
    public UMOLifecycleAdapter create(Object pojoService,
                                      UMOComponent component,
                                      UMOEntryPointResolverSet resolver) throws UMOException
    {
        return new TestDefaultLifecycleAdapter(pojoService, component, resolver);
    }

}

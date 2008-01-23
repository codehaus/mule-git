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

import org.mule.api.AbstractMuleException;
import org.mule.api.component.Component;
import org.mule.api.model.EntryPointResolverSet;
import org.mule.lifecycle.DefaultLifecycleAdapter;

/** <code>TestDefaultLifecycleAdapter</code> TODO document */
public class TestDefaultLifecycleAdapter extends DefaultLifecycleAdapter
{
    /**
     * @param component
     * @param descriptor
     * @throws AbstractMuleException
     */
    public TestDefaultLifecycleAdapter(Object pojoService, Component component) throws AbstractMuleException
    {
        super(pojoService, component);
    }

    /**
     * @param component
     * @param descriptor
     * @param epResolver
     * @throws AbstractMuleException
     */
    public TestDefaultLifecycleAdapter(Object pojoService,
                                       Component component,
                                       EntryPointResolverSet epResolver) throws AbstractMuleException
    {
        super(pojoService, component, epResolver);
    }

}

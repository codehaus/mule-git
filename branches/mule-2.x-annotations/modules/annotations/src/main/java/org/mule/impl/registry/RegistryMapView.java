/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.registry;

import org.mule.registry.Registry;

import java.util.HashMap;
import java.util.Map;

/**
 * provides a {@link java.util.HashMap} view on values stored in the registry
 */
public class RegistryMapView extends HashMap
{
    private Registry registry;

    public RegistryMapView(Registry registry)
    {
        this.registry = registry;
    }

    public RegistryMapView(int i, Registry registry)
    {
        super(i);
        this.registry = registry;
    }

    public RegistryMapView(int i, float v, Registry registry)
    {
        super(i, v);
        this.registry = registry;
    }

    public RegistryMapView(Map map, Registry registry)
    {
        super(map);
        this.registry = registry;
    }

    public Object get(Object key)
    {
        Object val = super.get(key);
        if (val == null)
        {
            val = registry.lookupObject(key.toString());
        }
        return val;
    }
}

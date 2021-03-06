/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry;

import org.mule.api.MuleContext;
import org.mule.api.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultRegistryBroker extends AbstractRegistryBroker
{
    private List<Registry> registries = new ArrayList<Registry>();

    public DefaultRegistryBroker(MuleContext context)
    {
        registries.add(0, new TransientRegistry(context));
    }

    public void addRegistry(long id, Registry registry)
    {
        registries.add((int) id, registry);
    }

    public void removeRegistry(long id)
    {
        registries.remove(id);
    }

    public void addRegistry(Registry registry)
    {
        registries.add(registry);
    }

    public void removeRegistry(Registry registry)
    {
        registries.remove(registry);
    }

    protected Collection<Registry> getRegistries()
    {
        return registries;
    }
}

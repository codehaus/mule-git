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

import org.mule.api.MuleException;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.LifecycleException;
import org.mule.api.registry.RegistrationException;
import org.mule.api.registry.Registry;
import org.mule.api.registry.RegistryBroker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractRegistryBroker implements RegistryBroker
{
    public void initialise() throws InitialisationException
    {
        try
        {
            fireLifecycle(Initialisable.PHASE_NAME);
        }
        catch (InitialisationException e)
        {
            throw e;
        }
        catch (MuleException me)
        {
            throw new InitialisationException(me, this);
        }
    }

    public void dispose()
    {
        for (Registry registry : getRegistries())
        {
            try
            {
                registry.fireLifecycle(Disposable.PHASE_NAME);
            }
            catch (MuleException e)
            {
                //ignore
            }
        }
    }

    public void fireLifecycle(String phase) throws LifecycleException
    {
        for (Registry registry : getRegistries())
        {
            registry.fireLifecycle(phase);
        }
    }

    public String getRegistryId()
    {
        return this.toString();
    }

    public boolean isReadOnly()
    {
        return false;
    }

    public boolean isRemote()
    {
        return false;
    }

    abstract protected Collection<Registry> getRegistries();

    ////////////////////////////////////////////////////////////////////////////////
    // Delegating methods
    ////////////////////////////////////////////////////////////////////////////////


    @SuppressWarnings("unchecked")
    public <T> T get(String key)
    {
        return (T) lookupObject(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T lookupObject(String key)
    {
        Object obj = null;
        Iterator it = getRegistries().iterator();
        while (obj == null && it.hasNext())
        {
            obj = ((Registry) it.next()).lookupObject(key);
        }
        return (T) obj;
    }

    public <T> T lookupObject(Class<T> type) throws RegistrationException
    {
        // Accumulate objects from all registries.
        Collection<T> objects = lookupObjects(type);

        if (objects.size() == 1)
        {
            return objects.iterator().next();
        }
        else if (objects.size() > 1)
        {
            throw new RegistrationException("More than one object of type " + type + " registered but only one expected.");
        }
        else
        {
            return null;
        }
    }

    public <T> Collection<T> lookupObjects(Class<T> type)
    {
        Collection<T> objects = new ArrayList<T>();

        Iterator it = getRegistries().iterator();
        while (it.hasNext())
        {
            objects.addAll(((Registry) it.next()).lookupObjects(type));
        }
        return objects;
    }

    public <T> Map<String, T> lookupByType(Class<T> type)
    {
        Map<String, T> results = new HashMap<String, T>();
        for (Registry registry : getRegistries())
        {
            results.putAll(registry.lookupByType(type));
        }

        return results;
    }

    public void registerObject(String key, Object value) throws RegistrationException
    {
        registerObject(key, value, null);
    }

    public void registerObject(String key, Object value, Object metadata) throws RegistrationException
    {
        Iterator it = getRegistries().iterator();
        Registry reg;
        while (it.hasNext())
        {
            reg = (Registry) it.next();
            if (!reg.isReadOnly())
            {
                reg.registerObject(key, value, metadata);
                break;
            }
        }
    }

    public void registerObjects(Map objects) throws RegistrationException
    {
        Iterator it = objects.keySet().iterator();
        Object key;
        while (it.hasNext())
        {
            key = it.next();
            registerObject((String) key, objects.get(key));
        }
    }

    public void unregisterObject(String key) throws RegistrationException
    {
        unregisterObject(key, null);
    }

    public void unregisterObject(String key, Object metadata) throws RegistrationException
    {
        Iterator it = getRegistries().iterator();
        Registry reg;
        while (it.hasNext())
        {
            reg = (Registry) it.next();
            if (!reg.isReadOnly() && reg.lookupObject(key) != null)
            {
                reg.unregisterObject(key, metadata);
                break;
            }
        }
    }
}

/*
 * $Id: 
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry.impl.store;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.registry.*;
import org.mule.registry.impl.persistence.PersistenceNotification;
import org.mule.registry.impl.persistence.PersistenceNotificationListener;
import org.mule.umo.UMOException;
import org.mule.umo.manager.UMOServerNotificationListener;

/**
 * The InMemoryStore represents an, well, in-memory store of 
 * ComponentReferences.
 */
public class InMemoryStore implements RegistryStore
{
    /**
     * The store is basically a Map of Maps
     */
    private Map store = null;
    
    /**
     * This is the listener for the persistence manager. When
     * the store wants to, it can alert the persistence manager
     * to schedule persistence of the store.
     */
    private PersistenceNotificationListener notificationListener = null;

    /**
     * logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(InMemoryStore.class);

    /**
     * This map keeps track of what types this registry stores
     */
    private Map typeList = null;

    public InMemoryStore() 
    {
    }

    public void registerComponent(ComponentReference component) throws RegistrationException
    {
        if (component.getParentId() != null) 
        {
            ComponentReference parent = 
                (ComponentReference)store.get(component.getParentId());
            if (parent != null)
            {
                parent.addChild(component);
            }

        }

        logger.info("Received registration of " + component.getType() + "/" + component.getId() + " under parent " + component.getParentId());
        store.put(component.getId(), component);
    }

    public void deregisterComponent(ComponentReference component) throws DeregistrationException
    {
        logger.info("Received deregistration of " + component.getType() + "/" + component.getId());
        ComponentReference ref = 
            (ComponentReference)store.get(component.getId());
        // We will throw an exception here
        if (ref == null) return;
        store.remove(ref);
    }

    public void reregisterComponent(ComponentReference component) throws ReregistrationException
    {
        ComponentReference ref = 
            (ComponentReference)store.get(component.getId());
        // We will throw an exception here
        if (ref == null) return;
        store.put(ref.getId(), ref);
    }

    public Map getRegisteredComponents(String parentId, String type)
    {
        Map components = new HashMap();
        Iterator iter = store.keySet().iterator();
        while (iter.hasNext())
        {
            String id = (String)iter.next();
            ComponentReference ref = (ComponentReference)store.get(id);
            if (ref.getType().equals(type))
                store.put(ref.getId(), ref);
        }

        return components;
    }

    public Map getRegisteredComponents(String parentId)
    {
        return new HashMap();
    }

    public ComponentReference getRegisteredComponent(String id)
    {
        return null;
    }

    /**
     * Start the registry store
     */
    public void start() throws UMOException
    {
        logger.info("Starting");
        store = new HashMap();
    }

    /**
     * Stop the registry store
     */
    public void stop() throws UMOException 
    {
        logger.info("Stopping");
    }

    /**
     * Clean up and release any resources
     */
    public void dispose() 
    {
    }

    public void persist()
    {
        logger.info("Got request to persist");
        if (notificationListener != null)
        {
            notificationListener.onNotification(new PersistenceNotification(this, PersistenceNotification.PERSISTABLE_READY));
        }
        else
        {
            logger.info("No persistence listener registered, so can't persist");
        }
    }

    public void registerPersistenceRequestListener(UMOServerNotificationListener listener) throws UMOException
    {
        if (listener instanceof PersistenceNotificationListener)
        {
            notificationListener = (PersistenceNotificationListener)listener;
        }
    }

    public Object getPersistableObject() throws UMOException
    {
        return store;
    }

}


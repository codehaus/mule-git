/*
 * $Id: 
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.registry.*;
import org.mule.registry.impl.persistence.*;
import org.mule.registry.impl.store.InMemoryStore;
import org.mule.registry.persistence.PersistenceManager;
import org.mule.umo.UMOException;

/**
 * The MuleRegistry implements the Registry interface
 */
public class MuleRegistry implements Registry {

    private long counter = 0L;
    private RegistryStore registryStore;
    private PersistenceManager persistenceManager;
    private ComponentReferenceFactory referenceFactory;

    /**
     * logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(MuleRegistry.class);

    /**
     * 
     */
    public MuleRegistry() 
    {
        persistenceManager = new RegistryPersistenceManager();
        registryStore = new InMemoryStore();
        referenceFactory = new ComponentReferenceFactoryImpl();
        PersistenceNotificationListener listener = 
            new PersistenceNotificationListener(persistenceManager);

        try {
            registryStore.registerPersistenceRequestListener(listener);
        } catch (Exception e) { 
            logger.info(e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#getRegistryStore
     */
    public RegistryStore getRegistryStore() 
    {
        return registryStore;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#registerComponent
     */
    public String registerComponent(ComponentReference component) throws RegistrationException 
    {
        String newId = "" + getNewId();
        component.setId(newId);
        registryStore.registerComponent(component);
        return newId;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#deregisterComponent
     */
    public void deregisterComponent(ComponentReference component) throws DeregistrationException
    {
        registryStore.deregisterComponent(component);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#reregisterComponent
     */
    public void reregisterComponent(ComponentReference component) throws ReregistrationException
    {
        registryStore.reregisterComponent(component);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#getRegisteredComponents
     */
    public Map getRegisteredComponents(String parentId)
    {
        return registryStore.getRegisteredComponents(parentId);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#getRegisteredComponents
     */
    public Map getRegisteredComponents(String parentId, String type)
    {
        return registryStore.getRegisteredComponents(parentId, type);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#getRegisteredComponent
     */
    public ComponentReference getRegisteredComponent(String id)
    {
        return registryStore.getRegisteredComponent(id);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#start
     */
    public void start() throws UMOException
    {
        counter = System.currentTimeMillis();
        registryStore.start();
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#stop
     */
    public void stop() throws UMOException
    {
        registryStore.stop();
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#dispose
     */
    public void dispose()
    {
        registryStore.dispose();
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#notifyStateChange
     */
    public void notifyStateChange(String id, int state)
    {
        logger.info("Component " + id + " has state changed to " + state);

        // This is for testing only
        if (state == 206)
        {
            registryStore.persist();
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see org.mule.registry.Registry#notifyPropertyChange
     */
    public void notifyPropertyChange(String id, String propertyName, Object propertyValue)
    {
    }

    private long getNewId()
    {
        synchronized (this)
        {
            counter++;
            return counter;
        }
    }

    public ComponentReference getComponentReferenceInstance()
    {
        return referenceFactory.getInstance();
    }

    public ComponentReference getComponentReferenceInstance(String referenceType)
    {
        return referenceFactory.getInstance(referenceType);
    }
}

/*
 * $Id: 
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.registry;

import java.util.HashMap;

import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.Disposable;
import org.mule.umo.lifecycle.Startable;
import org.mule.umo.lifecycle.Stoppable;

/**
 * The Registry store is responsible for storing and persisting
 * the component references. It is also queryable and discoverable.
 */
public interface RegistryStore extends Startable, Stoppable, Disposable 
{

    public void registerComponent(ComponentReference component) throws RegistrationException;

    public void deregisterComponent(ComponentReference component) throws DeregistrationException;

    public void reregisterComponent(ComponentReference component) throws ReregistrationException;

    public Map getRegisteredComponents(String type);

    public Map getRegisteredComponents(long id);

    public ComponentReference getRegisteredComponent(long id);

    /**
     * Start the registry store
     */
    public void start() throws UMOException;

    /**
     * Stop the registry store
     */
    public void stop() throws UMOException;

    /**
     * Clean up and release any resources
     */
    public void dispose();

}


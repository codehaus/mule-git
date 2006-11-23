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
 * The Registry interface represents a registry/repository for storing
 * configuration components that can be manipulated at runtime.
 *
 * This Registry is really a facade so that we can implement various
 * other types of registries in the future. 
 */
public interface Registry extends Startable, Stoppable, Disposable {

    public RegistryStore getRegistryStore();

    public long registerComponent(ComponentReference component) throws RegistrationException;

    public void deregisterComponent(ComponentReference component) throws DeregistrationException;

    public void reregisterComponent(ComponentReference component) throws ReregistrationException;

    public HashMap getRegisteredComponents(String type);

    public ComponentReference getRegisteredComponent(String type, String id);

    public void start() throws UMOException;

    public void stop() throws UMOException;

    public void dispose();

    public void notifyStateChange(long id, int state);

    public void notifyPropertyChange(long id, String propertyName, Object propertyValue);
}

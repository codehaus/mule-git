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

/**
 * The ComponentReference interface represents a component that is storable
 * in the registry.
 */

public interface ComponentReference {

    public static int STATE_NEW = 0;
    public static int STATE_INSTANTIATED = 1;
    public static int STATE_STARTING = 2;
    public static int STATE_STARTED = 3;
    public static int STATE_STOPPING = 4;
    public static int STATE_STOPPED = 5;
    public static int STATE_DISPOSED = 6;

    public String getType();

    public long getId();

    public long getParentId();

    public HashMap getProperties();

    public Object getProperty(String key);

    public int getState();

    public HashMap getChildren();

    public ComponentReference getChild(long childId);

    public ComponentVersion getVersion();

    public void setType(String type);

    public void setId(long id);

    public void setParentId(long parentId);

    public void setProperties(HashMap properties);

    public void setProperty(String key, Object property);

    public void addChild(ComponentReference component);

    public void setVersion(ComponentVersion);

    /*
    public void register();

    public void deploy();

    public void undeploy();

    public void unregister();

    public void addChildReference(ComponentReference ref);
    */
}


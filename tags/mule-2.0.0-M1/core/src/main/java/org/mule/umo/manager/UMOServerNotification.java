/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.manager;

import org.mule.RegistryContext;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.util.ClassUtils;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <code>UMOServerNotification</code> is an event triggered by something happening
 * in the Server itself such as the server starting or a component being registered
 *
 */
public abstract class UMOServerNotification extends EventObject
{
    public static final String TYPE_INFO = "info";
    public static final String TYPE_WARNING = "warn";
    public static final String TYPE_ERROR = "error";
    public static final String TYPE_FATAL = "fatal";

    public static final int MANAGER_EVENT_ACTION_START_RANGE = 100;
    public static final int MODEL_EVENT_ACTION_START_RANGE = 200;
    public static final int COMPONENT_EVENT_ACTION_START_RANGE = 300;
    public static final int SECURITY_EVENT_ACTION_START_RANGE = 400;
    public static final int MANAGEMENT_EVENT_ACTION_START_RANGE = 500;
    public static final int ADMIN_EVENT_ACTION_START_RANGE = 600;
    public static final int CONNECTION_EVENT_ACTION_START_RANGE = 700;
    public static final int MESSAGE_EVENT_ACTION_START_RANGE = 800;
    public static final int SPACE_EVENT_ACTION_START_RANGE = 900;
    public static final int REGISTRY_EVENT_ACTION_START_RANGE = 1000;
    public static final int EXCEPTION_EVENT_ACTION_START_RANGE = 1100;
    public static final int TRANSACTION_EVENT_ACTION_START_RANGE = 1200;
    public static final int CUSTOM_EVENT_ACTION_START_RANGE = 100000;

    public static final int NULL_ACTION = 0;
    public static final Object NULL_MESSAGE = "";

    public final String EVENT_NAME = ClassUtils.getClassName(getClass());

    protected String serverId;

    protected long timestamp;

    protected int action = NULL_ACTION;
    protected static Map actions = new HashMap();

    /**
     * The resourceIdentifier is used when firing inbound server notifications such
     * as Admin notifications or other action notifications triggered by an external
     * source Used to associate the event with a particular resource. For example, if
     * the event was a ComponentNotification the resourceIdentifier could be the name
     * of a particular component
     */
    protected String resourceIdentifier = null;

    public UMOServerNotification(Object message, int action)
    {
        super((message == null ? NULL_MESSAGE : message));
        this.action = action;
        if (RegistryContext.getRegistry()!=null)
        {
            serverId = message.toString();
        }
        timestamp = System.currentTimeMillis();
    }

    public UMOServerNotification(Object message, int action, String resourceIdentifier)
    {
        super((message == null ? NULL_MESSAGE : message));
        this.action = action;
        this.resourceIdentifier = resourceIdentifier;
        serverId = message.toString();
        timestamp = System.currentTimeMillis();
    }

    protected static void registerAction(String name, int i)
    {
        actions.put(new Integer(i), name.toLowerCase());
    }



    public int getAction()
    {
        return action;
    }

    public String getServerId()
    {
        return serverId;
    }

    public String getResourceIdentifier()
    {
        return resourceIdentifier;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public boolean isResourceIdentifierAnUri()
    {
        return MuleEndpointURI.isMuleUri(resourceIdentifier);
    }

    public String toString()
    {
        return EVENT_NAME + "{" + "action=" + getActionName(action) + ", resourceId=" + resourceIdentifier
               + ", serverId=" + serverId + ", timestamp=" + timestamp + "}";
    }

    protected String getPayloadToString()
    {
        return source.toString();
    }

    public String getType()
    {
        return TYPE_INFO;
    }

    public String getActionName()
    {
        return getActionName(action);
    }

    public static String getActionName(int action)
    {
        return (String) actions.get(new Integer(action));
    }

    public static int getActionId(String action)
    {
        int i = 0;
        Map.Entry entry;
        for (Iterator iterator = actions.entrySet().iterator(); iterator.hasNext();i++)
        {
            entry = (Map.Entry) iterator.next();
            if(entry.getValue().equals(action.toLowerCase()))
            {
                return ((Integer)entry.getKey()).intValue();
            }
        }
        throw new IllegalArgumentException("No Action called: " + action);

    }
}

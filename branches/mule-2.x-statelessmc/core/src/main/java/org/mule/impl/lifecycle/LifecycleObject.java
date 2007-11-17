/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.lifecycle;

import org.mule.impl.internal.notifications.ServerNotificationManager;
import org.mule.umo.manager.UMOServerNotification;

/**
 * TODO
 */
public class LifecycleObject
{
    private Class type;
    private UMOServerNotification preNotification;
    private UMOServerNotification postNotification;
    private ServerNotificationManager notificationManager;

    public LifecycleObject(Class type, ServerNotificationManager notificationManager)
    {
        this.type = type;
        this.notificationManager = notificationManager;
    }

    public UMOServerNotification getPostNotification()
    {
        return postNotification;
    }

    public void setPostNotification(UMOServerNotification postNotification)
    {
        this.postNotification = postNotification;
    }

    public UMOServerNotification getPreNotification()
    {
        return preNotification;
    }

    public void setPreNotification(UMOServerNotification preNotification)
    {
        this.preNotification = preNotification;
    }

    public Class getType()
    {
        return type;
    }

    public void setType(Class type)
    {
        this.type = type;
    }

    public void firePreNotification()
    {
        if(preNotification!=null)
        {
            notificationManager.fireEvent(preNotification);
        }
    }

    public void firePostNotification()
    {
        if(postNotification!=null)
        {
            notificationManager.fireEvent(postNotification);
        }
    }
}
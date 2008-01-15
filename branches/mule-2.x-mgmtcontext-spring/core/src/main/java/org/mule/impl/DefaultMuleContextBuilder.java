/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl;

import org.mule.config.MuleConfiguration;
import org.mule.impl.internal.notifications.AdminNotification;
import org.mule.impl.internal.notifications.AdminNotificationListener;
import org.mule.impl.internal.notifications.ComponentNotification;
import org.mule.impl.internal.notifications.ComponentNotificationListener;
import org.mule.impl.internal.notifications.ConnectionNotification;
import org.mule.impl.internal.notifications.ConnectionNotificationListener;
import org.mule.impl.internal.notifications.CustomNotification;
import org.mule.impl.internal.notifications.CustomNotificationListener;
import org.mule.impl.internal.notifications.ExceptionNotification;
import org.mule.impl.internal.notifications.ExceptionNotificationListener;
import org.mule.impl.internal.notifications.ManagementNotification;
import org.mule.impl.internal.notifications.ManagementNotificationListener;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.impl.internal.notifications.ManagerNotificationListener;
import org.mule.impl.internal.notifications.ModelNotification;
import org.mule.impl.internal.notifications.ModelNotificationListener;
import org.mule.impl.internal.notifications.RegistryNotification;
import org.mule.impl.internal.notifications.RegistryNotificationListener;
import org.mule.impl.internal.notifications.SecurityNotification;
import org.mule.impl.internal.notifications.SecurityNotificationListener;
import org.mule.impl.internal.notifications.TransactionNotification;
import org.mule.impl.internal.notifications.TransactionNotificationListener;
import org.mule.impl.internal.notifications.manager.ServerNotificationManager;
import org.mule.impl.lifecycle.GenericLifecycleManager;
import org.mule.impl.lifecycle.phases.MuleContextDisposePhase;
import org.mule.impl.lifecycle.phases.MuleContextInitialisePhase;
import org.mule.impl.lifecycle.phases.MuleContextStartPhase;
import org.mule.impl.lifecycle.phases.MuleContextStopPhase;
import org.mule.impl.work.MuleWorkManager;
import org.mule.umo.MuleContext;
import org.mule.umo.MuleContextBuilder;
import org.mule.umo.lifecycle.UMOLifecycleManager;
import org.mule.umo.manager.UMOWorkManager;

public class DefaultMuleContextBuilder implements MuleContextBuilder
{

    protected MuleConfiguration muleConfiguration;

    protected UMOLifecycleManager lifecycleManager;

    protected UMOWorkManager workManager;

    protected ServerNotificationManager notificationManager;

    public MuleContext buildMuleContext()
    {
        MuleContext muleContext = new DefaultMuleContext(getLifecycleManager());
        muleContext.setConfiguration(getMuleConfiguration());
        muleContext.setWorkManager(getWorkManager());
        muleContext.setNotificationManager(getNotificationManager());
        return muleContext;
    }

    public void setMuleConfiguration(MuleConfiguration muleConfiguration)
    {
        this.muleConfiguration = muleConfiguration;
    }

    public void setWorkManager(UMOWorkManager workManager)
    {
        this.workManager = workManager;
    }

    public void setNotificationManager(ServerNotificationManager notificationManager)
    {
        this.notificationManager = notificationManager;
    }

    public void setLifecycleManager(UMOLifecycleManager lifecycleManager)
    {
        this.lifecycleManager = lifecycleManager;
    }

    protected MuleConfiguration getMuleConfiguration()
    {
        if (muleConfiguration != null)
        {
            return muleConfiguration;
        }
        else
        {
            return new MuleConfiguration();

        }
    }

    protected UMOLifecycleManager getLifecycleManager()
    {
        if (lifecycleManager != null)
        {
            return lifecycleManager;
        }
        else
        {
            UMOLifecycleManager lifecycleManager = new GenericLifecycleManager();
            lifecycleManager.registerLifecycle(new MuleContextInitialisePhase());
            lifecycleManager.registerLifecycle(new MuleContextStartPhase());
            lifecycleManager.registerLifecycle(new MuleContextStopPhase());
            lifecycleManager.registerLifecycle(new MuleContextDisposePhase());
            return lifecycleManager;
        }
    }

    protected UMOWorkManager getWorkManager()
    {
        if (workManager != null)
        {
            return workManager;
        }
        else
        {
            return new MuleWorkManager(getMuleConfiguration().getDefaultComponentThreadingProfile(), "MuleServer");
           
        }
    }

    protected ServerNotificationManager getNotificationManager()
    {
        if (notificationManager != null)
        {
            return notificationManager;
        }
        else
        {
            ServerNotificationManager notificationManager = new ServerNotificationManager();
            notificationManager.addInterfaceToType(ManagerNotificationListener.class,
                ManagerNotification.class);
            notificationManager.addInterfaceToType(ModelNotificationListener.class, ModelNotification.class);
            notificationManager.addInterfaceToType(ComponentNotificationListener.class,
                ComponentNotification.class);
            notificationManager.addInterfaceToType(SecurityNotificationListener.class,
                SecurityNotification.class);
            notificationManager.addInterfaceToType(ManagementNotificationListener.class,
                ManagementNotification.class);
            notificationManager.addInterfaceToType(AdminNotificationListener.class, AdminNotification.class);
            notificationManager.addInterfaceToType(CustomNotificationListener.class, CustomNotification.class);
            notificationManager.addInterfaceToType(ConnectionNotificationListener.class,
                ConnectionNotification.class);
            notificationManager.addInterfaceToType(RegistryNotificationListener.class,
                RegistryNotification.class);
            notificationManager.addInterfaceToType(ExceptionNotificationListener.class,
                ExceptionNotification.class);
            notificationManager.addInterfaceToType(TransactionNotificationListener.class,
                TransactionNotification.class);
            return notificationManager;
        }
    }

}

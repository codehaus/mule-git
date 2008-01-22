/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.lifecycle.phases;

import org.mule.api.MuleContext;
import org.mule.api.UMOComponent;
import org.mule.api.context.UMOAgent;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.model.UMOModel;
import org.mule.api.registry.Registry;
import org.mule.api.transport.UMOConnector;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.impl.lifecycle.LifecyclePhase;
import org.mule.impl.lifecycle.NotificationLifecycleObject;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Start phase for the Management context LifecycleManager. Calling {@link org.mule.api.UMOManagementContext#start()}
 * with initiate this phase via the {@link org.mule.api.lifecycle.UMOLifecycleManager}.
 * This phase controls the order in which objects should be started.
 *
 * @see org.mule.api.UMOManagementContext
 * @see org.mule.api.lifecycle.UMOLifecycleManager
 * @see org.mule.api.lifecycle.Startable
 */
public class MuleContextStartPhase extends LifecyclePhase
{
    public MuleContextStartPhase()
    {
        this(new Class[]{Registry.class, MuleContext.class});
    }

    public MuleContextStartPhase(Class[] ignorredObjects)
    {
        super(Startable.PHASE_NAME, Startable.class, Stoppable.PHASE_NAME);

        Set startOrderedObjects = new LinkedHashSet();
        startOrderedObjects.add(new NotificationLifecycleObject(UMOConnector.class));
        startOrderedObjects.add(new NotificationLifecycleObject(UMOAgent.class));
        startOrderedObjects.add(new NotificationLifecycleObject(UMOModel.class, ManagerNotification.class,
                ManagerNotification.MANAGER_STARTING_MODELS,ManagerNotification.MANAGER_STARTED_MODELS));
        startOrderedObjects.add(new NotificationLifecycleObject(UMOComponent.class));
        startOrderedObjects.add(new NotificationLifecycleObject(Startable.class));


        setIgnorredObjectTypes(ignorredObjects);
        setOrderedLifecycleObjects(startOrderedObjects);
        registerSupportedPhase(Stoppable.PHASE_NAME);
        registerSupportedPhase(Initialisable.PHASE_NAME);
    }
}
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
import org.mule.api.context.UMOAgent;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.UMOLifecyclePhase;
import org.mule.api.model.UMOModel;
import org.mule.api.registry.Registry;
import org.mule.api.transport.UMOConnector;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.impl.lifecycle.LifecyclePhase;
import org.mule.impl.lifecycle.NotificationLifecycleObject;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Dispose phase for the TransientRegistry LifecycleManager. Calling {@link org.mule.impl.registry.TransientRegistry#dispose()}
 * with initiate this phase via the {@link org.mule.api.lifecycle.UMOLifecycleManager}.
 * This phase controls the order in which objects should be disposed.
 *
 * @see org.mule.api.MuleContext
 * @see org.mule.api.lifecycle.UMOLifecycleManager
 * @see org.mule.impl.registry.TransientRegistry
 * @see org.mule.api.lifecycle.Disposable
 */
public class TransientRegistryDisposePhase extends LifecyclePhase
{
    public TransientRegistryDisposePhase()
    {
        this(new Class[]{Registry.class});
    }

    public TransientRegistryDisposePhase(Class[] ignorredObjects)
    {
        super(Disposable.PHASE_NAME, Disposable.class, Initialisable.PHASE_NAME);

        Set disposeOrderedObjects = new LinkedHashSet();
//        disposeOrderedObjects.add(new NotificationLifecycleObject(MuleContext.class, ManagerNotification.class,
//                ManagerNotification.getActionName(ManagerNotification.MANAGER_DISPOSING),
//                ManagerNotification.getActionName(ManagerNotification.MANAGER_DISPOSED)));
        disposeOrderedObjects.add(new NotificationLifecycleObject(MuleContext.class));
        disposeOrderedObjects.add(new NotificationLifecycleObject(UMOConnector.class, ManagerNotification.class,
                ManagerNotification.MANAGER_DISPOSING_CONNECTORS,ManagerNotification.MANAGER_DISPOSED_CONNECTORS));
        disposeOrderedObjects.add(new NotificationLifecycleObject(UMOAgent.class));
        disposeOrderedObjects.add(new NotificationLifecycleObject(UMOModel.class));
        disposeOrderedObjects.add(new NotificationLifecycleObject(Disposable.class));

        setIgnorredObjectTypes(ignorredObjects);
        setOrderedLifecycleObjects(disposeOrderedObjects);
        registerSupportedPhase(UMOLifecyclePhase.ALL_PHASES);
    }
}

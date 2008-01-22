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
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.model.UMOModel;
import org.mule.api.registry.Registry;
import org.mule.api.transformer.UMOTransformer;
import org.mule.api.transport.UMOConnector;
import org.mule.impl.lifecycle.LifecyclePhase;
import org.mule.impl.lifecycle.NotificationLifecycleObject;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The Initialise phase for the TransientRegistry LifecycleManager. Calling {@link org.mule.impl.registry.TransientRegistry#initialise()}
 * with initiate this phase via the {@link org.mule.api.lifecycle.UMOLifecycleManager}.
 * This phase controls the order in which objects should be initialised.
 *
 * @see org.mule.api.MuleContext
 * @see org.mule.api.lifecycle.UMOLifecycleManager
 * @see org.mule.impl.registry.TransientRegistry
 * @see org.mule.api.lifecycle.Initialisable
 */
public class TransientRegistryInitialisePhase extends LifecyclePhase
{
    public TransientRegistryInitialisePhase()
    {
        this(new Class[]{Registry.class});
    }

    public TransientRegistryInitialisePhase(Class[] ignorredObjects)
    {
        super(Initialisable.PHASE_NAME, Initialisable.class, Disposable.PHASE_NAME);

        setIgnorredObjectTypes(ignorredObjects);
        Set initOrderedObjects = new LinkedHashSet();
        initOrderedObjects.add(new NotificationLifecycleObject(MuleContext.class));
        initOrderedObjects.add(new NotificationLifecycleObject(UMOConnector.class));
        initOrderedObjects.add(new NotificationLifecycleObject(UMOTransformer.class));
        initOrderedObjects.add(new NotificationLifecycleObject(UMOImmutableEndpoint.class));
        initOrderedObjects.add(new NotificationLifecycleObject(UMOAgent.class));
        initOrderedObjects.add(new NotificationLifecycleObject(UMOComponent.class));
        initOrderedObjects.add(new NotificationLifecycleObject(UMOModel.class));

        initOrderedObjects.add(new NotificationLifecycleObject(Initialisable.class));

        setOrderedLifecycleObjects(initOrderedObjects);
        registerSupportedPhase(NotInLifecyclePhase.PHASE_NAME);
    }
}

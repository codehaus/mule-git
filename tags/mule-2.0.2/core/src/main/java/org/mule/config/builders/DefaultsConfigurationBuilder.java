/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.builders;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.MuleProperties;
import org.mule.api.config.ThreadingProfile;
import org.mule.api.model.Model;
import org.mule.api.registry.Registry;
import org.mule.config.ChainedThreadingProfile;
import org.mule.config.bootstrap.SimpleRegistryBootstrap;
import org.mule.endpoint.DefaultEndpointFactory;
import org.mule.model.seda.SedaModel;
import org.mule.security.MuleSecurityManager;
import org.mule.transport.SingleAttemptConnectionStrategy;
import org.mule.util.DefaultStreamCloserService;
import org.mule.util.queue.FilePersistenceStrategy;
import org.mule.util.queue.QueueManager;
import org.mule.util.queue.TransactionalQueueManager;

/**
 * Configures defaults required by Mule. This configuration builder is used to
 * configure mule with these defaults when no other ConfigurationBuilder that sets
 * these is being used. This is used by both AbstractMuleTestCase and MuleClient.
 * <br>
 * <br>
 * Default instances of the following are configured:
 * <li> {@link SimpleRegistryBootstrap}
 * <li> {@link QueueManager}
 * <li> {@link SecurityManager}
 * <li> {@link DefaultEndpointFactory}
 * <li> {@link Model} systemModel
 * <li> {@link ThreadingProfile} defaultThreadingProfile
 * <li> {@link ThreadingProfile} defaultMessageDispatcherThreadingProfile
 * <li> {@link ThreadingProfile} defaultMessageRequesterThreadingProfile
 * <li> {@link ThreadingProfile} defaultMessageReceiverThreadingProfile
 * <li> {@link ThreadingProfile} defaultComponentThreadingProfile
 */
public class DefaultsConfigurationBuilder extends AbstractConfigurationBuilder
{

    protected void doConfigure(MuleContext muleContext) throws Exception
    {
        configureDefaults(muleContext.getRegistry());
    }

    protected void configureDefaults(Registry registry) throws MuleException
    {
        registry.registerObject(MuleProperties.OBJECT_MULE_SIMPLE_REGISTRY_BOOTSTRAP,
            new SimpleRegistryBootstrap());
        
        QueueManager queueManager = new TransactionalQueueManager();
        queueManager.setPersistenceStrategy(new FilePersistenceStrategy());
        registry.registerObject(MuleProperties.OBJECT_QUEUE_MANAGER, queueManager);
        
        registry.registerObject(MuleProperties.OBJECT_SECURITY_MANAGER, new MuleSecurityManager());
        
        registry.registerObject(MuleProperties.OBJECT_MULE_ENDPOINT_FACTORY, new DefaultEndpointFactory());
        registry.registerObject(MuleProperties.OBJECT_MULE_STREAM_CLOSER_SERVICE, new DefaultStreamCloserService());
        
        ThreadingProfile defaultThreadingProfile = new ChainedThreadingProfile();
        defaultThreadingProfile.setThreadWaitTimeout(30);
        defaultThreadingProfile.setMaxThreadsActive(10);
        defaultThreadingProfile.setMaxThreadsIdle(10);
        defaultThreadingProfile.setMaxBufferSize(0);
        defaultThreadingProfile.setThreadTTL(60000);
        defaultThreadingProfile.setPoolExhaustedAction(ThreadingProfile.WHEN_EXHAUSTED_RUN);
        registry.registerObject(MuleProperties.OBJECT_DEFAULT_THREADING_PROFILE, defaultThreadingProfile);
        registry.registerObject(MuleProperties.OBJECT_DEFAULT_MESSAGE_RECEIVER_THREADING_PROFILE,
            new ChainedThreadingProfile(defaultThreadingProfile));
        registry.registerObject(MuleProperties.OBJECT_DEFAULT_MESSAGE_REQUESTER_THREADING_PROFILE,
            new ChainedThreadingProfile(defaultThreadingProfile));
        registry.registerObject(MuleProperties.OBJECT_DEFAULT_MESSAGE_DISPATCHER_THREADING_PROFILE,
            new ChainedThreadingProfile(defaultThreadingProfile));
        registry.registerObject(MuleProperties.OBJECT_DEFAULT_COMPONENT_THREADING_PROFILE,
            new ChainedThreadingProfile(defaultThreadingProfile));
        
        registry.registerObject(MuleProperties.OBJECT_DEFAULT_CONNECTION_STRATEGY, new SingleAttemptConnectionStrategy());
        
        Model systemModel = new SedaModel();
        systemModel.setName(MuleProperties.OBJECT_SYSTEM_MODEL);
        registry.registerModel(systemModel);
    }
}

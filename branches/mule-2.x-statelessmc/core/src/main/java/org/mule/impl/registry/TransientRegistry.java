/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.registry;

import org.mule.MuleServer;
import org.mule.RegistryContext;
import org.mule.config.MuleConfiguration;
import org.mule.config.MuleProperties;
import org.mule.config.ThreadingProfile;
import org.mule.config.bootstrap.SimpleRegistryBootstrap;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.ManagementContext;
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
import org.mule.impl.internal.notifications.ServerNotificationManager;
import org.mule.impl.internal.notifications.TransactionNotification;
import org.mule.impl.internal.notifications.TransactionNotificationListener;
import org.mule.impl.lifecycle.ContainerManagedLifecyclePhase;
import org.mule.impl.lifecycle.GenericLifecycleManager;
import org.mule.impl.lifecycle.phases.ManagementContextStartPhase;
import org.mule.impl.lifecycle.phases.ManagementContextStopPhase;
import org.mule.impl.lifecycle.phases.TransientRegistryDisposePhase;
import org.mule.impl.lifecycle.phases.TransientRegistryInitialisePhase;
import org.mule.impl.model.ModelServiceDescriptor;
import org.mule.impl.security.MuleSecurityManager;
import org.mule.impl.work.MuleWorkManager;
import org.mule.registry.AbstractServiceDescriptor;
import org.mule.registry.RegistrationException;
import org.mule.registry.Registry;
import org.mule.registry.ServiceDescriptor;
import org.mule.registry.ServiceDescriptorFactory;
import org.mule.registry.ServiceException;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.endpoint.UMOEndpointBuilder;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.Disposable;
import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.Stoppable;
import org.mule.umo.lifecycle.UMOLifecycleManager;
import org.mule.umo.lifecycle.UMOLifecyclePhase;
import org.mule.umo.manager.UMOAgent;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.umo.model.UMOModel;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.security.UMOSecurityManager;
import org.mule.umo.transformer.DiscoverableTransformer;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.BeanUtils;
import org.mule.util.SpiUtils;
import org.mule.util.UUID;
import org.mule.util.queue.CachingPersistenceStrategy;
import org.mule.util.queue.MemoryPersistenceStrategy;
import org.mule.util.queue.QueueManager;
import org.mule.util.queue.TransactionalQueueManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * Read-write Registry for registering/unregistering objects at run-time.
 */
public class TransientRegistry extends AbstractRegistry
{
    /** logger used by this class */
    protected transient final Log logger = LogFactory.getLog(getClass());
    public static final String REGISTRY_ID = "org.mule.Registry.Transient";

    /** Registry is a single HashMap */
    private Map registry;

    public static Registry getInstance()
    {
        return new TransientRegistry();
    }
    
    public TransientRegistry()
    {
        super(REGISTRY_ID);
        init();
    }

    public TransientRegistry(Registry parent)
    {
        super(REGISTRY_ID, parent);
        init();
    }

    private void init()
    {
        registry = new ConcurrentHashMap();

        //Register ManagementContext Injector for locally registered objects
        //TODO this has to be registered once the managementContext is created
//        getObjectTypeMap(ObjectProcessor.class).put(MuleProperties.OBJECT_MANAGMENT_CONTEXT_PROCESSOR,
//                new ManagementContextDependencyProcessor(context));

        registry.put("_mulePropertyExtractorProcessor", new PropertyExtractorProcessor());

        RegistryContext.setRegistry(this);
        try
        {
            initialise();
        }
        catch (InitialisationException e)
        {
            e.printStackTrace();
        }

    }

    protected UMOLifecycleManager createLifecycleManager()
    {
        GenericLifecycleManager lcm = new GenericLifecycleManager();
        UMOLifecyclePhase initPhase = new TransientRegistryInitialisePhase(getNotificationManager());
        initPhase.setRegistryScope(Registry.SCOPE_IMMEDIATE);
        lcm.registerLifecycle(initPhase);
        UMOLifecyclePhase disposePhase = new TransientRegistryDisposePhase(getNotificationManager());
        disposePhase.setRegistryScope(Registry.SCOPE_IMMEDIATE);
        lcm.registerLifecycle(disposePhase);
        return lcm;
    }

    //@java.lang.Override
    protected void doInitialise() throws InitialisationException
    {
        int oldScope = getDefaultScope();
        setDefaultScope(Registry.SCOPE_IMMEDIATE);
        try
        {
            applyProcessors(getConnectors());
            applyProcessors(getTransformers());
            applyProcessors(getEndpoints());
            applyProcessors(getAgents());
            applyProcessors(getModels());
            applyProcessors(lookupComponents());
            applyProcessors(lookupObjects(Object.class));
        }
        finally
        {
            setDefaultScope(oldScope);
        }
    }

    protected void applyProcessors(Map objects)
    {
        if (objects == null)
        {
            return;
        }
        for (Iterator iterator = objects.values().iterator(); iterator.hasNext();)
        {
            Object o = iterator.next();
            Collection processors = lookupObjects(ObjectProcessor.class);
            for (Iterator iterator2 = processors.iterator(); iterator2.hasNext();)
            {
                ObjectProcessor op = (ObjectProcessor) iterator2.next();
                op.process(o);
            }
        }
    }


    public void registerObjects(Map objects) throws RegistrationException
    {
        if (objects == null)
        {
            return;
        }

        for (Iterator iterator = objects.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            registerObject(entry.getKey().toString(), entry.getValue());
        }
    }

    protected Object doLookupObject(String key)
    {
        return registry.get(key);
    }

    public Collection doLookupObjects(Class returntype)
    {
        Collection collection = new ArrayList();
        Object o;
        Iterator iterator = registry.values().iterator(); 
        while (iterator.hasNext())
        {
            o = iterator.next();
            if (returntype.isAssignableFrom(o.getClass()))
            {
                collection.add(o);
            }
        }
        return collection;
    }


    /** Looks up the service descriptor from a singleton cache and creates a new one if not found. */
    public ServiceDescriptor lookupServiceDescriptor(String type, String name, Properties overrides) throws ServiceException
    {
        AbstractServiceDescriptor.Key key = new AbstractServiceDescriptor.Key(name, overrides);
        //TODO If we want these descriptors loaded form Spring we need to checnge the key mechanism
        ServiceDescriptor sd = (ServiceDescriptor) lookupObject(String.valueOf(key.hashCode()));

        synchronized (this)
        {
            if (sd == null)
            {
                sd = createServiceDescriptor(type, name, overrides);
                try
                {
                    registerObject(String.valueOf(key.hashCode()), sd, ServiceDescriptor.class);
                }
                catch (RegistrationException e)
                {
                    throw new ServiceException(e.getI18nMessage(), e);
                }
            }
        }
        return sd;
    }

    /** @deprecated ServiceDescriptors will be created upon bundle startup for OSGi. */
    protected ServiceDescriptor createServiceDescriptor(String type, String name, Properties overrides) throws ServiceException
    {
        Properties props = SpiUtils.findServiceDescriptor(type, name);
        if (props == null)
        {
            throw new ServiceException(CoreMessages.failedToLoad(type + " " + name));
        }
        return ServiceDescriptorFactory.create(type, name, props, overrides, this);
    }

    protected Object applyProcessors(Object object)
    {
        Object theObject = object;
        Collection processors = lookupObjects(ObjectProcessor.class);
        for (Iterator iterator = processors.iterator(); iterator.hasNext();)
        {
            ObjectProcessor o = (ObjectProcessor) iterator.next();
            theObject = o.process(theObject);
        }
        return theObject;
    }

    /**
     * Allows for arbitary registration of transient objects
     *
     * @param key
     * @param value
     */
    protected void doRegisterObject(String key, Object value) throws RegistrationException
    {
        doRegisterObject(key, value, Object.class);
    }

    /**
     * Allows for arbitary registration of transient objects
     *
     * @param key
     * @param value
     */
    protected void doRegisterObject(String key, Object value, Object metadata) throws RegistrationException
    {
        if (isInitialised() || isInitialising())
        {
            value = applyProcessors(value);
        }

        if (registry.containsKey(key))
        {
            // registry.put(key, value) would overwrite a previous entity with the same name.  Is this really what we want?
            // Not sure whether to throw an exception or log a warning here.
            //throw new RegistrationException("TransientRegistry already contains an object named '" + key + "'.  The previous object would be overwritten.");
            logger.warn("TransientRegistry already contains an object named '" + key + "'.  The previous object will be overwritten.");
        }
        registry.put(key, value);
        
        try
        {
            getLifecycleManager().applyLifecycle(value);
        }
        catch (UMOException e)
        {
            throw new RegistrationException(e);
        }
    }

    //@java.lang.Override
    public void registerAgent(UMOAgent agent) throws UMOException
    {
        registerObject(agent.getName(), agent);
    }

    //@java.lang.Override
    public void registerConnector(UMOConnector connector) throws UMOException
    {
        registerObject(connector.getName(), connector);
    }

    //@java.lang.Override
    public void registerEndpoint(UMOImmutableEndpoint endpoint) throws UMOException
    {
        registerObject(endpoint.getName(), endpoint);
    }

    public void registerEndpointBuilder(String name, UMOEndpointBuilder builder) throws UMOException
    {
        registerObject(name, builder);
    }

    //@java.lang.Override
    public void registerModel(UMOModel model) throws UMOException
    {
        registerObject(model.getName(), model);
    }

    //@java.lang.Override
    protected void doRegisterTransformer(UMOTransformer transformer) throws UMOException
    {
        //TODO should we always throw an exception if an object already exists
        if (lookupTransformer(transformer.getName()) != null)
        {
            throw new RegistrationException(CoreMessages.objectAlreadyRegistered("transformer: " +
                    transformer.getName(), lookupTransformer(transformer.getName()), transformer).getMessage());
        }
        registerObject(transformer.getName(), transformer);
    }

    //@java.lang.Override
    public void registerComponent(UMOComponent component) throws UMOException
    {
        registerObject(component.getName(), component);
    }

    //@java.lang.Override
    public void unregisterObject(String key) throws UMOException
    {
        Object obj = registry.remove(key);
        if (obj == null)
        {
            logger.warn("Attempt to unregister object " + key + ", but was not registered.");
        }
        else if (obj instanceof Stoppable)
        {
            ((Stoppable) obj).stop();
        }
    }

    //@java.lang.Override
    public void unregisterComponent(String componentName) throws UMOException
    {
        unregisterObject(componentName);
    }


    //@java.lang.Override
    public void unregisterAgent(String agentName) throws UMOException
    {
        unregisterObject(agentName);
    }

    //@java.lang.Override
    public void unregisterConnector(String connectorName) throws UMOException
    {
        unregisterObject(connectorName);
    }

    //@java.lang.Override
    public void unregisterEndpoint(String endpointName) throws UMOException
    {
        unregisterObject(endpointName);
    }

    //@java.lang.Override
    public void unregisterModel(String modelName) throws UMOException
    {
        unregisterObject(modelName);
    }

    //@java.lang.Override
    public void unregisterTransformer(String transformerName) throws UMOException
    {
        UMOTransformer transformer = lookupTransformer(transformerName);
        if (transformer instanceof DiscoverableTransformer)
        {
            exactTransformerCache.clear();
            transformerListCache.clear();
        }
        unregisterObject(transformerName);
    }

    //@java.lang.Override
    public UMOTransformer lookupTransformer(String name)
    {
        UMOTransformer transformer = super.lookupTransformer(name);
        if (transformer != null)
        {
            try
            {
                if (transformer.getEndpoint() != null)
                {
                    throw new IllegalStateException("Endpoint cannot be set");
                }
//                Map props = BeanUtils.describe(transformer);
//                props.remove("endpoint");
//                props.remove("strategy");
//                transformer = (UMOTransformer)ClassUtils.instanciateClass(transformer.getClass(), ClassUtils.NO_ARGS);
                //TODO: friggin' cloning
                transformer = (UMOTransformer) BeanUtils.cloneBean(transformer);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return transformer;
    }

    public boolean isReadOnly()
    {
        return false;
    }

    public boolean isRemote()
    {
        return false;
    }

    protected void checkLifecycleForPropertySet(String propertyName, String phase) throws IllegalStateException
    {
        if (getLifecycleManager().isPhaseComplete(phase))
        {
            throw new IllegalStateException("Cannot set property: '" + propertyName + "' once the server has been gone through the " + phase + " phase.");
        }
    }
    
    public static Registry createNew() throws UMOException
    {
        //Use the default server lifecycleManager
        //UMOLifecycleManager lifecycleManager = new DefaultLifecycleManager();
        UMOLifecycleManager lifecycleManager = new GenericLifecycleManager();

        MuleConfiguration config = new MuleConfiguration();

        QueueManager queueManager = new TransactionalQueueManager();
        queueManager.setPersistenceStrategy(new CachingPersistenceStrategy(new MemoryPersistenceStrategy()));

        ThreadingProfile tp = config.getDefaultThreadingProfile();
        UMOWorkManager workManager = new MuleWorkManager(tp, "MuleServer");

        ServerNotificationManager notificationManager = new ServerNotificationManager();
        notificationManager.registerEventType(ManagerNotificationListener.class, ManagerNotification.class);
        notificationManager.registerEventType(ModelNotificationListener.class, ModelNotification.class);
        notificationManager.registerEventType(ComponentNotificationListener.class, ComponentNotification.class);
        notificationManager.registerEventType(SecurityNotificationListener.class, SecurityNotification.class);
        notificationManager.registerEventType(ManagementNotificationListener.class, ManagementNotification.class);
        notificationManager.registerEventType(AdminNotificationListener.class, AdminNotification.class);
        notificationManager.registerEventType(CustomNotificationListener.class, CustomNotification.class);
        notificationManager.registerEventType(ConnectionNotificationListener.class, ConnectionNotification.class);
        notificationManager.registerEventType(RegistryNotificationListener.class, RegistryNotification.class);
        notificationManager.registerEventType(ExceptionNotificationListener.class, ExceptionNotification.class);
        notificationManager.registerEventType(TransactionNotificationListener.class, TransactionNotification.class);

        lifecycleManager.registerLifecycle(new ContainerManagedLifecyclePhase(Initialisable.PHASE_NAME, Initialisable.class, Disposable.PHASE_NAME));
        lifecycleManager.registerLifecycle(new ManagementContextStartPhase(notificationManager));
        lifecycleManager.registerLifecycle(new ManagementContextStopPhase(notificationManager));
        lifecycleManager.registerLifecycle(new ContainerManagedLifecyclePhase(Disposable.PHASE_NAME, Disposable.class, Initialisable.PHASE_NAME));

        UMOSecurityManager securityManager = new MuleSecurityManager();

        UMOManagementContext context = new ManagementContext(lifecycleManager);

        //Create the registry
        Registry registry = getInstance();
        registry.registerObject(MuleProperties.OBJECT_MULE_CONFIGURATION, config, context);

        RegistryContext.setRegistry(registry);

        registry.registerObject(MuleProperties.OBJECT_MANAGMENT_CONTEXT_PROCESSOR, 
                                new ManagementContextDependencyProcessor(context), context);

        context.setId(UUID.getUUID());

//      // TODO MULE-2161
        MuleServer.setManagementContext(context);
        registry.registerObject(MuleProperties.OBJECT_MANAGEMENT_CONTEXT, context, context);
//        registry.registerObject(ObjectProcessor.class, MuleProperties.OBJECT_MANAGEMENT_CONTEXT_PROCESSOR,
//                new ManagementContextDependencyProcessor(context));

        //Register objects so we get lifecycle management
        registry.registerObject(MuleProperties.OBJECT_SECURITY_MANAGER, securityManager, context);
        registry.registerObject(MuleProperties.OBJECT_WORK_MANAGER, workManager, context);
        registry.registerObject(MuleProperties.OBJECT_NOTIFICATION_MANAGER, notificationManager, context);
        registry.registerObject(MuleProperties.OBJECT_QUEUE_MANAGER, queueManager, context);
        registry.registerObject(MuleProperties.OBJECT_MULE_SIMPLE_REGISTRY_BOOTSTRAP, new SimpleRegistryBootstrap(), context);

        //Register the system Model
        ModelServiceDescriptor sd = (ModelServiceDescriptor)
                registry.lookupServiceDescriptor(ServiceDescriptorFactory.MODEL_SERVICE_TYPE, config.getSystemModelType(), null);

        UMOModel model = sd.createModel();
        model.setName(MuleProperties.OBJECT_SYSTEM_MODEL);
        registry.registerModel(model);
        context.initialise();
        registry.initialise();
        return registry;
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config;

import org.mule.MuleServer;
import org.mule.RegistryContext;
import org.mule.impl.DefaultMuleContext;
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
import org.mule.registry.Registry;
import org.mule.umo.MuleContext;
import org.mule.umo.lifecycle.UMOLifecycleManager;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.util.PropertiesUtils;
import org.mule.util.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * A support class for {@link org.mule.config.ConfigurationBuilder} implementations
 * that handles the logic of creating config arrays and {@link java.util.Properties}
 * arguments
 * 
 * @see org.mule.config.ConfigurationBuilder
 */
public abstract class AbstractConfigurationBuilder implements ConfigurationBuilder
{

    private boolean configured = false;

    /**
     * Will configure a UMOManager based on the configuration file(s) provided.
     * 
     * @param configResources a comma separated list of configuration files to load,
     *            this should be accessible on the classpath or filesystem
     * @return A configured UMOManager
     * @throws org.mule.config.ConfigurationException
     */
    public MuleContext configure(String configResources) throws ConfigurationException
    {
        return configure(StringUtils.splitAndTrim(configResources, ",; "), new Properties());
    }

    /**
     * Will configure a UMOManager based on the configuration file(s) provided.
     * 
     * @param configResources - A comma-separated list of configuration files to
     *            load, these should be accessible on the classpath or filesystem
     * @param startupProperties - Optional properties to be set before configuring
     *            the Mule server. This is useful for managing different environments
     *            (dev, test, production)
     * @return A configured UMOManager
     * @throws org.mule.config.ConfigurationException
     */
    public MuleContext configure(String configResources, Properties startupProperties)
        throws ConfigurationException
    {
        return configure(StringUtils.splitAndTrim(configResources, ",; "), startupProperties);
    }

    /**
     * Will configure a UMOManager based on the configuration file(s) provided.
     * 
     * @param configResources an array of configuration files to load, this should be
     *            accessible on the classpath or filesystem
     * @return A configured UMOManager
     * @throws org.mule.config.ConfigurationException
     */
    public MuleContext configure(String[] configResources) throws ConfigurationException
    {
        return configure(configResources, new Properties());
    }

    /**
     * Will configure a UMOManager based on the configuration file(s) provided.
     * 
     * @param configResources - An array list of configuration files to load, these
     *            should be accessible on the classpath or filesystem
     * @param startupPropertiesFile - An optional file containing startup properties.
     *            This is useful for managing different environments (dev, test,
     *            production)
     * @return A configured UMOManager
     * @throws org.mule.config.ConfigurationException
     */
    public MuleContext configure(String[] configResources, String startupPropertiesFile)
        throws ConfigurationException
    {
        try
        {
            Properties props = PropertiesUtils.loadProperties(startupPropertiesFile, getClass());
            return configure(configResources, props);
        }
        catch (IOException e)
        {
            throw new ConfigurationException(e);
        }
    }

    /**
     * Will configure a UMOManager based on the configuration file(s) provided.
     * 
     * @param configResources - A comma-separated list of configuration files to
     *            load, these should be accessible on the classpath or filesystem
     * @param startupPropertiesFile - An optional file containing startup properties.
     *            This is useful for managing different environments (dev, test,
     *            production)
     * @return A configured UMOManager
     * @throws org.mule.config.ConfigurationException
     */
    public MuleContext configure(String configResources, String startupPropertiesFile)
        throws ConfigurationException
    {
        try
        {
            if (startupPropertiesFile != null)
            {
                Properties props = PropertiesUtils.loadProperties(startupPropertiesFile, getClass());
                return configure(configResources, props);
            }
            else
            {
                return configure(configResources);
            }
        }
        catch (IOException e)
        {
            throw new ConfigurationException(e);
        }
    }

    /**
     * Creates a default muleContext. This needs to go somewhere else before
     * ConfigurationBuilder invocation. This will also allow multiple
     * configurationBuilders to be used.
     * 
     * @return
     */
    protected MuleContext createMuleContext()
    {
        // Create MuleContext life-cycle manager
        UMOLifecycleManager lifecycleManager = new GenericLifecycleManager();
        lifecycleManager.registerLifecycle(new MuleContextInitialisePhase());
        lifecycleManager.registerLifecycle(new MuleContextStartPhase());
        lifecycleManager.registerLifecycle(new MuleContextStopPhase());
        lifecycleManager.registerLifecycle(new MuleContextDisposePhase());

        MuleConfiguration config = new MuleConfiguration();

        ThreadingProfile tp = config.getDefaultThreadingProfile();
        UMOWorkManager workManager = new MuleWorkManager(tp, "MuleServer");

        ServerNotificationManager notificationManager = new ServerNotificationManager();
        notificationManager.addInterfaceToType(ManagerNotificationListener.class, ManagerNotification.class);
        notificationManager.addInterfaceToType(ModelNotificationListener.class, ModelNotification.class);
        notificationManager.addInterfaceToType(ComponentNotificationListener.class,
            ComponentNotification.class);
        notificationManager.addInterfaceToType(SecurityNotificationListener.class, SecurityNotification.class);
        notificationManager.addInterfaceToType(ManagementNotificationListener.class,
            ManagementNotification.class);
        notificationManager.addInterfaceToType(AdminNotificationListener.class, AdminNotification.class);
        notificationManager.addInterfaceToType(CustomNotificationListener.class, CustomNotification.class);
        notificationManager.addInterfaceToType(ConnectionNotificationListener.class,
            ConnectionNotification.class);
        notificationManager.addInterfaceToType(RegistryNotificationListener.class, RegistryNotification.class);
        notificationManager.addInterfaceToType(ExceptionNotificationListener.class,
            ExceptionNotification.class);
        notificationManager.addInterfaceToType(TransactionNotificationListener.class,
            TransactionNotification.class);

        MuleContext muleContext = new DefaultMuleContext(lifecycleManager);
        muleContext.setNotificationManager(notificationManager);
        muleContext.setWorkManager(workManager);
        muleContext.setConfiguration(config);
        return muleContext;
    }

    /**
     * Will configure a UMOManager based on the configuration file(s) provided.
     * 
     * @param configResources - An array list of configuration files to load, these
     *            should be accessible on the classpath or filesystem
     * @param startupProperties - Optional properties to be set before configuring
     *            the Mule server. This is useful for managing different environments
     *            (dev, test, production)
     * @return A configured UMOManager
     * @throws org.mule.config.ConfigurationException
     */
    public MuleContext configure(String[] configResources, Properties startupProperties)
        throws ConfigurationException
    {
        // 1) Pre-ConfigurationBuilder logic. This should happen before
        // ConfigurationBuilder is invoked
        // --------------------------------------------------------------------------------
        Registry registry = RegistryContext.getOrCreateRegistry();
        MuleContext muleContext = createMuleContext();
        MuleServer.setMuleContext(muleContext);
        try
        {
            muleContext.initialise();
            registry.registerObjects(startupProperties);
            // --------------------------------------------------------------------------------

            // 2) ConfigurationBuilder logic---------------------------------------------------         
            
            doConfigure(muleContext, configResources);
            
            // --------------------------------------------------------------------------------
        }
        catch (Exception e)
        {
            throw new ConfigurationException(e);
        }

        configured = true;
        return muleContext;

    }

    protected abstract void doConfigure(MuleContext muleContext, String[] configResources)
        throws Exception;

    public boolean isConfigured()
    {
        return configured;
    }
}

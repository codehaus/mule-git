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

import org.mule.MuleServer;
import org.mule.RegistryContext;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.config.builders.AutoConfigurationBuilder;
import org.mule.config.builders.DefaultsConfigurationBuilder;
import org.mule.config.builders.SimpleConfigurationBuilder;
import org.mule.umo.MuleContext;
import org.mule.umo.MuleContextBuilder;
import org.mule.umo.MuleContextFactory;
import org.mule.umo.lifecycle.InitialisationException;

import java.util.List;
import java.util.Properties;

/**
 * Default implementation that stores MuleContext in {@link MuleServer} static and
 * uses {@link DefaultMuleContextBuilder} to build new {@link MuleContext} instances.
 */
public class DefaultMuleContextFactory implements MuleContextFactory
{

    /**
     * Currently MuleContext instance is stored in MuleServer static, this may change
     * in the future though, or if we don't want to be limited by having one
     * MuleContext instance per classloader.
     * 
     * @throws ConfigurationException
     */
    public MuleContext getMuleContext() throws InitialisationException, ConfigurationException
    {
        MuleContext muleContext = MuleServer.getMuleContext();
        if (muleContext == null)
        {
            muleContext = createMuleContext();
        }
        return muleContext;
    }

    /**
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     */
    public MuleContext createMuleContext() throws InitialisationException, ConfigurationException
    {
        MuleContext muleContext = doCreateMuleContext(null);
        new DefaultsConfigurationBuilder().configure(muleContext);
        return muleContext;
    }

    public MuleContext createMuleContext(String resource)
        throws InitialisationException, ConfigurationException
    {
        return createMuleContext(resource, null);
    }

    public MuleContext createMuleContext(String configResources, Properties properties)
        throws InitialisationException, ConfigurationException
    {
        // Create MuleContext
        MuleContext muleContext = doCreateMuleContext(null);

        // Set startup properties if any are passed in
        if (properties != null)
        {
            new SimpleConfigurationBuilder(properties).configure(muleContext);
        }

        // Automatically resolve Configuration to be used and delegate configuration
        // to it.
        new AutoConfigurationBuilder(configResources).configure(muleContext);

        return muleContext;
    }

    /**
     * 
     */
    public MuleContext createMuleContext(ConfigurationBuilder configurationBuilder)
        throws InitialisationException, ConfigurationException
    {
        return createMuleContext(configurationBuilder, null);
    }

    /**
     * @param configurationBuilder
     * @param properties
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     */
    public MuleContext createMuleContext(ConfigurationBuilder configurationBuilder, Properties properties)
        throws InitialisationException, ConfigurationException
    {
        // Create MuleContext
        MuleContext muleContext = doCreateMuleContext(null);

        // Set startup properties if any are passed in
        if (properties != null)
        {
            new SimpleConfigurationBuilder(properties).configure(muleContext);
        }

        configurationBuilder.configure(muleContext);
        return muleContext;
    }

    public MuleContext createMuleContext(List configurationBuilders, MuleContextBuilder muleContextBuilder)
        throws InitialisationException, ConfigurationException
    {
        // Create MuleContext
        MuleContext muleContext = doCreateMuleContext(muleContextBuilder);

        for (int i = 0; i < configurationBuilders.size(); i++)
        {
            ((ConfigurationBuilder) configurationBuilders.get(i)).configure(muleContext);
        }
        return muleContext;
    }

    protected MuleContext doCreateMuleContext(MuleContextBuilder muleContextBuilder)
        throws InitialisationException
    {
        // Create transent registry
        RegistryContext.getOrCreateRegistry();

        // Create muleContext instance and set it in MuleServer
        MuleContext muleContext = buildMuleContext(muleContextBuilder);
        MuleServer.setMuleContext(muleContext);

        // Initialiase MuleContext
        muleContext.initialise();

        return muleContext;
    }

    protected MuleContext buildMuleContext(MuleContextBuilder muleContextBuilder)
    {
        if (muleContextBuilder == null)
        {
            muleContextBuilder = new DefaultMuleContextBuilder();
        }
        return muleContextBuilder.buildMuleContext();
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo;

import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.config.builders.DefaultsConfigurationBuilder;
import org.mule.umo.lifecycle.InitialisationException;

import java.util.List;
import java.util.Properties;

/**
 * A {@link MuleContextFactory} is used to create instances of {@link MuleContext}.
 * The instances of {@link MuleContext} returned by this factory are initialised but
 * not started.
 */
public interface MuleContextFactory
{

    /**
     * Returns an existing instance of {@link MuleContext} is one exists, otherwise a
     * new {@link MuleContext} instance is created with defaults.
     * 
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     * @see {@link DefaultsConfigurationBuilder}
     */
    MuleContext getMuleContext() throws InitialisationException, ConfigurationException;

    /**
     * Creates a new {@link MuleContext} instance from the resource provided.
     * Implementations of {@link MuleContextFactory} can either use a default
     * {@link ConfigurationBuilder} to implement this, or do some auto-detection to
     * determine the {@link ConfigurationBuilder} that should be used.
     * 
     * @param configResources comma seperated list of configuration resources.
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     */
    MuleContext createMuleContext(String configResources)
        throws InitialisationException, ConfigurationException;

    /**
     * Creates a new {@link MuleContext} instance from the resource provided.
     * Implementations of {@link MuleContextFactory} can either use a default
     * {@link ConfigurationBuilder} to implement this, or do some auto-detection to
     * determine the {@link ConfigurationBuilder} that should be used. Properties if
     * provided are used to replace "property placeholder" value in configuration
     * files.
     * 
     * @param resource
     * @param properties
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     */
    MuleContext createMuleContext(String resource, Properties properties)
        throws InitialisationException, ConfigurationException;

    /**
     * Creates a new MuleContext using the given configurationBuilder
     * 
     * @param configurationBuilder
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     */
    MuleContext createMuleContext(ConfigurationBuilder configurationBuilder)
        throws InitialisationException, ConfigurationException;

    /**
     * Creates a new MuleContext using the given configurationBuilder. Properties if
     * provided are used to replace "property placeholder" value in configuration
     * files.
     * 
     * @param configurationBuilder
     * @param properties
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     */
    MuleContext createMuleContext(ConfigurationBuilder configurationBuilder, Properties properties)
        throws InitialisationException, ConfigurationException;

    /**
     * Creates a new MuleContext using the {@link MuleContextBuilder} provided and
     * configures it with the list of configuration builders. Configuration builders
     * will be invoked in the same or as provided in the List.
     * 
     * @param configurationBuilder
     * @return
     * @throws InitialisationException
     * @throws ConfigurationException
     */
    MuleContext createMuleContext(List configurationBuilders, MuleContextBuilder muleContextBuilder)
        throws InitialisationException, ConfigurationException;

}

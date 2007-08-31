/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 * <code>MuleApplicationContext</code> is A Simple extension Application context
 * that allows rosurces to be loaded from the Classpath of file system using the
 * MuleBeanDefinitionReader.
 * 
 * @see MuleBeanDefinitionReader
 */
public class MuleApplicationContext extends AbstractXmlApplicationContext
{
    private final Resource[] configResources;
    private final String[] configLocations;

    public MuleApplicationContext(Resource[] configResources)
    {
        this(configResources, true);
    }

    public MuleApplicationContext(Resource[] configResources, boolean refresh) throws BeansException
    {
        this.configResources = configResources;
        this.configLocations = null;
        if (refresh)
        {
            refresh();
        }
    }

    public MuleApplicationContext(String[] configLocations)
    {
        this(configLocations, true);
    }

    public MuleApplicationContext(String[] configLocations, boolean refresh) throws BeansException
    {
        this.configLocations = configLocations;
        this.configResources = null;
        if (refresh)
        {
            refresh();
        }
    }

    //@Override
    protected Resource[] getConfigResources() 
    {
        return configResources;
    }
    
    //@Override
    protected String[] getConfigLocations()
    {
        return configLocations;
    }

    //@Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws IOException 
    {
        XmlBeanDefinitionReader beanDefinitionReader = new MuleBeanDefinitionReader(beanFactory,
            configResources != null ? configResources.length : configLocations.length);

        initBeanDefinitionReader(beanDefinitionReader);
        loadBeanDefinitions(beanDefinitionReader);
    }
}

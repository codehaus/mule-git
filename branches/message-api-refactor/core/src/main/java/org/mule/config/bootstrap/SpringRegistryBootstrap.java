/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.bootstrap;

import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.util.ClassUtils;

import java.net.URL;
import java.util.Enumeration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This object will load objects defined in a spring file called <code>spring-registry-bootstrap.xml</code> into the
 * local spring registry.
 * This allows modules and transports to make certain objects available by default.  The most common use case is for a
 * module or transport to load stateless transformers into the registry.
 * For this file to be located it must be present in the modules META-INF directory under
 * <code>META-INF/services/org/mule/config/</code>
 *
 * This file just defines a single spring application context for a module or transport. This bootstrap only gets
 * loaded if spring is being used.
 */
public class SpringRegistryBootstrap implements Initialisable, ApplicationContextAware
{
    public static final String SERVICE_PATH = "META-INF/services/org/mule/config/";

    public static final String REGISTRY_XML = "spring-registry-bootstrap.xml";

    private ApplicationContext applicationContext;

    /** {@inheritDoc} */
    public void initialise() throws InitialisationException
    {
        Enumeration e = ClassUtils.getResources(SERVICE_PATH + REGISTRY_XML, getClass());
        while(e.hasMoreElements())
        {
            try
            {
                URL url = (URL)e.nextElement();
                AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{url.toExternalForm()});
                ApplicationContext parent = applicationContext;
                
                while(parent.getParent()!=null)
                {
                    parent = parent.getParent();
                }
                ((AbstractApplicationContext)parent).setParent(ctx);
            }
            catch (Exception e1)
            {
                throw new InitialisationException(e1, this);
            }
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}

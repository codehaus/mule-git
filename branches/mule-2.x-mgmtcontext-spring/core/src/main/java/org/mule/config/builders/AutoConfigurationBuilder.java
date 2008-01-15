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

import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.umo.MuleContext;
import org.mule.util.ClassUtils;

public class AutoConfigurationBuilder extends AbstractResourceConfigurationBuilder
{

    public AutoConfigurationBuilder(String resource)
    {
        super(resource);
    }

    protected void doConfigure(MuleContext muleContext) throws ConfigurationException
    {
        ConfigurationBuilder configurationBuilder = null;

        if (ClassUtils.isClassOnPath("org.mule.config.spring.SpringXmlConfigurationBuilder", this.getClass()))
        {
            try
            {
                configurationBuilder = (ConfigurationBuilder) ClassUtils.instanciateClass(
                    "org.mule.config.spring.SpringXmlConfigurationBuilder", new Object[]{configResources});
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else if (ClassUtils.isClassOnPath("org.mule.config.scripting.ScriptingConfigurationBuilder",
            this.getClass()))
        {
            try
            {
                configurationBuilder = (ConfigurationBuilder) ClassUtils.instanciateClass(
                    "org.mule.config.spring.SpringXmlConfigurationBuilder", new Object[]{configResources});
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            configurationBuilder = new DefaultsConfigurationBuilder();
        }
        configurationBuilder.configure(muleContext);
    }

}

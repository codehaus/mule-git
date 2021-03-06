/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck;

import org.mule.api.component.JavaComponent;
import org.mule.api.config.ConfigurationBuilder;
import org.mule.api.registry.RegistrationException;
import org.mule.api.service.Service;
import org.mule.component.AbstractJavaComponent;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Is a base tast case for tests that initialise Mule using a configuration file. The
 * default configuration builder used is the MuleXmlConfigurationBuilder. This you
 * need to have the mule-modules-builders module/jar on your classpath. If you want
 * to use a different builder, just overload the <code>getBuilder()</code> method
 * of this class to return the type of builder you want to use with your test. Note
 * you can overload the <code>getBuilder()</code> to return an initialised instance
 * of the QuickConfiguratonBuilder, this allows the developer to programmatically
 * build a Mule instance and roves the need for additional config files for the test.
 */
public abstract class FunctionalTestCase extends AbstractMuleTestCase
{
    /** Expected response after the test message has passed through the FunctionalTestComponent. */
    public static final String TEST_MESSAGE_RESPONSE = FunctionalTestComponent.received(TEST_MESSAGE);
    
    public FunctionalTestCase()
    {
        super();
        // A functional test case starts up the management context by default.
        setStartContext(true);
    }
    
    protected ConfigurationBuilder getBuilder() throws Exception
    {
        return new SpringXmlConfigurationBuilder(getConfigurationResources());
    }

    //Delegate to an abstract method to ensure that FunctionalTestCases know they need to pass in config resources
    protected String getConfigurationResources()
    {
        return getConfigResources();
    }

    protected abstract String getConfigResources();
    
    protected Object getComponent(String serviceName) throws Exception
    {
        Service service = muleContext.getRegistry().lookupService(serviceName);
        if (service != null)
        {
            return getComponent(service);
        }
        else
        {
            throw new RegistrationException("Service " + serviceName + " not found in Registry");
        }
    }
    
    protected Object getComponent(Service service) throws Exception
    {
        if (service.getComponent() instanceof JavaComponent)
        {
            return ((AbstractJavaComponent) service.getComponent()).getObjectFactory().getInstance();
        }
        else
        {
            fail("Componnent is not a JavaComponent and therefore has no component object instance");
            return null;
        }
    }

    protected String loadResourceAsString(String name) throws IOException
    {
        return IOUtils.getResourceAsString(name, getClass());
    }

    protected InputStream loadResource(String name) throws IOException
    {
        return IOUtils.getResourceAsStream(name, getClass());
    }
}

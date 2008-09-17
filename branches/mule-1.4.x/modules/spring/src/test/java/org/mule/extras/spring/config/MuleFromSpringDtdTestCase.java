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

import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.providers.vm.VMConnector;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMODescriptor;
import org.mule.umo.provider.UMOConnector;

import junit.framework.Assert;

public class MuleFromSpringDtdTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "mule-config-in-spring-using-dtd.xml";
    }

    public ConfigurationBuilder getBuilder()
    {
        return new SpringConfigurationBuilder();
    }

    public void testReadConfig()
    {
        UMOConnector connector = MuleManager.getInstance().lookupConnector("vmConnector");
        Assert.assertNotNull(connector);
        VMConnector vmConnector = (VMConnector)connector;
        Assert.assertEquals(42, vmConnector.getQueueTimeout());
        
        UMODescriptor descriptor = MuleManager.getInstance().lookupModel("mule").getDescriptor("appleComponent");
        Assert.assertNotNull(descriptor);
        Assert.assertNotNull(descriptor.getInboundRouter());
    }
}

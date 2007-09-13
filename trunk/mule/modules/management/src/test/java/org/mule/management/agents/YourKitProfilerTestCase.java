/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.management.agents;

import org.mule.management.AbstractMuleJmxTestCase;
import org.mule.management.support.JmxSupportFactory;
import org.mule.management.support.AutoDiscoveryJmxSupportFactory;
import org.mule.management.support.JmxSupport;
import org.mule.management.mbeans.YourKitProfilerService;

public class YourKitProfilerTestCase extends AbstractMuleJmxTestCase
{
    private JmxSupportFactory jmxSupportFactory = AutoDiscoveryJmxSupportFactory.getInstance();
    private JmxSupport jmxSupport = jmxSupportFactory.getJmxSupport();

    public void testRedeploy() throws Exception
    {
        mBeanServer.registerMBean(new YourKitProfilerService(),
            jmxSupport.getObjectName(YourKitProfilerAgent.JMX_OBJECT_NAME));

        YourKitProfilerAgent agent = new YourKitProfilerAgent();
        agent.initialise();
    }
}

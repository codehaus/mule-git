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
import org.mule.management.mbeans.YourKitProfilerService;

import javax.management.ObjectName;

public class YourKitProfilerTestCase extends AbstractMuleJmxTestCase
{
    public void testRedeploy() throws Exception
    {
        mBeanServer.registerMBean(new YourKitProfilerService(),
            ObjectName.getInstance(Log4jAgent.JMX_OBJECT_NAME));

        YourKitProfilerAgent agent = new YourKitProfilerAgent();
        agent.initialise();
    }
}

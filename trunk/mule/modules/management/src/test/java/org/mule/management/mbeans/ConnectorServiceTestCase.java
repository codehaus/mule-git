/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.mbeans;

import org.mule.management.AbstractMuleJmxTestCase;
import org.mule.management.agents.JmxAgent;
import org.mule.management.support.JmxSupport;
import org.mule.umo.manager.UMOManager;
import org.mule.umo.provider.UMOConnector;

import java.util.Set;

import javax.management.ObjectName;

public class ConnectorServiceTestCase extends AbstractMuleJmxTestCase
{
    public void testUndeploy() throws Exception
    {
        final UMOManager manager = getManager(true);
        final String configId = "ConnectorServiceTest";
        manager.setId(configId);
        final UMOConnector connector = getTestConnector();
        connector.setName("TEST_CONNECTOR");
        final JmxAgent jmxAgent = new JmxAgent();
        manager.registerConnector(connector);
        manager.registerAgent(jmxAgent);
        manager.start();

        final String query = JmxSupport.DEFAULT_JMX_DOMAIN_PREFIX + "." + configId + ":*";
        Set mbeans = mBeanServer.queryMBeans(ObjectName.getInstance(query), null);

        // Expecting following mbeans to be registered:
        // 1) org.mule.management.mbeans.StatisticsService@Mule.ConnectorServiceTest:type=org.mule.Statistics,name=AllStatistics
        // 2) org.mule.management.mbeans.MuleConfigurationService@Mule.ConnectorServiceTest:type=org.mule.Configuration,name=GlobalConfiguration
        // 3) org.mule.management.mbeans.ModelService@Mule.ConnectorServiceTest:type=org.mule.Model,name="_system(seda)"
        // 4) org.mule.management.mbeans.ModelService@Mule.ConnectorServiceTest:type=org.mule.Model,name="main(seda)"
        // 5) org.mule.management.mbeans.MuleService@Mule.ConnectorServiceTest:type=org.mule.ManagementContext,name=MuleServerInfo
        // 6) org.mule.management.mbeans.ConnectorService@Mule.ConnectorServiceTest:type=org.mule.Connector,name="TEST.CONNECTOR"
        assertEquals("Unexpected number of components registered in the domain.", 6, mbeans.size());

        manager.dispose();

        mbeans = mBeanServer.queryMBeans(ObjectName.getInstance(query), null);
        assertEquals("There should be no MBeans left in the domain", 0, mbeans.size());
    }
}

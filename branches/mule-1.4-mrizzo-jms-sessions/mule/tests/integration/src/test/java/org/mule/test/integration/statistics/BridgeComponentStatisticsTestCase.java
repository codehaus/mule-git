/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.integration.statistics;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.management.stats.RouterStatistics;
import org.mule.management.stats.SedaComponentStatistics;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BridgeComponentStatisticsTestCase extends FunctionalTestCase
{

    public static final String[] COMPONENT_NAMES = {"PassThroughComponent", "BridgeComponent", "ComplexBridgeComponent"};
    public static final int TIMEOUT = 5000;
    public static final String MODEL_NAME = "MuleClientStats";

    private final Map stats = new HashMap();

    protected String getConfigResources()
    {
        return "org/mule/test/integration/statistics/mule-jmx-stats-config.xml";
    }

    public void testBridge() throws Exception
    {


        for (int i = 0; i < 3; i++)
        {
            SedaComponentStatistics s = new SedaComponentStatistics(COMPONENT_NAMES[i], 0, 0);
            RouterStatistics rs = new RouterStatistics(RouterStatistics.TYPE_INBOUND);
            s.setInboundRouterStat(rs);
            rs = new RouterStatistics(RouterStatistics.TYPE_OUTBOUND);
            s.setOutboundRouterStat(rs);
            stats.put(COMPONENT_NAMES[i], s);
        }

        MuleClient client = new MuleClient();
        for (int i = 0; i < 7; i++)
        {
            client.send("vm://pass", "Hello!", null);
            getSedaComponentStatistics(COMPONENT_NAMES[0]).incReceivedEventSync();
            getSedaComponentStatistics(COMPONENT_NAMES[0]).incSentEventSync();
            List endpoints = MuleManager.getInstance().lookupModel(MODEL_NAME).getComponent(COMPONENT_NAMES[0]).getDescriptor().getInboundRouter().getEndpoints();
            getSedaComponentStatistics(COMPONENT_NAMES[0]).getInboundRouterStat().incrementRoutedMessage(endpoints);
            UMOMessage message = client.receive("vm://pass1", TIMEOUT);
            assertNotNull(message);
            message = client.receive("vm://pass3", TIMEOUT);
            assertNotNull(message);
            client.send("vm://bridge", "Bridge!", null);
            getSedaComponentStatistics(COMPONENT_NAMES[1]).incReceivedEventSync();
            getSedaComponentStatistics(COMPONENT_NAMES[1]).incSentEventSync();
            endpoints = MuleManager.getInstance().lookupModel(MODEL_NAME).getComponent(COMPONENT_NAMES[1]).getDescriptor().getInboundRouter().getEndpoints();
            getSedaComponentStatistics(COMPONENT_NAMES[0]).getInboundRouterStat().incrementRoutedMessage(endpoints);
            client.send("vm://b1", "Bridge1", null);
            getSedaComponentStatistics(COMPONENT_NAMES[2]).incReceivedEventSync();
            getSedaComponentStatistics(COMPONENT_NAMES[2]).incSentEventSync();
            client.send("vm://b2", "Bridge2", null);
            getSedaComponentStatistics(COMPONENT_NAMES[2]).incReceivedEventSync();
            getSedaComponentStatistics(COMPONENT_NAMES[2]).incSentEventSync();
            endpoints = MuleManager.getInstance().lookupModel(MODEL_NAME).getComponent(COMPONENT_NAMES[2]).getDescriptor().getInboundRouter().getEndpoints();
            getSedaComponentStatistics(COMPONENT_NAMES[0]).getInboundRouterStat().incrementRoutedMessage(endpoints);
            message = client.receive("vm://b3", TIMEOUT);
            assertNotNull(message);
            message = client.receive("vm://b4", TIMEOUT);
            assertNotNull(message);

        }

        MuleManager manager = (MuleManager) MuleManager.getInstance();
        assertNotNull(manager.getStatistics());
        Collection statistics = manager.getStatistics().getComponentStatistics();
        Iterator i = statistics.iterator();
        while (i.hasNext())
        {
            SedaComponentStatistics statistic = (SedaComponentStatistics) i.next();
            SedaComponentStatistics testStatistic = getSedaComponentStatistics(statistic.getName());
            if (testStatistic == null)
            {
                continue;
            }
            statistic.getSyncEventsReceived();
            statistic.getSyncEventsSent();
            assertEquals(testStatistic.getSyncEventsReceived(), statistic.getSyncEventsReceived());
            assertEquals(testStatistic.getSyncEventsSent(), statistic.getSyncEventsSent());
            assertEquals(statistic.getInboundRouterStat().getTotalRouted(), statistic.getInboundRouterStat().getTotalRouted());
        }


    }

    private SedaComponentStatistics getSedaComponentStatistics(String componentName)
    {
        return ((SedaComponentStatistics) stats.get(componentName));
    }

}



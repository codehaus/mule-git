/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.config;

import org.mule.api.agent.Agent;
import org.mule.management.agents.JmxAgent;
import org.mule.management.agents.JmxServerNotificationAgent;
import org.mule.management.agents.Log4jAgent;
import org.mule.tck.FunctionalTestCase;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ManagementDefaultNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "management-default-namespace-config.xml";
    }

    public void testDefaultJmxAgentConfig() throws Exception
    {
        Agent agent = muleContext.getRegistry().lookupAgent("JMX Agent");
        assertNotNull(agent);
        assertEquals(JmxAgent.class, agent.getClass());
        JmxAgent jmxAgent = (JmxAgent) agent;
        
        assertEquals(true, jmxAgent.isCreateServer());
        assertEquals(true, jmxAgent.isLocateServer());
        assertEquals(true, jmxAgent.isEnableStatistics());

        MBeanServer mBeanServer = jmxAgent.getMBeanServer();
        String domainName = jmxAgent.getJmxSupportFactory().getJmxSupport().getDomainName(muleContext);
        assertEquals(6, mBeanServer.queryMBeans(ObjectName.getInstance(domainName + ":*"), null).size());

        agent = muleContext.getRegistry().lookupAgent("Log4j JMX Agent");
        assertNotNull(agent);
        assertEquals(Log4jAgent.class, agent.getClass());

        agent = muleContext.getRegistry().lookupAgent(JmxServerNotificationAgent.DEFAULT_AGENT_NAME);
        assertNotNull(agent);
        assertEquals(JmxServerNotificationAgent.class, agent.getClass());

        agent = muleContext.getRegistry().lookupAgent("Default Jmx Agent Support");
        assertNull(agent);
        
        //Assertion to check that all Mule MBeans were unregistered during disposal phase.
        muleContext.dispose();
        assertEquals(0, mBeanServer.queryMBeans(ObjectName.getInstance(domainName + ":*"), null).size());
    
    }

}

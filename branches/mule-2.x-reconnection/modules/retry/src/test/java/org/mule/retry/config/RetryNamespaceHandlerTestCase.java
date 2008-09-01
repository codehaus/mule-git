/*
 * $Id$
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.retry.config;

import org.mule.api.retry.RetryNotifier;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.api.transport.Connector;
import org.mule.retry.notifiers.ConnectNotifier;
import org.mule.retry.policies.NoRetryPolicyTemplate;
import org.mule.retry.policies.RetryForeverPolicyTemplate;
import org.mule.retry.policies.SimpleRetryPolicyTemplate;
import org.mule.retry.test.TestRetryNotifier;
import org.mule.retry.test.TestRetryPolicyTemplate;
import org.mule.tck.FunctionalTestCase;

public class RetryNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "retry-namespace-config.xml";
    }

    public void testDefaultConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector1");
        assertNotNull(c);

        RetryPolicyTemplate rpf = c.getRetryPolicyTemplate();
        assertNotNull(rpf);
        assertTrue(rpf instanceof NoRetryPolicyTemplate);
        RetryNotifier rn = rpf.getNotifier();
        assertNotNull(rn);
        assertTrue(rn instanceof ConnectNotifier);
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testSimpleDefaults() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector2");
        assertNotNull(c);

        RetryPolicyTemplate rpf = c.getRetryPolicyTemplate();
        assertNotNull(rpf);
        assertTrue(rpf instanceof SimpleRetryPolicyTemplate);
        assertEquals(SimpleRetryPolicyTemplate.DEFAULT_RETRY_COUNT, ((SimpleRetryPolicyTemplate) rpf).getCount());
        assertEquals(SimpleRetryPolicyTemplate.DEFAULT_FREQUENCY, ((SimpleRetryPolicyTemplate) rpf).getFrequency());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testSimpleConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector3");
        assertNotNull(c);

        RetryPolicyTemplate rpf = c.getRetryPolicyTemplate();
        assertNotNull(rpf);
        assertTrue(rpf instanceof SimpleRetryPolicyTemplate);
        assertEquals(5, ((SimpleRetryPolicyTemplate) rpf).getCount());
        assertEquals(1000, ((SimpleRetryPolicyTemplate) rpf).getFrequency());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testForeverConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector4");
        assertNotNull(c);

        RetryPolicyTemplate rpf = c.getRetryPolicyTemplate();
        assertNotNull(rpf);
        assertTrue(rpf instanceof RetryForeverPolicyTemplate);
        assertEquals(5000, ((RetryForeverPolicyTemplate) rpf).getFrequency());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testCustomConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector5");
        assertNotNull(c);

        RetryPolicyTemplate rpf = c.getRetryPolicyTemplate();
        assertNotNull(rpf);
        assertTrue(rpf instanceof TestRetryPolicyTemplate);
        assertTrue(((TestRetryPolicyTemplate) rpf).isFooBar());
        assertEquals(500, ((TestRetryPolicyTemplate) rpf).getRevolutions());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testConnectNotifierConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector6");
        assertNotNull(c);

        RetryPolicyTemplate rpf = c.getRetryPolicyTemplate();
        assertNotNull(rpf);
        RetryNotifier rn = rpf.getNotifier();
        assertNotNull(rn);
        assertTrue(rn instanceof ConnectNotifier);
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testCustomNotifierConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector7");
        assertNotNull(c);

        RetryPolicyTemplate rpf = c.getRetryPolicyTemplate();
        assertNotNull(rpf);
        RetryNotifier rn = rpf.getNotifier();
        assertNotNull(rn);
        assertTrue(rn instanceof TestRetryNotifier);
        assertEquals("red", ((TestRetryNotifier) rn).getColor());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }
}

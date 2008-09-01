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

import org.mule.api.retry.RetryPolicyFactory;
import org.mule.api.retry.RetryTemplateFactory;
import org.mule.api.transport.Connector;
import org.mule.retry.DefaultRetryTemplate;
import org.mule.retry.policies.NoRetryPolicyFactory;
import org.mule.retry.policies.RetryForeverPolicyFactory;
import org.mule.retry.policies.SimpleRetryPolicyFactory;
import org.mule.retry.test.TestRetryPolicyFactory;
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

        RetryTemplateFactory rtf = c.getRetryTemplateFactory();
        assertNotNull(rtf);
        assertTrue(rtf instanceof DefaultRetryTemplate);
        assertFalse(rtf.isRetryEnabled());
        assertTrue(((DefaultRetryTemplate) rtf).getPolicyFactory() instanceof NoRetryPolicyFactory);
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testSimpleConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector2");
        assertNotNull(c);

        RetryTemplateFactory rtf = c.getRetryTemplateFactory();
        assertNotNull(rtf);
        assertTrue(rtf.isRetryEnabled());
        assertTrue(rtf instanceof DefaultRetryTemplate);
        RetryPolicyFactory rpf = ((DefaultRetryTemplate) rtf).getPolicyFactory();
        assertTrue(rpf instanceof SimpleRetryPolicyFactory);
        assertEquals(5, ((SimpleRetryPolicyFactory) rpf).getCount());
        assertEquals(1000, ((SimpleRetryPolicyFactory) rpf).getFrequency());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testForeverConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector3");
        assertNotNull(c);

        RetryTemplateFactory rtf = c.getRetryTemplateFactory();
        assertNotNull(rtf);
        assertTrue(rtf.isRetryEnabled());
        assertTrue(rtf instanceof DefaultRetryTemplate);
        RetryPolicyFactory rpf = ((DefaultRetryTemplate) rtf).getPolicyFactory();
        assertTrue(rpf instanceof RetryForeverPolicyFactory);
        assertEquals(5000, ((RetryForeverPolicyFactory) rpf).getFrequency());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

    public void testCustomConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector4");
        assertNotNull(c);

        RetryTemplateFactory rtf = c.getRetryTemplateFactory();
        assertNotNull(rtf);
        assertTrue(rtf.isRetryEnabled());
        assertTrue(rtf instanceof DefaultRetryTemplate);
        RetryPolicyFactory rpf = ((DefaultRetryTemplate) rtf).getPolicyFactory();
        assertTrue(rpf instanceof TestRetryPolicyFactory);
        assertTrue(((TestRetryPolicyFactory) rpf).isFooBar());
        assertEquals(500, ((TestRetryPolicyFactory) rpf).getRevolutions());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

}

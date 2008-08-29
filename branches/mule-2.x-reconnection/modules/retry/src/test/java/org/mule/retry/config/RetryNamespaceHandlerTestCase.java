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

import org.mule.api.retry.RetryTemplateFactory;
import org.mule.api.transport.Connector;
import org.mule.retry.DefaultRetryTemplate;
import org.mule.tck.FunctionalTestCase;

public class RetryNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "retry-namespace-config.xml";
    }

    public void testConfig() throws Exception
    {
        Connector c = muleContext.getRegistry().lookupConnector("testConnector");
        assertNotNull(c);

        RetryTemplateFactory rtf = c.getRetryTemplateFactory();
        assertNotNull(rtf);
        assertTrue(rtf instanceof DefaultRetryTemplate);
        assertTrue(rtf.isRetryEnabled());
        
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
    }

}

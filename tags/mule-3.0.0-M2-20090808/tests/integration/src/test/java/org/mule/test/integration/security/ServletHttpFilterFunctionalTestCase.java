/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.security;

import org.mule.module.spring.security.HttpFilterFunctionalTestCase;
import org.mule.transport.servlet.MuleReceiverServlet;
import org.mule.transport.servlet.jetty.util.EmbeddedJettyServer;

public class ServletHttpFilterFunctionalTestCase extends HttpFilterFunctionalTestCase
{
    public static final int HTTP_PORT = 4567;

    private EmbeddedJettyServer httpServer;

    protected String getConfigResources()
    {
        return "org/mule/test/integration/security/servlet-http-filter-test.xml";
    }
    
    protected String getUrl()
    {
        return "http://localhost:" + HTTP_PORT + "/test/index.html";
    }

    @Override
    protected void doSetUp() throws Exception 
    {
        super.doSetUp();
        
        httpServer = new EmbeddedJettyServer(HTTP_PORT, "/", "/*", new MuleReceiverServlet(), muleContext);

        httpServer.start();
    }

    @Override
    protected void doTearDown() throws Exception
    {
        if (httpServer != null && httpServer.isStarted())
        {
            httpServer.stop();
        }

        super.doTearDown();
    }

}

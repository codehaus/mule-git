/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http;

import org.mule.transport.http.HttpConnector;

public class HttpPlaceholderTestCase extends AbstractNamespaceHandlerTestCase
{

    public HttpPlaceholderTestCase()
    {
        super("http");
    }

    protected String getConfigResources()
    {
        return "http-placeholder-test.xml";
    }

    public void testConnectorProperties()
    {
        HttpConnector connector =
                (HttpConnector) muleContext.getRegistry().lookupConnector("httpConnector");
        testBasicProperties(connector);
    }

}
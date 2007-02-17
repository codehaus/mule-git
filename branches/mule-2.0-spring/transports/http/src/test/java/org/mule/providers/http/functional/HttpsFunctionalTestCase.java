/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.http.functional;

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.http.HttpsConnector;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpointURI;
import org.mule.umo.provider.UMOConnector;

public class HttpsFunctionalTestCase extends HttpFunctionalTestCase
{
    protected UMOConnector createConnector() throws Exception
    {
        HttpsConnector connector = new HttpsConnector();
        connector.setName("testHttps");
        connector.getDispatcherThreadingProfile().setDoThreading(false);
        connector.setKeyStore("serverKeystore");
        connector.setStorePassword("mulepassword");
        connector.setKeyPassword("mulepassword");
        connector.setTrustStore("trustStore");
        connector.setTrustStorePassword("mulepassword");
        return connector;
    }

    protected UMOEndpointURI getInDest()
    {
        try
        {
            return new MuleEndpointURI("https://localhost:60198");
        }
        catch (UMOException e)
        {
            fail(e.getMessage());
            return null;
        }
    }
}

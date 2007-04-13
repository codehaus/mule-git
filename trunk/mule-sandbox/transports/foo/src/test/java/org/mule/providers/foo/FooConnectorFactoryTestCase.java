/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo;

import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.providers.service.TransportFactory;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.endpoint.UMOEndpoint;


public class FooConnectorFactoryTestCase extends AbstractMuleTestCase
{
    public void testCreateFromFactory() throws Exception
    {
        MuleEndpointURI url = new MuleEndpointURI(getEndpointURI());
        UMOEndpoint endpoint = TransportFactory.createEndpoint(url, UMOEndpoint.ENDPOINT_TYPE_RECEIVER);
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertTrue(endpoint.getConnector() instanceof FooConnector);
        assertEquals(getEndpointURI(), endpoint.getEndpointURI().getAddress());
    }
    
    public String getEndpointURI() {
    	//Todo return a valid endpoint URI string for your transport i.e. tcp://localhost:1234
    	throw new UnsupportedOperationException("getEndpointURI");
    }
}

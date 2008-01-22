/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp;

import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.tck.AbstractMuleTestCase;

public class ConnectorFactoryTestCase extends AbstractMuleTestCase
{
    public void testCreate() throws Exception
    {
        UMOImmutableEndpoint endpoint = 
            muleContext.getRegistry().lookupEndpointFactory().getInboundEndpoint("tcp://7877");
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertEquals("tcp://localhost:7877", endpoint.getEndpointURI().getAddress());
    }
}

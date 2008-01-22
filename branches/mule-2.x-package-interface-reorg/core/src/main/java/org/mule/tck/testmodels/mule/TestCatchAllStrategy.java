/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.testmodels.mule;

import org.mule.api.UMOMessage;
import org.mule.api.UMOSession;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.routing.RoutingException;
import org.mule.api.routing.UMORouterCatchAllStrategy;
import org.mule.util.StringMessageUtils;

public class TestCatchAllStrategy implements UMORouterCatchAllStrategy
{
    private UMOImmutableEndpoint endpoint;

    public void setEndpoint(UMOImmutableEndpoint endpoint)
    {
        this.endpoint = endpoint;
    }

    public UMOImmutableEndpoint getEndpoint()
    {
        return endpoint;
    }

    public UMOMessage catchMessage(UMOMessage message, UMOSession session, boolean synchronous)
        throws RoutingException
    {
        System.out.println(StringMessageUtils.getBoilerPlate("Caught an event in the router!", '*', 40));
        return null;
    }
}

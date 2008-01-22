/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.routing;

import org.mule.api.UMOMessage;
import org.mule.api.UMOSession;
import org.mule.api.endpoint.UMOImmutableEndpoint;

/**
 * <code>UMORouterCatchAllStrategy</code> TODO
 */

public interface UMORouterCatchAllStrategy
{
    void setEndpoint(UMOImmutableEndpoint endpoint);

    UMOImmutableEndpoint getEndpoint();

    UMOMessage catchMessage(UMOMessage message, UMOSession session, boolean synchronous)
        throws RoutingException;
}

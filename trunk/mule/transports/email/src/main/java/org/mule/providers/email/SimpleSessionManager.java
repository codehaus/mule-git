/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email;

import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOConnector;

/**
 * Store {@link SessionDetails} assuming that the connector is assocated 1-1 with the endpoint.
 */
public class SimpleSessionManager implements SessionManager
{
    
    private SessionDetails session;

    public SessionDetails getSession(UMOImmutableEndpoint endpoint, UMOConnector connector)
    {
        return session;
    }

    public void setSession(UMOImmutableEndpoint endpoint, UMOConnector connector, SessionDetails session)
    {
        this.session = session;
    }

}

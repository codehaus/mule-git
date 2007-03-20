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
 * Associate {@link SessionDetails} with an endpoint and connector.  
 * {@link AbstractMailConnector} contains a SessionManager instance via a hard-coded constructor call.
 * To change the management of session state implement a new manager and change the connector.
 */
public interface SessionManager
{

    SessionDetails getSession(UMOImmutableEndpoint endpoint, UMOConnector connector);
    
    void setSession(UMOImmutableEndpoint endpoint, UMOConnector connector, SessionDetails session);
    
}

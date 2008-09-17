/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms.websphere;

import org.mule.providers.jms.XaTransactedJmsMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

import javax.jms.Session;

public class WebsphereTransactedJmsMessageReceiver extends XaTransactedJmsMessageReceiver
{

    public WebsphereTransactedJmsMessageReceiver(UMOConnector umoConnector, UMOComponent component, UMOEndpoint endpoint) throws InitialisationException
    {
        super(umoConnector, component, endpoint);
    }
    
    protected void doConnect() throws Exception
    {
        if (connector.isConnected() && connector.isEagerConsumer())
        {
            createConsumer();
        }
        
        // MULE-1150 check whether mule is really connected      
        if (connector.isConnected() && !this.connected.get() && connector.getSessionFromTransaction() == null)
        {
            // check connection by creating session
            Session s = connector.getConnection().createSession(false, 1);
            s.close();
        }
    }
}

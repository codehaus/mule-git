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
            // creating this consumer now would prevent from the actual worker
            // consumer
            // to receive the message!
            //Antoine Borg 08 Dec 2006 - Uncommented for MULE-1150
            // if we comment this line, if one tries to restart the service through
            // JMX,
            // this will fail...
            //This Line seems to be the root to a number of problems and differences between
            //Jms providers. A which point the consumer is created changes how the conneciton can be managed.
            //For example, WebsphereMQ needs the consumer created here, otherwise ReconnectionStrategies don't work properly
            //(See MULE-1150) However, is the consumer is created here for Active MQ, The worker thread cannot actually
            //receive the message.  We need to test with a few more Jms providers and transactions to see which behaviour
            // is correct.  My gut feeling is that the consumer should be created here and there is a bug in ActiveMQ
        }
        
        // MULE-1150 check whether mule is really connected      
        if (connector.isConnected() && (this.connected.compareTo(false) == 0))
        {
            if (connector.getSessionFromTransaction() == null)
            {
                // check connection by creating session
                Session s = connector.getConnection().createSession(false, 1);
                s.close();
            }
        }
    }
}

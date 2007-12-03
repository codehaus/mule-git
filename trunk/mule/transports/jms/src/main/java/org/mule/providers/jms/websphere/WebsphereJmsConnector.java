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

import org.mule.providers.jms.JmsConnector;
import java.util.Properties;

/**
 * Websphere-specific JMS connector.
 */
public class WebsphereJmsConnector extends JmsConnector
{
    public static final String RECEIVER_TYPE = "xa.transacted.message.receiver";
    public static final String RECEIVER_CLASS = WebsphereTransactedJmsMessageReceiver.class.getName(); 
    
    /** Constructs a new WebsphereJmsConnector. */
    public WebsphereJmsConnector()
    {
        setRecoverJmsConnections(false);
        if ((serviceOverrides == null) || (serviceOverrides.isEmpty()))
        {
            Properties override = new Properties();  
            override.setProperty(RECEIVER_TYPE, RECEIVER_CLASS);
            this.setServiceOverrides(override);
        }
    }
}

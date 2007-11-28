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
    /** Constructs a new WebsphereJmsConnector. */
    public WebsphereJmsConnector()
    {
        setRecoverJmsConnections(false);
        if (serviceOverrides == null)
        {
            Properties override = new Properties();  
            override.setProperty("transacted.message.receiver", "org.mule.providers.jms.websphere.WebsphereTransactedJmsMessageReceiver");
            override.setProperty("xa.transacted.message.receiver", "org.mule.providers.jms.websphere.WebsphereTransactedJmsMessageReceiver");
            this.setServiceOverrides(override);
        }
    }
}

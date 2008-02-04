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

import org.mule.config.MuleProperties;
import org.mule.providers.jms.JmsConnector;

import java.util.Collections;
import java.util.Map;

/**
 * Websphere-specific JMS connector.
 */
public class WebsphereJmsConnector extends JmsConnector
{
    public static final String DEFAULT_XA_RECEIVER_CLASS = WebsphereTransactedJmsMessageReceiver.class.getName();
    
    /** Constructs a new WebsphereJmsConnector. */
    public WebsphereJmsConnector()
    {
        setRecoverJmsConnections(false);
        if (serviceOverrides == null || serviceOverrides.isEmpty())
        {
            Map overrides = Collections.singletonMap(MuleProperties.CONNECTOR_XA_TRANSACTED_MESSAGE_RECEIVER_CLASS,
                                                     DEFAULT_XA_RECEIVER_CLASS);
            setServiceOverrides(overrides);
        }
    }
}

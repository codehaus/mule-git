/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.providers.email;

import org.mule.providers.email.Pop3Connector;
import org.mule.umo.provider.UMOConnector;

/**
 * Simple tests for pulling from a POP3 server.
 */
public class Pop3ConnectorTestCase extends AbstractReceivingMailConnectorTestCase
{

    public UMOConnector getConnector() throws Exception
    {
        Pop3Connector connector = new Pop3Connector();
        connector.setName("Pop3Connector");
        connector.setCheckFrequency(1000l);
        connector.initialise();
        return connector;
    }

    public String getTestEndpointURI()
    {
        return getPop3TestEndpointURI();
    }

}

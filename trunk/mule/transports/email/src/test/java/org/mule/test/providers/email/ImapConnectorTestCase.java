/*
 * $Id: Pop3ConnectorTestCase.java 4517 2007-01-03 23:14:53Z holger $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.providers.email;

import org.mule.providers.email.ImapConnector;
import org.mule.umo.provider.UMOConnector;

public class ImapConnectorTestCase extends AbstractReceivingMailConnectorTestCase
{

    public UMOConnector getConnector() throws Exception
    {
        ImapConnector connector = new ImapConnector();
        connector.setName("ImapConnector");
        connector.setCheckFrequency(1000l);
        connector.initialise();
        return connector;
    }

    public String getTestEndpointURI()
    {
        return getImapTestEndpointURI();
    }

}

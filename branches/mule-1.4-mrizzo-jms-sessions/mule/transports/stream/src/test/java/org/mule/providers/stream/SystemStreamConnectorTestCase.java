/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.stream;

import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.umo.provider.UMOConnector;

public class SystemStreamConnectorTestCase extends AbstractConnectorTestCase
{
    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.providers.AbstractConnectorTestCase#createConnector()
     */
    // @Override
    public UMOConnector createConnector() throws Exception
    {
        UMOConnector connector = new SystemStreamConnector();
        connector.setName("TestStream");
        connector.initialise();
        return connector;
    }

    public String getTestEndpointURI()
    {
        return "stream://System.out";
    }

    public Object getValidMessage() throws Exception
    {
        return "Test Message";
    }

}

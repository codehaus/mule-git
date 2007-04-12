/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo;

import org.mule.impl.MuleDescriptor;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;

public class FooConnectorTestCase extends AbstractConnectorTestCase
{
    /**
     * Create and initialise an instance of your connector here. Do not actually call the
     * connect method.
     */
    public UMOConnector getConnector() throws Exception
    {
        //Return an instance of your connector
        FooConnector c = new FooConnector();
        c.setName("Test-Foo");
        //Todo Set any additional properties on the connector here
        return c;
    }

    public String getTestEndpointURI()
    {
    	//Todo Return a valid endpoint for you transport here
        throw new UnsupportedOperationException("getTestEndpointURI");
    }

    public Object getValidMessage() throws Exception
    {
        //Todo Return an valid message for your transport
        throw new UnsupportedOperationException("getValidMessage");
    }


    public void testProperties() throws Exception
    {
        //Todo test setting and retrieving any custom properties on the Connector as necessary
    }
}

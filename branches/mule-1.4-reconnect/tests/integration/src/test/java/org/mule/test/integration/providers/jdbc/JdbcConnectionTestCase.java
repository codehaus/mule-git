/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.providers.jdbc;

import org.mule.MuleManager;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.impl.retry.RetryTemplate;
import org.mule.impl.retry.policies.SimpleRetryPolicyFactory;
import org.mule.providers.ConnectNotifier;
import org.mule.providers.jdbc.JdbcConnector;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;

import java.util.HashMap;

import javax.sql.DataSource;

/**
 * This test must be run manually. See the comments inline in testReconnection
 */
public class JdbcConnectionTestCase extends AbstractJdbcFunctionalTestCase
{

    protected JdbcConnector connector;

    protected void emptyTable() throws Exception
    {
        // TODO this overrides super.emptyTable() - is this correct?
        // the entire test seems to be incomplete, see the comments below..
    }

    public UMOConnector createConnector() throws Exception
    {
        connector = (JdbcConnector)super.createConnector();
        // TODO RM* This can be done using a worker to wrap the template in the connector:
        // strategy.setDoThreading(true);
        connector.setConnectionStrategy(new RetryTemplate(new SimpleRetryPolicyFactory(10, 1000),
            new ConnectNotifier()));
        return connector;
    }

    public void testReconnection() throws Exception
    {

        MuleDescriptor d = getTestDescriptor("anOrange", Orange.class.getName());

        UMOComponent component = model.registerComponent(d);
        UMOEndpoint endpoint = new MuleEndpoint("test", new MuleEndpointURI(
            "jdbc://test?sql=SELECT * FROM TABLE"), connector, null, UMOEndpoint.ENDPOINT_TYPE_SENDER, 0,
            null, new HashMap());
        MuleManager.getInstance().start();
        connector.registerListener(component, endpoint);

        // The derbydb instance should be put offline before starting test
        // The receiver should try to connect to the database
        //
        // Then put derbydb online.
        // Check that the receiver reconnect and polls the database
        //
        // Put derbydb offline.
        // The receiver should try to connect to the database.
        Thread.sleep(1000);
    }
    
    protected DataSource createDataSource() throws Exception
    {
        return createClientDataSource();
    }

}

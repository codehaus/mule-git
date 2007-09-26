/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.integration.providers.jms.activemq;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.impl.internal.notifications.ConnectionNotification;
import org.mule.impl.internal.notifications.ConnectionNotificationListener;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.provider.DispatchException;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

import javax.jms.TextMessage;

import org.apache.activemq.broker.BrokerService;

/**
 * This tests a number of scenarios
 * 1) Async connecting: In order to test reconnection in a single test case, the connections have to be done in a separate thread
 * 2) Mule starting with a fail connection
 * 3) Mule recognising that a connection is avialble again
 * 4) Ensuring that endpoints for the connector are also, conected and started (since the server is started by default). We do this
 * by sending a message from the client that the server picks up.
 * 5) Dispatchers also have retry strategies so that if the message cannot be written, it is retried. This test only tests that it
 * without failure it works
 * 6) We also do a receive, which now uses a retry to re-attempt if it fails (not if it times out) But again this only tests that
 * it works when there is no error.
 * */
public class JmsReconnectTestCase extends FunctionalTestCase implements ConnectionNotificationListener
{

    private AtomicInteger failedConnectorCount = new AtomicInteger(0);
    private AtomicInteger failedEndpointCount = new AtomicInteger(0);
    private AtomicInteger successConnectorCount = new AtomicInteger(0);
    private AtomicInteger successEndpointCount = new AtomicInteger(0);
    private AtomicInteger disconnectConnectorCount = new AtomicInteger(0);
    private AtomicInteger disconnectEndpointCount = new AtomicInteger(0);

    protected String getConfigResources()
    {
        return "jms-reconnect-test-config.xml";
    }

    //@java.lang.Override
    protected void doPostFunctionalSetUp() throws Exception
    {
        MuleManager.getInstance().registerListener(this);
    }

    public void testReconnect() throws Exception
    {
        Thread.sleep(4000);
        //depending on the machine there may be more than one attempt to connect
        assertTrue(failedConnectorCount.intValue() > 0);
        //No attempts to connect the endpoint (receiver) should be made until the connector has connected
        assertEquals(0, failedEndpointCount.intValue());
        assertEquals(0, successConnectorCount.intValue());
        assertEquals(0, successEndpointCount.intValue());
        assertEquals(0, disconnectConnectorCount.intValue());
        assertEquals(0, disconnectEndpointCount.intValue());

        MuleClient client = new MuleClient();
        UMOMessage result = null;
        try
        {
            result = client.send("clientEndpoint", "test", null);
            fail("This should fail because the connector has not connected yet");
        }
        catch (DispatchException e)
        {
            //expected
            //This tests that we don't go into a blocking state where the dispatcher attempts
            //to perform the write in a RetryTemplate when the connector has not connected, where they
            //both have the same retry policy
        }

        BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        broker.setUseJmx(false);
        // configure the broker
        broker.addConnector("tcp://localhost:61616");
        broker.start();

        Thread.sleep(4000);

        assertEquals(1, successConnectorCount.intValue());
        assertEquals(1, successEndpointCount.intValue());
        assertEquals(0, failedEndpointCount.intValue());
        assertEquals(0, disconnectConnectorCount.intValue());

        //TODO ActiveMQ barfs with ClassCastException when you call stop in the brokers
        //Ideally in this test we would stop and restart the broker and test to make sure things are working

        //broker.stop();

        //Thread.sleep(6000);

        //assertEquals(1, successConnectorCount.intValue());
        //assertEquals(1, disconnectConnectorCount.intValue());

        //Lets test that we can write to our endpoint
        client = new MuleClient();
        client.dispatch("clientEndpoint", "test", null);
        result = client.receive("outboundEndpoint", 5000);
        assertNotNull(result);
        assertEquals("test Received", ((TextMessage)result.getPayload()).getText());
        assertNull(result.getExceptionPayload());
    }

    public void onNotification(UMOServerNotification notification)
    {
        if(notification.getAction()== ConnectionNotification.CONNECTION_FAILED)
        {
            if(notification.getResourceIdentifier().startsWith("connector"))
            {
                failedConnectorCount.incrementAndGet();
            }
            else if(notification.getResourceIdentifier().startsWith("endpoint"))
            {
                failedEndpointCount.incrementAndGet();
            }
        }
        else if(notification.getAction()== ConnectionNotification.CONNECTION_CONNECTED)
        {
            if(notification.getResourceIdentifier().startsWith("connector"))
            {
                successConnectorCount.incrementAndGet();
            }
            else if(notification.getResourceIdentifier().startsWith("endpoint"))
            {
                successEndpointCount.incrementAndGet();
            }
        }
        else if(notification.getAction()== ConnectionNotification.CONNECTION_DISCONNECTED)
        {
            if(notification.getResourceIdentifier().startsWith("connector"))
            {
                disconnectConnectorCount.incrementAndGet();
            }
            else if(notification.getResourceIdentifier().startsWith("endpoint"))
            {
                disconnectEndpointCount.incrementAndGet();
            }
        }

    }
}
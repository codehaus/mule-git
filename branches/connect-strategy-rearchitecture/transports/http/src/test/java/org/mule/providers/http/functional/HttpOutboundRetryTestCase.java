/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.http.functional;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.impl.internal.notifications.ConnectionNotification;
import org.mule.impl.internal.notifications.ConnectionNotificationListener;
import org.mule.impl.MuleDescriptor;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.functional.EventCallback;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.UMODescriptor;
import org.mule.umo.UMOEventContext;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

public class HttpOutboundRetryTestCase extends FunctionalTestCase
{
    public static final long WAIT_TIMEOUT = 4000L;
    protected String getConfigResources()
    {
        return "http-outbound-retry-test.xml";
    }

    public void testRetryFail() throws Exception
    {
        MuleClient client = new MuleClient();
        //There should be two failed attempt
        final CountDownLatch failedCountdown = new CountDownLatch(2);

        final AtomicInteger calls = new AtomicInteger(0);
        final AtomicInteger sucessCalls = new AtomicInteger(0);

        MuleManager.getInstance().registerListener(new ConnectionNotificationListener()
        {
            public void onNotification(UMOServerNotification notification)
            {
                calls.incrementAndGet();                
                if(notification.getAction()== ConnectionNotification.CONNECTION_FAILED)
                {
                    failedCountdown.countDown();
                }
                else if(notification.getAction()== ConnectionNotification.CONNECTION_CONNECTED)
                {
                    sucessCalls.incrementAndGet();
                }
            }
        }, "endpoint.http*");

        client.dispatch("dispatchEndpoint", "test", null);
        failedCountdown.await(WAIT_TIMEOUT, TimeUnit.MILLISECONDS);
        assertEquals(2, calls.get());

        assertEquals(0, sucessCalls.get());
    }

    public void testRetrySuccess() throws Exception
    {
        MuleClient client = new MuleClient();
        final AtomicInteger calls = new AtomicInteger(0);

        final CountDownLatch failedCountdown = new CountDownLatch(1);
        final CountDownLatch successCountdown = new CountDownLatch(1);
        final CountDownLatch receivedCountdown = new CountDownLatch(1);
        MuleManager.getInstance().registerListener(new ConnectionNotificationListener()
        {
            public void onNotification(UMOServerNotification notification)
            {
                calls.incrementAndGet();
                if(notification.getAction()== ConnectionNotification.CONNECTION_FAILED)
                {
                    failedCountdown.countDown();
                }
                else if(notification.getAction()== ConnectionNotification.CONNECTION_CONNECTED)
                {
                    successCountdown.countDown();
                }
            }
        }, "endpoint.http*");

        client.dispatch("dispatchEndpoint", "test", null);
        failedCountdown.await(WAIT_TIMEOUT, TimeUnit.MILLISECONDS);

        FunctionalTestComponent comp = new FunctionalTestComponent();
        comp.setEventCallback(new EventCallback()
        {
            public void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                receivedCountdown.countDown();
                assertEquals("test", context.getMessageAsString());
            }
        });
        UMODescriptor d = new MuleDescriptor();
        d.setName("test");
        d.setImplementation(comp);
        d.getInboundRouter().addEndpoint(MuleManager.getInstance().lookupEndpoint("receiveEndpoint"));
        MuleManager.getInstance().lookupModel("main").registerComponent(d);

        assertEquals(2, calls.get());
        assertTrue(failedCountdown.await(WAIT_TIMEOUT, TimeUnit.MILLISECONDS));
        assertTrue(successCountdown.await(WAIT_TIMEOUT, TimeUnit.MILLISECONDS));
        assertTrue(receivedCountdown.await(WAIT_TIMEOUT, TimeUnit.MILLISECONDS));
    }
}
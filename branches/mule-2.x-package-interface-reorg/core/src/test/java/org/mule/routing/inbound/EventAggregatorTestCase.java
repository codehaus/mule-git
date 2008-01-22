/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.inbound;

import org.mule.DefaultMuleMessage;
import org.mule.MuleEvent;
import org.mule.api.AbstractMuleException;
import org.mule.api.Event;
import org.mule.api.MuleMessage;
import org.mule.api.Session;
import org.mule.api.component.Component;
import org.mule.api.endpoint.Endpoint;
import org.mule.api.routing.InboundRouterCollection;
import org.mule.routing.AggregationException;
import org.mule.routing.LoggingCatchAllStrategy;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Apple;

import java.util.Iterator;
import java.util.Map;

public class EventAggregatorTestCase extends AbstractMuleTestCase
{

    public void testMessageAggregator() throws Exception
    {
        Component testComponent = getTestComponent("test", Apple.class);
        Session session = getTestSession(testComponent);

        InboundRouterCollection messageRouter = new DefaultInboundRouterCollection();
        SimpleEventAggregator router = new SimpleEventAggregator(3);
        messageRouter.addRouter(router);
        messageRouter.setCatchAllStrategy(new LoggingCatchAllStrategy());

        MuleMessage message1 = new DefaultMuleMessage("test event A");
        MuleMessage message2 = new DefaultMuleMessage("test event B");
        MuleMessage message3 = new DefaultMuleMessage("test event C");

        Endpoint endpoint = getTestEndpoint("Test1Provider", Endpoint.ENDPOINT_TYPE_SENDER);
        Event event1 = new MuleEvent(message1, endpoint, session, false);
        Event event2 = new MuleEvent(message2, endpoint, session, false);
        Event event3 = new MuleEvent(message3, endpoint, session, false);
        assertTrue(router.isMatch(event1));
        assertTrue(router.isMatch(event2));
        assertTrue(router.isMatch(event3));

        assertNull(router.process(event1));
        assertNull(router.process(event2));

        Event[] results = router.process(event3);
        assertNotNull(results);
        assertEquals(1, results.length);
        assertEquals("test event A test event B test event C ", results[0].getMessageAsString());
    }

    public static class SimpleEventAggregator extends AbstractEventAggregator
    {
        private final int eventThreshold;
        private int eventCount = 0;

        public SimpleEventAggregator(int eventThreshold)
        {
            this.eventThreshold = eventThreshold;
        }

        protected boolean shouldAggregateEvents(EventGroup events)
        {
            eventCount++;
            if (eventCount == eventThreshold)
            {
                eventCount = 0;
                return true;
            }
            return false;
        }

        protected MuleMessage aggregateEvents(EventGroup events) throws AggregationException
        {
            if (events.size() != this.eventThreshold)
            {
                throw new IllegalStateException("eventThreshold not yet reached?");
            }

            StringBuffer newPayload = new StringBuffer(80);

            for (Iterator iterator = events.iterator(); iterator.hasNext();)
            {
                Event event = (Event)iterator.next();
                try
                {
                    newPayload.append(event.getMessageAsString()).append(" ");
                }
                catch (AbstractMuleException e)
                {
                    throw new AggregationException(events, event.getEndpoint(), e);
                }
            }

            return new DefaultMuleMessage(newPayload.toString(), (Map)null);
        }
    }
}

/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.test.routing.inbound;

import com.mockobjects.dynamic.Mock;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.routing.LoggingCatchAllStrategy;
import org.mule.routing.inbound.AbstractEventResequencer;
import org.mule.routing.inbound.EventGroup;
import org.mule.routing.inbound.InboundMessageRouter;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.umo.*;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.routing.UMOInboundMessageRouter;

import java.util.Comparator;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class EventResequencerTestCase extends AbstractMuleTestCase
{
    public void testMessageResequencer() throws Exception
    {
        Mock session = getMockSession();
        UMOComponent testComponent = getTestComponent(getTestDescriptor("test", Apple.class.getName()));

        UMOInboundMessageRouter messageRouter = new InboundMessageRouter();
        SimpleEventResequencer router = new SimpleEventResequencer(3);
        messageRouter.addRouter(router);
        messageRouter.setCatchAllStrategy(new LoggingCatchAllStrategy());

        UMOMessage message1 = new MuleMessage("test event A", null);
        UMOMessage message2 = new MuleMessage("test event B", null);
        UMOMessage message3 = new MuleMessage("test event C", null);

        UMOEndpoint endpoint = getTestEndpoint("Test1Provider", UMOEndpoint.ENDPOINT_TYPE_SENDER);
        UMOEvent event1 = new MuleEvent(message1, endpoint, (UMOSession) session.proxy(), false);
        UMOEvent event2 = new MuleEvent(message2, endpoint, (UMOSession) session.proxy(), false);
        UMOEvent event3 = new MuleEvent(message3, endpoint, (UMOSession) session.proxy(), false);
        assertTrue(router.isMatch(event1));
        assertTrue(router.isMatch(event2));
        assertTrue(router.isMatch(event3));

        assertNull(router.process(event2));
        assertNull(router.process(event3));

        UMOEvent[] results = router.process(event1);
        assertNotNull(results);
        assertEquals(3, results.length);

        assertEquals("test event B", results[0].getMessageAsString());
        assertEquals("test event C", results[1].getMessageAsString());
        assertEquals("test event A", results[2].getMessageAsString());

        //set a resequencing comparator
        router.setComparator(new EventPayloadComparator());

        assertNull(router.process(event2));
        assertNull(router.process(event3));

        results = router.process(event1);
        assertNotNull(results);
        assertEquals(3, results.length);

        assertEquals("test event A", results[0].getMessageAsString());
        assertEquals("test event B", results[1].getMessageAsString());
        assertEquals("test event C", results[2].getMessageAsString());
    }

    public static class SimpleEventResequencer extends AbstractEventResequencer
    {
        private int eventCount = 0;
        private int eventthreshold = 1;

        public SimpleEventResequencer(int eventthreshold)
        {
            this.eventthreshold = eventthreshold;
        }

        protected boolean shouldResequence(EventGroup events)
        {
            eventCount++;
            if(eventCount == eventthreshold) {
                eventCount=0;
                return true;
            }
            return false;
        }
    }

    public static class EventPayloadComparator implements Comparator
    {
        public int compare(Object o1, Object o2)
        {
            try
            {
                return ((UMOEvent)o1).getMessageAsString().compareTo(((UMOEvent)o2).getMessageAsString());
            } catch (UMOException e)
            {
                throw new IllegalArgumentException(e.getMessage());
            }

        }
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.inbound.processor;

import org.mule.api.MuleEvent;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.processor.InterceptingMessageProcessor;
import org.mule.routing.filters.EqualsFilter;

import org.junit.Test;

public class InboundExceptionDetailsProcessorTestCase extends AbstractInboundMessageProcessorTestCase
{

    @Test
    public void testFilterPass() throws Exception
    {
        InterceptingMessageProcessor mp = new InboundFilterMessageProcessor();
        TestListener listner = new TestListener();
        mp.setNext(listner);

        InboundEndpoint endpoint = createTestInboundEndpoint(new EqualsFilter(TEST_MESSAGE), null, true, null);
        MuleEvent inEvent = createTestInboundEvent(endpoint, true);
        MuleEvent resultEvent = mp.process(inEvent);
        assertNotNull(listner.sensedEvent);
        assertSame(inEvent, listner.sensedEvent);
        assertEquals(inEvent, resultEvent);

    }

    @Test
    public void testFilterFail() throws Exception
    {
        InterceptingMessageProcessor mp = new InboundFilterMessageProcessor();
        TestListener listner = new TestListener();
        mp.setNext(listner);

        InboundEndpoint endpoint = createTestInboundEndpoint(new EqualsFilter(null), null, true, null);
        MuleEvent inEvent = createTestInboundEvent(endpoint, true);
        MuleEvent resultEvent = mp.process(inEvent);
        assertNull(listner.sensedEvent);

        // This behaviour is questionable isn't it? Should this MessageProcessor
        // simply bounce the message back?
        assertEquals(inEvent.getMessage(), resultEvent.getMessage());
        assertNull(resultEvent.getMessage().getExceptionPayload());
    }

}

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

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.endpoint.EndpointException;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.api.security.EndpointSecurityFilter;
import org.mule.api.transaction.TransactionConfig;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.tck.AbstractMuleTestCase;

import java.util.Properties;

public class AbstractInboundMessageProcessorTestCase extends AbstractMuleTestCase
{
    protected InboundEndpoint createTestInboundEndpoint(Filter filter,
                                                        EndpointSecurityFilter securityFilter,
                                                        boolean sync,
                                                        TransactionConfig txConfig)
        throws EndpointException, InitialisationException
    {
        EndpointURIEndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder("test://test",
            muleContext);
        endpointBuilder.setFilter(filter);
        endpointBuilder.setSecurityFilter(securityFilter);
        endpointBuilder.setSynchronous(sync);
        endpointBuilder.setTransactionConfig(txConfig);
        InboundEndpoint endpoint = endpointBuilder.buildInboundEndpoint();
        return endpoint;
    }

    protected MuleEvent createTestInboundEvent(InboundEndpoint endpoint, boolean sync) throws Exception
    {
        Properties props = new Properties();
        props.put("prop1", "value1");
        return new DefaultMuleEvent(new DefaultMuleMessage(TEST_MESSAGE, props, muleContext), endpoint,
            getTestSession(getTestService(), muleContext), sync);
    }

    static class TestListener implements MessageProcessor
    {
        MuleEvent sensedEvent;

        public MuleEvent process(MuleEvent event) throws MuleException
        {
            sensedEvent = event;
            return event;
        }
    }
}

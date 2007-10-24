/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms;

import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.endpoint.UMOEndpoint;

import com.mockobjects.dynamic.Mock;

import javax.jms.Queue;
import javax.jms.Topic;

public class DefaultJmsTopicResolverTestCase extends AbstractMuleTestCase
{
    private JmsConnector connector;
    private DefaultJmsTopicResolver resolver;

    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        connector = new JmsConnector();
        resolver = new DefaultJmsTopicResolver(connector);
    }

    public void testSameConnector()
    {
        assertSame(connector, resolver.getConnector());
    }

    public void testEndpointNotTopicWithFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://queue.NotATopic", true);
        assertFalse(resolver.isTopic(endpoint));
    }

    public void testEndpointNotTopicWithFallback2() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://queue.NotATopic", true);
        assertFalse(resolver.isTopic(endpoint, true));
    }

    public void testEndpointNotTopicNoFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://queue.NotATopic", true);
        assertFalse(resolver.isTopic(endpoint, false));
    }

    public void testEndpointTopicPropertyWithFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://context.aTopic?topic=true", true);
        assertTrue(resolver.isTopic(endpoint));
    }

    public void testEndpointTopicPropertyWithFallback2() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://context.aTopic?topic=true", true);
        assertTrue(resolver.isTopic(endpoint, true));
    }

    public void testEndpointTopicPropertyNoFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://context.aTopic?topic=true", true);
        assertFalse(resolver.isTopic(endpoint, false));
    }

    public void testEndpointTopicPrefixWithFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://topic:context.ThisIsATopic", true);
        assertTrue(resolver.isTopic(endpoint));
    }

    public void testEndpointTopicPrefixWithFallback2() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://topic:context.ThisIsATopic", true);
        assertTrue(resolver.isTopic(endpoint, true));
    }

    public void testEndpointTopicPrefixNoFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://topic:context.ThisIsATopic", true);
        assertTrue(resolver.isTopic(endpoint, false));
    }

    public void testEndpointTopicPrefixAndPropertyWithFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://topic:context.ThisIsATopic?topic=false", true);
        assertTrue(resolver.isTopic(endpoint));
    }

    public void testEndpointTopicPrefixAndPropertyWithFallback2() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://topic:context.ThisIsATopic?topic=false", true);
        assertTrue(resolver.isTopic(endpoint, true));
    }

    public void testEndpointTopicPrefixAndPropertyNoFallback() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://topic:context.ThisIsATopic?topic=false", true);
        assertTrue(resolver.isTopic(endpoint, false));
    }

    public void testDestinationNotTopic() throws Exception
    {
        // prepare the mock
        Mock mock = new Mock(Queue.class);
        Queue queue = (Queue) mock.proxy();

        assertFalse(resolver.isTopic(queue));
        mock.verify();
    }

    public void testDestinationTopic() throws Exception
    {
        // prepare the mock
        Mock mock = new Mock(Topic.class);
        Topic topic = (Topic) mock.proxy();

        assertTrue(resolver.isTopic(topic));
        mock.verify();
    }

}

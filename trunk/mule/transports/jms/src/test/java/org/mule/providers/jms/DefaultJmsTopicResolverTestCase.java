/*
* $Id$
* --------------------------------------------------------------------------------------
* Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
*
* The software in this package is published under the terms of the MuleSource MPL
* license, a copy of which has been included with this distribution in the
* LICENSE.txt file.
*/

package org.mule.providers.jms;

import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.impl.endpoint.MuleEndpoint;

import com.mockobjects.dynamic.Mock;

import javax.jms.Queue;
import javax.jms.Topic;

public class DefaultJmsTopicResolverTestCase extends AbstractMuleTestCase
{
    private JmsConnector connector;
    private DefaultJmsTopicResolver resolver;

    protected void doSetUp () throws Exception
    {
        super.doSetUp();
        connector = new JmsConnector();
        resolver = new DefaultJmsTopicResolver(connector);
    }

    public void testSameConnector()
    {
        assertSame(connector, resolver.getConnector());
    }

    public void testEndpointNotTopic() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://queue/NotATopic", true);
        assertFalse(resolver.isTopic(endpoint));
    }

    public void testEndpointTopic() throws Exception
    {
        UMOEndpoint endpoint = new MuleEndpoint("jms://topic:topic/ThisIsATopic", true);
        assertFalse(resolver.isTopic(endpoint));
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

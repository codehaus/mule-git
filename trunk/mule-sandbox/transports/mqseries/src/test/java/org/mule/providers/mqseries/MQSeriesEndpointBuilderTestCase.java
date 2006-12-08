/* 
* $Header: /opt/cvsroot/framegroup/mqseries/main/test/java/org/mule/providers/mqseries/MQSeriesEndpointBuilderTestCase.java,v 1.4 2006/02/02 12:23:29 rossmason Exp $
* $Revision: 1.4 $
* $Date: 2006/02/02 12:23:29 $
* ------------------------------------------------------------------------------------------------------
* 
* Copyright (c) SymphonySoft Limited. All rights reserved.
* http://www.symphonysoft.com
* 
* The software in this package is published under the terms of the BSD
* style license a copy of which has been included with this distribution in
* the LICENSE.txt file. 
*
*/
package org.mule.providers.mqseries;

import junit.framework.TestCase;
import org.mule.impl.endpoint.MuleEndpointURI;
import org.mule.umo.endpoint.MalformedEndpointException;
import org.mule.umo.endpoint.UMOEndpointURI;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.4 $
 */
public class MQSeriesEndpointBuilderTestCase extends TestCase
{
    public void testQueue() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://my.queue");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.queue", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertNull(uri.getParams().getProperty("queueManager"));
        assertEquals("mqs://my.queue", uri.toString());

    }

    public void testQueueWithQM() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://QMgr/my.queue");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.queue", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertEquals("mqs://QMgr/my.queue", uri.toString());
    }

    public void testWithoutFullUrlAndProvider() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://QMgr/my.queue?endpointName=mqseriesProvider");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.queue", uri.getAddress());
        assertEquals("mqseriesProvider", uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertEquals("mqs://QMgr/my.queue?endpointName=mqseriesProvider", uri.toString());
    }

    public void testValidQueueWithUserInfo() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://user:password@QMgr/my.queue");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.queue", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertNull(uri.getResourceInfo());
        assertEquals("user:password", uri.getUserInfo());
        assertEquals("user", uri.getUsername());
        assertEquals("password", uri.getPassword());
        assertEquals("mqs://user:password@QMgr/my.queue", uri.toString());
    }

    public void testMQSeriesTopic() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://topic:my.topic");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.topic", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertEquals("topic:my.topic",uri.getParams().getProperty("queueManager"));
        assertEquals("topic", uri.getResourceInfo());
        assertEquals("mqs://topic:my.topic", uri.toString());

    }

    public void testMQSeriesTopicWithUserInfo() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://user:pass@topic:my.topic");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.topic", uri.getAddress());
        assertEquals("user", uri.getUsername());
        assertEquals("pass", uri.getPassword());
        assertNull(uri.getEndpointName());
        assertEquals("user:pass@topic:my.topic",uri.getParams().getProperty("queueManager"));
        assertEquals("topic", uri.getResourceInfo());
        assertEquals("mqs://user:pass@topic:my.topic", uri.toString());
    }

    public void testTopicWithQM() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://QMgr/topic:my.topic");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.topic", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertEquals("topic", uri.getResourceInfo());
        assertEquals("mqs://QMgr/topic:my.topic", uri.toString());
    }

    public void testTopicWithQMAndUserInfo() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://user:password@QMgr/topic:my.topic");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.topic", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertEquals("mqs", uri.getSchemeMetaInfo());
        assertEquals("user:password", uri.getUserInfo());
        assertEquals("user", uri.getUsername());
        assertEquals("password", uri.getPassword());
        assertEquals("mqs://user:password@QMgr/topic:my.topic", uri.toString());
    }

    public void testValidTopicWithEndpointName() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs://QMgr/topic:my.topic?endpointName=mqTopicProvider");
        assertEquals("mqs", uri.getScheme());
        assertEquals("my.topic", uri.getAddress());
        assertEquals("mqTopicProvider", uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertEquals("topic", uri.getResourceInfo());
        assertEquals("mqs://QMgr/topic:my.topic?endpointName=mqTopicProvider", uri.toString());
    }

    public void testValidMQSeriesQueueURI() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs:queue://QMgr/my.queue?persistence=false");
        assertEquals("mqs:queue", uri.getFullScheme());
        assertEquals("queue://QMgr/my.queue?persistence=false", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertEquals("queue", uri.getResourceInfo());
        //Todo Mule 2.0 toString should give full URI assertEquals("mqs:queue://QMgr/my.queue?persistence=false", uri.toString());
    }

    public void testValidMQSeriesTopicURI() throws Exception
    {
        UMOEndpointURI uri = new MuleEndpointURI("mqs:topic://QMgr/my.topic?persistence=false");
        assertEquals("mqs:topic", uri.getFullScheme());
        assertEquals("topic://QMgr/my.topic?persistence=false", uri.getAddress());
        assertNull(uri.getEndpointName());
        assertEquals("QMgr", uri.getParams().getProperty("queueManager"));
        assertEquals("topic", uri.getResourceInfo());
        //Todo Mule 2.0 toString should give full URI assertEquals("mqs:topic://QMgr/my.topic?persistence=false", uri.toString());
    }
}
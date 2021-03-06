/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms.integration;

import org.junit.Test;

/**
 * Message is put to topic with two subscribers
 */
public class JmsTopicTestCase extends AbstractJmsFunctionalTestCase
{
    public JmsTopicTestCase(JmsVendorConfiguration config)
    {
        super(config);
        // This test actually uses the topic as if it were a queue
        //setUseTopics(true);
    }

    protected String getConfigResources()
    {
        return "integration/jms-topic.xml";
    }

    @Test
    public void testJmsTopic() throws Exception
    {
        // One message is sent.
        send();
        // The same message is read twice from the same JMS topic.
        receiveAndAssert();
        receiveAndAssert();
    }

    @Test
    public void testMultipleSend() throws Exception
    {
        // One message is sent.
        send();
        send();
        // The same message is read twice from the same JMS topic.
        receiveAndAssert();
        receiveAndAssert();
        receiveAndAssert();
        receiveAndAssert();
    }
}

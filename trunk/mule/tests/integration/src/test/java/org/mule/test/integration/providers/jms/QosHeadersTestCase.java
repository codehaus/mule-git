/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.providers.jms;

import org.mule.tck.FunctionalTestCase;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class QosHeadersTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "org/mule/test/integration/providers/jms/qosheaders-test.xml";
    }
    
    public void testQosHeadersPropagation() throws JMSException
    {
        String user = ActiveMQConnection.DEFAULT_USER;
        String password = ActiveMQConnection.DEFAULT_PASSWORD;
        String brokerUrl = "vm://localhost";
        String producerQueue = "test.in";
        String consumerQueue = "test.out";
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, brokerUrl);
        
        // Producer part
        Connection producerConnection = connectionFactory.createConnection();
        producerConnection.start();
        
        Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination producerDestination = producerSession.createQueue(producerQueue);
        MessageProducer producer = producerSession.createProducer(producerDestination);
        
        // Consumer part
        Connection consumerConnection = connectionFactory.createConnection();
        consumerConnection.start();
        
        Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination consumerDestination = consumerSession.createQueue(consumerQueue);
        MessageConsumer consumer = consumerSession.createConsumer(consumerDestination);
        
        String message = "QoS Headers Propagation Test";
        TextMessage textMessage = producerSession.createTextMessage(message);
        producer.setPriority(7);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(textMessage);
        
        Message response = consumer.receive(10000);
        assertNotNull(response);
        assertEquals(7, response.getJMSPriority());
        assertEquals(DeliveryMode.PERSISTENT, response.getJMSDeliveryMode());
        
        consumer.close();
        consumerSession.close();
        consumerConnection.close();

        producer.close();
        producerSession.close();
        producerConnection.close();
    }
}



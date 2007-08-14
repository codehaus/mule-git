/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.providers.jms.activemq;

import org.mule.providers.jms.JmsConnector;
import org.mule.providers.jms.JmsConstants;
import org.mule.providers.jms.JmsTransactionFactory;
import org.mule.providers.jms.activemq.ActiveMqJmsConnector;
import org.mule.test.integration.providers.jms.AbstractJmsTransactionFunctionalTest;
import org.mule.umo.UMOTransactionFactory;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQJmsTransactionFunctionalTestCase extends AbstractJmsTransactionFunctionalTest
{
    protected ActiveMQConnectionFactory factory = null;

    public ActiveMQJmsTransactionFunctionalTestCase()
    {
        exclude(TRANSACTED_REDELIVERY_TO_DL_DESTINATION); // MULE-2071
    }

    public ConnectionFactory getConnectionFactory() throws Exception
    {
        if (factory == null)
        {
            factory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false&broker.useJmx=false");
        }
        return factory;
    }

    protected void doTearDown() throws Exception
    {
        factory = null;
        super.doTearDown();
    }

    public UMOTransactionFactory getTransactionFactory()
    {
        return new JmsTransactionFactory();
    }

    public JmsConnector createConnector() throws Exception
    {
        ActiveMqJmsConnector connector = new ActiveMqJmsConnector();
        connector.setSpecification(JmsConstants.JMS_SPECIFICATION_11);
        connector.setName(CONNECTOR_NAME);
        connector.getDispatcherThreadingProfile().setDoThreading(false);
        return connector;
    }

}

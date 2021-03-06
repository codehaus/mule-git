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

import org.mule.extras.client.MuleClient;
import org.mule.tck.AbstractMuleTestCase;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MultipleJmsConnectorsTestCase extends AbstractMuleTestCase
{
    public void testMultipleJmsClientConnections() throws Exception
    {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
            "vm://localhost?broker.persistent=false&broker.useJmx=false");

        MuleClient client = new MuleClient();
        client.setProperty("jms.connectionFactory", factory);
        client.setProperty("jms.specification", "1.1");
        client.getManager().start();
        client.dispatch("jms://admin:admin@admin.queue?createConnector=ALWAYS", "testing", null);
        client.dispatch("jms://ross:ross@ross.queue?createConnector=ALWAYS", "testing", null);

        // wait a bit to let the messages go on their way
        Thread.sleep(3000);

        assertEquals(2, client.getManager().getConnectors().size());
    }
}

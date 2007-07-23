/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.udp.functional;

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import java.util.HashSet;
import java.util.Set;

public class UdpConnectorFunctionalTestCase extends FunctionalTestCase
{

    public static final String MESSAGE = "hello";


    protected String getConfigResources()
    {
        return "udp-functional-test.xml";
    }

    public void testSendTestData() throws Exception
    {
        // no idea what number to use here.
        // i have seen as low as 97%
        double okPercentage = 90.0; // UDP is unreliable...
        final int numberOfMessages = 10000;
        MuleClient client = new MuleClient();

        for (int sentPackets = 0; sentPackets < numberOfMessages; sentPackets++)
        {
            String msg = MESSAGE + sentPackets;
            client.dispatch("serverEndpoint", msg, null);
        }

        Set receivedMessages = new HashSet(numberOfMessages);

        for (int i = 0; i < numberOfMessages; i++)
        {
            UMOMessage message = client.receive("vm://foo", 2000);
            if (null != message)
            {
                receivedMessages.add(message.getPayloadAsString());
            }
        }
        assertTrue("Received only " + receivedMessages.size() + " messages",
                receivedMessages.size() > numberOfMessages * okPercentage / 100.0 );

        int errCount = 0;
        for (int i = 0; i < numberOfMessages; i++)
        {
            String message = MESSAGE + i + " Received";
            if (! receivedMessages.contains(message))
            {
                logger.warn("Message " + i + " dropped");
                errCount++;
            }
        }
        assertTrue("Dropped " + errCount + " messages",
                errCount < numberOfMessages * (1.0 - (okPercentage / 100.0 )));

    }
}

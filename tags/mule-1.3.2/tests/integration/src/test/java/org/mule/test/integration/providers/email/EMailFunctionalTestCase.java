/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.providers.email;

import java.util.HashMap;
import java.util.Map;

import org.mule.extras.client.MuleClient;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOMessage;

public class EMailFunctionalTestCase extends AbstractMuleTestCase
{

    public void testPopRoundtrip() throws Exception
    {
        doRoundtrip("pop3://mule%40inmail24.com:testbox@pop3.inmail24.com?checkFrequency=5000",
            "smtp://mule%40inmail24.com:testbox@smtp.inmail24.com?address=mule@inmail24.com");
    }

    // public void testSecurePopRoundtrip() throws Exception
    // {
    // doRoundtrip("pop3s://muletestbox:testbox@pop.gmail.com",
    // "smtps://muletestbox:testbox@smtp.gmail.com?address=muletestbox@gmail.com&fromAddress=muletestbox@gmail.com&ccAddresses=ross.mason@symphonysoft.com");
    // }

    // public void testSecureImapRoundtrip() throws Exception
    // {
    // // TODO When Gmail supports it
    // doRoundtrip("imaps://muletestbox:testbox@pop.gmail.com",
    // "smtps://muletestbox:testbox@smtp.gmail.com?address=muletestbox@gmail.com");
    // }

    public void doRoundtrip(String receiveUrl, String sendUrl) throws Exception
    {
        MuleClient mc = new MuleClient();
        UMOMessage msg = mc.receive(receiveUrl, 5000);
        while (msg != null)
        {
            logger.debug("Received:" + msg.getPayloadAsString());
            msg = mc.receive(receiveUrl, 5000);
        }

        String messageString = "testtesttesttesttesttest";
        Map props = new HashMap();

        mc.sendNoReceive(sendUrl, messageString, props);
        msg = mc.receive(receiveUrl, 30000);
        assertNotNull(msg);
        assertTrue(msg.getPayloadAsString().indexOf(messageString) > -1);
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package emailtests;

import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;

/**
 * Retrieve messages via the client directly
 */
public class Pop3APITestCase extends MailFunctionalTestCase
{

    protected void doPreFunctionalSetUp() throws Exception
    {
        super.doPreFunctionalSetUp();
        servers.setUser("mule@symphonysoft.com", "login", "password");
    }

    public void testPlainMessageAPI() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage msg = null;
        String result;
        final String subject = servers.util().random();
        final String body = servers.util().random();

        for (int i = 0; i < messageCount; i++)
        {
            // Send Messages to Inbox
            servers.util().sendTextEmailTest("mule@symphonysoft.com", "from@symphonysoft.com", subject,
                body + " " + i);
            assertEquals(i + 1, servers.getReceivedMessages().length);
        }

        // Retrieve using Mule Client
        for (int i = 0; i < messageCount; i++)
        {
            msg = client.receive("pop3://login:password@localhost:3110", 100);
            assertNotNull(msg);
            assertTrue(msg.getPayload() instanceof String);
            result = (String)msg.getPayload();
            assertEquals(result.trim(), body + " " + i);
        }
    }

    // @Override
    protected String getConfigResources()
    {
        return "mule-empty-config.xml";
    }

}

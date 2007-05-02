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

import javax.mail.Message;

import org.mule.extras.client.MuleClient;

/**
 * Test sending email with Smtp
 */
public class SmtpTestCase extends MailFunctionalTestCase
{

    public void testPlainMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        boolean[] received = new boolean[messageCount];

        // Set 1
        for (int i = 0; i < messageCount; i++)
        {
            client.dispatch("vm://smtpinbound", "Message : " + i, null);
            received[i] = false;
        }

        // wait for max 5s for 1 email to arrive
        assertTrue(servers.waitForIncomingEmail(5000, messageCount));

        // Retrieve using GreenMail API
        Message[] messages = servers.getReceivedMessages();
        assertEquals(messageCount, messages.length);

        for (int i = 0; i < messageCount; i++)
        {
            String message = servers.util().getBody(messages[i]).trim();
            int messageNumber = Integer.parseInt(message.substring(message.lastIndexOf(" ") + 1));
            assertFalse(received[messageNumber]);
            received[messageNumber] = true;
        }

        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(received[i]);
        }

        // Set2
        for (int i = 0; i < messageCount; i++)
        {
            client.dispatch("vm://smtpinbound", "Message : " + (i + messageCount), null);
            received[i] = false;
        }

        // wait for max 5s for 1 email to arrive
        assertTrue(servers.waitForIncomingEmail(5000, messageCount * 2));

        // Retrieve using GreenMail API
        messages = servers.getReceivedMessages();
        assertEquals(messageCount * 2, messages.length);
        for (int i = messageCount; i < messageCount * 2; i++)
        {
            String message = servers.util().getBody(messages[i]).trim();
            int messageNumber = Integer.parseInt(message.substring(message.lastIndexOf(" ") + 1));
            assertFalse(received[messageNumber - messageCount]);
            received[messageNumber - messageCount] = true;
        }

        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(received[i]);
        }
    }

    // @Override
    protected String getConfigResources()
    {
        return "mule-smtp-config.xml";
    }

}

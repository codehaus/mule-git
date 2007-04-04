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

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;

/**
 * Retrieve Messages with Pop3
 */
public class Pop3TestCase extends MailFunctionalTestCase
{

    protected void doPreFunctionalSetUp() throws Exception
    {
        super.doPreFunctionalSetUp();
        servers.setUser("mule@symphonysoft.com", "login", "password");
    }

    public void testPlainMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage msg = null;
        String result;
        String[] split;
        int messageNumber;
        final String subject = servers.util().random();
        final String body = servers.util().random();
        boolean[] received = new boolean[messageCount];

        for (int i = 0; i < messageCount; i++)
        {
            // Send Messages to Inbox
            servers.util().sendTextEmailTest("mule@symphonysoft.com", "from@symphonysoft.com", subject,
                body + " " + i);
            assertEquals(i + 1, servers.getReceivedMessages().length);
            received[i] = false;
        }

        // Retrieve using Mule Client
        for (int i = 0; i < messageCount; i++)
        {
            msg = client.receive("vm://outbound", 30000);
            assertNotNull(msg);
            assertTrue(msg.getPayload() instanceof String);
            result = (String)msg.getPayload();
            split = result.trim().split(" ");
            // assertTrue(result.trim().compareTo(body+" "+i)==0);
            assertEquals(split[0], body);
            messageNumber = Integer.parseInt(split[1]);
            assertFalse("Message Received : " + messageNumber, received[messageNumber]);
            received[messageNumber] = true;
        }

        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(received[i]);
        }

        assertNull(client.receive("vm://outbound", 50));
    }

    public void testLatinEncodedMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage msg = null;
        String result;
        String[] split;
        int messageNumber;
        final String subject = servers.util().random();
        boolean[] received = new boolean[messageCount];

        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "3025");
        props.put("mail.smtp.ssl", "false");
        Session session = Session.getInstance(props, null);

        for (int i = 0; i < messageCount; i++)
        {
            // Send Messages to Inbox
            Address[] tos = new InternetAddress[]{new InternetAddress("mule@symphonysoft.com")};
            Address[] froms = new InternetAddress[]{new InternetAddress("from@symphonysoft.com")};
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSubject(subject);
            mimeMessage.setFrom(froms[0]);

            mimeMessage.setContent("\u00CB\u00C7" + " " + i, "text/plain; charset=ISO-8859-1");
            Transport.send(mimeMessage, tos);

            // servers.util().sendTextEmailTest("mule@symphonysoft.com",
            // "from@symphonysoft.com",
            // subject,"\u65e5\u672c\u8a9e\u6587\u5b57\u5217"+body+" "+i);
            assertEquals(i + 1, servers.getReceivedMessages().length);
            received[i] = false;
        }

        // Retrieve using Mule Client
        for (int i = 0; i < messageCount; i++)
        {
            msg = client.receive("vm://outbound", 1000000000);
            assertNotNull(msg);
            assertTrue(msg.getPayload() instanceof String);
            result = (String)msg.getPayload();
            split = result.trim().split(" ");
            // assertTrue(result.trim().compareTo("\u00CB\u00C7"+" "+i)==0);
            // assertEquals(result.trim(),"\u00CB\u00C7"+" "+i);
            assertEquals(split[0], "\u00CB\u00C7");
            messageNumber = Integer.parseInt(split[1]);
            assertFalse("Message Received : " + messageNumber, received[messageNumber]);
            received[messageNumber] = true;
        }

        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(received[i]);
        }

        assertNull(client.receive("vm://outbound", 50));
    }

    // @Override
    protected String getConfigResources()
    {
        return "mule-pop3endpoint-test-config.xml";
    }

}

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
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.extras.client.MuleClient;

/**
 * In this test we check sending attachments where the service component creates an
 * EmailWithAttachmentMessage, which is then converted to a MimeMessage using a
 * Transformer.
 */
public class SmtpAttachmentTestCase extends MailFunctionalTestCase
{

    protected static transient Log logger = LogFactory.getLog(SmtpAttachmentTestCase.class);

    public void testAttachment() throws Exception
    {
        MuleClient client = new MuleClient();
        boolean[] received = new boolean[messageCount];

        for (int i = 0; i < messageCount; i++)
        {
            client.dispatch("vm://smtpinbound", "Message : " + i + ";email.jpg;email.jpg", null);
            received[i] = false;
        }

        // wait for max 5s for 1 email to arrive
        assertTrue(servers.waitForIncomingEmail(5000, messageCount));

        // Retrieve using GreenMail API
        Message[] messages = servers.getReceivedMessages();
        assertEquals(messageCount, messages.length);
        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(messages[i].getContent() instanceof MimeMultipart);
            MimeMultipart mp = (MimeMultipart)messages[i].getContent();
            assertEquals(3, mp.getCount());

            // TODO check correctness of attachmentsString
            // message=servers.util().getBody(messages[i]).trim();
            String message = servers.util().getBody(mp.getBodyPart(0)).trim();
            int messageNumber = Integer.parseInt(message.substring(message.lastIndexOf(" ") + 1));
            assertFalse(received[messageNumber]);
            received[messageNumber] = true;
        }

        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(received[i]);
        }
    }

    protected String getConfigResources()
    {
        return "mule-smtp-attachment-config.xml";
    }

}


package emailtests;

import javax.mail.Message;

import org.mule.extras.client.MuleClient;

/**
 * This test was setup since there where problems when both Smtp & Smtps were being
 * used in the same mule server at the same time. This was because System variables
 * were being used to store config values which were thus being propagated to both
 * connections, thus creating connections which the wrong properties.
 */
public class SmtpAndSmtpsTestCase extends MailFunctionalTestCase
{

    public void testPlainMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        boolean[] received = new boolean[messageCount];

        // smtp
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

        // smtps
        for (int i = 0; i < messageCount; i++)
        {
            client.dispatch("vm://smtpsinbound", "Message : " + i, null);
            received[i] = false;
        }

        // wait for max 5s for 1 email to arrive
        assertTrue(servers.waitForIncomingEmail(5000, messageCount * 2));

        // Retrieve using GreenMail API
        messages = servers.getReceivedMessages();
        assertEquals(messageCount * 2, messages.length);
        for (int i = 0; i < messageCount; i++)
        {
            String message = servers.util().getBody(messages[i + messageCount]).trim();
            int messageNumber = Integer.parseInt(message.substring(message.lastIndexOf(" ") + 1));
            assertFalse(received[messageNumber]);
            received[messageNumber] = true;
        }

        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(received[i]);
        }
    }

    public void testBothPlainMessage() throws Exception
    {
        MuleClient client = new MuleClient();
        int[] checklist = new int[messageCount];
        int tmpResult;
        for (int i = 0; i < messageCount; i++)
        {
            client.dispatch("vm://bothinbound", "Message : " + i, null);
            checklist[i] = 0;
        }

        // wait for max 5s for 1 email to arrive
        assertTrue(servers.waitForIncomingEmail(5000, messageCount * 2));

        // Retrieve using GreenMail API
        Message[] messages = servers.getReceivedMessages();
        assertEquals(messageCount * 2, messages.length);
        for (int i = 0; i < messageCount * 2; i++)
        {
            tmpResult = Integer.parseInt(servers.util().getBody(messages[i]).replaceAll("Message : ", ""));
            assertTrue(checklist[tmpResult] < 2);
            checklist[tmpResult]++;
        }

        for (int i = 0; i < messageCount; i++)
        {
            assertTrue(checklist[i] == 2);
        }
    }

    // @Override
    protected String getConfigResources()
    {
        return "mule-smtpsmtps-config.xml";
    }

}


package emailtests;

import com.icegreen.greenmail.util.ServerSetupTest;
import com.icegreen.greenmail.util.Servers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

/**
 * These are a collection of tests aimed at testing only GreenMail.
 */
public class GreenMailTestCase extends TestCase
{

    protected Servers servers;
    protected int messageCount = 5;
    protected boolean debugSession = false;

    protected void tearDown() throws Exception
    {
        if (null != servers)
        {
            servers.stop();
        }
        super.tearDown();
    }

    public void testSmtpPlainMail() throws Exception
    {
        // start all email servers using non-default ports.
        servers = new Servers(ServerSetupTest.ALL);
        servers.start();

        // Mail Sending Code
        String from = "stephen.fenech@symphonysoft.com";
        String to = "mule@symphonydoft.com";
        String subject;
        String body;

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "3025");
        props.put("mail.smtp.ssl", "false");
        props.remove("mail.smtp.socketFactory.class");

        // Get session
        Session session = Session.getInstance(props, null);
        session.setDebug(debugSession);

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        for (int i = 1; i <= messageCount; i++)
        {
            subject = servers.util().random();
            body = servers.util().random();

            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            // wait for max 5s for i email to arrive
            assertTrue(servers.waitForIncomingEmail(5000, i));

            // Retrieve using GreenMail API
            Message[] messages = servers.getReceivedMessages();
            assertEquals(i, messages.length);
            assertEquals(subject, messages[i - 1].getSubject());
            assertEquals(body, servers.util().getBody(messages[i - 1]).trim());
        }

    }

    public void testSmtpsPlainMail() throws Exception
    {
        // start all email servers using non-default ports.
        servers = new Servers(ServerSetupTest.SMTPS);
        servers.start();

        servers.setUser("stephen.fenech@symphonysoft.com", "usrName", "password");

        // Mail Sending Code
        String from = "stephen.fenech@symphonysoft.com";
        String to = "mule@symphonysoft.com";
        String subject;
        String body;

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.smtps.host", "localhost");
        props.put("mail.smtps.port", "3465");
        props.put("mail.smtps.auth", "true");
        props.setProperty("mail.smtps.socketFactory.class", "emailtests.others.DummySSLSocketFactory");
        props.setProperty("mail.smtps.socketFactory.fallback", "false");

        // Get session
        Session session = Session.getInstance(props, null);
        session.setDebug(debugSession);

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        for (int i = 1; i <= messageCount; i++)
        {
            subject = servers.util().random();
            body = servers.util().random();

            message.setSubject(subject);
            message.setText(body);

            Transport tr = session.getTransport("smtps");
            tr.connect("localhost", "usrName", "password");
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

            // wait for max 5s for i email to arrive
            assertTrue(servers.waitForIncomingEmail(5000, i));

            // Retrieve using GreenMail API
            Message[] messages = servers.getReceivedMessages();
            assertEquals(i, messages.length);
            assertEquals(subject, messages[i - 1].getSubject());
            assertEquals(body, servers.util().getBody(messages[i - 1]).trim());
        }
    }
}

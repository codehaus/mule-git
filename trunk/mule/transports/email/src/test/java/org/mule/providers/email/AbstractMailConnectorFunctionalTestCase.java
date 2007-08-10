/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email;

import org.mule.tck.providers.AbstractConnectorTestCase;
import org.mule.umo.provider.UMOConnector;

import com.icegreen.greenmail.util.Servers;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

/**
 * Start a (greenmail) mail server with a known message, for use in subclasses.
 * Each test gets a new set of ports to avoid conflicts (shouldn't be needed, but
 * greenmail doesn't seem to be closing ports).  Also contains utility methods
 * for comparing emails, building endpoints, etc.
 */
public abstract class AbstractMailConnectorFunctionalTestCase extends AbstractConnectorTestCase
{

    // ie must succeed within RETRY_LIMIT attempts
    public static final int RETRY_LIMIT = 2;
    public static final String LOCALHOST = "127.0.0.1";
    public static final String USER = AbstractGreenMailSupport.BOB;
    public static final String EMAIL = AbstractGreenMailSupport.BOB_EMAIL;
    public static final String PASSWORD = AbstractGreenMailSupport.PASSWORD;
    
    private boolean initialEmail = false;
    private String connectorName;
    private AbstractGreenMailSupport greenMailSupport = new AutoIncrementGreenMailSupport();
    private MimeMessage message;

    protected AbstractMailConnectorFunctionalTestCase(boolean initialEmail, String connectorName)
    {
        this.initialEmail = initialEmail;
        this.connectorName = connectorName;
    }

    // @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        startServers();
    }

    // @Override
    protected void doTearDown() throws Exception
    {
        stopServers();
        super.doTearDown();
    }

    private void storeEmail() throws Exception
    {
        greenMailSupport.createBobAndStoreEmail(getValidMessage());
    }

    protected void startServers() throws Exception
    {
        greenMailSupport.startServers();
        if (initialEmail)
        {
            storeEmail();
        }
    }

    protected void stopServers() throws Exception
    {
        greenMailSupport.stopServers();
    }

    protected Servers getServers()
    {
        return greenMailSupport.getServers();
    }

    // @Override
    public Object getValidMessage() throws Exception
    {
        if (null == message)
        {
            message = greenMailSupport.getValidMessage(AbstractGreenMailSupport.ALICE_EMAIL);
        }
        return message;
    }
    
    public String getConnectorName() 
    {
        return connectorName;
    }
    
    public UMOConnector getConnector() throws Exception
    {
        return getConnector(true);
    }
    
    public abstract UMOConnector getConnector(boolean init) throws Exception;
        
    protected String getPop3TestEndpointURI()
    {
        return buildEndpoint("pop3", greenMailSupport.getServers().getPop3().getPort());
    }

    protected String getPop3sTestEndpointURI()
    {
        return buildEndpoint("pop3s", greenMailSupport.getServers().getPop3s().getPort());
    }

    protected String getImapTestEndpointURI()
    {
        return buildEndpoint("imap", greenMailSupport.getServers().getImap().getPort());
    }
    
    protected String getImapsTestEndpointURI()
    {
        return buildEndpoint("imaps", greenMailSupport.getServers().getImaps().getPort());
    }
    
    protected String getSmtpTestEndpointURI()
    {
        return buildEndpoint("smtp", greenMailSupport.getServers().getSmtp().getPort());
    }
    
    protected String getSmtpsTestEndpointURI()
    {
        return buildEndpoint("smtps", greenMailSupport.getServers().getSmtps().getPort());
    }
    
   private String buildEndpoint(String protocol, int port) 
    {
        return protocol + "://" + USER + ":" + PASSWORD + "@" + LOCALHOST + ":" + port +
        "?connector=" + connectorName;
    }
    
    protected void assertMessageOk(Object message) throws Exception
    {
        assertTrue("Did not receive a MimeMessage", message instanceof MimeMessage);
        MimeMessage received = (MimeMessage) message;
        // for some reason, something is adding a newline at the end of messages
        // so we need to strip that out for comparison
        assertTrue("Did not receive a message with String contents",
            received.getContent() instanceof String);
        String receivedText = ((String) received.getContent()).trim();
        assertEquals(AbstractGreenMailSupport.MESSAGE, receivedText);
        assertNotNull(received.getRecipients(Message.RecipientType.TO));
        assertEquals(1, received.getRecipients(Message.RecipientType.TO).length);
        assertEquals(received.getRecipients(Message.RecipientType.TO)[0].toString(), EMAIL);
    }

    /**
     * Tests are intermittently failing due to busy ports.  Here we repeat a test a number of times
     * (more than twice should not be necessary!) to make sure that the first failure was not due to
     * an active port.
     *
     * @param method The method name of the test
     * @throws Exception If the failure occurs repeatedly
     */
    protected void repeatTest(String method) throws Exception
    {
        boolean success = false;

        for (int count = 1; !success; ++count)
        {
            try
            {
                getClass().getMethod(method, new Class[0]).invoke(this, new Object[0]);
                success = true;
            }
            catch (Exception e)
            {
                if (count >= RETRY_LIMIT)
                {
                    logger.warn("Test attempt " + count + " for " + method
                            + " failed (will fail): " + e.getMessage());
                    throw e;
                }
                else
                {
                    logger.warn("Test attempt " + count + " for " + method
                            + " failed (will retry): " + e.getMessage());
                    stopServers();
                    startServers();
                }
            }
        }
    }

}

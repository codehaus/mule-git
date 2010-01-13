/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.xmpp;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

import org.jivesoftware.smack.packet.Message;

public class XmppMessageSyncTestCase extends AbstractXmppTestCase
{
    private static final long JABBER_SEND_THREAD_SLEEP_TIME = 1000;
    protected static final String REPLY = "Jabber reply";
    
    @Override
    protected void configureJabberClient(JabberClient client)
    {
        client.setAutoReply(true);
        client.setReplyPayload(REPLY);
    }
    
    @Override
    protected String getXmppConfigResources()
    {
        return "xmpp-message-sync-config.xml";
    }

    public void XXXtestSendSync() throws Exception
    {   
        MuleClient client = new MuleClient(muleContext);
        MuleMessage reply = client.send("vm://in", TEST_MESSAGE, null);
        assertNotNull(reply);
        
        assertEquals(Message.class, reply.getPayload().getClass());
        Message xmppReply = (Message) reply.getPayload();
        assertXmppMessage(xmppReply);
    }

    protected void assertXmppMessage(Message xmppReply)
    {
        assertEquals(Message.Type.normal, xmppReply.getType());
        assertEquals(REPLY, xmppReply.getBody());
    }
    
    public void XXXtestReceiveSync() throws Exception
    {
        sendJabberMessageFromNewThread();
        requestMessageAndAssert("vm://fromJabber");
    }
    
    public void testRequestSync() throws Exception
    {
        sendJabberMessageFromNewThread();
        requestMessageAndAssert("xmpp://MESSAGE/mule2@localhost?synchronous=true");
    }

    private void sendJabberMessageFromNewThread()
    {
        Thread sendThread = new Thread(new SendIt());
        sendThread.setName("JabberClient send");
        sendThread.start();
    }

    private void requestMessageAndAssert(String url) throws MuleException
    {
        MuleClient client = new MuleClient();
        MuleMessage muleMessage = client.request(url, RECEIVE_TIMEOUT);
        assertNotNull(muleMessage);

        Message xmppMessage = (Message) muleMessage.getPayload();
        assertEquals(Message.Type.normal, xmppMessage.getType());
        assertEquals(TEST_MESSAGE, xmppMessage.getBody());
    }

    private class SendIt implements Runnable
    {        
        public void run()
        {
            try
            {
                Thread.sleep(JABBER_SEND_THREAD_SLEEP_TIME);
                jabberClient.sendMessage(muleJabberUserId, TEST_MESSAGE);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Exception while sending", e);
            }
        }
    }
}

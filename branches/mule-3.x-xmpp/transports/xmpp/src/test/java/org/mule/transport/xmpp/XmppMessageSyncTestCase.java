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

import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.util.concurrent.Latch;

import junit.framework.Assert;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

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

    public void testSendSync() throws Exception
    {   
        MuleClient client = new MuleClient(muleContext);
        MuleMessage reply = client.send("vm://in", TEST_MESSAGE, null);
        assertNotNull(reply);
        
        assertEquals(Message.class, reply.getPayload().getClass());
        Message xmppReply = (Message) reply.getPayload();
        assertXmppMessage(xmppReply);
    }
    
    public void testReceiveSync() throws Exception
    {
        Latch receiveLatch = new Latch();
        setupTestServiceComponent(receiveLatch);
        
        sendJabberMessageFromNewThread();
        
        assertTrue(receiveLatch.await(RECEIVE_TIMEOUT, TimeUnit.MILLISECONDS));
    }

    public void testRequestSync() throws Exception
    {
        sendJabberMessageFromNewThread();
        requestMessageAndAssert("xmpp://MESSAGE/mule2@localhost?synchronous=true");
    }

    private void setupTestServiceComponent(Latch receiveLatch) throws Exception
    {
        Object testComponent = getComponent("receiveMessage");
        assertTrue(testComponent instanceof FunctionalTestComponent);
        FunctionalTestComponent component = (FunctionalTestComponent) testComponent;
        
        Callback callback = new Callback(receiveLatch);
        component.setEventCallback(callback);
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

    protected void assertXmppMessage(Message xmppMessage)
    {
        assertJabberMessage(xmppMessage, REPLY);
    }
    
    private static void assertJabberMessage(Message xmppMessage, String payload)
    {
        assertEquals(Message.Type.normal, xmppMessage.getType());
        assertEquals(payload, xmppMessage.getBody());
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
    
    private static class Callback implements EventCallback
    {
        private Latch latch;

        public Callback(Latch latch)
        {
            super();
            this.latch = latch;
        }

        public void eventReceived(MuleEventContext context, Object component) throws Exception
        {
            MuleMessage muleMessage = context.getMessage();
            Object payload = muleMessage.getPayload();
            Assert.assertTrue(payload instanceof Message);
            
            Message xmppMessage = (Message) payload;
            assertJabberMessage(xmppMessage, TEST_MESSAGE);
            
            latch.countDown();
        }
    }
}

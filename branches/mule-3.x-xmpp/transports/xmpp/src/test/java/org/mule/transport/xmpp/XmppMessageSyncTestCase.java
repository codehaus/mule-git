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
import org.mule.api.MuleMessage;
import org.mule.api.service.Service;
import org.mule.module.client.MuleClient;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.util.concurrent.Latch;

import junit.framework.Assert;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.packet.Message;

public class XmppMessageSyncTestCase extends AbstractXmppTestCase
{
    protected static final long JABBER_SEND_THREAD_SLEEP_TIME = 1000;
    protected static final String REPLY = "Jabber reply";
    private static final String RECEIVE_SERVICE_NAME = "receiveFromJabber";
    
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
        assertXmppReply(xmppReply);
    }
    
    public void testReceiveSync() throws Exception
    {
        startReceiverService();
        
        Latch receiveLatch = new Latch();
        setupTestServiceComponent(receiveLatch);
        
        sendJabberMessageFromNewThread();
        
        assertTrue(receiveLatch.await(RECEIVE_TIMEOUT, TimeUnit.MILLISECONDS));
    }

    private void startReceiverService() throws Exception
    {
        Service service = muleContext.getRegistry().lookupService(RECEIVE_SERVICE_NAME);
        assertNotNull(service);
        
        service.start();
    }

    private void setupTestServiceComponent(Latch receiveLatch) throws Exception
    {   
        Object testComponent = getComponent(RECEIVE_SERVICE_NAME);
        assertTrue(testComponent instanceof FunctionalTestComponent);
        FunctionalTestComponent component = (FunctionalTestComponent) testComponent;
        
        Callback callback = new Callback(receiveLatch, expectedXmppMessageType());
        component.setEventCallback(callback);
    }

    public void testRequestSync() throws Exception
    {
        doTestRequest("xmpp://MESSAGE/mule2@localhost?synchronous=true");
    }
    
    protected void doTestRequest(String url) throws Exception
    {
        sendJabberMessageFromNewThread();

        MuleClient client = new MuleClient();
        MuleMessage muleMessage = client.request(url, RECEIVE_TIMEOUT);
        assertNotNull(muleMessage);

        Message xmppMessage = (Message) muleMessage.getPayload();
        assertEquals(expectedXmppMessageType(), xmppMessage.getType());
        assertEquals(TEST_MESSAGE, xmppMessage.getBody());
    }

    protected Message.Type expectedXmppMessageType()
    {
        return Message.Type.normal;
    }

    protected void sendJabberMessageFromNewThread()
    {
        Thread sendThread = new Thread(new SendIt());
        sendThread.setName("JabberClient send");
        sendThread.start();
    }

    private void assertXmppReply(Message xmppMessage)
    {
        assertEquals(expectedXmppMessageType(), xmppMessage.getType());
        assertEquals(REPLY, xmppMessage.getBody());
    }
        
    private class SendIt extends RunnableWithExceptionHandler
    {        
        @Override
        protected void doRun() throws Exception
        {
            Thread.sleep(JABBER_SEND_THREAD_SLEEP_TIME);
            jabberClient.sendMessage(muleJabberUserId, TEST_MESSAGE);
        }
    }
    
    private static class Callback implements EventCallback
    {
        private Latch latch;
        private Message.Type expectedMessageType;

        public Callback(Latch latch, Message.Type type)
        {
            super();
            this.latch = latch;
            this.expectedMessageType = type;
        }

        public void eventReceived(MuleEventContext context, Object component) throws Exception
        {
            MuleMessage muleMessage = context.getMessage();
            Object payload = muleMessage.getPayload();
            Assert.assertTrue(payload instanceof Message);
            
            Message xmppMessage = (Message) payload;
            assertEquals(expectedMessageType, xmppMessage.getType());
            assertEquals(TEST_MESSAGE, xmppMessage.getBody());
            
            latch.countDown();
        }
    }
}

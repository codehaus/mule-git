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

import org.mule.module.client.MuleClient;

import java.util.List;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.packet.Message;


public class XmppMessageAsyncTestCase extends AbstractXmppTestCase
{
    private CountDownLatch latch = new CountDownLatch(1);
    
    @Override
    protected String getConfigResources()
    {
        return "xmpp-connector-config.xml, xmpp-message-async-config.xml";
    }

    @Override
    protected void configureJabberClient(JabberClient jabberClient)
    {
        jabberClient.setSynchronous(false);
        jabberClient.setMessageLatch(latch);
    }

    public void testDispatch() throws Exception
    {
        MuleClient client = new MuleClient();
        client.dispatch("vm://in", TEST_MESSAGE, null);
        
        assertTrue(latch.await(RECEIVE_TIMEOUT, TimeUnit.MILLISECONDS));
        
        List<Message> receivedMessages = jabberClient.getReceivedMessages();
        assertEquals(1, receivedMessages.size());

        Message message = receivedMessages.get(0);
        assertXmppMessage(message);
    }

    protected void assertXmppMessage(Message message)
    {
        assertEquals(Message.Type.normal, message.getType());
        assertEquals(TEST_MESSAGE, message.getBody());
    }
}

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

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

import org.jivesoftware.smack.packet.Message;

public class XmppMessageSyncTestCase extends AbstractXmppTestCase
{
    protected static final String REPLY = "Jabber reply";
    
    @Override
    protected void configureJabberClient(JabberClient jabberClient)
    {
        jabberClient.setAutoReply(true);
        jabberClient.setReplyPayload(REPLY);
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

    protected void assertXmppMessage(Message xmppReply)
    {
        assertEquals(Message.Type.normal, xmppReply.getType());
        assertEquals(REPLY, xmppReply.getBody());
    }
    
    public void testReceiveSync() throws Exception
    {
        jabberClient.sendMessage(recipient, TEST_MESSAGE);
        
        MuleClient client = new MuleClient();
        MuleMessage muleMessage = client.request("vm://fromJabber", RECEIVE_TIMEOUT);
        assertNotNull(muleMessage);
        
        Message xmppMessage = (Message) muleMessage.getPayload();
        assertEquals(Message.Type.normal, xmppMessage.getType());
        assertEquals(TEST_MESSAGE, xmppMessage.getBody());
    }
}

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
import org.mule.transport.NullPayload;
import org.mule.util.UUID;

import java.util.Properties;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class XmppMucSyncTestCase extends AbstractXmppTestCase
{
    private static final long SHORT_RETRIEVE_TIMEOUT = 100;
    
    @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();

        Properties properties = (Properties) muleContext.getRegistry().lookupObject("properties");
        String chatroom = properties.getProperty("chatroom");
        assertNotNull(chatroom);
        
        jabberClient.joinGroupchat(chatroom);
    }

    @Override
    protected String getXmppConfigResources()
    {
        return "xmpp-muc-sync-config.xml";
    }

    public void testSendSync() throws Exception
    {   
        String input = UUID.getUUID().toString();
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage reply = client.send("vm://in", input, null);
        assertNotNull(reply);
        
        assertEquals(NullPayload.getInstance(), reply.getPayload());
        
        Packet packet = jabberClient.receive(RECEIVE_TIMEOUT);
        // The groupchat may have a backlog of messages whis is sent before our input is transmitted.
        // Poll the entire groupchat history
        boolean inputSeen = false;
        packet = jabberClient.receive(SHORT_RETRIEVE_TIMEOUT);
        while (packet != null)
        {
            String payload = ((Message) packet).getBody();
            if (payload.equals(input))
            {
                inputSeen = true;
                break;
            }

            packet = jabberClient.receive(SHORT_RETRIEVE_TIMEOUT);
        }
        assertTrue(inputSeen);
    }
}

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

public class XmppMucSyncTestCase extends AbstractXmppTestCase
{
    @Override
    protected void configureJabberClient(JabberClient client)
    {
        // TODO xmpp: this method should not be abstract
    }

    @Override
    protected String getXmppConfigResources()
    {
        return "xmpp-muc-sync-config.xml";
    }

    public void testSendSync() throws Exception
    {   
        MuleClient client = new MuleClient(muleContext);
        MuleMessage reply = client.send("vm://in", TEST_MESSAGE, null);
        assertNotNull(reply);
        
        assertEquals(NullPayload.getInstance(), reply.getPayload());
        
//        Packet packet = jabberClient.receive(RECEIVE_TIMEOUT);
//        // the first packet may be an error presence from the chat ... retry in this case
//        if (packet instanceof Presence)
//        {
//            packet = jabberClient.receive(RECEIVE_TIMEOUT);
//        }
//        assertReceivedPacketEqualsMessageSent(packet);
    }

}

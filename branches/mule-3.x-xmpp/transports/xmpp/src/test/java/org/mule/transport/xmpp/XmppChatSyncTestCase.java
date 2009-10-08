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

import org.jivesoftware.smack.packet.Message;

public class XmppChatSyncTestCase extends XmppMessageSyncTestCase
{
    @Override
    protected String getXmppConfigResources()
    {
        return "xmpp-chat-sync-config.xml";
    }

    @Override
    protected void assertXmppMessage(Message xmppReply)
    {
        assertEquals(Message.Type.chat, xmppReply.getType());
        assertEquals(REPLY, xmppReply.getBody());
    }
}

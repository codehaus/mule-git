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

public class XmppChatAsyncTestCase extends XmppMessageAsyncTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "xmpp-connector-config.xml, xmpp-chat-async-config.xml";
    }
    
    @Override
    protected void assertXmppMessage(Message message)
    {
        assertEquals(Message.Type.chat, message.getType());
        assertEquals(TEST_MESSAGE, message.getBody());
    }
}

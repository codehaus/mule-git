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

import org.mule.api.endpoint.ImmutableEndpoint;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromMatchesFilter;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;

/**
 * {@link XmppConversation} implementation that sends messages via {@link Chat}
 */
public class XmppChatConversation extends AbstractXmppConversation implements MessageListener
{
    private Chat chat;
    
    public XmppChatConversation(ImmutableEndpoint endpoint)
    {
        super(endpoint);
    }

    @Override
    protected void doConnect()
    {
        chat = connection.getChatManager().createChat(recipient, this);
    }

    @Override
    protected PacketFilter createPacketFilter()
    {
        PacketFilter recipientFilter = new FromMatchesFilter(recipient);
        PacketFilter messageTypeFilter = new MessageTypeFilter(Message.Type.chat);
        return new AndFilter(recipientFilter, messageTypeFilter);
    }

    @Override
    protected void doDisconnect()
    {
        chat = null;
    }

    public void dispatch(Message message) throws XMPPException
    {
        message.setType(Message.Type.chat);
        chat.sendMessage(message);
    }

    public void processMessage(Chat chat, Message message)
    {
        // TODO xmpp: work out how to listen for chat messages
    }
}

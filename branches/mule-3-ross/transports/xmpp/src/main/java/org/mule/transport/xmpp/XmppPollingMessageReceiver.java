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

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.api.transport.MessageAdapter;
import org.mule.transport.AbstractPollingMessageReceiver;

import org.jivesoftware.smack.packet.Message;

public class XmppPollingMessageReceiver extends AbstractPollingMessageReceiver
{
    private final XmppConnector connector;
    private XmppConversation conversation;

    public XmppPollingMessageReceiver(Connector conn, Service service, InboundEndpoint endpoint)
        throws CreateException
    {
        super(conn, service, endpoint);
        connector = (XmppConnector) conn;
        conversation = connector.getConversationFactory().create(endpoint);
    }

    @Override
    protected void doConnect() throws Exception
    {
        conversation.connect();
    }

    @Override
    protected void doDisconnect() throws Exception
    {
        conversation.disconnect();
    }

    @Override
    protected void doDispose()
    {
        conversation = null;
    }

    @Override
    public void poll() throws Exception
    {
        // Wait 10% less than the polling frequency. This approach makes sure that we finish 
        // in time before the next poll call comes in
        long frequency = getFrequency();
        long tenPercent = (long)(frequency * 0.1);
        long pollTimeout = frequency - tenPercent;
        
        Message xmppMessage = conversation.receive(pollTimeout);
        if (xmppMessage == null)
        {
            return;
        }
        
        processMessage(xmppMessage);
    }

    protected void processMessage(Message xmppMessage) throws MuleException
    {
        MessageAdapter messageAdapter = connector.getMessageAdapter(xmppMessage);
        MuleMessage muleMessage = new DefaultMuleMessage(messageAdapter, connector.getMuleContext());
        
        routeMessage(muleMessage, endpoint.isSynchronous());
    }
}    

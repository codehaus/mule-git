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

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;


public abstract class AbstractXmppConversation implements XmppConversation
{
    protected XMPPConnection connection;
    protected String recipient;
    private boolean isSynchronous;
    protected PacketCollector packetCollector;

    public AbstractXmppConversation(ImmutableEndpoint endpoint)
    {
        super();        
        connection = ((XmppConnector) endpoint.getConnector()).getXmppConnection();
        recipient = XmppConnector.getRecipient(endpoint);
        isSynchronous = endpoint.isSynchronous();
    }

    public void connect()
    {
        doConnect();

        if (isSynchronous)
        {
            // create the packet filter that's used to retrieve sync responses
            PacketFilter filter = createPacketFilter();
            packetCollector = connection.createPacketCollector(filter);
        }
        else
        {
            // TODO xmpp: implement me
        }
    }
    
    /**
     * Subclasses can override this method to create their conversation specific connection.
     */
    protected void doConnect()
    {
        // template method
    }

    /**
     * @return a {@link PacketFilter} instance that matches the desired message type and recipient
     * for this conversation.
     */
    protected abstract PacketFilter createPacketFilter();
    
    public void disconnect()
    {
        if (packetCollector != null)
        {
            packetCollector.cancel();
        }
        
        doDisconnect();
    }

    /**
     * Subclasses can override this method to perform custom disconnect actions.
     */
    protected void doDisconnect()
    {
        // template method
    }
    
    public Message receive(long timeout)
    {
        // The filter of our packetCollector should make sure that we receive only
        // Message instances here
        return (Message) packetCollector.nextResult(timeout);
    }
    
    public Message receive()
    {
        // The filter of our packetCollector should make sure that we receive only
        // Message instances here
        return (Message) packetCollector.nextResult();
    }
}

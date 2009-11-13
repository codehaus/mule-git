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

import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class JabberClient implements PacketListener
{
    private static Log logger = LogFactory.getLog(JabberClient.class);
    
    private String host;
    private String user;
    private String password;
    private boolean synchronous = true;
    private String replyPayload = "Reply";
    private boolean autoreply = false;

    private XMPPConnection connection;
    private List<Message> replies;
    private PacketCollector packetFilter = null;
    private CountDownLatch messageLatch = null;

    public JabberClient(String host, String user, String password) throws Exception
    {
        super();
        this.host = host;
        
        int index = user.indexOf("@");
        if (index > -1)
        {
            this.user = user.substring(0, index);
        }
        else
        {
            this.user = user;
        }
        
        this.password = password;        

        replies = new ArrayList<Message>();
    }

    public void connect(CountDownLatch latch) throws Exception
    {
        ConnectionConfiguration connectionConfig = new ConnectionConfiguration(host);
        // no roster required
        connectionConfig.setRosterLoadedAtLogin(false);
        
        connection = new XMPPConnection(connectionConfig);
        connection.connect();
        if (logger.isDebugEnabled())
        {
            logger.debug("connected to " + host);
        }
        
        connection.login(user, password);
        if (logger.isDebugEnabled())
        {
            logger.debug("logged into " + host + " as " + user);
        }

        registerListener();
        
        // notify the caller that we're finished connecting
        latch.countDown();
    }

    private void registerListener()
    {
        PacketFilter normalTypeFilter = new MessageTypeFilter(Message.Type.normal);
        PacketFilter chatTypeFilter = new MessageTypeFilter(Message.Type.chat);
        PacketFilter filter = new OrFilter(normalTypeFilter, chatTypeFilter);

        if (synchronous)
        {
            packetFilter = connection.createPacketCollector(filter);
        }
        else
        {
            connection.addPacketListener(this, filter);
        }
    }

    public void disconnect()
    {
        connection.removePacketListener(this);
        
        if (packetFilter != null)
        {
            packetFilter.cancel();
        }
        
        connection.disconnect();
    }

    //
    // Jabber packet listener
    //
    public void processPacket(Packet packet)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("received " + packet);
        }
        
        // our filters make sure that we ever only see Message instances
        replies.add((Message) packet);
        
        countDownMessageLatch();        
        sendAutoreply(packet);
    }

    private void countDownMessageLatch()
    {
        if (messageLatch != null)
        {
            messageLatch.countDown();
        }
    }

    private void sendAutoreply(Packet packet)
    {
        if (autoreply)
        {
            Message incomingMessage = (Message) packet;
            
            Message message = new Message();
            message.setType(incomingMessage.getType());
            message.setTo(incomingMessage.getFrom());
            message.setBody(replyPayload);
            
            connection.sendPacket(message);
            
            if (logger.isDebugEnabled())
            {
                logger.debug("sent autoreply message with payload: \"" + replyPayload + "\"");
            }
        }
    }

    public List<Message> getReceivedMessages()
    {
        return replies;
    }

    //
    // setters for config parameters
    // 
    public void setReplyPayload(String reply)
    {
        replyPayload = reply;
    }

    public void setAutoReply(boolean flag)
    {
        autoreply = flag;
        // autoreply only works in an async mode
        synchronous = false;
    }
    
    public void setSynchronous(boolean flag)
    {
        synchronous = flag;
    }

    public void setMessageLatch(CountDownLatch latch)
    {
        messageLatch = latch;
    }

    public void sendMessage(String recipient, String payload)
    {
        Message message = new Message();
        message.setType(Message.Type.normal);
        message.setTo(recipient);
        message.setBody(payload);
        
        connection.sendPacket(message);
    }
}

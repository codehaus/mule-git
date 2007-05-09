package org.mule.providers.jgroups;

import java.io.Serializable;
import java.util.Map;

import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.providers.jgroups.adapters.JGroupsAdapter;
import org.mule.providers.jgroups.adapters.MuleMessageDispatcherAdapter;
import org.mule.providers.jgroups.listeners.MuleReceiverMembershipListener;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.util.StringUtils;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.blocks.GroupRequest;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.stack.IpAddress;
import org.jgroups.util.Rsp;
import org.jgroups.util.RspList;

public class JGroupsMessageDispatcher extends AbstractMessageDispatcher 
{
    public static String PROPERTY_DEST_ADDRESS = "destAddress";
    public static String PROPERTY_DEST_PORT = "destPort";
    private String groupName = null;
    private JChannel channel = null;
    private int[] state = null;
    private final JGroupsConnector connector;
    private JGroupsAdapter adapter;
    private Address destAddress = null;
    private Address preferredDestAddress = null;
    private boolean sendToAll = true;

    public JGroupsMessageDispatcher(UMOImmutableEndpoint endpoint) throws InitialisationException
    {
        super(endpoint);
        this.connector = (JGroupsConnector)endpoint.getConnector();
        this.groupName = endpoint.getEndpointURI().getAddress();

        try
        {
            logger.debug("Creating channel");
            this.channel = this.connector.createChannel(groupName);
            this.adapter = new MuleMessageDispatcherAdapter(channel, 
                    null, null, null);
        }
        catch (Exception e)
        {
            throw new InitialisationException(e, this);
        }

        Map props = endpoint.getProperties();
        if (props != null)
        {
            String addr = (String)props.get(PROPERTY_DEST_ADDRESS);
            String p = (String)props.get(PROPERTY_DEST_PORT);

            if (StringUtils.isNotBlank(addr))
            {
                String strPort = null;

                if (addr.indexOf(":") > -1)
                {
                    int pos = addr.indexOf(":");
                    addr = addr.substring(0);
                    strPort = addr.substring(pos+1);
                }
                else if (StringUtils.isNotBlank(p))
                {
                    strPort = p;
                }

                try
                {
                    int port = Integer.parseInt(strPort);
                    destAddress = new IpAddress(addr, port);
                    sendToAll = false;
                }
                catch (NumberFormatException nfe)
                {
                    throw new InitialisationException(new Exception("Unable to set the destination address of " + addr + ", " + strPort + ": " + nfe.toString()), this);
                }
            }
        }
    }

    protected void doDispatch(UMOEvent event) throws Exception
    {
        byte[] data = event.getTransformedMessageAsBytes();
        Message out = new Message(destAddress, channel.getLocalAddress(), data);
        channel.send(out);
    }

    protected UMOMessage doReceive(long timeout) throws Exception
    {
        Object resp = channel.receive(timeout);
        MuleMessage message = new MuleMessage(resp);
        return message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.provider.UMOConnectorSession#send(org.mule.umo.UMOEvent)
     */
    protected UMOMessage doSend(UMOEvent event) throws Exception
    {
        byte[] data = event.getTransformedMessageAsBytes();
        // A hack for now, until we have more use cases to genericise this
        MessageDispatcher dispatcher = (MessageDispatcher)adapter;

        boolean usePreferred = false;
        int mode = GroupRequest.GET_ALL;
        Address tmpDest = destAddress;

        if (tmpDest == null) 
        {
            if (preferredDestAddress != null) 
            {
                tmpDest = preferredDestAddress;
                usePreferred = true;
                logger.debug("Sending preferred member " + tmpDest.toString());
            }
        }

        Message out = new Message(tmpDest, channel.getLocalAddress(), data);
        UMOMessage message = new MuleMessage(null);
        Object ret = null;
        Address actualMember = null;

        logger.debug("Current view is " + channel.getView().toString());

        // If we are sending to one member, use this method
        if (tmpDest != null)
        {
            logger.debug("Sending to a single destination");

            // Let's make sure the destination is in the view
            if (channel.getView().containsMember(tmpDest))
            {
                try
                {
                    ret = dispatcher.sendMessage(out, GroupRequest.GET_FIRST, 0L);
                }
                catch (Exception e)
                {
                    // This means the destination is no longer available
                }
            }

            if (ret != null)
            {
                actualMember = tmpDest;
            }

            if (usePreferred && ret == null)
            {
                // Oops, our preferred member isn't working now
                logger.debug("Preferred member " + 
                    preferredDestAddress.toString() + " is no longer available");
                out.setDest(null);
                preferredDestAddress = null;
            }
        }

        if (tmpDest == null || preferredDestAddress == null)
        {
            logger.debug("Sending to the group");
            RspList rsps = dispatcher.castMessage(null, out, mode, 0L);

            logger.info("rsps size is "+ rsps.size());
            for (int i = 0; i < rsps.size(); i++)
            {
                Rsp rsp = (Rsp)rsps.elementAt(i);
                Object o = rsp.getValue();

                if (o != null)
                {
                    logger.info("Received " + o.toString() + " from " + 
                        rsp.getSender().toString());

                    ret = o;
                    actualMember = (Address)rsp.getSender();
                    preferredDestAddress = (Address)rsp.getSender();
                    logger.debug("Our preferred member is " +
                        preferredDestAddress.toString());
                    break;
                }
                else
                {
                    logger.info("Received nothing from " + rsp.getSender().toString());
                }
            }
        }

        if (ret != null)
        {
            if (ret instanceof UMOMessage)
            {
               message = (UMOMessage)ret;
            }
            else
            {
               message = new MuleMessage(ret);
            }

            message.setStringProperty("JGROUP_MEMBER_SOURCE", 
                actualMember.toString());
        }

        return message;
    }

    protected void doDispose()
    {
        this.adapter.dispose();
    }

    protected void doConnect() throws Exception
    {
        logger.debug("Connecting to " + groupName);
        channel.connect(groupName);
        this.adapter.start();
    }

    protected void doDisconnect() throws Exception
    {
        logger.debug("Disconnecting from " + groupName);
        this.adapter.stop();
        channel.disconnect();
        channel.close();
    }

}

package org.mule.providers.jgroups;

import java.io.Serializable;
import java.util.Map;

import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
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
    private MessageDispatcher dispatcher;
    private Address destAddress = null;

    public JGroupsMessageDispatcher(UMOImmutableEndpoint endpoint) throws InitialisationException
    {
        super(endpoint);
        this.connector = (JGroupsConnector)endpoint.getConnector();
        this.groupName = endpoint.getEndpointURI().getAddress();

        try
        {
            logger.debug("Creating channel");
            this.channel = this.connector.createChannel(groupName);
            this.dispatcher = new MessageDispatcher(channel, null, null, null);
            this.dispatcher.start();
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
        Message out = new Message(destAddress, channel.getLocalAddress(), data);
        RspList rsps = dispatcher.castMessage(null, out, 
                GroupRequest.GET_ALL, 0L);

        MuleMessage message = new MuleMessage(null);

        logger.info("rsps size is "+ rsps.size());
        for (int i = 0; i < rsps.size(); i++)
        {
            Rsp rsp = (Rsp)rsps.elementAt(i);
            Object o = rsp.getValue();
            logger.info("Received " + rsp.toString());

            if (o != null)
            {
                logger.info("Received " + o.toString() + " from " + rsp.getSender().toString());
                if (i == 0) 
                {
                    message = new MuleMessage(o);
                    message.setStringProperty("messageSource", 
                            rsp.getSender().toString());
                    break;
                }
            }
            else
            {
                logger.info("Received nothing from " + rsp.getSender().toString());
            }
        }

        return message;
    }

    protected void doDispose()
    {
        // no op
    }

    protected void doConnect() throws Exception
    {
        logger.debug("Connecting to " + groupName);
        channel.connect(groupName);
    }

    protected void doDisconnect() throws Exception
    {
        logger.debug("Disconnecting from " + groupName);
        channel.disconnect();
        channel.close();
    }

}

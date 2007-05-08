package org.mule.providers.jgroups;

import org.mule.MuleException;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.View;
import org.jgroups.blocks.PullPushAdapter;
import org.jgroups.util.Util;

public class JGroupsMessageReceiver extends AbstractMessageReceiver implements MembershipListener, MessageListener
{
    private String groupName = "mule_group";
    private JChannel channel = null;
    private PullPushAdapter adapter = null;
    private int[] state = null;
    private final JGroupsConnector connector;

    public JGroupsMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint) throws InitialisationException
    {
        super(connector, component, endpoint);
        this.connector = (JGroupsConnector)connector;
        this.groupName = endpoint.getEndpointURI().getAddress();
        this.channel = this.connector.createChannel(groupName);

        if (channel == null)
            throw new InitialisationException(new Exception("Unable to create a channel for group " + groupName), this);
    }

    protected void doStart() throws UMOException
    {
        try {
            logger.debug("Connecting to " + groupName);
            channel.connect(groupName);
            // For now, no MembershipListener
            adapter = new PullPushAdapter(channel, this, null);

            if (!channel.getState(null,2000)) {
                state = new int[3];
                state[0] = 1; state[1] = 2; state[2] = 3;
            }
        
            if( channel.getLocalAddress() != null)
                logger.debug("Local address is " + channel.getLocalAddress());

        } catch (Exception e) {
            logger.error(e);
            throw new MuleException(e);
        }
    }

    protected void doStop() throws UMOException
    {
        if (channel != null) {
            logger.debug("Stopping channel for group " + groupName);
            channel.disconnect();
            channel.close();
        }
    }

    protected void doConnect() throws Exception
    {
    }

    protected void doDisconnect() throws Exception
    {
        // template method
    }

    protected void doDispose()
    {
        // nothing to do               
    }

    public void viewAccepted(View inView) {
        logger.debug("View: " + inView.toString());
    }

    public void suspect(Address suspected_mbr) {
        logger.debug("Suspected address: " + suspected_mbr);
    }

    public void block() {
        logger.debug("Block called");
    }

    public byte[] getState() {
        logger.debug("getState has been called");
        try {
            return Util.objectToByteBuffer(state);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public void receive(org.jgroups.Message msg ) {
        try
        {
            routeMessage(new MuleMessage(msg.getBuffer()));
        } 
        catch (Exception e)
        {
            handleException(e);
        }
    }

    public void setState(byte[] state) {
        Object newState = null;
        try {
            newState = Util.objectFromByteBuffer(state);
        } catch (Exception e) {
            logger.error(e);
            return;
        }

        if (newState != null) this.state = (int[])newState;

        if (state != null)
            logger.debug("My state is " + (new String(state)));
        else
            logger.debug("My state is null");
    }

}

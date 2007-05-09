package org.mule.providers.jgroups;

import org.mule.MuleException;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.providers.jgroups.adapters.JGroupsAdapter;
import org.mule.providers.jgroups.adapters.MuleMessageDispatcherAdapter;
import org.mule.providers.jgroups.adapters.MulePullPushAdapter;
import org.mule.providers.jgroups.listeners.MuleReceiverMessageListener;
import org.mule.providers.jgroups.listeners.MuleReceiverRequestHandler;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.util.Util;

public class JGroupsMessageReceiver extends AbstractMessageReceiver 
{
    private String groupName = "mule_group";
    private JChannel channel = null;
    private JGroupsAdapter adapter = null;
    private final JGroupsConnector connector;

    public JGroupsMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint) throws InitialisationException
    {
        super(connector, component, endpoint);
        this.connector = (JGroupsConnector)connector;
        this.groupName = endpoint.getEndpointURI().getAddress();
        logger.debug("Creating channel");
        this.channel = this.connector.createChannel(groupName);

        if (channel == null)
            throw new InitialisationException(new Exception("Unable to create a channel for group " + groupName), this);
    }

    protected void doStart() throws UMOException
    {
        try {
            if (endpoint.isSynchronous())
            {
                logger.debug("Endpoint is synchronous: creating MessageDispatcher");
                this.adapter = new MuleMessageDispatcherAdapter(channel, 
                        null, null, new MuleReceiverRequestHandler(this));
            }
            else
            {
                logger.debug("Endpoint is asynchronous: creating PullPushAdapter");
                // For now, no MembershipListener
                this.adapter = new MulePullPushAdapter(channel, 
                        new MuleReceiverMessageListener(this, null), null);
            }

            logger.debug("Connecting to " + groupName);
            channel.connect(groupName);
            int[] state = null;

            if (!channel.getState(null,2000)) {
                state = new int[3];
                state[0] = 1; state[1] = 2; state[2] = 3;
            }
        
            if( channel.getLocalAddress() != null)
                logger.debug("Local address is " + channel.getLocalAddress());

            this.adapter.start();
        } catch (Exception e) {
            logger.error(e);
            throw new MuleException(e);
        }
    }

    protected void doStop() throws UMOException
    {
        if (adapter != null)
        {
            this.adapter.stop();
        }

        if (channel != null) 
        {
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
        if (adapter != null)
        {
            this.adapter.dispose();
        }

    }

}

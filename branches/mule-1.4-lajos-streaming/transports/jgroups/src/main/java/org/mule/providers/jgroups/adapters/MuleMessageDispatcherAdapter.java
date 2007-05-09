package org.mule.providers.jgroups.adapters;

import org.mule.umo.UMOException;

import org.jgroups.Channel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;

public class MuleMessageDispatcherAdapter extends MessageDispatcher implements JGroupsAdapter
{
    public MuleMessageDispatcherAdapter(Channel channel, MessageListener l, MembershipListener ml, RequestHandler rh)
    {
        super(channel, l, ml, rh);
    }

    public void start()
    {
        super.start();
    }

    public void stop()
    {
        super.stop();
    }

    public void dispose()
    {
    }
}


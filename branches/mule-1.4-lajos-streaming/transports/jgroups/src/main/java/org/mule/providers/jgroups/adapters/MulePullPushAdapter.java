package org.mule.providers.jgroups.adapters;

import org.mule.umo.UMOException;
import org.mule.umo.lifecycle.Startable;
import org.mule.umo.lifecycle.Stoppable;

import org.jgroups.Channel;
import org.jgroups.MembershipListener;
import org.jgroups.MessageListener;
import org.jgroups.blocks.PullPushAdapter;

public class MulePullPushAdapter extends PullPushAdapter implements JGroupsAdapter
{
    public MulePullPushAdapter(Channel channel, MessageListener l, MembershipListener ml)
    {
        super(channel, l, ml);
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

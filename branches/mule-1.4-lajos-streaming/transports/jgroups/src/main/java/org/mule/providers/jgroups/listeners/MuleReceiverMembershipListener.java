package org.mule.providers.jgroups.listeners;

import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.providers.AbstractMessageReceiver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Address;
import org.jgroups.MembershipListener;
import org.jgroups.Message;
import org.jgroups.View;
import org.jgroups.util.Util;

public class MuleReceiverMembershipListener implements MembershipListener
{
    private AbstractMessageReceiver receiver;
    private int[] state = null;
    private static Log logger = LogFactory.getLog(MuleReceiverMembershipListener.class);
    private View view = null;

    public MuleReceiverMembershipListener(AbstractMessageReceiver receiver, int[] state)
    {
        this.receiver = receiver;
        this.state = state;
    }

    public MuleReceiverMembershipListener(AbstractMessageDispatcher dispatcher, int[] state)
    {
        this.receiver = receiver;
        this.state = state;
    }

    public void viewAccepted(View inView) {
        logger.debug("View: " + inView.toString());
        this.view = inView;
    }

    public void suspect(Address suspected_mbr) {
        logger.debug("Suspected address: " + suspected_mbr);
    }

    public void block() {
        logger.debug("Block called");
    }

    public boolean isInView(Address dest)
    {
        return view.containsMember(dest);
    }
}

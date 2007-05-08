package org.mule.providers.jgroups.listeners;

import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Message;
import org.jgroups.MessageListener;
import org.jgroups.util.Util;

public class MuleReceiverMessageListener implements MessageListener
{
    private AbstractMessageReceiver receiver;
    private int[] state = null;
    private static Log logger = LogFactory.getLog(MuleReceiverMessageListener.class);

    public MuleReceiverMessageListener(AbstractMessageReceiver receiver, int[] state)
    {
        this.receiver = receiver;
        this.state = state;
    }

    public void receive(org.jgroups.Message msg ) {
        try
        {
            receiver.routeMessage(new MuleMessage(msg.getBuffer()));
        } 
        catch (Exception e)
        {
            receiver.handleException(e);
        }
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

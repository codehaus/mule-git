package org.mule.providers.jgroups.listeners;

import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.umo.UMOMessage;

import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;

public class MuleReceiverRequestHandler implements RequestHandler
{
    private AbstractMessageReceiver receiver;

    public MuleReceiverRequestHandler(AbstractMessageReceiver receiver)
    {
        this.receiver = receiver;
    }

    public Object handle(Message msg)
    {
        try
        {
            UMOMessage resp = 
                receiver.routeMessage(new MuleMessage(msg.getBuffer()));
            return resp.getPayloadAsBytes();
        }        
        catch (Exception e)
        {
            receiver.handleException(e);
        }

        return null;
    }
}



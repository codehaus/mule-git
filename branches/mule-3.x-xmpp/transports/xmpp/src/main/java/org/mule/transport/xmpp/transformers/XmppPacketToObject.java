/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.xmpp.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

import org.jivesoftware.smack.packet.Message;

public class XmppPacketToObject extends AbstractMessageAwareTransformer
{

    public XmppPacketToObject()
    {
        registerSourceType(Message.class);
        setReturnClass(String.class);
    }

    public Object transform(MuleMessage message, String outputEncoding) throws TransformerException
    {
        Message xmppMessage = (Message) message.getPayload();

        for (String name : xmppMessage.getPropertyNames())
        {
            message.setProperty(name, xmppMessage.getProperty(name));
        }

        return xmppMessage.getBody();
    }

}

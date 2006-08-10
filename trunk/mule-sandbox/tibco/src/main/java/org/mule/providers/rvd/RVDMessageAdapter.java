/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.rvd;

import org.mule.providers.AbstractMessageAdapter;
import org.mule.umo.MessagingException;
import org.mule.umo.provider.MessageTypeNotSupportedException;

import com.tibco.tibrv.TibrvMsg;


/**
 * Gives a uniform view of incomming RVD messages.  The Adapter expects a
 * TibrvMsg.
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revision: 1.1 $
 */
public class RVDMessageAdapter extends AbstractMessageAdapter
{
    private TibrvMsg message = null;

    /**
     * @throws MessageTypeNotSupportedException if message is not a TibrvMsg
     */
    public RVDMessageAdapter( Object message ) throws MessagingException
    {
        if( message instanceof TibrvMsg )
            this.message = (TibrvMsg) message;
        else
            throw new MessageTypeNotSupportedException( message, getClass() );
    }

    /**
     * whole message as string
     */
    public String getPayloadAsString(String encoding) throws Exception
    {
        if (encoding != null &&
            encoding.equals(message.getStringEncoding()) == false) {
            logger.warn("Message encoding is " + message.getStringEncoding() +
                        " but Mule's encoding is set to " + encoding);
        }
        return message.toString();
    }

    /**
     * whole message as bytes
     */
    public byte[] getPayloadAsBytes() throws Exception
    {
        return message.getAsBytes();
    }

    /**
     * @return the current message
     */
    public Object getPayload()
    {
        return message;
    }
}

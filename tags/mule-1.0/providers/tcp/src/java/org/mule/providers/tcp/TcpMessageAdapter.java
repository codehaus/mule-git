/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.tcp;

import org.mule.providers.AbstractMessageAdapter;
import org.mule.umo.MessagingException;
import org.mule.umo.provider.MessageTypeNotSupportedException;
/**
 * <code>TcpMessageAdapter</code> TODO
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */

public class TcpMessageAdapter extends AbstractMessageAdapter
{
    private byte[] message;

    public TcpMessageAdapter(Object message) throws MessagingException {
        if(message instanceof byte[]) {
            this.message = (byte[])message;
        } else{
            throw new MessageTypeNotSupportedException(message, getClass());
        }
    }
    public String getPayloadAsString() throws Exception
    {
        return new String(message);
    }

    public byte[] getPayloadAsBytes() throws Exception
    {
        return message;
    }

    public Object getPayload()
    {
        return message;
    }
}

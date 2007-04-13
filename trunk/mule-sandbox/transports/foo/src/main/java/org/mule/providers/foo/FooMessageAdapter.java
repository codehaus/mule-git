/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo;

import org.mule.providers.AbstractMessageAdapter;
import org.mule.umo.MessagingException;

/**
 * <code>FooMessageAdapter</code> TODO document
 * 
 */

public class FooMessageAdapter extends AbstractMessageAdapter
{
    /* IMPLEMENTATION NOTE:
    The MessageAdapter is used to wrap an underlying message. It should store a copy of the
    underlying message as an instance variable.
    */
    
    public FooMessageAdapter(Object message) throws MessagingException
    {
    	/* IMPLEMENTATION NOTE:
    	The constructor should deterime that the message is of the correct type or
    	throw an exception i.e.
    	
        if (message instanceof byte[]) {
            this.message = (byte[]) message;
        } else {
            throw new MessageTypeNotSupportedException(message, getClass());
        }
        */
    }

    public String getPayloadAsString(String encoding) throws Exception
    {
        //Todo return the string representation of the wrapped message
        throw new UnsupportedOperationException("getPayloadAsString");
    }

    public byte[] getPayloadAsBytes() throws Exception
    {
        //Todo return the byte[] representation of the wrapped message
        throw new UnsupportedOperationException("getPayloadAsBytes");
    }

    public Object getPayload()
    {
        //Todo return the actual wrapped message
        throw new UnsupportedOperationException("getPayload");
    }
}

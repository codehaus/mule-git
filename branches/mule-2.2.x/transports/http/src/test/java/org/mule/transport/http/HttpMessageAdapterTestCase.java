/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http;

import org.mule.DefaultMuleMessage;
import org.mule.api.MessagingException;
import org.mule.api.transport.MessageAdapter;
import org.mule.transport.AbstractMessageAdapterTestCase;

import edu.emory.mathcs.backport.java.util.Arrays;

import org.apache.commons.lang.SerializationUtils;

public class HttpMessageAdapterTestCase extends AbstractMessageAdapterTestCase
{

    private byte[] message = TEST_MESSAGE.getBytes();

    public Object getValidMessage() throws Exception
    {
        return message;
    }

    public MessageAdapter createAdapter(Object payload) throws MessagingException
    {
        return new HttpMessageAdapter(payload);
    }
    
    public void testSerialization() throws Exception
    {
        MessageAdapter messageAdapter = createAdapter(getValidMessage());
        DefaultMuleMessage muleMessage = new DefaultMuleMessage(messageAdapter);

        byte[] serializedMessage = SerializationUtils.serialize(muleMessage);

        DefaultMuleMessage readMessage = 
            (DefaultMuleMessage) SerializationUtils.deserialize(serializedMessage);
        assertNotNull(readMessage.getAdapter());

        MessageAdapter readMessageAdapter = readMessage.getAdapter();
        assertTrue(readMessageAdapter instanceof HttpMessageAdapter);
        assertTrue(Arrays.equals(message, (byte[]) readMessageAdapter.getPayload()));
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.file;

import org.mule.DefaultMuleMessage;
import org.mule.api.MessagingException;
import org.mule.api.MuleMessage;
import org.mule.api.transport.MessageAdapter;
import org.mule.transport.AbstractMessageAdapterTestCase;
import org.mule.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.apache.commons.lang.SerializationUtils;

public class FileMessageAdapterTestCase extends AbstractMessageAdapterTestCase
{
    private File message;

    protected void doSetUp() throws Exception
    {
        super.doSetUp();

        // The working directory is deleted on tearDown
        File dir = FileUtils.newFile(muleContext.getConfiguration().getWorkingDirectory(), "tmp");
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        message = File.createTempFile("simple", ".mule", dir);
    }

    public Object getValidMessage()
    {
        return message;
    }

    public MessageAdapter createAdapter(Object payload) throws MessagingException
    {
        return new FileMessageAdapter(payload);
    }

    public void testMessageRetrieval2() throws Exception
    {
        Object msg = new ReceiverFileInputStream((File) getValidMessage(), false, null);

        MessageAdapter adapter = createAdapter(msg);
        MuleMessage muleMessage = new DefaultMuleMessage(adapter);

        doTestMessageEqualsPayload(msg, adapter.getPayload());

        byte[] bytes = muleMessage.getPayloadAsBytes();
        assertNotNull(bytes);

        String stringMessage = muleMessage.getPayloadAsString();
        assertNotNull(stringMessage);

        assertNotNull(adapter.getPayload());
    }

    protected void doTestMessageEqualsPayload(Object msg, Object payload) throws Exception
    {

        if (msg instanceof File)
        {
            File file = (File) msg;
            assertTrue(payload instanceof File);
            assertEquals(file, payload);
        }
        else if (msg instanceof FileInputStream)
        {
            byte[] messageBytes = null;
            byte[] payloadBytes = null;

            FileInputStream fis = (FileInputStream) msg;
            FileInputStream payloadFis = (FileInputStream) payload;
            messageBytes = new byte[fis.available()];
            payloadBytes = new byte[payloadFis.available()];
            fis.read(messageBytes);
            payloadFis.read(payloadBytes);
            assertTrue(Arrays.equals(messageBytes, payloadBytes));
        }
        else
        {
            fail("FileMessageAdaptor supports File or FileInputStream");
        }
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
        assertTrue(readMessageAdapter instanceof FileMessageAdapter);
        assertEquals(getValidMessage(), readMessageAdapter.getPayload());
    }

}

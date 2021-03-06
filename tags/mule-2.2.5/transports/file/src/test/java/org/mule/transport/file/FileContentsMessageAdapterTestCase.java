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
import org.mule.api.transport.MessageAdapter;
import org.mule.transport.AbstractMessageAdapterTestCase;
import org.mule.util.FileUtils;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.lang.SerializationUtils;

public class FileContentsMessageAdapterTestCase extends AbstractMessageAdapterTestCase
{
    private String validMessageContent = "Yabbadabbadooo!";
    private byte[] validMessage = validMessageContent.getBytes();
    private File messageFile;

    @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();

        // The working directory is deleted on tearDown
        File dir = FileUtils.newFile(muleContext.getConfiguration().getWorkingDirectory(), "tmp");
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        messageFile = File.createTempFile("simple", ".mule", dir);
        FileUtils.writeStringToFile(messageFile, validMessageContent, null);
    }

    public Object getValidMessage()
    {
        return validMessage;
    }

    public MessageAdapter createAdapter(Object payload) throws MessagingException
    {
        if (payload.equals(validMessage))
        {
            return new FileContentsMessageAdapter(messageFile);
        }
        else
        {
            return new FileContentsMessageAdapter(payload);
        }
    }

    public void testMessageContentsProperlyLoaded() throws Exception
    {
        // get new message adapter to test
        MessageAdapter adapter = new FileContentsMessageAdapter(messageFile);

        // delete the file before accessing the payload
         assertTrue(messageFile.delete());

        // slight detour for testing :)
        doTestMessageEqualsPayload(validMessage, adapter.getPayload());
    }
    
    public void testSerialization() throws Exception
    {
        MessageAdapter messageAdapter = createAdapter(messageFile);        
        DefaultMuleMessage muleMessage = new DefaultMuleMessage(messageAdapter);

        byte[] serializedMessage = SerializationUtils.serialize(muleMessage);

        DefaultMuleMessage readMessage = 
            (DefaultMuleMessage) SerializationUtils.deserialize(serializedMessage);
        assertNotNull(readMessage.getAdapter());

        MessageAdapter readMessageAdapter = readMessage.getAdapter();
        assertTrue(readMessageAdapter instanceof FileContentsMessageAdapter);
        
        assertTrue(Arrays.equals((byte[]) getValidMessage(), 
            (byte[]) readMessageAdapter.getPayload()));
    }

}

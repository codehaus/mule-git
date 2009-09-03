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
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * <code>FileContentsMessageFactory</code> converts the {@link ReceiverFileInputStream}'s content
 * into a <code>byte[]</code> as payload for the {@link MuleMessage}.
 */
public class FileContentsMessageFactory extends FileMessageFactory
{
    public FileContentsMessageFactory(MuleContext context)
    {
        super(context);
    }

    @Override
    protected MuleMessage doCreate(Object transportMessage) throws Exception
    {
        InputStream inputStream = convertToInputStream(transportMessage);
        byte[] payload = IOUtils.toByteArray(inputStream);
        inputStream.close();

        File file = convertToFile(transportMessage);
        MuleMessage message = new DefaultMuleMessage(payload, muleContext);
        setPropertiesFromFile(message, file);
        
        return message;
    }

    private InputStream convertToInputStream(Object transportMessage) throws Exception
    {
        InputStream stream = null;
        
        if (transportMessage instanceof InputStream)
        {
            stream = (InputStream) transportMessage;
        }
        else if (transportMessage instanceof File)
        {
            stream = new FileInputStream((File) transportMessage);
        }
        else
        {
            cannotHandlePayload(transportMessage);
        }
        
        return stream;
    }
}

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
import org.mule.api.transport.MessageTypeNotSupportedException;
import org.mule.transport.AbstractMuleMessageFactory;

import java.io.File;

/**
 * <code>FileMessageFactory</code> creates a new {@link MuleMessage} with a {@link File} or 
 * {@link ReceiverFileInputStream} payload. Users can obtain the filename and directory in the 
 * properties using <code>FileConnector.PROPERTY_FILENAME</code> and 
 * <code>FileConnector.PROPERTY_DIRECTORY</code>.
 */
public class FileMessageFactory extends AbstractMuleMessageFactory
{
    public FileMessageFactory(MuleContext context)
    {
        super(context);
    }

    @Override
    protected MuleMessage doCreate(Object transportMessage) throws Exception
    {
        MuleMessage message = new DefaultMuleMessage(transportMessage, muleContext);

        File file = convertToFile(transportMessage);
        setPropertiesFromFile(message, file);
        return message;
    }
    
    protected File convertToFile(Object transportMessage) throws MessageTypeNotSupportedException
    {
        File file = null;

        if (transportMessage instanceof File)
        {
            file = (File) transportMessage;
        }
        else if (transportMessage instanceof ReceiverFileInputStream)
        {
            file = ((ReceiverFileInputStream) transportMessage).getCurrentFile();
        }
        else
        {
            cannotHandlePayload(transportMessage);
        }
        
        return file;
    }

    protected void setPropertiesFromFile(MuleMessage message, File file)
    {
        message.setProperty(FileConnector.PROPERTY_ORIGINAL_FILENAME, file.getName());
        message.setProperty(FileConnector.PROPERTY_DIRECTORY, file.getParent());
    }
}



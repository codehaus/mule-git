/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp.protocols;

import org.mule.impl.RequestContext;
import org.mule.providers.tcp.TcpProtocol;
import org.mule.providers.tcp.i18n.TcpMessages;
import org.mule.umo.UMOMessage;
import org.mule.umo.provider.OutputHandler;
import org.mule.umo.transformer.TransformerException;
import org.mule.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class StreamingProtocol implements TcpProtocol
{

    public Object read(InputStream is) throws IOException
    {
        return is;
    }

    public void write(OutputStream os, Object data) throws IOException
    {
        boolean understood = false;
        if (data instanceof UMOMessage)
        {
            understood = process(os, ((UMOMessage) data).getPayload());
            
            if (!understood) 
            {
                try
                {
                    process(os, ((UMOMessage) data).getPayload(OutputHandler.class));
                }
                catch (TransformerException e)
                {
                    IOException e2 = new IOException();
                    e2.initCause(e);
                    throw e2;
                }
            }
        } 
        else 
        {
            understood = process(os, data);
        }
        
        if (!understood)
        {
            throw new IOException(TcpMessages.invalidStreamingOutputType(data.getClass()).toString());
        }   
    }

    private boolean process(OutputStream os, Object data) throws IOException, UnsupportedEncodingException
    {
        if (data instanceof InputStream)
        {
            System.out.println("Writing message in StreamingProtocol");
            InputStream is = (InputStream) data;
            IOUtils.copy(is, os);
            is.close();
            System.out.println("Closing iS");
            return true;
        }
        else if (data instanceof OutputHandler)
        {
            ((OutputHandler) data).write(RequestContext.getEvent(), os);
            return true;
        }
        else if (data instanceof String)
        {
            String enc = RequestContext.getEvent().getEncoding();
            os.write(((String) data).getBytes(enc));
            return true;
        }
        else if (data instanceof byte[])
        {
            os.write((byte[]) data);
            return true;
        }
     
        return false;
    }

}



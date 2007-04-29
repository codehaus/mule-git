/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.ftp;

import org.mule.umo.endpoint.UMOEndpointURI;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;

/**
 * TODO
 */
public class FtpOutputStreamWrapper extends OutputStream
{
    private final FTPClient client;
    private final OutputStream out;
    private final FtpConnector connector;
    private final UMOEndpointURI uri;

    public FtpOutputStreamWrapper(FtpConnector connector, UMOEndpointURI uri,
                                  FTPClient client, OutputStream out)
    {
        this.connector = connector;
        this.uri = uri;
        this.client = client;
        this.out = out;
    }

    public void write(int b) throws IOException
    {
        out.write(b);
    }

    public void write(byte b[]) throws IOException
    {
        out.write(b);
    }

    public void write(byte b[], int off, int len) throws IOException
    {
        out.write(b, off, len);
    }

    public void flush() throws IOException
    {
        out.flush();
    }

    public void close() throws IOException
    {
        try
        {
            out.close();
            if (!client.completePendingCommand())
            {
                client.logout();
                client.disconnect();
                throw new IOException("FTP Stream failed to complete pending request");
            }
        }
        finally
        {
            try
            {
                connector.releaseFtp(uri, client);
            }
            catch (Exception e)
            {
                throw (IOException)  new IOException(e.getMessage()).initCause(e);
            }
        }
    }

    FTPClient getFtpClient()
    {
        return client;
    }
}
/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.http;

import java.io.IOException;
import java.io.InputStream;

public class DelegatingInputStream extends InputStream {
    private InputStream inputStream;

    public DelegatingInputStream(InputStream is)
    {
        this.inputStream = is;
    }

    public int available() throws IOException
    {
        return inputStream.available();
    }

    public void close() throws IOException
    {
        inputStream.close();
    }

    public boolean equals(Object obj)
    {
        return inputStream.equals(obj);
    }

    public int hashCode()
    {
        return inputStream.hashCode();
    }

    public void mark(int readlimit)
    {
        inputStream.mark(readlimit);
    }

    public boolean markSupported()
    {
        return inputStream.markSupported();
    }

    public int read() throws IOException
    {
        return inputStream.read();
    }

    public int read(byte[] b, int off, int len) throws IOException
    {
        return inputStream.read(b, off, len);
    }

    public int read(byte[] b) throws IOException
    {
        return inputStream.read(b);
    }

    public void reset() throws IOException
    {
        inputStream.reset();
    }

    public long skip(long n) throws IOException
    {
        return inputStream.skip(n);
    }

    public String toString()
    {
        return inputStream.toString();
    }
}


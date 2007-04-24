/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ftp.server;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import org.apache.ftpserver.ftplet.FileObject;

public class StreamingFile extends NamedFile {

    public StreamingFile(String name, ServerState state)
    {
        super(name, state);
    }

    public boolean isDirectory()
    {
        return false;
    }

    public boolean isFile()
    {
        return true;
    }

    public FileObject[] listFiles()
    {
        return new FileObject[0];
    }

    public OutputStream createOutputStream(long offset) throws IOException {
        return new SignallingOutputStream(getState());
    }

    public InputStream createInputStream(long offset) throws IOException
    {
        return new SignallingInputStream(
                new ByteArrayInputStream(getState().getPayload()), getState());
    }

}

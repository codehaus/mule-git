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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.ftpserver.ftplet.FileObject;

public class Directory extends NamedFile
{

    public Directory(String name, ServerState state)
    {
        super(name, state);
    }

    public boolean isDirectory()
    {
        return true;
    }

    public boolean isFile()
    {
        return false;
    }

    public FileObject[] listFiles()
    {
        return new FileObject[]{new StreamingFile("file", getState())};
    }

    public OutputStream createOutputStream(long offset) throws IOException {
        return null;
    }

    public InputStream createInputStream(long offset) throws IOException {
        return null;
    }
}

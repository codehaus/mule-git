/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package net.sf.pzfilereader.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * This class encapsulates the writer that's used to output the data.
 */
public abstract class AbstractPzWriter extends Object implements PZWriter
{
    private BufferedWriter writer;

    public AbstractPzWriter(OutputStream output, char delimiter)
    {
        super();
        writer = new BufferedWriter(new OutputStreamWriter(output));
    }
    
    public void nextRecord() throws IOException
    {
        writer.newLine();
    }
    
    protected void write(Object value) throws IOException
    {
        if (value == null)
        {
            value = "";
        }
        // TODO converter/formatter for converting value to string?
        writer.write(value.toString());
    }
    
    protected void write(char character) throws IOException
    {
        writer.write(character);
    }
    
    public void flush() throws IOException
    {
        writer.flush();
    }

    public void close() throws IOException
    {
        writer.flush();
        writer.close();
    }
}



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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FixedWriterFactory extends Object implements PZFixedWriterFactory
{
    private static final char DEFAULT_FILL_CHAR = ' ';
    
    private char fillChar = DEFAULT_FILL_CHAR;

    public FixedWriterFactory()
    {
        super();
    }
    
    public FixedWriterFactory(char fillChar)
    {
        this();
        this.fillChar = fillChar;
    }
    
    public PZWriter createWriter(InputStream mapping, OutputStream output) throws IOException
    {
        return new FixedLengthWriter(mapping, output, fillChar);
    }
}



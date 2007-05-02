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

public class DefaultPZWriterFactory extends Object implements PZWriterFactory
{
    private static final DefaultPZWriterFactory INSTANCE = new DefaultPZWriterFactory();

    public static PZWriterFactory getInstance() 
    {
        return INSTANCE;
    }
    
    public DelimiterPZWriter newDelimiterWriter(OutputStream output, char delimiter, char qualifier) 
        throws IOException
    {
        return new DefaultDelimiterWriter(output, delimiter, qualifier);
    }
    
    public PZWriter newDelimiterWriter(InputStream mapping, OutputStream output,
        char delimiter, char qualifier) throws IOException
    {
        return new MappedDelimiterWriter(mapping, output, delimiter, qualifier);
    }
    
    public PZWriter newFixedLengthWriter(InputStream mapping, OutputStream output) 
        throws IOException
    {
        return new FixedLengthWriter(mapping, output, ' ');
    }
}



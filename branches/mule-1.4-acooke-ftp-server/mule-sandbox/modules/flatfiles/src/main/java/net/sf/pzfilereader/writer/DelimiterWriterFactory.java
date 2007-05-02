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

import java.io.OutputStream;


public class DelimiterWriterFactory extends Object implements PZDelimiterWriterFactory
{
    private static final char DEFAULT_DELIMITER = ';';
    private static final char DEFAULT_QUALIFIER = '"';

    private char delimiter = DEFAULT_DELIMITER;
    private char qualifier = DEFAULT_QUALIFIER;
    
    public DelimiterWriterFactory()
    {
        super();
    }
    
    public DelimiterWriterFactory(char delimiter)
    {
        this();
        this.delimiter = delimiter;
    }
    
    public DelimiterWriterFactory(char delimiter, char qualifier)
    {
        this(delimiter);
        this.qualifier = qualifier;
    }
    
    public char getDelimiter()
    {
        return delimiter;
    }

    public PZDelimiterWriter createWriter(OutputStream out)
    {
        return new DefaultDelimiterWriter(out, delimiter, qualifier);
    }
}



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

import org.jdom.JDOMException;

public class MappedDelimiterWriterFactory extends AbstractWriterFactory implements PZDelimiterWriterFactory
{
    private static final char DEFAULT_DELIMITER = ';';
    private static final char DEFAULT_QUALIFIER = '"';

    private final char delimiter;
    private final char qualifier;

    public MappedDelimiterWriterFactory(InputStream mappingSrc) throws IOException, JDOMException
    {
        this(mappingSrc, DEFAULT_DELIMITER);
    }

    public MappedDelimiterWriterFactory(InputStream mappingSrc, char delimiter)
        throws IOException, JDOMException
    {
        this(mappingSrc, delimiter, DEFAULT_QUALIFIER);
    }

    public MappedDelimiterWriterFactory(InputStream mappingSrc, char delimiter, char qualifier)
        throws IOException
    {
        super(mappingSrc);

        this.delimiter = delimiter;
        this.qualifier = qualifier;
    }

    public char getDelimiter()
    {
        return delimiter;
    }

    public char getQualifier()
    {
        return qualifier;
    }

    public PZWriter createWriter(OutputStream out) throws IOException
    {
        return new MappedDelimiterWriter(this.getParsedMapping(), out, delimiter, qualifier);
    }

}

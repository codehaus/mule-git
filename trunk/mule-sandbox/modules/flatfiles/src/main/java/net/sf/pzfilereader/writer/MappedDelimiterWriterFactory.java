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
import java.util.Map;

import net.sf.pzfilereader.InitialisationException;
import net.sf.pzfilereader.xml.PZMapParser;

import org.jdom.JDOMException;

public class MappedDelimiterWriterFactory extends Object
{
    private static final char DEFAULT_DELIMITER = ';';
    private static final char DEFAULT_QUALIFIER = '"';

    private final char delimiter;
    private final char qualifier;
    private final Map parsedMapping;

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
        super();

        this.delimiter = delimiter;
        this.qualifier = qualifier;

        try
        {
            this.parsedMapping = PZMapParser.parse(mappingSrc);
        }
        catch (JDOMException jde)
        {
            throw new InitialisationException(jde);
        }
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
        return new MappedDelimiterWriter(parsedMapping, out, delimiter, qualifier);
    }

}

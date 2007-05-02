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

public class FixedWriterFactory extends Object implements PZFixedWriterFactory
{
    public static final char DEFAULT_PADDING_CHARACTER = ' ';

    private final Map parsedMapping;
    private final char pad;

    public FixedWriterFactory()
    {
        this(DEFAULT_PADDING_CHARACTER);
    }

    public FixedWriterFactory(char fillChar)
    {
        super();
        this.parsedMapping = null;
        this.pad = fillChar;
    }

    public FixedWriterFactory(InputStream mappingSrc) throws IOException, JDOMException
    {
        this(mappingSrc, DEFAULT_PADDING_CHARACTER);
    }

    public FixedWriterFactory(InputStream mappingSrc, char fillChar) throws IOException
    {
        super();
        this.pad = fillChar;

        try
        {
            this.parsedMapping = PZMapParser.parse(mappingSrc);
        }
        catch (JDOMException jde)
        {
            throw new InitialisationException(jde);
        }
    }

    public PZWriter createWriter(OutputStream output) throws IOException
    {
        return new FixedLengthWriter(parsedMapping, output, pad);
    }
}

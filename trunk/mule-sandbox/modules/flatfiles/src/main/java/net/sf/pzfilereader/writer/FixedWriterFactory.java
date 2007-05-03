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

import org.jdom.JDOMException;

public class FixedWriterFactory extends AbstractWriterFactory
{
    public static final char DEFAULT_PADDING_CHARACTER = ' ';

    private final char pad;

    /**
     * @deprecated
     */
    // TODO HH: creating a FixedWriter without mapping is not supported, what's this method good for?
    public FixedWriterFactory()
    {
        this(DEFAULT_PADDING_CHARACTER);
    }

    /**
     * @deprecated
     */
    // TODO HH: creating a FixedWriter without mapping is not supported, what's this method good for?
    public FixedWriterFactory(char fillChar)
    {
        super();
        this.pad = fillChar;
    }
    
    public FixedWriterFactory(Map mapping)
    {
        super(mapping);
        this.pad = DEFAULT_PADDING_CHARACTER;
    }

    public FixedWriterFactory(InputStream mappingSrc) throws IOException, JDOMException
    {
        this(mappingSrc, DEFAULT_PADDING_CHARACTER);
    }

    public FixedWriterFactory(InputStream mappingSrc, char fillChar) throws IOException
    {
        super(mappingSrc);
        this.pad = fillChar;
    }

    public PZWriter createWriter(OutputStream output) throws IOException
    {
        return new FixedLengthWriter(this.getColumnMapping(), output, pad);
    }
}

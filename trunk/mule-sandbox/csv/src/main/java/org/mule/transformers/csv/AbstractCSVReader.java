/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.csv;

import java.io.Reader;

/**
 * All CSV readers should extend this class.
 */
public abstract class AbstractCSVReader implements CSVReader
{
    protected final Reader in;
    protected final char separator;

    /**
     * Create a new reader that will read our CSV file.
     * 
     * @param in the implementation that will read the character stream
     * @param separator the seperator (e.g. ';', ',' or whatever character)
     */
    public AbstractCSVReader(Reader in, char separator)
    {
        super();
        this.in = in;
        this.separator = separator;
    }

}

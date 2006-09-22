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

import java.io.Writer;

/**
 * All CSV writers should extend this class.
 */
public abstract class AbstractCSVWriter implements CSVWriter
{
    protected final Writer out;
    protected final char separator;

    /**
     * Create a new writer that will write our CSV file.
     * 
     * @param out the implementation that will write the character stream
     * @param separator the seperator (e.g. ';', ',' or whatever character)
     */
    public AbstractCSVWriter(Writer out, char separator)
    {
        super();
        this.out = out;
        this.separator = separator;
    }
}

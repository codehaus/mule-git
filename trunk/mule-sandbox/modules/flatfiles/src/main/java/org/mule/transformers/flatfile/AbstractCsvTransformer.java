/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.flatfile;

import org.mule.transformers.AbstractTransformer;

public abstract class AbstractCsvTransformer extends AbstractTransformer
{
    protected static final char DEFAULT_DELIMITER = ';';
    protected static final char DEFAULT_QUALIFIER = '"';

    private char delimiter = DEFAULT_DELIMITER;
    private char qualifier = DEFAULT_QUALIFIER;

    /**
     * @return record delimiter in the CSV file. The default is ';'.
     */
    public char getDelimiter()
    {
        return delimiter;
    }

    /**
     * Sets the delimiter to use when parsing CSV. May not be null.
     * 
     * @param delimiter
     */
    public void setDelimiter(char delimiter)
    {
        this.delimiter = delimiter;
    }

    /**
     * @return qualifier to quote delimiter characters inside the data.
     */
    public char getQualifier()
    {
        return qualifier;
    }

    /**
     * Sets the qualifier to quote delimiter characters inside the data. May not be null.
     * 
     * @param qualifier
     */
    public void setQualifier(char qualifier)
    {
        this.qualifier = qualifier;
    }
}



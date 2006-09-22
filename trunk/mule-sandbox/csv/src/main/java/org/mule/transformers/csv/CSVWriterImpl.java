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

import au.com.bytecode.opencsv.CSVWriter;

import java.io.Writer;
import java.util.List;

/**
 * Actual implementation to write CSV files.
 */
public class CSVWriterImpl extends AbstractCSVWriter
{
    private CSVWriter writer = null;

    public CSVWriterImpl(Writer out, char separator)
    {
        super(out, separator);
        if (separator == '\u0000')
        {
            separator = CSVWriter.DEFAULT_SEPARATOR;
        }
        writer = new CSVWriter(out, separator);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transformers.csv.CsvWriter#write(java.lang.Object)
     */
    public void write(Object o) throws Exception
    {
        if (o instanceof List)
        {
            write((List)o);
        }
    }

    /**
     * Write the List as a CSV string. The List will contain an array of strings.
     * Each string in this array represents a field in the CSV file.
     * 
     * @param l the List of String[]
     * @throws Exception
     */
    public void write(List l) throws Exception
    {
        writer.writeAll(l);
        writer.close();
    }

}

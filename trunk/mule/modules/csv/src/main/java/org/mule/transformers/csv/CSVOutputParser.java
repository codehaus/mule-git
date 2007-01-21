/*
 * $Id: $
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
import java.util.Map;

/**
 * Parser to write CSV data using the OpenCSV writer
 */
public class CSVOutputParser implements CSVParser
{
    /**
     * The OpenCVS writer
     */
    private CSVWriter writer = null;

    /**
     * Constructor
     *
     * @param out output writer
     * @param separator character to use as the field delimiter
     */
    public CSVOutputParser(Writer out, char separator, char quoteChar)
    {
        if (separator == '\u0000')
        {
            separator = CSVParser.DEFAULT_SEPARATOR;
        }

        writer = new CSVWriter(out, separator, quoteChar);
    }

    /*
     * Convert the object to CSV. We accept a List of Maps that represents
     * multiple rows or just a Map that represents on row
     *
     * @param o source object
     */
    public void write(Object o) throws Exception
    {
        if (o instanceof List)
        {
            write((List)o);
        }
        else if (o instanceof Map)
        {
            writeRow((Map)o);
        }
    }

    /**
     * Write the List as a CSV string. The List will contain Maps
     * Each string in this array represents a field in the CSV file.
     * 
     * @param l the List of Maps
     * @throws Exception
     */
    public void write(List l) throws Exception
    {
        for (int i = 0; i < l.size(); i++) {
            Map row = (Map)l.get(i);
            writeRow(row);
        }
        writer.close();
    }

    /**
     * Write the row Map as a CSV string.
     *
     * @param row the Map containing row data
     */
    public void writeRow(Map row) throws Exception
    {
        Object[] a = row.values().toArray();
        String[] sa = new String[a.length];
        for (int i = 0; i < a.length; i++) 
        {
            if (a[i] != null) sa[i] = a[i].toString();
            else sa[i] = new String("");
        }
        writer.writeNext(sa);
    }

}

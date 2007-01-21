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

import au.com.bytecode.opencsv.CSVReader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Parser to read CSV data using the OpenCSV Reader
 */
public class CSVInputParser implements CSVParser
{
    /**
     * This is the OpenCSV reader
     */
    private CSVReader reader = null;

    /**
     * Indicates whether to extract labels from the first line or not
     */
    private boolean firstLineLabels = false;

    /**
     * Holds a list of field names
     */
    private List labels = null;

    /**
     * Constructor
     *
     * @param in input reader
     * @param separator character to use as the field delimiter
     */
    public CSVInputParser(Reader in, char separator, char quoteChar, int startLine)
    {
        if (separator == '\u0000')
        {
            separator = CSVParser.DEFAULT_SEPARATOR;
        }

        if (quoteChar == '\u0000')
        {
            quoteChar = CSVParser.DEFAULT_QUOTE_CHARACTER;
        }

        reader = new CSVReader(in, separator, quoteChar, startLine);
    }

    /*
     * Reads the input and build a List of Maps
     *
     * @return Object the List
     */
    public Object parse() throws Exception
    {
        int counter = 0;

        try
        {
            List rows = new ArrayList();
            String[] line = reader.readNext();
            while (line != null)
            {
                counter++;

                if (firstLineLabels && counter == 1 && !isEmpty(line))
                {
                    labels = new ArrayList();
                    for (int i = 0; i < line.length; i++)
                    {
                        labels.add(line[i]);
                    }
                } else {
                    if (!isEmpty(line))
                    {
                        Map row = new HashMap();
                        for (int i = 0; i < line.length; i++)
                        {
                            if (labels != null)
                            {
                                row.put(labels.get(i), line[i]);
                            } 
                            else
                            {
                                row.put("" + i, line[i]);
                            }

                            //row.add(line[i]);
                        }
                        rows.add(row);
                    }
                }

                line = reader.readNext();
            }
            return rows;
        }
        finally
        {
            reader.close();
        }
    }

    /**
     * Check if the array that was passed is completely empty. Will return 
     * false if at least one element was found that contained a value.
     * 
     * @param line
     * @return true if the array is completely empty.
     */
    protected boolean isEmpty(String[] line)
    {
        if (line != null && line.length > 0)
        {
            for (int i = 0; i < line.length; i++)
            {
                if (StringUtils.isNotEmpty(line[i]))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets whether to extract field names from the first line or not
     *
     * @param firstLineLabels boolean yes/no
     */
    public void setFirstLineLabels(boolean firstLineLabels)
    {
        this.firstLineLabels = firstLineLabels;
    }

    /**
     * Gets whether or not to extract field names
     *
     * @return true of extracting field names from the first line
     */
    public void setLabels(List labels) {
        this.labels = labels;
    }

    /**
     * Returns the List of field names
     *
     * @return the List
     */
    public List getLabels()
    {
        return labels;
    }

}

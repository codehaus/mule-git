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

import au.com.bytecode.opencsv.CSVReader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Actual implementation to read/parse CSV files.
 */
public class CSVReaderImpl extends AbstractCSVReader
{
    private CSVReader reader = null;

    public CSVReaderImpl(Reader in, char separator)
    {
        super(in, separator);
        if (separator == '\u0000')
        {
            separator = CSVReader.DEFAULT_SEPARATOR;
        }
        reader = new CSVReader(in, separator);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transformers.csv.CsvReader#parse()
     */
    public Object parse() throws Exception
    {
        try
        {
            List/* <String[]> */lines = new ArrayList/* <String[]> */();
            String[] line = reader.readNext();
            while (line != null)
            {
                if (!isEmpty(line))
                {
                    lines.add(line);
                }
                line = reader.readNext();
            }
            return lines;
        }
        finally
        {
            reader.close();
        }
    }

    /**
     * Check if the array that was passed is completely empty. Will return false if
     * at least one element was found that contained a value.
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

}

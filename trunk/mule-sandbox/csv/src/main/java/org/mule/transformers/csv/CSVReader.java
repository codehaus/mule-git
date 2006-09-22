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

/**
 * All CSV readers should implement this interface.
 */
public interface CSVReader
{
    public static char DEFAULT_SEPARATOR = ';';

    /**
     * Parse the content of the CSV file and return an object containing: - An array
     * of arrays, e.g. String[][] - A List of arrays, e.g. List<String[]>
     * 
     * @return
     * @throws Exception
     */
    public Object parse() throws Exception;
}

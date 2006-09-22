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
 * All CSV writers should implement this interface.
 */
public interface CSVWriter
{
    public static char DEFAULT_SEPARATOR = ';';

    /**
     * Write the object to a CSV file. The object can be: - An array of arrays, e.g.
     * String[][] - A List of arrays, e.g. List<String[]>
     * 
     * @param o the object to write to the CSV file
     * @throws Exception
     */
    public void write(Object o) throws Exception;
}

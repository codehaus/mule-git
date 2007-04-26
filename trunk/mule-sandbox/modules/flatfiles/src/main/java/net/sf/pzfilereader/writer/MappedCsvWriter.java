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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.pzfilereader.structure.ColumnMetaData;
import net.sf.pzfilereader.xml.PZMapParser;

import org.jdom.JDOMException;

public class MappedCsvWriter extends DefaultCsvWriter
{
    public MappedCsvWriter(InputStream mapping, OutputStream output, char delimiter, char qualifier) 
        throws IOException, JDOMException
    {
        super(output, delimiter, qualifier);
        
        Map parsedMapping = PZMapParser.parse(mapping);
        List columns = (List)parsedMapping.get("detail");
        Iterator columnIter = columns.iterator();
        while (columnIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData)columnIter.next();
            super.addColumnTitle(element.getColName());
        }
        // write the column headers
        this.nextRecord();
    }

    /**
     * This implementation throws an UnsupportedOperationException because the columns 
     * to write are already defined in the mapping file.
     * 
     * @throws UnsupportedOperationException
     */
    public void addColumnTitle(String string)
    {
        throw new UnsupportedOperationException("columns cannot be added when working with a mapping file");
    }
}



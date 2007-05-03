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
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.pzfilereader.structure.ColumnMetaData;
import net.sf.pzfilereader.util.PZConstants;
import net.sf.pzfilereader.util.StringUtils;

public class FixedLengthWriter extends AbstractPZWriter
{
    private Map columnMapping;
    private char fillChar;
    
    public FixedLengthWriter(Map parsedMapping, OutputStream output, char fillChar) throws IOException
    {
        super(output);
        this.fillChar = fillChar;
        columnMapping = parsedMapping;
    }

    public void addRecordEntry(String columnName, Object value)
    {
        if (value != null)
        {
            ColumnMetaData metaData = this.getColumnMetaData(columnName);
            
            String valueString = value.toString();
            if (valueString.length() > metaData.getColLength())
            {
                throw new IllegalArgumentException(valueString 
                    + " exceeds the maximum length for column " + columnName 
                    + "(" + metaData.getColLength() + ")");
            }
        }
        super.addRecordEntry(columnName, value);
    }

    public void nextRecord() throws IOException
    {
        Iterator columnIter = this.getColumnMetaData().iterator();
        while (columnIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData)columnIter.next();
            
            String outputString = null;
            Object value = this.getRowMap().get(element.getColName());
            if (value == null)
            {
                value = "";
            }
            // TODO DO: add formatting of values
            outputString = StringUtils.rightPad(value.toString(), element.getColLength(), fillChar);
            this.write(outputString);
        }
        
        super.nextRecord();
    }

    protected boolean validateColumnTitle(String columnTitle)
    {
        Map columnNameToIndex = (Map)columnMapping.get(PZConstants.COL_IDX);
        return columnNameToIndex.keySet().contains(columnTitle);
    }

    public void printFooter()
    {
        // TODO DO: implement footer handling

    }

    public void printHeader()
    {
        // TODO DO: implement header handling

    }
    
    /**
     * @return List of ColumnMetaData objects describing the mapping defined in the XML file.
     */
    private List getColumnMetaData()
    {
        return (List)columnMapping.get(PZConstants.DETAIL_ID);
    }
    
    private ColumnMetaData getColumnMetaData(String columnName)
    {
        Iterator metaDataIter = this.getColumnMetaData().iterator();
        while (metaDataIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData)metaDataIter.next();
            if (element.getColName().equals(columnName))
            {
                return element;
            }
        }
        
        throw new IllegalArgumentException("Column \"" + columnName + "\" unknown");
    }
}



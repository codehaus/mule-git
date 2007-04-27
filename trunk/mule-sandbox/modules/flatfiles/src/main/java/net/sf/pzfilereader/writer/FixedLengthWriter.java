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

import net.sf.pzfilereader.InitialisationException;
import net.sf.pzfilereader.structure.ColumnMetaData;
import net.sf.pzfilereader.xml.PZMapParser;

import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;

public class FixedLengthWriter extends AbstractPzWriter
{
    private Map columnMapping;
    
    public FixedLengthWriter(InputStream mapping, OutputStream output) throws IOException
    {
        super(output);
        try
        {
            columnMapping = PZMapParser.parse(mapping);
        }
        catch (JDOMException jde)
        {
            throw new InitialisationException(jde);
        }
    }

    public void nextRecord() throws IOException
    {
        Iterator columnIter = this.getColumnMetaData().iterator();
        while (columnIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData)columnIter.next();
            
            String outputString = "";
            Object value = this.getRowMap().get(element.getColName());
            if (value != null)
            {
                outputString = StringUtils.rightPad(value.toString(), element.getColLength());
            }
            this.write(outputString);
        }
        
        super.nextRecord();
    }

    protected boolean validateColumnTitle(String columnTitle)
    {
        Map columnNameToIndex = (Map)columnMapping.get("colIndex");
        return columnNameToIndex.keySet().contains(columnTitle);
    }

    public void printFooter()
    {
        // TODO Auto-generated method stub

    }

    public void printHeader()
    {
        // TODO Auto-generated method stub

    }
    
    /**
     * @return List of ColumnMetaData objects describing the mapping defined in the XML file.
     */
    private List getColumnMetaData()
    {
        return (List)columnMapping.get("detail");
    }
}



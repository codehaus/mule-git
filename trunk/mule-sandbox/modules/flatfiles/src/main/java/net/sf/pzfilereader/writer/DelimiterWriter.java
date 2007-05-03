/*
 * $Id: AbstractDelimiterWriter.java 6289 2007-05-03 11:03:21Z dirk.olmes $
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.pzfilereader.structure.ColumnMetaData;
import net.sf.pzfilereader.util.PZConstants;

public class DelimiterWriter extends AbstractPZWriter
{
    private char delimiter;
    private char qualifier;
    private List columnTitles = null;
    private boolean columnTitlesWritten = false;
    
    public DelimiterWriter(Map columnMapping, OutputStream output, 
        char delimiter, char qualifier) throws IOException
    {
        super(output);
        this.delimiter = delimiter;
        this.qualifier = qualifier;
        
        columnTitles = new ArrayList();
        List columns = (List) columnMapping.get(PZConstants.DETAIL_ID);
        Iterator columnIter = columns.iterator();
        while (columnIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData) columnIter.next();
            columnTitles.add(element.getColName());
        }
        // write the column headers
        this.nextRecord();
    }

    protected void writeWithDelimiter(Object value) throws IOException
    {
        this.write(value);
        this.write(delimiter);
    }
    
    protected void write(Object value) throws IOException
    {
        String stringValue = "";
        if (value != null)
        {
            // TODO DO: format the value 
            stringValue = value.toString();
            if (stringValue.indexOf(delimiter) > -1)
            {
                int strlen = stringValue.length();
                char[] newValue = new char[strlen + 2];
                
                stringValue.getChars(0, strlen, newValue, 1);
                newValue[0] = qualifier;
                newValue[strlen + 1] = qualifier;
                
                stringValue = new String(newValue);
            }
        }
        super.write(stringValue);
    }

    protected void addColumnTitle(String string)
    {
        if (string == null)
        {
            throw new IllegalArgumentException("column title may not be null");
        }
        columnTitles.add(string);
    }

    protected void writeColumnTitles() throws IOException
    {
        Iterator titleIter = columnTitles.iterator();
        while (titleIter.hasNext())
        {
            String title = (String)titleIter.next();
            
            if (titleIter.hasNext())
            {
                this.writeWithDelimiter(title);
            }
            else
            {
                this.write(title);
            }
        }
    }

    protected void writeRow() throws IOException
    {
        Iterator titlesIter = columnTitles.iterator();
        while (titlesIter.hasNext())
        {
            String columnTitle = (String)titlesIter.next();
            if (titlesIter.hasNext())
            {
                this.writeWithDelimiter(this.getRowMap().get(columnTitle));
            }
            else
            {
                this.write(this.getRowMap().get(columnTitle));
            }
        }
    }

    public void nextRecord() throws IOException
    {
        if (columnTitlesWritten == false)
        {
            this.writeColumnTitles();
            columnTitlesWritten  = true;
        }
        else
        {
            this.writeRow();
        }

        super.nextRecord();
    }

    public void printFooter()
    {
        // TODO DO: implement footer handling
    }

    public void printHeader()
    {
        // TODO DO: implement header handling
    }

    protected boolean validateColumnTitle(String columnTitle)
    {
        return columnTitles.contains(columnTitle);
    }
}



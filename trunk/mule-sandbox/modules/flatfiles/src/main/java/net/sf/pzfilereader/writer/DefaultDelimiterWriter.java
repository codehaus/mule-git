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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultDelimiterWriter extends AbstractPZWriter implements DelimiterPZWriter
{
    private char delimiter;
    private char qualifier;
    private List columnTitles = null;
    private boolean columnTitlesWritten = false;
    
    public DefaultDelimiterWriter(OutputStream output, char delimiter, char qualifier)
    {
        super(output);
        this.delimiter = delimiter;
        this.qualifier = qualifier;
        this.columnTitles = new ArrayList();
    }

    protected void writeWithDelimiter(Object value) throws IOException
    {
        // TODO format the value 
        // TODO surround with qualifier if value's string contains delimiter
        this.write(value);
        this.write(delimiter);
    }

    public void addColumnTitle(String string)
    {
        if (string == null)
        {
            throw new IllegalArgumentException("column title may not be null");
        }
        columnTitles.add(string);
    }

    protected void writeColumnTitles() throws IOException
    {
        // TODO make sure that column titles exist
        
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
        // TODO Auto-generated method stub
    }

    public void printHeader()
    {
        // TODO Auto-generated method stub
    }

    protected boolean validateColumnTitle(String columnTitle)
    {
        return columnTitles.contains(columnTitle);
    }
}



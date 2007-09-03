
package net.sf.pzfilereader.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.flatpack.structure.ColumnMetaData;
import net.sf.flatpack.util.FPConstants;

public class DelimiterWriter extends AbstractPZWriter
{
    private char delimiter;
    private char qualifier;
    private List columnTitles = null;
    private boolean columnTitlesWritten = false;

    protected DelimiterWriter(Map columnMapping, OutputStream output, char delimiter, char qualifier)
        throws IOException
    {
        super(output);
        this.delimiter = delimiter;
        this.qualifier = qualifier;

        columnTitles = new ArrayList();
        List columns = (List) columnMapping.get(FPConstants.DETAIL_ID);
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
        }

        boolean needsQuoting = (stringValue.indexOf(delimiter) != -1);

        if (needsQuoting)
        {
            super.write(qualifier);
        }

        super.write(stringValue);

        if (needsQuoting)
        {
            super.write(qualifier);
        }
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
            String title = (String) titleIter.next();

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
            String columnTitle = (String) titlesIter.next();
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
            columnTitlesWritten = true;
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

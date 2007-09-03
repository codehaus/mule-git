package net.sf.flatpack.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.flatpack.structure.ColumnMetaData;
import net.sf.flatpack.util.FPConstants;

public class FixedLengthWriter extends AbstractPZWriter
{
    private final Map columnMapping;
    private final char padChar;

    protected FixedLengthWriter(Map parsedMapping, OutputStream output, char padChar) throws IOException
    {
        super(output);
        this.columnMapping = parsedMapping;
        this.padChar = padChar;
    }

    public void addRecordEntry(String columnName, Object value)
    {
        if (value != null)
        {
            ColumnMetaData metaData = this.getColumnMetaData(columnName);

            String valueString = value.toString();
            if (valueString.length() > metaData.getColLength())
            {
                throw new IllegalArgumentException(valueString + " exceeds the maximum length for column "
                                + columnName + "(" + metaData.getColLength() + ")");
            }
        }
        super.addRecordEntry(columnName, value);
    }

    public void nextRecord() throws IOException
    {
        Iterator columnIter = this.getColumnMetaData().iterator();
        while (columnIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData) columnIter.next();
            Object value = this.getRowMap().get(element.getColName());
            this.write(this.formattedValue(value, element));
        }

        super.nextRecord();
    }

    protected char[] formattedValue(Object value, ColumnMetaData element)
    {
        if (value == null)
        {
            // TODO DO: maybe have a way to substitute default values here?
            value = "";
        }

        // TODO DO: add formatting of values
        String stringValue = value.toString();
        int stringLength = stringValue.length();

        int columnLength = element.getColLength();
        char[] formattedValue = new char[columnLength];

        /*
         * Copy the contents of the original string; for Strings up to ~8-10
         * characters this loop is consistently faster than String.getChars(). Short
         * Strings are usually the majority of values in fixed-width data columns.
         */
        int numCharacters = Math.min(stringLength, columnLength);
        if (numCharacters < 8)
        {
            for (int i = 0; i < numCharacters; i++)
            {
                formattedValue[i] = stringValue.charAt(i);
            }
        }
        else
        {
            stringValue.getChars(0, numCharacters, formattedValue, 0);
        }

        if (stringLength < columnLength)
        {
            // pad if necessary
            Arrays.fill(formattedValue, stringLength, columnLength, padChar);
        }

        return formattedValue;
    }

    protected boolean validateColumnTitle(String columnTitle)
    {
        Map columnNameToIndex = (Map) columnMapping.get(FPConstants.COL_IDX);
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
     * @return List of ColumnMetaData objects describing the mapping defined in the
     *         XML file.
     */
    private List getColumnMetaData()
    {
        return (List) columnMapping.get(FPConstants.DETAIL_ID);
    }

    private ColumnMetaData getColumnMetaData(String columnName)
    {
        Iterator metaDataIter = this.getColumnMetaData().iterator();
        while (metaDataIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData) metaDataIter.next();
            if (element.getColName().equals(columnName))
            {
                return element;
            }
        }

        throw new IllegalArgumentException("Column \"" + columnName + "\" unknown");
    }
}

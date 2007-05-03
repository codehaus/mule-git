/*
 * $Id: MappedDelimiterWriterFactory.java 6291 2007-05-03 12:03:50Z dirk.olmes $
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
import java.util.List;
import java.util.Map;

import net.sf.pzfilereader.structure.ColumnMetaData;
import net.sf.pzfilereader.util.PZConstants;

import org.jdom.JDOMException;

public class DelimiterWriterFactory extends AbstractWriterFactory
{
    private static final char DEFAULT_DELIMITER = ';';
    private static final char DEFAULT_QUALIFIER = '"';

    private final char delimiter;
    private final char qualifier;

    public DelimiterWriterFactory(char delimiter, char qualifier)
    {
        super();
        this.delimiter = delimiter;
        this.qualifier = qualifier;
    }
    
    public DelimiterWriterFactory(InputStream mappingSrc) throws IOException, JDOMException
    {
        this(mappingSrc, DEFAULT_DELIMITER);
    }

    public DelimiterWriterFactory(InputStream mappingSrc, char delimiter)
        throws IOException, JDOMException
    {
        this(mappingSrc, delimiter, DEFAULT_QUALIFIER);
    }

    public DelimiterWriterFactory(InputStream mappingSrc, char delimiter, char qualifier)
        throws IOException
    {
        super(mappingSrc);
        this.delimiter = delimiter;
        this.qualifier = qualifier;
    }
    
    public DelimiterWriterFactory(Map mapping)
    {
        this(mapping, DEFAULT_DELIMITER, DEFAULT_QUALIFIER);
    }
    
    public DelimiterWriterFactory(Map mapping, char delimiter)
    {
        this(mapping, delimiter, DEFAULT_QUALIFIER);
    }
    
    public DelimiterWriterFactory(Map mapping, char delimiter, char qualifier)
    {
        super(mapping);
        this.delimiter = delimiter;
        this.qualifier = qualifier;
    }

    public char getDelimiter()
    {
        return delimiter;
    }

    public char getQualifier()
    {
        return qualifier;
    }

    public PZWriter createWriter(OutputStream out) throws IOException
    {
        return new DelimiterWriter(this.getColumnMapping(), out, delimiter, qualifier);
    }
    
    // TODO DO: check that no column titles can be added after first nextRecord
    public void addColumnTitle(String columnTitle)
    {
        Map columnMapping = this.getColumnMapping();
        List columnMetaDatas = (List)columnMapping.get(PZConstants.DETAIL_ID);
        Map columnIndices = (Map)columnMapping.get(PZConstants.COL_IDX);
        
        ColumnMetaData metaData = new ColumnMetaData();
        metaData.setColName(columnTitle);
        columnMetaDatas.add(metaData);
        
        Integer columnIndex = new Integer(columnMetaDatas.indexOf(metaData));
        columnIndices.put(columnIndex, columnTitle);
    }
}

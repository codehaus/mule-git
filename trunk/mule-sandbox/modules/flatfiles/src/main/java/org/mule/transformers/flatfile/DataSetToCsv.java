/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.flatfile;

import org.mule.umo.transformer.TransformerException;

import java.io.StringWriter;

import net.sf.pzfilereader.DataSet;

public class DataSetToCsv extends AbstractCsvTransformer
{
    public DataSetToCsv()
    {
        super();
        this.registerSourceType(DataSet.class);
        this.setReturnClass(String.class);
    }
    
    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        if (src == null)
        {
            return null;
        }
        
        DataSet dataSet = (DataSet) src;
        StringWriter writer = new StringWriter();
        this.writeColumnHeaders(dataSet.getColumns(), writer);
        this.writeContent(dataSet, writer);
        
        writer.flush();
        return writer.toString();
    }

    private void writeColumnHeaders(String[] columnHeaders, StringWriter writer)
    {
        if (columnHeaders == null)
        {
            return;
        }
        
        for (int i = 0; i < columnHeaders.length; i++)
        {
            writer.write(columnHeaders[i]);
            if (i < (columnHeaders.length - 1))
            {
                writer.write(this.getDelimiter());
            }
        }
        
        writer.write("\n");
    }

    private void writeContent(DataSet dataSet, StringWriter writer)
    {
        String[] headers = dataSet.getColumns();
        while (dataSet.next())
        {
            for (int i = 0; i < headers.length; i++)
            {
                writer.write(dataSet.getString(headers[i]));
                if (i < (headers.length - 1))
                {
                    writer.write(this.getDelimiter());
                }
            }
            
            writer.write("\n");
        }
    }
}



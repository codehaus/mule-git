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

import org.mule.config.i18n.Message;
import org.mule.umo.transformer.TransformerException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.sf.pzfilereader.DataSet;
import net.sf.pzfilereader.DefaultPZParserFactory;
import net.sf.pzfilereader.PZParser;

public class CsvToDataSet extends AbstractCsvTransformer
{
    public CsvToDataSet()
    {
        super();        
        this.registerSourceType(byte[].class);
        this.setReturnClass(DataSet.class);
    }
    
    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        if (src == null)
        {
            return null;
        }
        
        InputStream dataSource = this.createInputStream(src);
        PZParser parser = 
            DefaultPZParserFactory.getInstance().newDelimitedParser(dataSource, 
                this.getDelimiter(), this.getQualifier());
        
        DataSet dataSet = parser.parse();
        if (dataSet == null)
        {
            throw new TransformerException(Message.createStaticMessage("Could not create a data set"));
        }

        return dataSet;
    }

    private InputStream createInputStream(Object src) throws TransformerException
    {
        if (src instanceof byte[])
        {
            return new ByteArrayInputStream((byte[]) src);
        }
        
        String message = "Could not create a transformer for input of type " + src.getClass().getName();
        throw new TransformerException(Message.createStaticMessage(message));
    }
}



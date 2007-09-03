/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.flatfile;

import org.mule.transformers.AbstractTransformer;
import org.mule.transformers.flatfile.i18n.FlatfileMessages;
import org.mule.umo.transformer.TransformerException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.flatpack.writer.DelimiterWriterFactory;
import net.sf.flatpack.writer.Writer;

/**
 * This transformer accepts a {@link List} of {@link Map}s and transforms each {@link Map} to 
 * a row in a CSV file. The transformer will return a {@link String} containing the CSV.
 */
public class MapsToCsv extends AbstractTransformer
{
    private String delimiter;
    private String qualifier;
    private String mapping;
    
    public MapsToCsv()
    {
        super();
        delimiter = String.valueOf(DelimiterWriterFactory.DEFAULT_DELIMITER);
        qualifier = String.valueOf(DelimiterWriterFactory.DEFAULT_QUALIFIER);
        mapping = null;

        this.setReturnClass(String.class);
        this.registerSourceType(List.class);
    }
    
    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        try
        {
            StringWriter output = new StringWriter();
            Writer writer = this.createWriter(output);
            
            Iterator rowIter = ((List)src).iterator();
            while (rowIter.hasNext())
            {
                Map row = (Map)rowIter.next();
                this.writeRow(writer, row);
            }
            
            writer.close();
            return output.toString();
        }
        catch (IOException iox)
        {
            throw new TransformerException(this, iox);
        }
    }

    private Writer createWriter(java.io.Writer output) throws TransformerException, IOException
    {
        if (delimiter.length() > 1)
        {
            throw new TransformerException(FlatfileMessages.delimiterLengthExceeded());
        }
        char delimiterChar = delimiter.charAt(0);

        if (qualifier.length() > 1)
        {
            throw new TransformerException(FlatfileMessages.qualifierLengthExceeded());
        }
        char qualifierChar = qualifier.charAt(0);
        
        if (mapping == null)
        {
            throw new TransformerException(FlatfileMessages.missingMappingFile());
        }
        Reader mappingReader = new FileReader(this.mapping);
        
        return new DelimiterWriterFactory(mappingReader, delimiterChar, qualifierChar).createWriter(output);
    }

    private void writeRow(Writer writer, Map row) throws IOException
    {
        Iterator keyIter = row.keySet().iterator();
        while (keyIter.hasNext())
        {
            String key = (String)keyIter.next();
            writer.addRecordEntry(key, row.get(key));
        }
        writer.nextRecord();
    }

    public String getDelimiter()
    {
        return delimiter;
    }

    public void setDelimiter(String delimiter)
    {
        this.delimiter = delimiter;
    }

    public String getQualifier()
    {
        return qualifier;
    }

    public void setQualifier(String qualifier)
    {
        this.qualifier = qualifier;
    }

    public String getMapping()
    {
        return mapping;
    }

    public void setMapping(String mapping)
    {
        this.mapping = mapping;
    }
}



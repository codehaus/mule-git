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
import org.mule.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.flatpack.writer.DelimiterWriterFactory;
import net.sf.flatpack.writer.PZWriter;

public class MapsToCsv extends AbstractTransformer
{
    private String delimiter;
    private String qualifier;
    private String outputPath;
    private String mapping;
    
    public MapsToCsv()
    {
        super();
        delimiter = String.valueOf(DelimiterWriterFactory.DEFAULT_DELIMITER);
        qualifier = String.valueOf(DelimiterWriterFactory.DEFAULT_QUALIFIER);
        mapping = null;

        this.setReturnClass(File.class);
        this.registerSourceType(List.class);
    }
    
    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        try
        {
            File outputFile = this.createOutputFile(outputPath);
            PZWriter writer = this.createWriter(outputFile);
            
            Iterator rowIter = ((List)src).iterator();
            while (rowIter.hasNext())
            {
                Map row = (Map)rowIter.next();
                this.writeRow(writer, row);
            }
            
            writer.close();
            return outputFile;
        }
        catch (IOException iox)
        {
            throw new TransformerException(this, iox);
        }
    }

    private File createOutputFile(String path) throws TransformerException
    {
        if (StringUtils.isEmpty(path))
        {
            throw new TransformerException(FlatfileMessages.outputPathMustBeSet());
        }
        
        File outputFile = new File(path);
        if (outputFile.canWrite() == false)
        {
            throw new TransformerException(FlatfileMessages.cannotWriteToFile(outputFile));
        }
        
        return outputFile;
    }

    private PZWriter createWriter(File outputFile) throws TransformerException, IOException
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
        InputStream mappingStream = new FileInputStream(mapping);
        
        OutputStream output = new FileOutputStream(outputFile);
        return new DelimiterWriterFactory(mappingStream, delimiterChar, qualifierChar).createWriter(output);
    }

    private void writeRow(PZWriter writer, Map row) throws IOException
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

    public String getOutputPath()
    {
        return outputPath;
    }

    public void setOutputPath(String outputPath)
    {
        this.outputPath = outputPath;
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



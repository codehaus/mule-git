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

import org.mule.umo.transformer.TransformerException;

import java.util.Arrays;

import junit.framework.Assert;
import junit.framework.TestCase;

import net.sf.flatpack.DataSet;

import org.apache.commons.lang.StringUtils;

public class CsvToDataSetTestCase extends TestCase
{
    private String lineSeparator = System.getProperty("line.separator");

    public void testParseDataWithHeadings() throws TransformerException
    {
        String[] headers = new String[] {"col1", "col2"};
        String[] data = new String[] {"foo", "bar"};
        String input = this.buildInputString(headers, data);
        
        CsvToDataSet transformer = new CsvToDataSet();
        DataSet result = (DataSet)transformer.transform(input);
        
        Assert.assertTrue(Arrays.equals(result.getColumns(), headers));
        result.next();
        Assert.assertEquals(data[0], result.getString(headers[0]));
        Assert.assertEquals(data[1], result.getString(headers[1]));
    }

    public void testParseDataWrongInput()
    {
        String[] headers = new String[] {"col1", "col2"};
        String[] data = new String[] {"data1", "data2", "data3"};
        String input = this.buildInputString(headers, data);
   
        try
        {
            CsvToDataSet transformer = new CsvToDataSet();
            transformer.transform(input);
            Assert.fail();
        }
        catch (TransformerException te)
        {
            // we expected this one
        }
    }

    public void testParseDataDifferentDelimiterAndQualifier() throws TransformerException
    {
        String[] headers = new String[] {"col1", "col2"};
        String[] data = new String[] {"data1", "'data|2'"};
        String input = this.buildInputString(headers, data, '|');
        
        CsvToDataSet transformer = new CsvToDataSet();
        transformer.setDelimiter('|');
        transformer.setQualifier('\'');
        DataSet result = (DataSet)transformer.transform(input);
        
        Assert.assertEquals(0, result.getErrorCount());
        Assert.assertTrue(Arrays.equals(headers, result.getColumns()));
        Assert.assertTrue(result.next());
        Assert.assertEquals(data[0], result.getString(headers[0]));
        String expected = data[1].replaceAll("'", "");
        Assert.assertEquals(expected, result.getString(headers[1]));
    }
    
    private String buildInputString(String[] headers, String[] data)
    {
        return this.buildInputString(headers, data, AbstractCsvTransformer.DEFAULT_DELIMITER);
    }
    
    private String buildInputString(String[] headers, String[] data, char delimiter)
    {
        StringBuffer input = new StringBuffer();
        input.append(StringUtils.join(headers, delimiter));
        input.append(lineSeparator);
        input.append(StringUtils.join(data, delimiter));
        input.append(lineSeparator);
        
        return input.toString();
    }
}

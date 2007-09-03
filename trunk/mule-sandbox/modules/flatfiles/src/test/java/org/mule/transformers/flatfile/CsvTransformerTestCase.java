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

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

import com.mockobjects.dynamic.Mock;

import java.util.Arrays;

import junit.framework.Assert;

import net.sf.flatpack.DataSet;

public class CsvTransformerTestCase extends AbstractTransformerTestCase
{
    private static final String TEST_DATA = "col1;col2\nfoo;bar\n";
    
    public Object getResultData()
    {
        Mock mock = new Mock(DataSet.class);
        mock.expectAndReturn("getColumns", new String[] {"col1", "col2"});
        mock.expectAndReturn("getColumns", new String[] {"col1", "col2"});
        mock.expectAndReturn("next", true);
        mock.expectAndReturn("getString", "col1", "foo");
        mock.expectAndReturn("getString", "col2", "bar");
        mock.expectAndReturn("next", false);
        
        return mock.proxy();
    }

    public UMOTransformer getRoundTripTransformer() throws Exception
    {
//        return new DataSetToCsv();
        
        // TODO make this work or disable it
        return null;
    }

    public Object getTestData()
    {
        return TEST_DATA.getBytes();
    }

    public UMOTransformer getTransformer() throws Exception
    {
        return new CsvToDataSet();
    }
    
    public boolean compareResults(Object expected, Object results)
    {
        if (results instanceof DataSet)
        {
            return this.compareDataSetResults((DataSet)results);
        }
        
        if (results instanceof String)
        {
            String resultString = (String)results;
            Assert.assertEquals(TEST_DATA, resultString);
            return true;
        }
        
        return false;
    }
    
    private boolean compareDataSetResults(DataSet dataSet)
    {        
        String[] columns = new String[] {"col1", "col2"};
        Assert.assertTrue(Arrays.equals(dataSet.getColumns(), columns));

        return true;
    }
}


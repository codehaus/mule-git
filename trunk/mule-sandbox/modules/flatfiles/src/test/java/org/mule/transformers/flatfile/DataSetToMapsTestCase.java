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

import com.mockobjects.constraint.IsEqual;
import com.mockobjects.dynamic.Mock;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import net.sf.pzfilereader.DataSet;

public class DataSetToMapsTestCase extends TestCase
{
    private DataSet mockDataSet = null;
    
    protected void setUp() throws Exception
    {
        super.setUp();
        
        Mock mock = new Mock(DataSet.class);
        mock.expectAndReturn("getColumns", new String[]{"col1", "col2"});
        mock.expectAndReturn("getRowCount", 2);
        mock.expectAndReturn("next", true);
        mock.expectAndReturn("getString", new IsEqual("col1"), "value1.1");
        mock.expectAndReturn("getString", new IsEqual("col2"), "value1.2");
        mock.expectAndReturn("next", true);
        mock.expectAndReturn("getString", new IsEqual("col1"), "value2.1");
        mock.expectAndReturn("getString", new IsEqual("col2"), "value2.2");
        mock.expectAndReturn("next", false);
        mockDataSet = (DataSet) mock.proxy();
    }

    public void testDataSetToMap() throws Exception
    {
        DataSetToMaps transformer = new DataSetToMaps();
        List result = (List) transformer.transform(mockDataSet);

        Assert.assertEquals(2, result.size());
        Map row = (Map)result.get(0);
        Assert.assertEquals("value1.1", row.get("col1"));
        
        row = (Map)result.get(1);
        Assert.assertEquals("value2.2", row.get("col2"));
    }

    public void testFilter() throws Exception
    {
        DataSetToMaps transformer = new FilteringDataSetToMaps();
        List result = (List) transformer.transform(mockDataSet);

        Assert.assertEquals(1, result.size());
        Map row = (Map)result.get(0);
        // result set may only contain second row, first one was filtered by transformer
        Assert.assertEquals("value2.1", row.get("col1"));
    }
}

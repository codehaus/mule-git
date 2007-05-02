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

import com.mockobjects.dynamic.Mock;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import net.sf.pzfilereader.DataSet;

public class DataSetToMapTestCase extends TestCase
{
    public void testDataSetToMap() throws Exception
    {
        Mock mock = new Mock(DataSet.class);
        mock.expectAndReturn("getColumns", new String[] {"col1", "col2"});
        
        DataSet dataSet = (DataSet)mock.proxy();
        DataSetToMap transformer = new DataSetToMap();
        List result = (List)transformer.transform(dataSet);
        
        Assert.assertEquals(2, result.size());
    }
    
    public void testFilter()
    {
        Assert.fail("implement me");
    }
}



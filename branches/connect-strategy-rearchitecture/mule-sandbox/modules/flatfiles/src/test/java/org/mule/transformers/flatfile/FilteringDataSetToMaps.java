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

import java.util.Map;

import net.sf.pzfilereader.DataSet;

public class FilteringDataSetToMaps extends DataSetToMaps
{
    protected boolean acceptRow(Map row, DataSet dataSet)
    {
        // filter out first row of the mock result set
        if (row.get("col1").toString().indexOf("1.1") > -1) 
        {
            return false;
        }
        return true;
    }
}



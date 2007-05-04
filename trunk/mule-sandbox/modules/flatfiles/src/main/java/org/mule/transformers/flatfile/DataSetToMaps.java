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

import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.pzfilereader.DataSet;

public class DataSetToMaps extends AbstractTransformer
{
    public DataSetToMaps()
    {
        super();
        this.registerSourceType(DataSet.class);
        this.setReturnClass(List.class);
    }

    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        if (src == null)
        {
            return null;
        }

        DataSet dataSet = (DataSet) src;
        String[] headers = dataSet.getColumns();
        List rowList = new ArrayList(dataSet.getRowCount());

        while (dataSet.next())
        {
            Map row = new HashMap();

            for (int i = 0; i < headers.length; i++)
            {
                String key = headers[i];
                String value = dataSet.getString(key);
                row.put(key, value);
            }

            if (this.acceptRow(row, dataSet))
            {
                rowList.add(row);
            }
        }

        return rowList;
    }

    /**
     * Subclasses can override this method to skip rows from the final list.
     * <p>
     * This implementation always returns <code>true</code> to indicate that all
     * rows should be included in the final list.
     * 
     * @param row
     * @param dataSet
     * @return <code>true</code> to include <code>row</code> into the final list
     *         (the default), <code>false</code> otherwise.
     */
    protected boolean acceptRow(Map row, DataSet dataSet)
    {
        return true;
    }

}

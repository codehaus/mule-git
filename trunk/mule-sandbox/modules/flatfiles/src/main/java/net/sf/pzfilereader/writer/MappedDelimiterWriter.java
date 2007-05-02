/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package net.sf.pzfilereader.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.pzfilereader.structure.ColumnMetaData;
import net.sf.pzfilereader.util.PZConstants;

public class MappedDelimiterWriter extends AbstractDelimiterWriter implements PZWriter
{
    public MappedDelimiterWriter(Map parsedMapping, OutputStream output, char delimiter, char qualifier)
        throws IOException
    {
        super(output, delimiter, qualifier);

        List columns = (List) parsedMapping.get(PZConstants.DETAIL_ID);
        Iterator columnIter = columns.iterator();
        while (columnIter.hasNext())
        {
            ColumnMetaData element = (ColumnMetaData) columnIter.next();
            super.doAddColumnTitle(element.getColName());
        }
        // write the column headers
        this.nextRecord();
    }
}

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

import java.io.OutputStream;

/**
 * This class exists only to expose the implementation of the <code>DelimiterPZWriter</code>
 * interface.
 */
public class DefaultDelimiterWriter extends AbstractDelimiterWriter implements DelimiterPZWriter
{
    public DefaultDelimiterWriter(OutputStream output, char delimiter, char qualifier)
    {
        super(output, delimiter, qualifier);
    }

    public void addColumnTitle(String columnTitle)
    {
        super.doAddColumnTitle(columnTitle);
    }
}

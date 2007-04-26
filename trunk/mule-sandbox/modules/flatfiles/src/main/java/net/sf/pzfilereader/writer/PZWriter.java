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

public interface PZWriter
{
    /** put writer into header mode. TODO: how to handle multiple header lines? */
    void printHeader();
    /** put writer into footer mode. TODO: how to handle multiple footer lines? */
    void printFooter();

    void addRecordEntry(String columnName, Object value);
    void nextRecord() throws IOException;

    void flush() throws IOException;
    void close() throws IOException;
}



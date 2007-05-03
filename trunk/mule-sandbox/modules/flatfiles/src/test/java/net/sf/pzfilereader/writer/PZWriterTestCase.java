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

import junit.framework.TestCase;

public class PZWriterTestCase extends TestCase
{
    private String lineSeparator = System.getProperty("line.separator");

    protected String joinLines(String line1, String line2)
    {
        if (line1 == null)
        {
            throw new IllegalArgumentException("parameter string1 may not be null");
        }
        
        StringBuffer result = new StringBuffer(line1);
        result.append(lineSeparator);
        if (line2 != null)
        {
            result.append(line2);
            result.append(lineSeparator);
        }
        
        return result.toString();
    }
    
    protected String normalizeLineEnding(String line)
    {
        return this.joinLines(line, null);
    }
}



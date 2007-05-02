/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package net.sf.pzfilereader.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StringUtilTestCase extends TestCase
{
    public void testRightPad()
    {
        String result = StringUtils.rightPad("foo", 5, ' ');
        Assert.assertEquals("foo  ", result);
    }
     
    public void testRightPadExactLength()
    {
        String result = StringUtils.rightPad("foo", 3, ' ');
        Assert.assertEquals("foo", result);
    }
    
    public void testRightPadTooTooShort()
    {
        String result = StringUtils.rightPad("foo", 2, ' ');
        Assert.assertEquals("foo", result);
    }
    
    public void testRightPadNullInput()
    {
        String result = StringUtils.rightPad(null, 3, ' ');
        Assert.assertNull(result);
    }
}



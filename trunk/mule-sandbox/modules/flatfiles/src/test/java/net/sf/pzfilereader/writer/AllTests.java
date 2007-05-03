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

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite("Test for net.sf.pzfilereader.writer");
        //$JUnit-BEGIN$
        suite.addTestSuite(FixedLengthWriterTestCase.class);
        suite.addTestSuite(DelimiterWriterTestCase.class);
        //$JUnit-END$
        return suite;
    }

}

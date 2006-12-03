/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.providers.file;

import org.mule.providers.file.filters.FilenameRegexFilter;
import org.mule.tck.AbstractMuleTestCase;

public class FilenameRegexFilterTestCase extends AbstractMuleTestCase
{
    public void testFilenameRegexFilter()
    {
        FilenameRegexFilter filter = new FilenameRegexFilter();
        filter.setPattern("[0-9]*_test.csv");
        filter.setCaseSensitive(true);
        String fileNameMatch = "20060101_test.csv";
        String fileNameNoMatch1 = "20060101_test_test.csv";
        String fileNameNoMatch2 = "20060101_TEST.csv";

        assertNotNull(filter.getPattern());
        assertTrue(filter.accept(fileNameMatch));
        assertFalse(filter.accept(fileNameNoMatch1));
        assertFalse(filter.accept(fileNameNoMatch2));

        filter.setCaseSensitive(false);
        assertTrue(filter.accept(fileNameMatch));
        assertFalse(filter.accept(fileNameNoMatch1));
        assertTrue(filter.accept(fileNameNoMatch2));
    }
}

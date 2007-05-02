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

import java.util.Arrays;

public class StringUtils
{
    /**
     * Never create instances of this class.
     */
    private StringUtils()
    {
        // no instances, please
    }

    public static String rightPad(String string, int size, char padChar)
    {
        if (string == null)
        {
            return null;
        }

        int strlen = string.length();
        if (size - strlen <= 0)
        {
            // return the original String when possible
            return string;
        }

        char[] retValue = new char[size];

        /*
         * Copy the contents of the original string; for Strings up to ~8-10
         * characters this loop is consistently faster than String.getChars(). Short
         * Strings are usually the majority of values in fixed-width data columns.
         */
        for (int i = 0; i < strlen; i++)
        {
            retValue[i] = string.charAt(i);
        }

        // fill with pad characters
        Arrays.fill(retValue, strlen, size, padChar);

        return new String(retValue);
    }

}

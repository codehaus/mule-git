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


public class StringUtils
{
    public static String rightPad(String string, int size, char padChar) 
    {
        if (string == null) 
        {
            return null;
        }
        
        int strlen = string.length();
        int pads = size - strlen;
        if (pads <= 0) 
        {
            return string; // returns original String when possible
        }

        char[] retValue = new char[size];
        
        // copy over the contents of string
        for (int i = 0; i < strlen; i++)
        {
            retValue[i] = string.charAt(i);
        }
        
        // fill with pad chars
        for (int i = strlen; i < size; i++)
        {
            retValue[i] = padChar;
        }
        
        return new String(retValue);
    }
        
    /**
     * Never create instances of this class.
     */
    private StringUtils() 
    { 
        // no instances, please 
    }
}



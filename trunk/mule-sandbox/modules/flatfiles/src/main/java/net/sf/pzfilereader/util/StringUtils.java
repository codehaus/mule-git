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
    // 100000 iterations: 179 ms
    public static String XrightPad(String string, int size, char padChar) 
    {
        if (string == null) 
        {
            return null;
        }
        int pads = size - string.length();
        if (pads <= 0) 
        {
            return string; // returns original String when possible
        }

        char[] padding = new char[pads];
        Arrays.fill(padding, padChar);
        
        return string.concat(new String(padding));
    }

    // 100000 iterations: 247 ms
    public static String XXrightPad(String string, int size, char padChar) 
    {
        if (string == null) 
        {
            return null;
        }
        int pads = size - string.length();
        if (pads <= 0) 
        {
            return string; // returns original String when possible
        }

        StringBuffer buffer = new StringBuffer(string);
        char[] padding = new char[pads];
        Arrays.fill(padding, padChar);
        
        return buffer.append(padding).toString();
    }

    // 100000 iterations: 120 ms
    public static String XXXrightPad(String string, int size, char padChar) 
    {
        if (string == null) 
        {
            return null;
        }
        
        int pads = size - string.length();
        if (pads <= 0) 
        {
            return string; // returns original String when possible
        }

        char[] retValue = new char[size];
        
        // copy over the contents of string
        int strlen = string.length();
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
    
    // 100000 iterations: 172 ms
    public static String rightPad(String string, int size, char padChar) 
    {
        return org.apache.commons.lang.StringUtils.rightPad(string, size, padChar);
    }
    
    /**
     * Never create instances of this class.
     */
    private StringUtils() 
    { 
        // no instances, please 
    }
}



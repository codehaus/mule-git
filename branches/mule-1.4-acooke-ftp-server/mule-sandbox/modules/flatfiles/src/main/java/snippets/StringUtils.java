/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package snippets;

import java.util.Arrays;

public class StringUtils extends Object
{
    private static final int TEST_LOOP_COUNT = 1000000;
    private static final int PADDING_SIZE = 100;
    
    public static void main(String[] args)
    {
        System.out.println("JDK: " + System.getProperty("java.version"));
        
        long before = System.currentTimeMillis();
        for (int i = 0; i < TEST_LOOP_COUNT; i++)
        {
            rightPadWithFillAndConcat("foo", PADDING_SIZE, ' ');
        }
        System.out.println("rightPadWithFillAndConcat: " + (System.currentTimeMillis() - before) + " ms");

        before = System.currentTimeMillis();
        for (int i = 0; i < TEST_LOOP_COUNT; i++)
        {
            rightPadWithStringBuffer("foo", PADDING_SIZE, ' ');
        }
        System.out.println("rightPadWithStringBuffer: " + (System.currentTimeMillis() - before) + " ms");

        before = System.currentTimeMillis();
        for (int i = 0; i < TEST_LOOP_COUNT; i++)
        {
            rightPadWithCommonsLang("foo", PADDING_SIZE, ' ');
        }
        System.out.println("rightPadWithCommonsLang: " + (System.currentTimeMillis() - before) + " ms");

        before = System.currentTimeMillis();
        for (int i = 0; i < TEST_LOOP_COUNT; i++)
        {
            rightPadWithCharArrayCharAt("foo", PADDING_SIZE, ' ');
        }
        System.out.println("rightPadWithCharArrayCharAt: " + (System.currentTimeMillis() - before) + " ms");

        before = System.currentTimeMillis();
        for (int i = 0; i < TEST_LOOP_COUNT; i++)
        {
            rightPadWithCharArrayArraycopy("foo", PADDING_SIZE, ' ');
        }
        System.out.println("rightPadWithCharArrayArraycopy: " + (System.currentTimeMillis() - before) + " ms");

        before = System.currentTimeMillis();
        for (int i = 0; i < TEST_LOOP_COUNT; i++)
        {
            rightPadWithCharArrayGetChars("foo", PADDING_SIZE, ' ');
        }
        System.out.println("rightPadWithCharArrayGetChars: " + (System.currentTimeMillis() - before) + " ms");
    }

    public static String rightPadWithFillAndConcat(String string, int size, char padChar) 
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

    public static String rightPadWithStringBuffer(String string, int size, char padChar) 
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

    public static String rightPadWithCharArrayCharAt(String string, int size, char padChar) 
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
    
    public static String rightPadWithCommonsLang(String string, int size, char padChar) 
    {
        return org.apache.commons.lang.StringUtils.rightPad(string, size, padChar);
    }
    
    public static String rightPadWithCharArrayArraycopy(String string, int size, char padChar) 
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
        char[] stringContent = string.toCharArray();
        System.arraycopy(stringContent, 0, retValue, 0, stringContent.length);
                
        // fill with pad chars
        for (int i = stringContent.length; i < size; i++)
        {
            retValue[i] = padChar;
        }
        
        return new String(retValue);
    }

    public static String rightPadWithCharArrayGetChars(String string, int size, char padChar) 
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
        string.getChars(0, strlen, retValue, 0);
                
        // fill with pad chars
        for (int i = strlen; i < size; i++)
        {
            retValue[i] = padChar;
        }
        
        return new String(retValue);
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.file.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilenameRegexFilter extends FilenameWildcardFilter
{
    protected Pattern[] compiledPatterns = null;

    public boolean accept(Object object)
    {
        if (object == null)
        {
            return false;
        }

        boolean match = false;
        for (int i = 0; i < getCompiledPatterns().length; i++)
        {
            Pattern pattern = getCompiledPatterns()[i];

            String string = object.toString();

            /* Determine if there is an exact match. */
            Matcher matcher = pattern.matcher(string);
            match = matcher.matches();

            if (match)
            {
                // we found a match, bail
                break;
            }
        }

        return match;
    }

    public void setCaseSensitive(boolean caseSensitive)
    {
        super.setCaseSensitive(caseSensitive);
        this.compiledPatterns = null;
    }

    protected Pattern[] getCompiledPatterns()
    {
        if (compiledPatterns == null)
        {
            /* Patterns not set, compile them. */

            compiledPatterns = new Pattern[patterns.length];

            Pattern pattern;
            for (int i = 0; i < patterns.length; i++)
            {
                if (!isCaseSensitive())
                {
                    /* Add insensitive option if set in the configuration. */
                    pattern = Pattern.compile(patterns[i],
                                              Pattern.CASE_INSENSITIVE);
                }
                else
                {
                    pattern = Pattern.compile(patterns[i]);
                }
                compiledPatterns[i] = pattern;
            }
        }
        return compiledPatterns;
    }
}


/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.routing.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.i18n.CoreMessages;
import org.mule.transformers.simple.ByteArrayToString;
import org.mule.umo.UMOFilter;
import org.mule.umo.UMOMessage;
import org.mule.umo.transformer.TransformerException;

import java.util.regex.Pattern;

/**
 * <code>RegExFilter</code> is used to match a String argument against a regular
 * expression.
 */

public class RegExFilter implements UMOFilter, ObjectFilter
{
    protected transient Log logger = LogFactory.getLog(getClass());

    private Pattern pattern;

    public RegExFilter()
    {
        super();
    }

    public RegExFilter(String pattern)
    {
        this.pattern = Pattern.compile(pattern);
    }

    public boolean accept(UMOMessage message)
    {
        return accept(message.getPayload());
    }

    public boolean accept(Object object)
    {
        if (object == null)
        {
            return false;
        }

        Object tempObject = object;

        // check whether the payload is a byte[] or a char[]. If it is, then it has 
        // to be transformed otherwise the toString will not represent the true contents
        // of the payload for the RegEx filter to use.
        if (object instanceof byte[])
        {
            ByteArrayToString transformer = new ByteArrayToString();
            try
            {
                object = transformer.transform(object);
            }
            catch (TransformerException e)
            {
            	logger.warn(CoreMessages.transformFailedBeforeFilter(), e);
                // revert transformation
                object = tempObject;
            }
        }
        else if (object instanceof char[])
        {
            object = new String((char[]) object);
        }

        return (pattern != null ? pattern.matcher(object.toString()).find() : false);
    }

    public String getPattern()
    {
        return (pattern == null ? null : pattern.pattern());
    }

    public void setPattern(String pattern)
    {
        this.pattern = (pattern != null ? Pattern.compile(pattern) : null);
    }

}

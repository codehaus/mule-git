/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.xml.util.properties;

import org.mule.MuleRuntimeException;
import org.mule.umo.UMOMessage;
import org.mule.util.properties.PropertyExtractor;
import org.mule.xml.i18n.XmlMessages;

import java.util.Map;
import java.util.WeakHashMap;

import org.jaxen.JaxenException;
import org.jaxen.XPath;

/**
 * Provides a base class for XPath property extractors. The XPath engine used is jaxen (http://jaxen.org) which supports
 * XPath queries on other object models such as JavaBeans as well as Xml
 */
public abstract class AbstractXPathPropertyExtractor implements PropertyExtractor
{
    private Map cache = new WeakHashMap(8);

    /** {@inheritDoc} */
    public Object getProperty(String expression, Object message)
    {
        try
        {
            if(message instanceof UMOMessage)
            {
                message = ((UMOMessage)message).getPayload();
            }
            XPath xpath = getXPath(expression, message);

            Object result = xpath.selectSingleNode(message);
            result = extractResultFromNode(result);
            return result;
        }
        catch (JaxenException e)
        {
            throw new MuleRuntimeException(XmlMessages.failedToProcessXPath(expression), e);
        }
    }

    /** {@inheritDoc} */
    public final void setName(String name)
    {
        throw new UnsupportedOperationException("setName");
    }

    protected XPath getXPath(String expression, Object object) throws JaxenException
    {
        XPath xpath = (XPath)cache.get(expression);
        if(xpath==null)
        {
            xpath = createXPath(expression, object);
            cache.put(expression, xpath);
        }
        return xpath;
    }

    protected abstract XPath createXPath(String expression, Object object) throws JaxenException;

    protected abstract Object extractResultFromNode(Object result);
}

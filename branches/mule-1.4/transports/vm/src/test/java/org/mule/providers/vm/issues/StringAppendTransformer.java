/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.vm.issues;

import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

public class StringAppendTransformer extends AbstractTransformer
{

    public static final String DEFAULT_TEXT = " transformed";
    private String text;

    public StringAppendTransformer()
    {
        this(DEFAULT_TEXT);
    }

    public StringAppendTransformer(String text)
    {
        setText(text);
    }

    protected Object doTransform(Object src, String encoding) throws TransformerException
    {
        return ((String) src) + getText();
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
    
}

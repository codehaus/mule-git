/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util.expression;

import org.mule.api.transport.MessageAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Looks up the property on the message using the property name given.
 *
 * @see MessageHeadersListExpressionEvaluator
 * @see MessageHeadersExpressionEvaluator
 * @see ExpressionEvaluator
 * @see ExpressionEvaluatorManager
 */
public class MessageHeaderExpressionEvaluator implements ExpressionEvaluator
{
    public static final String NAME = "header";

    public Object evaluate(String expression, Object message)
    {
        if (message instanceof MessageAdapter)
        {
            return ((MessageAdapter) message).getProperty(expression);
        }
        return null;
    }

    /** {@inheritDoc} */
    public String getName()
    {
        return NAME;
    }

    /** {@inheritDoc} */
    public void setName(String name)
    {
        throw new UnsupportedOperationException("setName");
    }
}
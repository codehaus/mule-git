/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.transformer.simple;

import org.mule.api.UMOMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.impl.transformer.AbstractMessageAwareTransformer;

import org.apache.commons.lang.SerializationUtils;

/** TODO */
public class UMOMessageToByteArray extends AbstractMessageAwareTransformer
{
    public UMOMessageToByteArray()
    {
        registerSourceType(UMOMessage.class);
        setReturnClass(byte[].class);
    }

    public Object transform(UMOMessage message, String outputEncoding) throws TransformerException
    {
        return SerializationUtils.serialize(message);
    }
}

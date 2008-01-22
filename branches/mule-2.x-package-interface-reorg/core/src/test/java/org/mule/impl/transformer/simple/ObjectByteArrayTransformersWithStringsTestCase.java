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

import org.mule.api.transformer.UMOTransformer;
import org.mule.impl.transformer.simple.ByteArrayToObject;
import org.mule.impl.transformer.simple.ObjectToByteArray;
import org.mule.tck.AbstractTransformerTestCase;

public class ObjectByteArrayTransformersWithStringsTestCase extends AbstractTransformerTestCase
{
    private String testObject = "test";

    public UMOTransformer getTransformer() throws Exception
    {
        return new ObjectToByteArray();
    }

    public UMOTransformer getRoundTripTransformer() throws Exception
    {
        return new ByteArrayToObject();
    }

    public Object getTestData()
    {
        return testObject;
    }

    public Object getResultData()
    {
        return testObject.getBytes();
    }
}

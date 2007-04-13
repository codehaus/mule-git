/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo.transformers;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.umo.transformer.UMOTransformer;

public class FooTransformersTestCase extends AbstractTransformerTestCase
{

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getTestData()
     */
    public Object getTestData()
    {
        //Todo create a test data object that will be passed into the transformer
        throw new UnsupportedOperationException("getResultData");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getResultData()
     */
    public Object getResultData()
    {
        try {
            //Todo Return the result data expected once the getTestData() value has been transformed
            throw new UnsupportedOperationException("getResultData");
        }
        catch (Exception ex) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getTransformers()
     */
    public UMOTransformer getTransformer()
    {
        UMOTransformer t = new FooMessageToObject();
        // Set the correct return class for this roundtrip test
        t.setReturnClass(this.getResultData().getClass());
        return t;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.tck.AbstractTransformerTestCase#getRoundTripTransformer()
     */
    public UMOTransformer getRoundTripTransformer()
    {
        UMOTransformer t = new ObjectToFooMessage();
        // Set the correct return class for this roundtrip test
        t.setReturnClass(this.getTestData().getClass());
        return t;
    }

}

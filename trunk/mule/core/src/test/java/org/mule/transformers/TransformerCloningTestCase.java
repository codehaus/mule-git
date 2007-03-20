/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.tck.MuleTestUtils;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.transformer.UMOTransformer;

public class TransformerCloningTestCase extends AbstractTransformerTestCase
{

    public static class NonAbstractTransformer extends AbstractTransformer
    {
        protected Object doTransform(Object src, String encoding) throws TransformerException
        {
            // nothing to do here
            return src;
        }
    }

    public UMOTransformer getTransformer() throws Exception
    {
        NonAbstractTransformer t1 = new NonAbstractTransformer();
        t1.setName("abstract");
        t1.setReturnClass(this.getClass());
        t1.registerSourceType(this.getClass());

        NonAbstractTransformer t2 = new NonAbstractTransformer();
        t2.setName("nextTransformer");
        t2.setReturnClass(this.getClass());
        t2.registerSourceType(this.getClass());
        t2.registerSourceType(StringBuffer.class);
        
        t1.setNextTransformer(t2);
        t1.setEndpoint(MuleTestUtils.getTestEndpoint("abstract", UMOImmutableEndpoint.ENDPOINT_TYPE_SENDER));
        t1.initialise();
        return t1;
    }

    public UMOTransformer getRoundTripTransformer() throws Exception
    {
        return null;
    }

    public Object getTestData()
    {
        return this;
    }

    public Object getResultData()
    {
        return this;
    }

    // @Override
    protected void doTestClone(UMOTransformer original, UMOTransformer clone) throws Exception
    {
        super.doTestClone(original, clone);

        NonAbstractTransformer t1 = (NonAbstractTransformer) original;
        NonAbstractTransformer t2 = (NonAbstractTransformer) clone;

        // name must be equal
        assertEquals("name", t1.name, t2.name);

        // returnClass must be equal
        assertEquals("returnClass", t1.returnClass, t2.returnClass);

        // sourceTypes must be a copy
        assertNotSame("sourceTypes", t1.sourceTypes, t2.sourceTypes);
        assertEquals("sourceTypes", t1.sourceTypes, t2.sourceTypes);

        // endpoint must be a copy
        assertNotSame("endpoint", t1.endpoint, t2.endpoint);

        // nextTransformer must be a copy the entire chain!)
        assertNotSame("nextTransformer", t1.nextTransformer, t2.nextTransformer);
    }

}

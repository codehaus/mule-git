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
import org.mule.transformers.AbstractTransformer;
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
        NonAbstractTransformer t = new NonAbstractTransformer();
        t.setName("abstract");
        t.setEndpoint(MuleTestUtils.getTestEndpoint("abstract", UMOImmutableEndpoint.ENDPOINT_TYPE_SENDER));
        t.initialise();
        return t;
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
    public boolean compareClone(UMOTransformer original, UMOTransformer clone)
    {
        // TODO MULE-1511: need to access the clone's private parts but cannot
        // because we're in a different package..

        /*
        NonAbstractTransformer t1 = (NonAbstractTransformer)original;
        NonAbstractTransformer t2 = (NonAbstractTransformer)clone;

        // sourceTypes must be a copy
        if (t1.sourceTypes == t2.sourceTypes)
        {
            return false;
        }
        */

        return super.compareClone(original, clone);
    }

}

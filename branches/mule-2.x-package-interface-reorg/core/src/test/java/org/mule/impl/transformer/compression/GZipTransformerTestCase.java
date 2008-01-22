/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.transformer.compression;

import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transformer.UMOTransformer;
import org.mule.impl.transformer.compression.GZipCompressTransformer;
import org.mule.impl.transformer.compression.GZipUncompressTransformer;
import org.mule.tck.AbstractTransformerTestCase;
import org.mule.util.compression.GZipCompression;

import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

public class GZipTransformerTestCase extends AbstractTransformerTestCase
{
    protected GZipCompression strat;

    // @Override
    protected void doSetUp() throws Exception
    {
        strat = new GZipCompression();
    }

    public Object getResultData()
    {
        try
        {
            return strat.compressByteArray(SerializationUtils.serialize((Serializable) this.getTestData()));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
            return null;
        }
    }

    public Object getTestData()
    {
        return "the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog the quick brown fox jumped over the lazy dog";
    }

    public UMOTransformer getTransformer()
    {
        return new GZipCompressTransformer();
    }

    public UMOTransformer getRoundTripTransformer()
    {
        GZipUncompressTransformer transformer = new GZipUncompressTransformer();
        transformer.setReturnClass(String.class);

        try
        {
            transformer.initialise();
        }
        catch (InitialisationException e)
        {
            fail(e.getMessage());
        }

        return transformer;
    }

}

/*
 * $Id: XmlObjectTransformersTestCase.java 3921 2006-11-18 18:19:12Z holger $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.transformers;

import java.util.List;

import org.mule.tck.AbstractTransformerTestCase;
import org.mule.transformers.csv.CSVToMapList;
import org.mule.transformers.csv.MapListToCSV;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.IOUtils;

public class CSVTransformersTestCase extends AbstractTransformerTestCase
{
    private String srcData;
    private String resultData;

    public CSVTransformersTestCase()
    {
    }

    protected void doSetUp() throws Exception
    {
        srcData = IOUtils.getResourceAsString("addresses.csv", getClass());
        resultData = IOUtils.getResourceAsString("addresses.csv", getClass());
    }

    public void testTransform() throws Exception
    {
        Object result = this.getTransformer().transform(getTestData());
        assertNotNull(result);
        assert(result instanceof List);
        assertEquals(((List)result).size(), 6);
    }

    /**
     * Note: testing with input data that has quote won't work because
     * OpenCSV will escape them with another quote. You can't turn this off
     * (at least in this version), so the last assert will always fail.
     */
    public void testRoundtripTransform() throws Exception
    {
        Object outbound = this.getTransformer().transform(getTestData());
        assertNotNull(outbound);
        Object inbound = this.getRoundTripTransformer().transform(outbound);
        assertNotNull(inbound);
        assert(compareResults(inbound, getTestData()));
    }

    public UMOTransformer getTransformer() throws Exception
    {
        CSVToMapList t = new CSVToMapList();
        return t;
    }

    public UMOTransformer getRoundTripTransformer() throws Exception
    {
        MapListToCSV t = new MapListToCSV();
        t.setQuoteCharacter('\u0000');
        return t;
    }

    public Object getTestData()
    {
        return srcData;
    }

    public Object getResultData()
    {
        return resultData;
    }

}


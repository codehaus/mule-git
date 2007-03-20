/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.xml;

import org.mule.transformers.xml.XsltTransformer;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.IOUtils;

public class XsltTransformerTestCase extends AbstractXmlTransformerTestCase
{

    private String srcData;
    private String resultData;

    // @Override
    protected void doSetUp() throws Exception
    {
        srcData = IOUtils.getResourceAsString("cdcatalog.xml", getClass());
        resultData = IOUtils.getResourceAsString("cdcatalog.html", getClass());
    }

    public UMOTransformer getTransformer() throws Exception
    {
        XsltTransformer transformer = new XsltTransformer();
        transformer.setXslFile("cdcatalog.xsl");
        transformer.initialise();
        return transformer;
    }

    public UMOTransformer getRoundTripTransformer() throws Exception
    {
        return null;
    }

    // @Override
    public void testRoundtripTransform() throws Exception
    {
        // disable this test
    }

    public Object getTestData()
    {
        return srcData;
    }

    public Object getResultData()
    {
        return resultData;
    }

    // @Override
    public boolean compareClone(UMOTransformer original, UMOTransformer clone)
    {
        // TODO MULE-1511: need to access the clone's private parts but cannot
        // because we're in a different package..

        /*
        XsltTransformer t1 = (XsltTransformer)original;
        XsltTransformer t2 = (XsltTransformer)clone;

        // The transformerPool must be different 
        if (t1.transformerPool == t2.transformerPool)
        {
            return false;
        }
        */

        return super.compareClone(original, clone);
    }

}

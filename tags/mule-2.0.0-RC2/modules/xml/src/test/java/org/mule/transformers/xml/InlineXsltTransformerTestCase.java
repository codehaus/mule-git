/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transformers.xml;

import org.mule.api.transformer.Transformer;
import org.mule.util.IOUtils;

public class InlineXsltTransformerTestCase extends AbstractXmlTransformerTestCase
{

    private String srcData;
    private String resultData;

    protected void doSetUp() throws Exception
    {
        srcData = IOUtils.getResourceAsString("simple.xml", getClass());
        resultData = IOUtils.getResourceAsString("simple-out.xml", getClass());
    }

    public Transformer getTransformer() throws Exception
    {
        XsltTransformer transformer = new XsltTransformer();
        transformer.setXslt("<?xml version='1.0'?>\n"
                            + "<xsl:stylesheet version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>\n"
                            + "<xsl:output method='xml'/>\n" + "<xsl:template match='/'>\n"
                            + "  <some-xml>\n" + "    <xsl:copy-of select='.'/>\n" + "  </some-xml>\n"
                            + "</xsl:template>\n" + "</xsl:stylesheet>");
        transformer.setReturnClass(String.class);
        transformer.initialise();
        return transformer;
    }

    public Transformer getRoundTripTransformer() throws Exception
    {
        return null;
    }

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
}

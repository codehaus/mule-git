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

import org.mule.tck.FunctionalTestCase;

public class XmlNamespaceHandlerTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "xml-namespace-test.xml";
    }

    public void testXsltConfiguration() throws Exception
    {
        XsltTransformer trans = (XsltTransformer)muleContext.getRegistry().lookupTransformer("test1");
        assertNotNull(trans);
        assertEquals(4, trans.getMaxActiveTransformers());
        assertEquals(4, trans.getMaxIdleTransformers());
        assertNotNull(trans.getContextProperties());
        assertEquals(1, trans.getContextProperties().size());
        assertEquals("${header:Welcome}", trans.getContextProperties().get("echo"));
        assertEquals(String.class, trans.getReturnClass());
        assertNull(trans.getXslFile());
        assertNotNull(trans.getXslt());
        assertTrue(trans.getXslt().indexOf("<xsl:stylesheet") > -1);
    }
}

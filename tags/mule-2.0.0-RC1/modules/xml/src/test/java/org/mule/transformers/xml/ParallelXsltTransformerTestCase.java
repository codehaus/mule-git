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

import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.transformer.UMOTransformer;
import org.mule.util.IOUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.custommonkey.xmlunit.XMLAssert;

public class ParallelXsltTransformerTestCase extends AbstractMuleTestCase
{
    private String srcData;
    private String resultData;
    private Collection actualResults = Collections.synchronizedCollection(new LinkedList());

    // @Override
    protected void doSetUp() throws Exception
    {
        srcData = IOUtils.toString(IOUtils.getResourceAsStream("cdcatalog-utf-8.xml", getClass()), "UTF-8");
        resultData = IOUtils.toString(IOUtils.getResourceAsStream("cdcatalog-utf-8.html", getClass()),
            "UTF-8");
    }

    public UMOTransformer getTransformer() throws Exception
    {
        XsltTransformer transformer = new XsltTransformer();
        transformer.setReturnClass(String.class);
        transformer.setXslFile("cdcatalog.xsl");
        transformer.initialise();
        return transformer;
    }

    int running = 0;

    public synchronized void signalStarted()
    {
        ++running;
    }

    public synchronized void signalDone()
    {
        if (--running == 0) this.notify();
    }

    public void testParallelTransformation() throws Exception
    {
        final UMOTransformer transformer = getTransformer();
        
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < getParallelThreadCount(); ++i)
        {
            new Thread(new Runnable()
            {
                public void run()
                {
                    signalStarted();
                    for (int j = 0; j < getCallsPerThread(); ++j)
                    {
                        try
                        {
                            actualResults.add(transformer.transform(srcData));
                        }
                        catch (TransformerException e)
                        {
                            actualResults.add(e);
                        }
                    }
                    signalDone();
                }
            }).start();
        }

        checkResult();

        long endTime = System.currentTimeMillis();

        if (logger.isDebugEnabled())
        {
            logger.debug("Parallel transformations in " + getParallelThreadCount() + " threads with "
                            + getCallsPerThread() + " calls/thread took " + (endTime - startTime) + " ms.");
        }
    }

    private synchronized void checkResult() throws Exception
    {
        this.wait();
        Object expectedResult = resultData;
        for (Iterator it = actualResults.iterator(); it.hasNext();)
        {
            Object result = it.next();
            if (result instanceof Exception) throw (Exception)result;

            if (expectedResult instanceof String && result instanceof String)
            {
                XMLAssert.assertXMLEqual((String)expectedResult, (String)result);
            }
            else
            {
                XMLAssert.assertEquals(expectedResult, result);
            }
        }
    }

    private int getParallelThreadCount()
    {
        return 10;
    }

    private int getCallsPerThread()
    {
        return 50;
    }

}

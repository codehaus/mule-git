/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.providers.jms;

/**
 * Comment
 */
public class JmsXaTransactionNoneTestCase extends AbstractJmsFunctionalTestCase
{

    ControlCounter blackBoxTx = new ControlCounter(99, 0, 99);

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }

    public void testNoneTransaction() throws Exception
    {
        this.runAsynchronousDispatching();
        //getControlCounter().verifyXaTx();
    }


    protected String getConfigResources()
    {
        return "jms-xa-tx-NONE.xml";
    }

}

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
public class JmsXaTransactionAlwaysJoinTestCase extends AbstractJmsFunctionalTestCase
{
    ControlCounter blackBoxTx = new ControlCounter(99, 1, 99, 1);

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }

    public void testAlwaysJoin() throws Exception
    {
        this.runAsynchronousDispatching();
        //getControlCounter().verifyXaTx();
        getControlCounter().getExceptionInfo().verify();
    }


    protected String getConfigResources()
    {
        return "jms-xa-tx-ALWAYS_JOIN.xml";
    }

}

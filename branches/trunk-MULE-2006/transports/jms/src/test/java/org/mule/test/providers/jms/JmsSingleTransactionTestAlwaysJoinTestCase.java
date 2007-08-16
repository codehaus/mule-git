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
public class JmsSingleTransactionTestAlwaysJoinTestCase extends AbstractJmsFunctionalTestCase
{

    private final ControlCounter blackBoxTx = new ControlCounter(0, 0, 0, 1);

    protected String getConfigResources()
    {
        return "jms-single-tx-ALWAYS_JOIN.xml";
    }

    public void testAlwaysJoin() throws Exception
    {

        this.runAsynchronousDispatching();
        getControlCounter().verifySingleTx();
        getControlCounter().getExceptionInfo().verify();
    }

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }


}

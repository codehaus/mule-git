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

import org.mule.umo.UMOMessage;

/**
 * TODO: Incorrect test
 * Transactions don't sharing between descriptors
 */
public class JmsSingleTransactionBeginOrJoinTestCase extends AbstractJmsFunctionalTestCase
{

    private final ControlCounter blackBoxTx = new ControlCounter(1, 1, 0);

    protected String getConfigResources()
    {
        return "jms-single-tx-BEGIN_OR_JOIN.xml";
    }

    public void testBeginOrJoin() throws Exception
    {
        UMOMessage message = super.runAsynchronousDispatching();
        getControlCounter().verifySingleTx();
        assertNull(message.getExceptionPayload());
    }

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }


}

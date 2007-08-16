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
 * Comment
 */
public class JmsXaTransactionBeginOrJoinTestCase extends AbstractJmsFunctionalTestCase
{

    ControlCounter blackBoxTx = new ControlCounter(99, 1, 99);

    public void testBeginOrJoin() throws Exception
    {
        UMOMessage message = super.runAsynchronousDispatching();
        //getControlCounter().verifyXaTx();
        assertNull(message.getExceptionPayload());
    }

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }

    protected String getConfigResources()
    {
        return "jms-xa-tx-BEGIN_OR_JOIN.xml";
    }


}

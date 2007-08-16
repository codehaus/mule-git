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
public class JmsXaTransactionAlwaysBeginTestCase extends AbstractJmsFunctionalTestCase
{
    ControlCounter blackBoxTx = new ControlCounter(99, 2, 99);

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }

    public void testAlwaysBegin() throws Exception
    {
        UMOMessage message = super.runAsynchronousDispatching();
        //getControlCounter().verifyXaTx();
        assertNull(message.getExceptionPayload());
    }


    protected String getConfigResources()
    {
        return "jms-xa-tx-ALWAYS_BEGIN.xml";
    }
}

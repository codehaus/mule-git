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
public class JmsXaTransactionJoinIfPossibleTestCase extends AbstractJmsFunctionalTestCase
{
    ControlCounter blackBoxTx = new ControlCounter(99, 2, 99);
    private static final String DEFAULT_OUTPUT_MESSAGE_NOTX = "OUTPUT MESSAGE-NoTx";
    private static final String DEFAULT_OUTPUT_MESSAGE_TX = "OUTPUT MESSAGE-Tx";
    private static final String DEFUALT_OUTPUT_QUEUE_TX = "vm://out-wtx";


    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }

    public void testJoinIfPossible() throws Exception
    {
        getClient().dispatch(DEFAULT_INPUT_QUEUE, DEFAULT_MESSAGE, null);

        UMOMessage result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertNull(result.getExceptionPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE_TX, result.getPayload());

        /*result = getClient().receive(DEFUALT_OUTPUT_QUEUE_TX, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertNull(result.getExceptionPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE_NOTX, result.getPayload());
        */

        //getControlCounter().verifyXaTx();
    }


    protected String getConfigResources()
    {
        return "jms-xa-tx-JOIN_IF_POSSIBLE.xml";
    }

}

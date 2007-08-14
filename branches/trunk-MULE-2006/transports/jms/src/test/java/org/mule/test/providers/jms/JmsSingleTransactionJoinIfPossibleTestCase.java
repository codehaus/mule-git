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

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;

/**
 * Comment
 */
public class JmsSingleTransactionJoinIfPossibleTestCase extends AbstractJmsFunctionalTestCase
{

    protected static final int beginTxCount = 1;
    private final CountDownLatch beginTxCountDownLatch = new CountDownLatch(beginTxCount);
    protected static final int commitTxCount = 1;
    private final CountDownLatch commitTxCountDownLatch = new CountDownLatch(commitTxCount);
    protected static final int rollbackTxCount = 0;
    private final CountDownLatch rollbackTxCountDownLatch = new CountDownLatch(rollbackTxCount);
    private static final String DEFAULT_OUTPUT_MESSAGE_NOTX = "OUTPUT MESSAGE-NoTx";
    private static final String DEFAULT_OUTPUT_MESSAGE_TX = "OUTPUT MESSAGE-Tx";
    protected static final String DEFUALT_OUTPUT_QUEUE_TX = "vm://out-tx";

    protected String getConfigResources()
    {
        return "jms-single-tx-JOIN_IF_POSSIBLE.xml";
    }

    public void testJoinIfPossible() throws Exception
    {

        getClient().dispatch(DEFUALT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        //!!!assertEquals(success.get(),true);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertNull(result.getExceptionPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE_NOTX, result.getPayload());


        result = getClient().receive(DEFUALT_OUTPUT_QUEUE_TX, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertNull(result.getExceptionPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE_TX, result.getPayload());

        super.verifyCountDownLatch(beginTxCountDownLatch, beginTxCount);
        super.verifyCountDownLatch(commitTxCountDownLatch, commitTxCount);

    }

    protected CountDownLatch getBeginTxCoundDownLatch()
    {
        return beginTxCountDownLatch;
    }

    protected CountDownLatch getCommitTxCoundDownLatch()
    {
        return commitTxCountDownLatch;
    }

    protected CountDownLatch getRollbackTxCoundDownLatch()
    {
        return rollbackTxCountDownLatch;
    }

}

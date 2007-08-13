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
public class JmsSingleTransactionRollbackTestCase extends AbstractJmsFunctionalTestCase
{
    protected static final int beginTxCount = 2;
    private final CountDownLatch beginTxCountDownLatch = new CountDownLatch(beginTxCount);
    protected static final int commitTxCount = 0;
    private final CountDownLatch commitTxCountDownLatch = new CountDownLatch(commitTxCount);
    protected static final int rollbackTxCount = 1;
    private final CountDownLatch rollbackTxCountDownLatch = new CountDownLatch(rollbackTxCount);

    public void testRollback() throws Exception
    {
        getClient().dispatch(DEFUALT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNull(result);
        super.verifyCountDownLatch(rollbackTxCountDownLatch, rollbackTxCount);
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

    protected String getConfigResources()
    {
        return "jms-single-tx-ROLLBACK.xml";
    }
}

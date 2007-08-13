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

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;

/**
 * Test jms using JmsClientAcknowledgeTransactionFactory
 */
public class JmsClientAcknowledgeTransactionTestCase extends AbstractJmsFunctionalTestCase
{
    protected static final int beginTxCount = 1;
    private final CountDownLatch beginTxCountDownLatch = new CountDownLatch(beginTxCount);
    protected static final int commitTxCount = 1;
    private final CountDownLatch commitTxCountDownLatch = new CountDownLatch(commitTxCount);
    private static final int rollbackTxCount = 0;
    private final CountDownLatch rollbackTxCountDownLatch = new CountDownLatch(rollbackTxCount);

    public void testJmsClientAcknowledgeTransaction() throws Exception
    {
        super.runAsynchronousDispatching();
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
        return "jms-client-acknowledge-tx.xml";
    }
}

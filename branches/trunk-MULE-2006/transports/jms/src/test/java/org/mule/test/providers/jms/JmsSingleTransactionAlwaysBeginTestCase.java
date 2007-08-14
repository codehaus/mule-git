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
 * Comment
 */
public class JmsSingleTransactionAlwaysBeginTestCase extends AbstractJmsFunctionalTestCase
{
    protected static final int beginTxCount = 1;
    private final CountDownLatch beginTxCountDownLatch = new CountDownLatch(beginTxCount);
    protected static final int commitTxCount = 1;
    private final CountDownLatch commitTxCountDownLatch = new CountDownLatch(commitTxCount);
    protected static final int rollbackTxCount = 0;
    private final CountDownLatch rollbackTxCountDownLatch = new CountDownLatch(rollbackTxCount);

    protected String getConfigResources()
    {
        return "jms-single-tx-ALWAYS_BEGIN.xml";
    }

    public void testAlwaysBegin() throws Exception
    {
        super.runAsynchronousDispatching();

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

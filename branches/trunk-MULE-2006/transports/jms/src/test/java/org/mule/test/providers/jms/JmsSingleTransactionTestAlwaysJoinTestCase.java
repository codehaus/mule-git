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

import org.mule.MuleManager;
import org.mule.impl.DefaultExceptionStrategy;
import org.mule.umo.UMOMessage;
import org.mule.umo.model.UMOModel;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;

/**
 * Comment
 */
public class JmsSingleTransactionTestAlwaysJoinTestCase extends AbstractJmsFunctionalTestCase
{

    protected static final int exceptionCount = 1;
    private final CountDownLatch exceptionCountDownLatch = new CountDownLatch(exceptionCount);
    protected static final int beginTxCount = 1;
    private final CountDownLatch beginTxCountDownLatch = new CountDownLatch(beginTxCount);
    protected static final int commitTxCount = 1;
    private final CountDownLatch commitTxCountDownLatch = new CountDownLatch(commitTxCount);
    protected static final int rollbackTxCount = 0;
    private final CountDownLatch rollbackTxCountDownLatch = new CountDownLatch(rollbackTxCount);


    public void testAlwaysJoin() throws Exception
    {

        UMOModel model = (UMOModel) MuleManager.getInstance().getModels().get(MODEL_NAME);
        model.setExceptionListener(new TestExceptionStrategy(exceptionCountDownLatch));
        super.runAsynchronousDispatching();

        super.verifyCountDownLatch(beginTxCountDownLatch, beginTxCount);
        super.verifyCountDownLatch(commitTxCountDownLatch, commitTxCount);
        super.verifyCountDownLatch(exceptionCountDownLatch, exceptionCount);

    }

    protected String getConfigResources()
    {
        return "jms-single-tx-ALWAYS_JOIN.xml";
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


    public static class TestExceptionStrategy extends DefaultExceptionStrategy
    {

        private final CountDownLatch exceptionCountDownLatch;

        public TestExceptionStrategy(CountDownLatch exceptionCountDownLatch)
        {
            this.exceptionCountDownLatch = exceptionCountDownLatch;
        }

        public void handleMessagingException(UMOMessage message, Throwable t)
        {
            logger.debug("@@@handleMessagingException@@@");
            logger.debug(message + " ::: " + t);
            exceptionCountDownLatch.countDown();

        }
    }

}

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
import org.mule.extras.client.MuleClient;
import org.mule.impl.DefaultExceptionStrategy;
import org.mule.impl.internal.notifications.TransactionNotification;
import org.mule.impl.internal.notifications.TransactionNotificationListener;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.manager.UMOServerNotificationListener;
import org.mule.umo.provider.UMOConnector;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/**
 * The main idea
 */
public abstract class AbstractJmsFunctionalTestCase extends FunctionalTestCase
{

    public static final String DEFAULT_MESSAGE = "INPUT MESSAGE";
    public static final String DEFAULT_OUTPUT_MESSAGE = "OUTPUT MESSAGE";
    public static final String DEFUALT_INPUT_QUEUE = "vm://in";
    public static final String DEFUALT_OUTPUT_QUEUE = "vm://out";
    public static final String CONNECTOR_NAME = "jmsConnector";
    public static final long TIMEOUT = 5000;
    public static final long LOCK_WAIT = 1000;
    private MuleClient client;

    protected void doPostFunctionalSetUp() throws Exception
    {
        MuleManager.getInstance().registerListener(getUMOServerNotificationListener());
        client = new MuleClient();
        TestExceptionStrategy exceptionListener =
                new TestExceptionStrategy(getControlCounter());
        UMOConnector umoCnn = MuleManager.getInstance().lookupConnector(CONNECTOR_NAME);
        umoCnn.setExceptionListener(exceptionListener);

    }

    protected void doFunctionalTearDown() throws Exception
    {
        client.dispose();
    }

    protected UMOServerNotificationListener getUMOServerNotificationListener()
    {
        return new TransactionNotificationListener()
        {
            public void onNotification(UMOServerNotification notification)
            {
                logger.info("The transaction action is - " + notification.getActionName());
                switch (notification.getAction())
                {
                    case TransactionNotification.TRANSACTION_BEGAN:
                        onBeganTx(notification);
                        break;
                    case TransactionNotification.TRANSACTION_COMMITTED:
                        onCommitedTx(notification);
                        break;
                    case TransactionNotification.TRANSACTION_ROLLEDBACK:
                        onRolledbackTx(notification);
                        break;
                    default:
                        throw new IllegalStateException("Only 3 statuses are supported");
                }
            }
        };
    }

    protected abstract ControlCounter getControlCounter();

    protected void onBeganTx(UMOServerNotification notification)
    {
        this.getControlCounter().getBeginTxInfo().countDown();
    }

    protected void onCommitedTx(UMOServerNotification notification)
    {
        this.getControlCounter().getCommitTxInfo().countDown();
    }

    protected void onRolledbackTx(UMOServerNotification notification)
    {
        this.getControlCounter().getRollbackTxInfo().countDown();
    }

    protected MuleClient getClient()
    {
        return client;
    }

    protected UMOMessage runAsynchronousDispatching() throws Exception
    {
        client.dispatch(DEFUALT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = client.receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE, result.getPayload());
        return result;
    }

    public static class TestExceptionStrategy extends DefaultExceptionStrategy
    {

        private final ControlCounter info;

        public TestExceptionStrategy(ControlCounter info)
        {
            this.info = info;
        }

        public void handleMessagingException(UMOMessage message, Throwable t)
        {
            logger.debug("@@@handleMessagingException@@@ " + message + " :: " + t);
            info.getExceptionInfo().countDown();
        }
    }


    public static class ControlCounter
    {

        private TxInfo beginTxInfo;
        private TxInfo commitTxInfo;
        private TxInfo rollbackTxInfo;
        private TxInfo exceptionInfo;

        public ControlCounter(int beginCount, int commitCount, int rollbackCount)
        {
            beginTxInfo = new TxInfo(beginCount);
            commitTxInfo = new TxInfo(commitCount);
            rollbackTxInfo = new TxInfo(rollbackCount);
        }

        public ControlCounter(int beginCount, int commitCount, int rollbackCount, int exceptionCount)
        {
            this(beginCount, commitCount, rollbackCount);
            exceptionInfo = new TxInfo(exceptionCount);
        }

        public TxInfo getExceptionInfo()
        {
            return exceptionInfo;
        }

        public TxInfo getBeginTxInfo()
        {
            return beginTxInfo;
        }

        public TxInfo getCommitTxInfo()
        {
            return commitTxInfo;
        }

        public TxInfo getRollbackTxInfo()
        {
            return rollbackTxInfo;
        }

        public void verifySingleTx() throws InterruptedException
        {
            beginTxInfo.verify();
            commitTxInfo.verify();
            rollbackTxInfo.verify();
        }

        public void verifyXaTx() throws InterruptedException
        {
            commitTxInfo.verify();
        }


        public class TxInfo
        {
            private int maxTxSize;
            private CountDownLatch txCountDownLatch;
            private volatile boolean success = true;

            public TxInfo(int maxTxSize)
            {
                this.maxTxSize = maxTxSize;
                this.txCountDownLatch = new CountDownLatch(this.maxTxSize);
            }

            public void await()
                    throws InterruptedException
            {
                txCountDownLatch.await();
            }

            public void countDown()
            {


                if (txCountDownLatch.getCount() == 0)
                {
                    success = false;
                }
                else
                {
                    txCountDownLatch.countDown();
                }

            }

            public long getCount()
            {
                return txCountDownLatch.getCount();
            }

            public boolean isSuccess()
            {
                return success;
            }

            public void verify() throws InterruptedException
            {
                assertTrue(isSuccess());
                txCountDownLatch.await(LOCK_WAIT, TimeUnit.MILLISECONDS);
                assertTrue("Only " + (maxTxSize - txCountDownLatch.getCount()) + " of " + maxTxSize
                           + " checkpoints hit", txCountDownLatch.getCount() == 0);

            }

        }
    }
}

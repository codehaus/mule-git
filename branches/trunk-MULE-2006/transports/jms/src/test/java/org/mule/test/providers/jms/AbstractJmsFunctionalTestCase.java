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
import org.mule.transaction.XaTransaction;
import org.mule.umo.UMOMessage;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.manager.UMOServerNotificationListener;
import org.mule.umo.provider.UMOConnector;
import org.mule.util.concurrent.ConcurrentHashSet;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/**
 * The main idea
 */
public abstract class AbstractJmsFunctionalTestCase extends FunctionalTestCase
{

    private static final String ARJUNA_UID_PATTERN = ".*?(-?\\w*):(-?\\w*):(-?\\w*):(-?\\w*).*";
    public static final String DEFAULT_MESSAGE = "INPUT MESSAGE";
    public static final String DEFAULT_OUTPUT_MESSAGE = "OUTPUT MESSAGE";
    public static final String DEFAULT_INPUT_QUEUE = "vm://in";
    public static final String DEFUALT_OUTPUT_QUEUE = "vm://out";
    public static final String CONNECTOR_NAME = "jmsConnector";
    public static final long TIMEOUT = 10000;
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
                TransactionNotification txNotification = (TransactionNotification) notification;
                logger.info("The transaction action is - " + txNotification.getActionName() + " " + txNotification.getTransactionStringId());
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
        getControlCounter().registerGlobalTx(getXAArjunaUid(notification));
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
        client.dispatch(DEFAULT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = client.receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE, result.getPayload());
        return result;
    }

    private String getXAArjunaUid(UMOServerNotification notification)
    {
        Object tx = notification.getSource();
        if (tx instanceof XaTransaction)
        {
            try
            {
                Field field = tx.getClass().getDeclaredField("transaction");
                field.setAccessible(true);
                Object object = field.get(tx);
                String toString = object.toString();
                Pattern pattern = Pattern.compile(ARJUNA_UID_PATTERN);
                Matcher matcher = pattern.matcher(toString);
                if (matcher.find())
                {
                    //TODO may be it's a global tx uid
                    return matcher.group(1) + ":" + matcher.group(2) + ":" + matcher.group(3);
                }
            }
            catch (Exception e)
            {
                logger.error(e);
            }
        }
        return null;
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
            if (info.getExceptionInfo() != null)
            {
                info.getExceptionInfo().countDown();
            }
        }
    }


    public class ControlCounter
    {

        private TxInfo beginTxInfo;
        private TxInfo commitTxInfo;
        private TxInfo rollbackTxInfo;
        private TxInfo exceptionInfo;
        ConcurrentHashSet globalTx = new ConcurrentHashSet();


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

        public void registerGlobalTx(String uid)
        {
            if (uid != null)
            {
                globalTx.add(uid);
            }
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
            logger.debug("GLOBALTX :: " + globalTx);
            assertEquals(globalTx.size(), commitTxInfo.maxTxSize);
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

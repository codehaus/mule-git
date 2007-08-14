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
import org.mule.impl.internal.notifications.TransactionNotification;
import org.mule.impl.internal.notifications.TransactionNotificationListener;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.manager.UMOServerNotificationListener;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;

/**
 * The main idea
 */
public abstract class AbstractJmsFunctionalTestCase extends FunctionalTestCase
{

    protected static final String DEFAULT_MESSAGE = "INPUT MESSAGE";
    protected static final String DEFAULT_OUTPUT_MESSAGE = "OUTPUT MESSAGE";
    protected static final String DEFUALT_INPUT_QUEUE = "vm://in";
    protected static final String DEFUALT_OUTPUT_QUEUE = "vm://out";
    public static final long TIMEOUT = 5000;
    public static final long LOCK_WAIT = 20000;
    protected static final String MODEL_NAME = "TEST";
    private MuleClient client;

    private AtomicBoolean success = new AtomicBoolean(true);

    protected void doPostFunctionalSetUp() throws Exception
    {
        MuleManager.getInstance().registerListener(getUMOServerNotificationListener());
        client = new MuleClient();
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

    protected void verifyCountDownLatch(CountDownLatch countDownLatch, int maxSize) throws InterruptedException
    {
        countDownLatch.await(LOCK_WAIT, TimeUnit.MILLISECONDS);
        assertTrue("Only " + (maxSize - countDownLatch.getCount()) + " of " + maxSize
                   + " checkpoints hit", countDownLatch.getCount() == 0);

    }

    protected abstract CountDownLatch getBeginTxCoundDownLatch();

    protected abstract CountDownLatch getCommitTxCoundDownLatch();

    protected abstract CountDownLatch getRollbackTxCoundDownLatch();

    protected void onBeganTx(UMOServerNotification notification)
    {
        if (this.getBeginTxCoundDownLatch().getCount() == 0)
        {
            //throw new IllegalStateException("Permitted transactions are exhausted");
            logger.error("Permitted transactions are exhausted");
            success.set(false);

        }
        this.getBeginTxCoundDownLatch().countDown();
    }

    protected void onCommitedTx(UMOServerNotification notification)
    {
        if (this.getCommitTxCoundDownLatch().getCount() == 0)
        {
            logger.error("Permitted commited transactions  are exhausted");
            success.set(false);
            //throw new IllegalStateException("Permitted commited transactions  are exhausted");
        }
        this.getCommitTxCoundDownLatch().countDown();
    }

    protected void onRolledbackTx(UMOServerNotification notification)
    {
        if (this.getRollbackTxCoundDownLatch().getCount() == 0)
        {
            //throw new IllegalStateException("Permitted rolledback transactions are exhausted");
            logger.error("Permitted rolledback transactions are exhausted");
            success.set(false);
        }
        this.getRollbackTxCoundDownLatch().countDown();

    }

    protected MuleClient getClient()
    {
        return client;
    }

    protected void runAsynchronousDispatching() throws Exception
    {
        client.dispatch(DEFUALT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = client.receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertNull(result.getExceptionPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE, result.getPayload());
        assertEquals(success.get(), true);
    }

}

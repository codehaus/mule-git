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

import org.mule.umo.manager.UMOServerNotification;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;

/**
 * Comment
 */
public class JmsSingleTransactionNoneTestCase extends AbstractJmsFunctionalTestCase
{

    protected String getConfigResources()
    {
        return "jms-single-tx-NONE.xml";
    }

    public void testWithoutAnyTx() throws Exception
    {
        super.runAsynchronousDispatching();
    }

    protected CountDownLatch getBeginTxCoundDownLatch()
    {
        return null;
    }

    protected CountDownLatch getCommitTxCoundDownLatch()
    {
        return null;
    }

    protected CountDownLatch getRollbackTxCoundDownLatch()
    {
        return null;
    }

    protected void onBeganTx(UMOServerNotification notification)
    {
        throw new IllegalStateException("Start Tx is forbidden");
    }

    protected void onCommitedTx(UMOServerNotification notification)
    {
        throw new IllegalStateException("Start Tx is forbidden");
    }

    protected void onRolledbackTx(UMOServerNotification notification)
    {
        throw new IllegalStateException("Start Tx is forbidden");
    }

}

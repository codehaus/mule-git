/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transaction;

import org.mule.api.MuleContext;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.config.i18n.CoreMessages;
import org.mule.context.notification.TransactionNotification;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This base class provides low level features for transactions.
 */
public abstract class AbstractTransaction implements Transaction
{

    protected final transient Log logger = LogFactory.getLog(getClass());

    protected MuleContext muleContext;

    protected AbstractTransaction(MuleContext muleContext)
    {
        this.muleContext = muleContext;
    }

    public boolean isRollbackOnly() throws TransactionException
    {
        return getStatus() == STATUS_MARKED_ROLLBACK;
    }

    public boolean isBegun() throws TransactionException
    {
        int status = getStatus();
        return status != STATUS_NO_TRANSACTION && status != STATUS_UNKNOWN;
    }

    public boolean isRolledBack() throws TransactionException
    {
        return getStatus() == STATUS_ROLLEDBACK;
    }

    public boolean isCommitted() throws TransactionException
    {
        return getStatus() == STATUS_COMMITTED;
    }

    public void begin() throws TransactionException
    {
        logger.debug("Beginning transaction");
        doBegin();
        TransactionCoordination.getInstance().bindTransaction(this);
        fireNotification(new TransactionNotification(this, TransactionNotification.TRANSACTION_BEGAN));
    }

    public void commit() throws TransactionException
    {
        try
        {
            logger.debug("Committing transaction " + this);

            if (isRollbackOnly())
            {
                throw new IllegalTransactionStateException(CoreMessages.transactionMarkedForRollback());
            }

            doCommit();
            fireNotification(new TransactionNotification(this, TransactionNotification.TRANSACTION_COMMITTED));
        }
        finally
        {
            TransactionCoordination.getInstance().unbindTransaction(this);
        }
    }

    public void rollback() throws TransactionException
    {
        try
        {
            logger.debug("Rolling back transaction");
            setRollbackOnly();
            doRollback();
            fireNotification(new TransactionNotification(this, TransactionNotification.TRANSACTION_ROLLEDBACK));
        }
        finally
        {
            TransactionCoordination.getInstance().unbindTransaction(this);
        }
    }

    /**
     * Really begin the transaction. Note that resources are enlisted yet.
     * 
     * @throws TransactionException
     */
    protected abstract void doBegin() throws TransactionException;

    /**
     * Commit the transaction on the underlying resource
     * 
     * @throws TransactionException
     */
    protected abstract void doCommit() throws TransactionException;

    /**
     * Rollback the transaction on the underlying resource
     * 
     * @throws TransactionException
     */
    protected abstract void doRollback() throws TransactionException;

    /**
     * Fires a server notification to all registered
     * {@link org.mule.api.context.notification.TransactionNotificationListener}s.
     *
     */
    protected void fireNotification(TransactionNotification notification)
    {
        // TODO profile this piece of code
        muleContext.fireNotification(notification);
    }

    public boolean isXA()
    {
        return false;
    }

    public void resume() throws TransactionException
    {
        throw new IllegalTransactionStateException(CoreMessages.notMuleXaTransaction(this));
    }

    public javax.transaction.Transaction suspend() throws TransactionException
    {
        throw new IllegalTransactionStateException(CoreMessages.notMuleXaTransaction(this));
    }

    public String getId()
    {
        return String.valueOf(this.hashCode());
    }

    @Override
    public String toString()
    {
        int status;
        try
        {
            status = getStatus();
        }
        catch (TransactionException e)
        {
            status = -1;
        }
        return super.toString() + "[status=" + status + "]";
    }
}

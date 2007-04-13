/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo;

import org.mule.transaction.AbstractSingleResourceTransaction;
import org.mule.umo.TransactionException;


/**
 * <code>FooTransaction</code> is a wrapper for a Foo local transaction. This
 * object holds the tx resource and controls the when the transaction committed
 * or rolled back.
 *
 */
public class FooTransaction extends AbstractSingleResourceTransaction
{

    /*
     * (non-Javadoc)
     *
     * @see org.mule.umo.UMOTransaction#bindResource(java.lang.Object,
     *      java.lang.Object)
     */
    public void bindResource(Object key, Object resource) throws TransactionException
    {
        //IMPLEMENTATION NOTE:
        //Validate as necessary, the resource here and then pass it to the super class to bind it
        //to the key and also do any other specific binding here

        /*
        try {
            //Todo Validate Resource state here if necessary

        } catch (XXXException e) {
            throw new IllegalTransactionStateException(new Message(Messages.TX_CANT_READ_STATE), e);
        }
        */
        super.bindResource(key, resource);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.transaction.AbstractSingleResourceTransaction#doBegin()
     */
    protected void doBegin() throws TransactionException
    {
        //IMPLEMENTATION NOTE:
        //Sometimes the resource has already begun the transaction when its been made available
        //i.e. A transacted Jms Session doesn't have a .begin() method, because initiation is handled
        //by the Jms provider

        /*
        try {
            //Todo begin transaction - i.e. <resource>.begin();
        } catch (XXXException e) {
            throw new TransactionException(new Message(Messages.TX_COMMIT_FAILED), e);
        }
        */
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.transaction.AbstractSingleResourceTransaction#doCommit()
     */
    protected void doCommit() throws TransactionException
    {
        /*
        try {
            //Todo commit transaction - i.e. <resource>.commit();
        } catch (XXXException e) {
            throw new TransactionException(new Message(Messages.TX_COMMIT_FAILED), e);
        }
        */
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.transaction.AbstractSingleResourceTransaction#doRollback()
     */
    protected void doRollback() throws TransactionException
    {
        /*
        try {
            //Todo rollback transaction - i.e. <resource>.rollback();
        } catch (XXXException e) {
            throw new TransactionException(new Message(Messages.TX_ROLLBACK_FAILED), e);
        }
        */
    }

}

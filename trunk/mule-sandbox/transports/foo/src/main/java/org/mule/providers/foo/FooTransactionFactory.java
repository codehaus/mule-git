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

import org.mule.umo.TransactionException;
import org.mule.umo.UMOTransaction;
import org.mule.umo.UMOTransactionFactory;

/**
 * <code>FooTransactionFactory</code> Creates a FooTransaction
 *
 * @see FooTransaction
 */
public class FooTransactionFactory implements UMOTransactionFactory
{
    /*
     * (non-Javadoc)
     *
     * @see org.mule.umo.UMOTransactionFactory#beginTransaction(org.mule.umo.provider.UMOMessageDispatcher)
     */
    public UMOTransaction beginTransaction() throws TransactionException
    {
        FooTransaction tx = new FooTransaction();
        tx.begin();
        return tx;
    }

    public boolean isTransacted()
    {
        return true;
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.memelet.mule.spring;

import javax.transaction.TransactionManager;

import org.mule.umo.manager.UMOTransactionManagerFactory;

/**
 * @author <a href="mailto:groups1@memelet.com">Barry Kaplan</a>
 */
public class SpringTransactionManagerFactory implements UMOTransactionManagerFactory {

    private TransactionManager transactionManager;
    
    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    public TransactionManager create() throws Exception {
        return transactionManager;
    }

}

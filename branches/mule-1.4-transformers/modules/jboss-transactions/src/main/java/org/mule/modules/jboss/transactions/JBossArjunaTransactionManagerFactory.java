/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.jboss.transactions;

import org.mule.umo.manager.UMOTransactionManagerFactory;
import org.mule.MuleManager;

import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.arjuna.ats.arjuna.common.Environment;
import com.arjuna.ats.arjuna.gandiva.ClassName;
import com.arjuna.ats.arjuna.ArjunaNames;
import com.arjuna.common.util.logging.LogFactory;
import com.arjuna.common.util.logging.DebugLevel;
import com.arjuna.common.internal.util.logging.commonPropertyManager;

import javax.transaction.TransactionManager;

public class JBossArjunaTransactionManagerFactory implements UMOTransactionManagerFactory
{
    static
    {
        //arjPropertyManager.propertyManager.setProperty(LogFactory.LOGGER_PROPERTY, "log4j_releveler");
        //arjPropertyManager.propertyManager.setProperty(LogFactory.LOGGER_PROPERTY, "jakarta");
        //arjPropertyManager.propertyManager.setProperty(LogFactory.DEBUG_LEVEL, String.valueOf(DebugLevel.FULL_DEBUGGING));
        //commonPropertyManager.propertyManager.setProperty(LogFactory.LOGGER_PROPERTY, "jakarta");
        //commonPropertyManager.propertyManager.setProperty(LogFactory.DEBUG_LEVEL, String.valueOf(DebugLevel.FULL_DEBUGGING));
    }

    private TransactionManager tm;

    public JBossArjunaTransactionManagerFactory()
    {
        //arjPropertyManager.propertyManager.setProperty("com.arjuna.ats.arjuna.objectstore.objectStoreType", "ShadowNoFileLockStore");
        //arjPropertyManager.propertyManager.setProperty(Environment.OBJECTSTORE_TYPE, ArjunaNames.Implementation_ObjectStore_JDBCStore().stringForm());
        final String muleInternalDir = MuleManager.getConfiguration().getWorkingDirectory();
        arjPropertyManager.propertyManager.setProperty(Environment.OBJECTSTORE_DIR, muleInternalDir + "/transaction-log");
    }

    public synchronized TransactionManager create() throws Exception
    {
        if (tm == null)
        {
            tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
        }
        return tm;
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.integration.providers.jms.functional;

import org.mule.MuleManager;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOTransaction;

import java.util.HashSet;
import java.util.Set;


/**
 * Get transaction status inside component
 * There is a separate transaction for each component  
 * when single transaction(action: BEGIN_OR_JOIN) and jms transaport are used
 */
public class JmsSingleTransactionComponentTestCase extends AbstractJmsFunctionalTestCase
{
    public static final String MODEL_NAME = "TEST";
    public static final String COMPONENT_NAME = "Part";
    /**
     * Every Transaction has to be different instance
     */
    public final Set transactions = new HashSet();

    protected String getConfigResources()
    {
        return "providers/activemq/jms-single-tx-component.xml";
    }

    public void testDifferentTx() throws Exception
    {
        EventCallback callback = new EventCallback()
        {
            public void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                UMOTransaction transaction = TransactionCoordination.getInstance().getTransaction();
                assertNotNull(transaction);
                assertFalse(transactions.contains(transaction));
                transactions.add(transaction);
            }
        };

        for (int i = 1; i < 5; i++)
        {
            ((FunctionalTestComponent) MuleManager.getInstance().lookupModel(MODEL_NAME).getComponent(COMPONENT_NAME + i).getDescriptor().getImplementation()).
                    setEventCallback(callback);
        }

        dispatchMessage();
        recieveMessage();
    }
}

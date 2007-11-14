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
import org.mule.extras.client.MuleClient;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOTransaction;

/**
 * Get transaction status inside component
 */
public class JmsXATransactionComponentTestCase extends AbstractJmsFunctionalTestCase
{
    public static final String MODEL_NAME = "TEST";
    public static final String COMPONENT_NAME = "Part";
    /**
     * Every Transaction has to be one instance
     */
    private UMOTransaction umoTransaction = null;

    protected String getConfigResources()
    {
        return "providers/activemq/jms-xa-tx-component.xml";
    }

    public void testOneGlobalTx() throws Exception
    {
        EventCallback callback = new EventCallback()
        {
            public void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                if (umoTransaction == null)
                {
                    umoTransaction = TransactionCoordination.getInstance().getTransaction();
                }
                else
                {
                    logger.error("Transactiuon: " + TransactionCoordination.getInstance().getTransaction());
                    assertTrue(TransactionCoordination.getInstance().getTransaction().equals(umoTransaction));
                }
            }
        };

        for (int i = 1; i < 5; i++)
        {
            ((FunctionalTestComponent) MuleManager.getInstance().lookupModel(MODEL_NAME).getComponent(COMPONENT_NAME + i).getDescriptor().getImplementation()).
                    setEventCallback(callback);
        }

        MuleClient client = new MuleClient();
        client.dispatch("vm://in", DEFAULT_INPUT_MESSAGE, null);
        UMOMessage result = client.receive("vm://out", TIMEOUT);
        assertNotNull(result);
        client.dispose();
    }

}

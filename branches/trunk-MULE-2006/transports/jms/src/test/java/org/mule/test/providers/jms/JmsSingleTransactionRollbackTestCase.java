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

import org.mule.umo.UMOMessage;

/**
 * Comment
 */
public class JmsSingleTransactionRollbackTestCase extends AbstractJmsFunctionalTestCase
{
    private final ControlCounter blackBoxTx = new ControlCounter(1, 0, 1);

    protected String getConfigResources()
    {
        return "jms-single-tx-ROLLBACK.xml";
    }

    public void testRollback() throws Exception
    {
        //for (int i=0;i<10;i++)
        getClient().dispatch(DEFAULT_INPUT_QUEUE, DEFAULT_MESSAGE, null);

        UMOMessage result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNull(result);
        getControlCounter().verifySingleTx();
    }

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }

}

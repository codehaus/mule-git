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
 * TODO Inccorect test
 * Actual: One tx is committed (successfully delivered message to jms://in)
 * FTC throw Exception, by ExceptionStrategy Message catch in Part3(jms://error.queue)
 * Expect: Get 2 rollback (maxRedelivery set to 2)
 */
public class JmsSingleTransactionRollbackTestCase extends AbstractJmsFunctionalTestCase
{

    private final ControlCounter blackBoxTx = new ControlCounter(1, 0, 2, 0);

    protected String getConfigResources()
    {
        return "jms-single-tx-ROLLBACK.xml";
    }

    public void testRollback() throws Exception
    {
        getClient().dispatch(DEFAULT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        getControlCounter().verifySingleTx();
    }

    protected ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }

}

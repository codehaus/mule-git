/**
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
 * Tx is began in "Part1" and Part2-JointTX join to this tx
 * TODO not work, because tx doesn't sharing between descriptors
 */
public class JmsSingleTransactionAlwaysJoinJoinTestCase extends AbstractJmsFunctionalTestCase
{

    private final ControlCounter blackBoxTx = new ControlCounter(1, 1, 0, 0);

    protected String getConfigResources()
    {
        return "jms-single-tx-ALWAYS_JOIN-JOIN.xml";
    }

    public void testAlwaysJoin() throws Exception
    {

        getClient().dispatch(DEFAULT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        getControlCounter().verifySingleTx();
        getControlCounter().getExceptionInfo().verify();
    }

    protected AbstractJmsFunctionalTestCase.ControlCounter getControlCounter()
    {
        return blackBoxTx;
    }


}

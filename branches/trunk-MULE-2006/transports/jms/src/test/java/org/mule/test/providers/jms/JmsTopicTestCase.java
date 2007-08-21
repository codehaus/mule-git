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
 * Message is put to topic with two subscribers
 */
public class JmsTopicTestCase extends AbstractJmsFunctionalTestCase
{

    public void testJmsTopic() throws Exception
    {
        getClient().dispatch(DEFAULT_INPUT_QUEUE, DEFAULT_MESSAGE, null);
        UMOMessage result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE, result.getPayload());
        result = getClient().receive(DEFUALT_OUTPUT_QUEUE, TIMEOUT);
        assertNotNull(result);
        assertNotNull(result.getPayload());
        assertEquals(DEFAULT_OUTPUT_MESSAGE, result.getPayload());

    }

    protected ControlCounter getControlCounter()
    {
        return null;
    }

    protected String getConfigResources()
    {
        return "jms-topic.xml";
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.routing.outbound;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

/**
 */
public class ChainingRouterRemoteSyncTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "org/mule/test/integration/routing/outbound/chaining-router-remote-sync.xml";
    }

    public void testRemoteSync() throws Exception
    {

        MuleClient muleClient = new MuleClient();
        MuleMessage result = muleClient.send("vm://in", new DefaultMuleMessage("test"));

        assertNull("Shouldn't have any exceptions", result.getExceptionPayload());
        assertEquals("test [REMOTESYNC RESPONSE] [REMOTESYNC RESPONSE 2]", result.getPayloadAsString());
    }

    /**
     * MULE-4619 ChainingRouter sets MULE_REMOTE_SYNC_PROPERTY true on message when dispatching to last endpoint
     * @throws Exception
     */
    public void testRemoteSyncLastEndpointDispatch() throws Exception
    {

        MuleClient muleClient = new MuleClient();
        MuleMessage result = muleClient.send("vm://in2", new DefaultMuleMessage("test"));

        assertNull("Shouldn't have any exceptions", result.getExceptionPayload());
        assertEquals("test", result.getPayloadAsString());

        MuleMessage jmsMessage = muleClient.request("jms://out2", FunctionalTestCase.RECEIVE_TIMEOUT);
        assertEquals("test [REMOTESYNC RESPONSE] [REMOTESYNC RESPONSE 2]", jmsMessage.getPayloadAsString());
        assertFalse(jmsMessage.getBooleanProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY, false));
    }

}

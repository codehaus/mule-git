/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms;

import org.mule.providers.jms.JmsMessageReceiverTestCase;
import org.mule.impl.MuleDescriptor;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.umo.provider.UMOMessageReceiver;

public class MultiConsumerJmsMessageReceiverTestCase extends JmsMessageReceiverTestCase
{
    public void testReceive() throws Exception
    {
        MultiConsumerJmsMessageReceiver receiver = (MultiConsumerJmsMessageReceiver)getMessageReceiver();
        assertNotNull(receiver.getComponent());
        assertNotNull(receiver.getConnector());
        assertNotNull(receiver.getEndpoint());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.tck.providers.AbstractMessageReceiverTestCase#getMessageReceiver()
     */
    public UMOMessageReceiver getMessageReceiver() throws Exception
    {
        MuleDescriptor descriptor = getTestDescriptor("orange", Orange.class.getName());
        return new MultiConsumerJmsMessageReceiver(endpoint.getConnector(), getTestComponent(descriptor), endpoint);
    }
}

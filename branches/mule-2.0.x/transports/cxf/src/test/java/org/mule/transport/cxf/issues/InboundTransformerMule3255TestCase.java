/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.cxf.issues;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent2;
import org.mule.tck.functional.StringAppendTestTransformer;
import org.mule.util.concurrent.Latch;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class InboundTransformerMule3255TestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "inbound-transformer-mule3255.xml";
    }

    public void testSyncResponse() throws Exception
    {
        final Latch latch = new Latch();

        EventCallback callback1 = new EventCallback()
        {
            public void eventReceived(final MuleEventContext context, final Object component) throws Exception
            {
                assertEquals("request" + StringAppendTestTransformer.DEFAULT_TEXT, context.transformMessage());
                latch.countDown();
            }
        };

        ((FunctionalTestComponent2) getComponent("CXFService")).setEventCallback(callback1);
        MuleClient client = new MuleClient();
        MuleMessage message = client.send("cxf:http://localhost:4444/services/CXFService?method=onReceive", "request",
            null);
        assertNotNull(message);
        assert (latch.await(5, TimeUnit.SECONDS));
    }
}

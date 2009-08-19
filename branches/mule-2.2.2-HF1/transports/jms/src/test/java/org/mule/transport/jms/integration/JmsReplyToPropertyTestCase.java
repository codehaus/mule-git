package org.mule.transport.jms.integration;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

public class JmsReplyToPropertyTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "integration/jms-replyto-property.xml";
    }

    public void testReplyTo() throws Exception
    {
        MuleClient client = new MuleClient();
        Map props = new HashMap();
        props.put("JMSReplyTo", "forQ2");
        client.dispatch("jms://in", "TestMessage", props);
        MuleMessage output = client.request("vm://out", 3000);
        assertNotNull(output);
        assertEquals("TestMessage", output.getPayload());
        assertEquals("queue://forQ2", output.getProperty("JMSReplyTo").toString());
    }
}



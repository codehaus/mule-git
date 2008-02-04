/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.jms.issues;

import org.mule.config.MuleProperties;
import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleMessage;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOMessage;
import org.mule.umo.lifecycle.Callable;

public class ActiveMQJmsTransformersMule2629TestCase extends FunctionalTestCase
{

    public void testMule2629() throws Exception
    {
        MuleMessage muleMsg = new MuleMessage(new Payload(2));
        muleMsg.setProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY, Boolean.TRUE);
        MuleClient client = new MuleClient();
        UMOMessage umoResp = client.send("jms://echo2", muleMsg);
        assertEquals(2, ((Payload) umoResp.getPayload()).getNum());
        client.dispose();
    }

    protected String getConfigResources()
    {
        return "issue-2629.xml";
    }

    public static class JMSTest implements Callable
    {
        public Object onCall(UMOEventContext eventContext) throws Exception
        {
            Object o=eventContext.getTransformedMessage();
            assertNotNull(o);
            assertEquals(o.getClass(), Payload.class);
            return o;

        }
    }

    public static class Payload implements java.io.Serializable
    {
        int num = 0;

        public Payload(int i)
        {
            this.num = i;
        }

        public int getNum()
        {
            return num;
        }
    }


}

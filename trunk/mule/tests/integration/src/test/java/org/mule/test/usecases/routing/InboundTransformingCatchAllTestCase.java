/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.usecases.routing;

import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleMessage;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;


public class InboundTransformingCatchAllTestCase extends FunctionalTestCase
{
    
    public void testNormal() throws Exception
    {
        MuleClient client=new MuleClient();
        client.dispatch("vm://in1",new MuleMessage("HELLO!"));
        UMOMessage msg=client.receive("vm://catchall", 1000);
        assertNotNull(msg);
        assertTrue(msg.getPayload() instanceof String);
        
        client.dispatch("vm://in2",new MuleMessage("HELLO!"));
        msg=client.receive("vm://catchall", 1000);
        assertNotNull(msg);
        assertTrue(msg.getPayload() instanceof byte[]);
    }

    protected String getConfigResources()
    {        
        return "org/mule/test/usecases/routing/inbound-transforming-catchall.xml";
    }
}
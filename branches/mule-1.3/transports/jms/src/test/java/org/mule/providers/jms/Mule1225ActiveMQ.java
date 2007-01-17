/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms;

import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleMessage;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

public class Mule1225ActiveMQ extends FunctionalTestCase
{

    public void testJPMConfig()throws Exception
    
    {
        MuleClient client=new MuleClient();
        UMOMessage reply = null;
        //let's empty the queue first because of previous tests that may have failed
        do {
            reply = client.receive("jms://endpoint3?connector=jmsConnector1", 1000);
         } while (reply != null);

        //1 - Send on EP1, receive on EP3
        client.dispatch("endpoint1", new MuleMessage ("EP1 "));
        reply = client.receive("jms://endpoint3?connector=jmsConnector1", 4000);
        assertNotNull (reply);
        assertNotNull (reply.getPayload());
        assertEquals ("EP1 ", reply.getPayloadAsString());
       
        //2 - Send on EP2, receive on EP3
        client.dispatch("endpoint2", new MuleMessage ("EP2 "));
        UMOMessage reply2 = client.receive("jms://endpoint3?connector=jmsConnector2", 4000);        
        assertNotNull (reply2);
        assertNotNull (reply2.getPayload());
        assertEquals ("EP2 ", reply2.getPayloadAsString());
        
        //3 - Send on EP1, should receive on EP3 but will get error
        client.dispatch("endpoint1", new MuleMessage ("EP1 again"));
        UMOMessage reply3 = client.receive("jms://endpoint3?connector=jmsConnector1", 6000);     
        assertNotNull (reply3);
        assertNotNull (reply3.getPayload());
        assertEquals ("EP1 again", reply3.getPayloadAsString());
    }
    

    protected String getConfigResources()
    {
        return "Mule1225-ActiveMQ-config.xml";
    }

}



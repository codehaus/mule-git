/*
 * $Id$
 * --------------------------------------------------------------------------------------
 *
 * (c) 2003-2008 MuleSource, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSource's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSource. If such an agreement is not in place, you may not use the software.
 */

package org.mule.transport.vm.functional;

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.api.MuleMessage;

public class PersistentUnaddressedVmQueueTestCase extends FunctionalTestCase
{
    private static final int RECEIVE_TIMEOUT = 5000;

    protected String getConfigResources()
    {
        return "vm/persistent-unaddressed-vm-queue-test.xml";
    }

    public void testAsynchronousDispatching() throws Exception
    {
        MuleClient client = new MuleClient();
        client.dispatch("vm://receiver1?connector=Connector1", "Test", null);
        MuleMessage result = client.request("vm://out?connector=Connector2", RECEIVE_TIMEOUT);
	    assertNotNull(result);
	    assertEquals(result.getPayloadAsString(),"Test");
    }

}

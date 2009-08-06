/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.exceptions;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.Map;

public class MessageContextTestCase extends FunctionalTestCase 
{

	String request = "Hello World";

	protected String getConfigResources() 
	{
		return "org/mule/test/integration/exceptions/message-context-test.xml";
	}

	/**
	 * Test for MULE-4361
	 */
	public void testAlternateExceptionStrategy() throws Exception 
	{
		MuleClient client1 = new MuleClient();
		DefaultMuleMessage msg1 = new DefaultMuleMessage(request, (Map) null);
		MuleMessage response1 = client1.send("testin", msg1, 200000);
		assertNotNull(response1);
		Thread.sleep(10000); // Wait for test to finish
	}

}

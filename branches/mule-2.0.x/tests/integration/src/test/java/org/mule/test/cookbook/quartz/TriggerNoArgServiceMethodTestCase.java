/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.cookbook.quartz;

import org.mule.tck.FunctionalTestCase;
import org.mule.module.client.MuleClient;
import org.mule.api.MuleMessage;

// START SNIPPET: full-class
public class TriggerNoArgServiceMethodTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "org/mule/test/cookbook/quartz/trigger-no-args-method-config.xml";
    }

    public void testTrigger() throws Exception
    {
        MuleClient client = new MuleClient();

        //Our method should have fired and we can pick up the result
        MuleMessage result = client.request("resultQueue", 2000);

        //Always check method is not null. It wuld be rude not to!
        assertNotNull(result);

        //Check we have a hit
        assertEquals("Bullseye!", result.getPayloadAsString());
    }
}
// END SNIPPET: full-class

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.vm.issues;

import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

public class EndpointTransformerMule2131TestCase extends FunctionalTestCase
{

    public static final long TIMEOUT = 5000L;
    public static final String MESSAGE = "a message";

    protected String getConfigResources()
    {
        return "endpoint-transformer-mule-2131-test.xml";
    }

    public void testAllCases() throws Exception
    {
        MuleClient client = new MuleClient();
        client.dispatch("in", MESSAGE, null);
        for (int i = 0; i < 3; i++)
        {
            receive(client, "vm://outT?connector=queue", MESSAGE + StringAppendTransformer.DEFAULT_TEXT);
        }
        receive(client, "vm://outD?connector=queue", MESSAGE);

        client.dispose();

    }

    // not possible before 2.0?
//    public void testLocalNameLocalTransformer() throws Exception
//    {
//        doTestTransformed("local-name-local-transformer");
//    }


    protected String receive(MuleClient client, String endpoint, String src) throws Exception
    {
        UMOMessage message = client.receive(endpoint, TIMEOUT);
        assertNotNull(message);
        assertNotNull(message.getPayloadAsString());
        logger.debug("Receive " + message.getPayloadAsString());
        assertEquals(src, message.getPayloadAsString());
        return message.getPayloadAsString();
    }

}
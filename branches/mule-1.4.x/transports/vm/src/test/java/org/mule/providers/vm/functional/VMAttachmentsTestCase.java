/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.vm.functional;

import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleMessage;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

public class VMAttachmentsTestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "vm-attachments-test.xml";
    }

    public void testAttachments() throws Exception
    {
        MuleMessage msg = new MuleMessage("Mmm... attachments!");
        FileDataSource ds = new FileDataSource(new File("transports/vm/src/test/resources/"
                                                        + getConfigResources()).getAbsoluteFile());
        msg.addAttachment("test-attachment", new DataHandler(ds));
        MuleClient client = new MuleClient();
        UMOMessage reply = client.send("vm-in", msg);

        assertNotNull(reply);
        if (reply.getExceptionPayload() != null)
        {
            fail(reply.getExceptionPayload().getException().getCause().toString());
        }

        assertEquals(1, reply.getAttachmentNames().size());
        assertNotNull(reply.getAttachment("mule"));
        assertTrue(reply.getAttachment("mule").getContentType().startsWith("image/gif"));
    }

}

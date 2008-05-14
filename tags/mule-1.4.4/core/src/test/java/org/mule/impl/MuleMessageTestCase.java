/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl;

import org.mule.tck.AbstractMuleTestCase;
import org.mule.transformers.simple.ByteArrayToObject;
import org.mule.transformers.simple.ObjectToByteArray;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;

public class MuleMessageTestCase extends AbstractMuleTestCase
{

    public void testAttachmentPersistence() throws Exception
    {
        ObjectToByteArray transformer = new ObjectToByteArray();
        transformer.setAcceptUMOMessage(true);

        UMOEvent event = RequestContext.setEvent(getTestEvent("Mmm... attachments!"));
        UMOMessage msg = event.getMessage();
        msg.addAttachment("test-attachment", new DataHandler(new StringDataSource("attachment")));

        Object serialized = transformer.transform(msg);
        assertNotNull(serialized);

        UMOMessage deserialized = (UMOMessage) new ByteArrayToObject().transform(serialized);
        assertNotNull(deserialized);
        assertEquals(deserialized.getUniqueId(), msg.getUniqueId());
        assertEquals(deserialized.getPayload(), msg.getPayload());
        assertEquals(deserialized.getAttachmentNames(), msg.getAttachmentNames());
    }

    // silly little fake DataSource so that we don't need to use javamail
    protected static class StringDataSource implements DataSource
    {
        protected String content;

        public StringDataSource(String payload)
        {
            super();
            content = payload;
        }

        public InputStream getInputStream() throws IOException
        {
            return new StringBufferInputStream(content);
        }

        public OutputStream getOutputStream()
        {
            throw new UnsupportedOperationException("Read-only javax.activation.DataSource");
        }

        public String getContentType()
        {
            return "text/plain";
        }

        public String getName()
        {
            return "StringDataSource";
        }
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email;

import org.mule.config.i18n.Messages;
import org.mule.providers.AbstractMessageAdapter;
import org.mule.umo.MessagingException;
import org.mule.umo.provider.MessageTypeNotSupportedException;
import org.mule.util.IOUtils;
import org.mule.util.StringUtils;
import org.mule.util.SystemUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Enumeration;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.Part;

/**
 * <code>SimpleMailMessageAdapter</code> is an adapter for mail messages.  
 * Unlike {@link MailMessageAdapter} this preserves the message intact in its original 
 * form.
 */
public class SimpleMailMessageAdapter extends AbstractMessageAdapter {

    private static final long serialVersionUID = 8002607243523460556L;
    private Part part;
    private byte[] cache = null;

    public SimpleMailMessageAdapter(Object object) throws MessagingException 
    {
        Message message = assertMessageType(object);

        try 
        {
            setMessageDetails(message);
            handleMessage(message);
        } 
        catch (Exception e) 
        {
            throw new MessagingException(
                new org.mule.config.i18n.Message(Messages.FAILED_TO_CREATE_X, "Message Adapter"), 
                e);
        }
    }

    /**
     * By default, this simply stores the entire message as a single part.
     * Sub-classes may override with more complex processing.
     * 
     * @param message
     * @throws Exception
     */
    protected void handleMessage(Message message) throws Exception 
    {
        setPart(message);
    }

    protected void setPart(Part part) 
    {
        this.part = part;
    }

    public Object getPayload() {
        return part;
    }

    public byte[] getPayloadAsBytes() throws Exception 
    {
        return buildCache(getEncoding());
    }

    public String getPayloadAsString(String encoding) throws Exception
    {
        // TODO - i don't understand how encoding is used here
        // could this method be called with various encodings?
        // does that invalidate the cache?
        // (ie there are two encodings -- one used to generate the cache from
        // the mail message, and one used to generate the string from the cache)
        return new String(buildCache(encoding), encoding);
    }

    private byte[] buildCache(String encoding) throws Exception
    {
        if (null == cache)
        {
            if (part.getContentType().startsWith("text/"))
            {
                cache = textPayload(encoding);
            }
            else
            {
                cache = binaryPayload();
            }
        }
        return cache;
    }

    private static Message assertMessageType(Object message) throws MessageTypeNotSupportedException
    {
        if (message instanceof Message)
        {
            return (Message)message;
        }
        else
        {
            throw new MessageTypeNotSupportedException(message, MailMessageAdapter.class);
        }
    }

    private void setMessageDetails(Message message) throws javax.mail.MessagingException 
    {
        setProperty(MailProperties.TO_ADDRESSES_PROPERTY,
            MailUtils.mailAddressesToString(message.getRecipients(Message.RecipientType.TO)));
        setProperty(MailProperties.CC_ADDRESSES_PROPERTY,
            MailUtils.mailAddressesToString(message.getRecipients(Message.RecipientType.CC)));
        setProperty(MailProperties.BCC_ADDRESSES_PROPERTY,
            MailUtils.mailAddressesToString(message.getRecipients(Message.RecipientType.BCC)));
        setProperty(MailProperties.REPLY_TO_ADDRESSES_PROPERTY,
            MailUtils.mailAddressesToString(message.getReplyTo()));
        setProperty(MailProperties.FROM_ADDRESS_PROPERTY, 
            MailUtils.mailAddressesToString(message.getFrom()));
        setProperty(MailProperties.SUBJECT_PROPERTY, 
            StringUtils.defaultIfEmpty(message.getSubject(), "(no subject)"));
        setProperty(MailProperties.CONTENT_TYPE_PROPERTY, 
            StringUtils.defaultIfEmpty(message.getContentType(), "text/plain"));

        Date sentDate = message.getSentDate();
        if (sentDate == null)
        {
            sentDate = new Date();
        }
        setProperty(MailProperties.SENT_DATE_PROPERTY, sentDate);

        for (Enumeration e = message.getAllHeaders(); e.hasMoreElements();)
        {
            Header h = (Header)e.nextElement();
            setProperty(h.getName(), h.getValue());
        }
    }

    private static InputStream addBuffer(InputStream stream) 
    {
        if (!(stream instanceof BufferedInputStream))
        {
            stream = new BufferedInputStream(stream);
        }
        return stream;
    }

    private byte[] binaryPayload() throws Exception 
    {
        InputStream stream = addBuffer(part.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream(32768);
        IOUtils.copy(stream, baos);
        return baos.toByteArray();
    }

    private byte[] textPayload(String encoding) throws Exception 
    {
        InputStream stream = addBuffer(part.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer buffer = new StringBuffer(32768);

        String line;
        while ((line = reader.readLine()) != null)
        {
            buffer.append(line).append(SystemUtils.LINE_SEPARATOR);
        }

        return buffer.toString().getBytes(encoding);
    }

}

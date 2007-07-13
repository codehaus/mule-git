/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers;

import org.mule.MuleRuntimeException;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.RequestContext;
import org.mule.impl.SafeThreadAccess;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.util.ExceptionUtils;
import org.mule.util.SystemUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <code>DefaultMessageAdapter</code> can be used to wrap an arbitary object where
 * no special 'apapting' is needed. The adapter allows for a set of properties to be
 * associated with an object.
 */

public class DefaultMessageAdapter extends AbstractMessageAdapter
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 1908152148142575505L;

    /**
     * The message object wrapped by this adapter
     */
    protected Object message;

    /**
     * Creates a default message adapter with properties and attachments
     * 
     * @param message the message to wrap. If this is null and NullPayload object
     *            will be used
     * @see NullPayload
     */
    public DefaultMessageAdapter(Object message)
    {
        if (message == null)
        {
            this.message = NullPayload.getInstance();
        }
        else
        {
            this.message = message;
        }
    }

    public DefaultMessageAdapter(Object message, UMOMessageAdapter previous)
    {
        if (previous != null)
        {
            id = previous.getUniqueId();

            if (message == null)
            {
                this.message = NullPayload.getInstance();
            }
            else
            {
                this.message = message;
            }
            for (Iterator iterator = previous.getAttachmentNames().iterator(); iterator.hasNext();)
            {
                String name = (String) iterator.next();
                try
                {
                    addAttachment(name, previous.getAttachment(name));
                }
                catch (Exception e)
                {
                    throw new MuleRuntimeException(CoreMessages.failedToReadPayload(), e);
                }
            }
            final Set propertyNames = previous.getPropertyNames();
            //final UMOEvent event = RequestContext.getEvent();
            //final Object v = event.getMessage().getProperty(MuleProperties.MULE_REMOTE_SYNC_PROPERTY);
            //final String s = event.toString();
            for (Iterator iterator = propertyNames.iterator(); iterator.hasNext();)
            {
                String name = (String) iterator.next();

                if (previous.getProperty(name) == null)
                {
                    //System.out.println("EVENT: " + s);
                    //System.out.println(RequestContext.getEvent());
                    final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");

                    RequestContext.TraceHolder here = new RequestContext.TraceHolder(
                            new Throwable().fillInStackTrace(),
                            Thread.currentThread().getName(),
                            System.currentTimeMillis(), "PROBLEM DETECTED",
                            previous.toString()
                    );
                    RequestContext.history.addLast(here);

                    for  (int i = 0; i < 10 && ! RequestContext.history.isEmpty(); i++)
                    {
                        dump(sdf);
                    }
                }

                try
                {
                    setProperty(name, previous.getProperty(name));
                }
                catch (Exception e)
                {
                    throw new MuleRuntimeException(CoreMessages.failedToReadPayload(), e);
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("previousAdapter may not be null");
        }
    }

    private void dump(final SimpleDateFormat sdf)
    {
        final RequestContext.TraceHolder trace;
        trace = (RequestContext.TraceHolder) RequestContext.history.removeLast();
        StringBuffer sb = new StringBuffer(2048);
        sb.append(sdf.format(new Date(trace.timestamp))).append(" : ");
        sb.append(trace.threadName).append(' ');
        sb.append("<-------------- ").append(trace.tag).append(SystemUtils.LINE_SEPARATOR);
        sb.append(trace.messageName).append(SystemUtils.LINE_SEPARATOR);
        sb.append(ExceptionUtils.getStackTrace(trace.throwable));
        System.out.println(sb);
        System.out.println("---------------------------------");
        System.out.println();
    }

    /**
     * Creates a default message adapter with properties and attachments
     * 
     * @param message the message to wrap. If this is null and NullPayload object
     *            will be used
     * @param properties a map properties to set on the adapter. Can be null.
     * @param attachments a map attaches (DataHandler objects) to set on the adapter.
     *            Can be null.
     * @see NullPayload
     * @see javax.activation.DataHandler
     */
    public DefaultMessageAdapter(Object message, Map properties, Map attachments)
    {
        this(message);
        if (properties != null)
        {
            this.properties.putAll(properties);
        }
        if (attachments != null)
        {
            this.attachments.putAll(attachments);
        }
    }

    /**
     * Converts the message implementation into a String representation
     * 
     * @param encoding The encoding to use when transforming the message (if
     *            necessary). The parameter is used when converting from a byte array
     * @return String representation of the message payload
     * @throws Exception Implementation may throw an endpoint specific exception
     */
    public String getPayloadAsString(String encoding) throws Exception
    {
        if (message instanceof byte[])
        {
            if (encoding != null)
            {
                return new String((byte[]) message, encoding);
            }
            else
            {
                return new String((byte[]) message);
            }
        }
        else
        {
            return message.toString();
        }
    }

    /**
     * Converts the message implementation into a String representation
     * 
     * @return String representation of the message
     * @throws Exception Implemetation may throw an endpoint specific exception
     */
    public byte[] getPayloadAsBytes() throws Exception
    {
        return convertToBytes(message);
    }

    /**
     * @return the current message
     */
    public Object getPayload()
    {
        return message;
    }

    public String getUniqueId()
    {
        return id;
    }

    public SafeThreadAccess newCopy()
    {
        return new DefaultMessageAdapter(getPayload(), this);
    }

}

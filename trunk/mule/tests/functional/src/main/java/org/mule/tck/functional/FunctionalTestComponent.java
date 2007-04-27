/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.functional;

import org.mule.MuleException;
import org.mule.MuleManager;
import org.mule.config.i18n.Message;
import org.mule.impl.RequestContext;
import org.mule.umo.UMOEventContext;
import org.mule.umo.lifecycle.Callable;
import org.mule.util.StringMessageUtils;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>FunctionalTestComponent</code> is a component that can be used by
 * functional tests. This component accepts an EventCallback that can be used to
 * assert the state of the current event.
 * 
 * @see EventCallback
 */

public class FunctionalTestComponent implements Callable
{
    protected transient Log logger = LogFactory.getLog(getClass());

    public static final int STREAM_SAMPLE_SIZE = 4;
    public static final int STREAM_BUFFER_SIZE = 4096;
    private EventCallback eventCallback;
    private Object returnMessage = null;
    private boolean appendComponentName = false;
    private boolean throwException = false;

    public Object onCall(UMOEventContext context) throws Exception
    {
        String contents = context.getTransformedMessageAsString();
        String msg = StringMessageUtils.getBoilerPlate("Message Received in component: "
                        + context.getComponentDescriptor().getName() + ". Content is: "
                        + StringMessageUtils.truncate(contents, 100, true), '*', 80);

        logger.info(msg);

        if (eventCallback != null)
        {
            eventCallback.eventReceived(context, this);
        }

        Object replyMessage;
        if (returnMessage != null)
        {
            replyMessage = returnMessage;
        }
        else
        {
            replyMessage = contents + " Received" + (appendComponentName ?  " " + context.getComponentDescriptor().getName() : "");
        }

        MuleManager.getInstance().fireNotification(
            new FunctionalTestNotification(context, replyMessage, FunctionalTestNotification.EVENT_RECEIVED));

        if (throwException)
        {
            throw new MuleException(Message.createStaticMessage("Functional Test Component Exception"));
        }

        return replyMessage;
    }

    public Object onReceive(Object data) throws Exception
    {
        UMOEventContext context = RequestContext.getEventContext();

        String contents = data.toString();
        String msg = StringMessageUtils.getBoilerPlate("Message Received in component: "
                        + context.getComponentDescriptor().getName() + ". Content is: "
                        + StringMessageUtils.truncate(contents, 100, true), '*', 80);

        logger.info(msg);

        if (eventCallback != null)
        {
            eventCallback.eventReceived(context, this);
        }

        Object replyMessage;
        if (returnMessage != null)
        {
            replyMessage = returnMessage;
        }
        else
        {
            replyMessage = contents + " Received";
        }

        MuleManager.getInstance().fireNotification(
            new FunctionalTestNotification(context, replyMessage, FunctionalTestNotification.EVENT_RECEIVED));

        if (throwException)
        {
            throw new MuleException(Message.createStaticMessage("Functional Test Component Exception"));
        }

        return replyMessage;
    }

    public EventCallback getEventCallback()
    {
        return eventCallback;
    }

    public void setEventCallback(EventCallback eventCallback)
    {
        this.eventCallback = eventCallback;
    }

    public Object getReturnMessage()
    {
        return returnMessage;
    }

    public void setReturnMessage(Object returnMessage)
    {
        this.returnMessage = returnMessage;
    }

    public boolean isThrowException()
    {
        return throwException;
    }

    public void setThrowException(boolean throwException)
    {
        this.throwException = throwException;
    }

    public boolean isAppendComponentName()
    {
        return appendComponentName;
    }

    public void setAppendComponentName(boolean appendComponentName)
    {
        this.appendComponentName = appendComponentName;
    }

    /**
     * Experimental extension for testing streaming - we don't return a stream here (ie include
     * OuputStream as a second parameter) anything because I can't get synchronous reply to
     * work with streaming (I'm reduced to baby steps here).
     */
    public Object receiveStream(InputStream in) throws Exception
    {
        try
        {
            logger.debug("arrived at FTC");
            byte[] startData = new byte[STREAM_SAMPLE_SIZE];
            int startDataSize = 0;
            byte[] endData = new byte[STREAM_SAMPLE_SIZE]; // ring buffer
            int endDataSize = 0;
            int endRingPointer = 0;
            int streamLength = 0;
            byte[] buffer = new byte[STREAM_BUFFER_SIZE];

            // throw data on the floor, but keep a record of size, start and end values
            int bytesRead = 0;
            while (bytesRead >= 0)
            {
                bytesRead = in.read(buffer);
                if (bytesRead > 0)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("read " + bytesRead + " bytes");
                    }
                    streamLength += bytesRead;
                    int startOfEndBytes = 0;
                    for (int i = 0; startDataSize < STREAM_SAMPLE_SIZE && i < bytesRead; ++i)
                    {
                        startData[startDataSize++] = buffer[i];
                        ++startOfEndBytes; // skip data included in startData
                    }
                    startOfEndBytes = Math.max(startOfEndBytes, bytesRead - STREAM_SAMPLE_SIZE);
                    for (int i = startOfEndBytes; i < bytesRead; ++i)
                    {
                        ++endDataSize;
                        endData[endRingPointer++ % STREAM_SAMPLE_SIZE] = buffer[i];
                    }
                }
            }
            endDataSize = Math.min(endDataSize, STREAM_SAMPLE_SIZE);

            // make a nice summary of the data
            StringBuffer result = new StringBuffer("Received stream");
            result.append("; length: ");
            result.append(streamLength);
            result.append("; '");
            for (int i = 0; i < startDataSize; ++i)
            {
                result.append((char) startData[i]);
            }
            if (endDataSize > 0)
            {
                result.append("...");
                for (int i = 0; i < endDataSize; ++i)
                {
                    result.append((char) endData[(endRingPointer + i) % STREAM_SAMPLE_SIZE]);
                }
            }
            result.append("'");

            String message = result.toString();
            logger.debug(message);
            return onReceive(message);
        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(e);
            }
            throw e;
        }
    }

}

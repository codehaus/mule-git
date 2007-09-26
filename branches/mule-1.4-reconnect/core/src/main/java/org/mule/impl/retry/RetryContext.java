/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.retry;

import org.mule.umo.UMOMessage;
import org.mule.umo.retry.UMORetryContext;

import java.util.Map;

/**
 * TODO
 */
public class RetryContext implements UMORetryContext
{
    private UMOMessage[] returnMessages;
    private Map metaInfo;
    private String description;

    public RetryContext(String description)
    {
        this.description = description;
    }

    public Map getMetaInfo()
    {
        return metaInfo;
    }

    public void setMetaInfo(Map metaInfo)
    {
        this.metaInfo = metaInfo;
    }

    public UMOMessage[] getReturnMessages()
    {
        return returnMessages;
    }

    public UMOMessage getFirstReturnMessage()
    {
        return (returnMessages == null ? null : returnMessages[0]);
    }

    public void setReturnMessages(UMOMessage[] returnMessages)
    {
        this.returnMessages = returnMessages;
    }

    public void addReturnMessage(UMOMessage result)
    {
        if(returnMessages ==null)
        {
            returnMessages = new UMOMessage[]{result};
        }
        else
        {
            UMOMessage[] newReturnMessages = new UMOMessage[returnMessages.length+1];
            System.arraycopy(newReturnMessages, 0, returnMessages, 0, 1);
            returnMessages = newReturnMessages;
        }
    }

    public String getDescription()
    {
        return description;
    }
}

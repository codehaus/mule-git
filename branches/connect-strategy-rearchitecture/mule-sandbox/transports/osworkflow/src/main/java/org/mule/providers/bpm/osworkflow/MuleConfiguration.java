/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm.osworkflow;

import org.mule.providers.bpm.MessageService;

import com.opensymphony.workflow.config.DefaultConfiguration;

public class MuleConfiguration extends DefaultConfiguration
{
    private MessageService msgService;
    
    public MuleConfiguration(MessageService msgService)
    {
        super();
        this.msgService = msgService;
    }

    public MessageService getMsgService()
    {
        return msgService;
    }

    public void setMsgService(MessageService msgService)
    {
        this.msgService = msgService;
    }
}



/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.transport.MuleMessageFactory;

public class DefaultMuleMessageFactory implements MuleMessageFactory
{
    private MuleContext muleContext;

    public DefaultMuleMessageFactory(MuleContext context)
    {
        super();
        muleContext = context;
    }
    
    public MuleMessage create(Object transportMessage) throws Exception
    {
        if (transportMessage == null)
        {
            transportMessage = NullPayload.getInstance();
        }
        
        return new DefaultMuleMessage(transportMessage, muleContext);
    }
}



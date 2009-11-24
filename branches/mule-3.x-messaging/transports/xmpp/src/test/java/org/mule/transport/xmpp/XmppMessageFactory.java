/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.xmpp;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.transport.AbstractMuleMessageFactory;

public class XmppMessageFactory extends AbstractMuleMessageFactory
{
    public XmppMessageFactory(MuleContext context)
    {
        super(context);
    }

    @Override
    protected MuleMessage doCreate(Object transportMessage) throws Exception
    {
        return new DefaultMuleMessage(transportMessage, muleContext);
    }
}



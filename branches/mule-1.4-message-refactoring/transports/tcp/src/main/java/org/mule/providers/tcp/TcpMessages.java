/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.tcp;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

import java.net.URI;

public class TcpMessages extends MessageFactory
{
    private static final String BUNDLE_NAME = "tcp";

    public static Message failedToBindToUri(URI uri)
    {
        return createMessage(BUNDLE_NAME, 1);
    }

    public static Message failedToCloseSocket()
    {
        return createMessage(BUNDLE_NAME, 2);
    }

    public static Message failedToInitMessageReader()
    {
        return createMessage(BUNDLE_NAME, 3);
    }
}



/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.client;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

public class ClientMessages extends MessageFactory
{
    private static final String BUNDLE_NAME = "client";

    public static Message failedToDispatchClientEvent()
    {
        return createMessage(BUNDLE_NAME, 1);
    }

    public static Message managerIsAlreadyConfigured()
    {
        return createMessage(BUNDLE_NAME, 3);
    }

}



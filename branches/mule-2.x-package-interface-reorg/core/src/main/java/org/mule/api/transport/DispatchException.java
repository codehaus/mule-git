/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.transport;

import org.mule.api.UMOMessage;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.routing.RoutingException;
import org.mule.imple.config.i18n.Message;

/**
 * <code>DispatchException</code> is thrown when an endpoint dispatcher fails to
 * send, dispatch or receive a message.
 */

public class DispatchException extends RoutingException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -8204621943732496605L;

    public DispatchException(UMOMessage message, UMOImmutableEndpoint endpoint)
    {
        super(message, endpoint);
    }

    public DispatchException(UMOMessage umoMessage, UMOImmutableEndpoint endpoint, Throwable cause)
    {
        super(umoMessage, endpoint, cause);
    }

    public DispatchException(Message message, UMOMessage umoMessage, UMOImmutableEndpoint endpoint)
    {
        super(message, umoMessage, endpoint);
    }

    public DispatchException(Message message,
                             UMOMessage umoMessage,
                             UMOImmutableEndpoint endpoint,
                             Throwable cause)
    {
        super(message, umoMessage, endpoint, cause);
    }
}

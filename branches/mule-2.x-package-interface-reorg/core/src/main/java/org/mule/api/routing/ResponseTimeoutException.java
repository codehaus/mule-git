/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.routing;

import org.mule.api.UMOMessage;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.imple.config.i18n.Message;

/**
 * <code>ResponseTimeoutException</code> is thrown when a response is not received
 * in a given timeout in the Response Router.
 * 
 * @see org.mule.api.routing.UMOResponseRouter
 */
public class ResponseTimeoutException extends RoutingException
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 6882278747922113239L;

    public ResponseTimeoutException(Message message, UMOMessage umoMessage, UMOImmutableEndpoint endpoint)
    {
        super(message, umoMessage, endpoint);
    }

    public ResponseTimeoutException(Message message,
                                    UMOMessage umoMessage,
                                    UMOImmutableEndpoint endpoint,
                                    Throwable cause)
    {
        super(message, umoMessage, endpoint, cause);
    }
}

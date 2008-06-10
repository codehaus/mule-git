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

import org.mule.api.MuleEventContext;

/**
 * THis interface can be implemented by components who service definition contains a <async-reply> router.
 * This method will be called when a response is received befroe it is passed back to the callee.
 */
public interface ReplyCallback
{
    public Object onReply(MuleEventContext eventContext);
}

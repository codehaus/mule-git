/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.testmodels.mule;

import org.mule.api.UMOEvent;
import org.mule.api.UMOMessage;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.routing.RoutingException;
import org.mule.impl.transport.AbstractMessageDispatcher;

public class TestMessageDispatcher extends AbstractMessageDispatcher
{

    public TestMessageDispatcher(final UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
    }

    protected void doDispose()
    {
        // template method
    }

    protected void doDispatch(UMOEvent event) throws Exception
    {
        if (event.getEndpoint().getEndpointURI().toString().equals("test://AlwaysFail"))
        {
            throw new RoutingException(event.getMessage(), event.getEndpoint());
        }
    }

    protected UMOMessage doSend(UMOEvent event) throws Exception
    {
        if (event.getEndpoint().getEndpointURI().toString().equals("test://AlwaysFail"))
        {
            throw new RoutingException(event.getMessage(), event.getEndpoint());
        }
        return event.getMessage();
    }

    protected void doConnect() throws Exception
    {
        // no op
    }

    protected void doDisconnect() throws Exception
    {
        // no op
    }

}

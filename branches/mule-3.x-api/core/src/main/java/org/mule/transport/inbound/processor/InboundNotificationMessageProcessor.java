/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.inbound.processor;

import org.mule.api.MuleEvent;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.service.Service;
import org.mule.context.notification.EndpointMessageNotification;
import org.mule.processor.AbstractMessageObserver;
import org.mule.transport.AbstractConnector;

public class InboundNotificationMessageProcessor extends AbstractMessageObserver
{
    @Override
    public void observe(MuleEvent event)
    {
        ImmutableEndpoint endpoint = event.getEndpoint();
        AbstractConnector connector = (AbstractConnector) endpoint.getConnector();
        Service service = event.getService();
        if (connector.isEnableMessageEvents())
        {
            connector.fireNotification(new EndpointMessageNotification(event.getMessage(), endpoint,
                service.getName(), EndpointMessageNotification.MESSAGE_RECEIVED));
        }
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.routing.inbound;

import org.mule.api.MessagingException;
import org.mule.api.UMOEvent;
import org.mule.api.UMOFilter;
import org.mule.api.UMOMessage;
import org.mule.api.routing.RoutingException;
import org.mule.api.routing.UMOInboundRouter;
import org.mule.api.transformer.TransformerException;
import org.mule.impl.MuleMessage;
import org.mule.impl.routing.AbstractRouter;
import org.mule.imple.config.i18n.CoreMessages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>SelectiveConsumer</code> is an inbound router used to filter out unwanted
 * events. The filtering is performed by a <code>UMOFilter</code> that can be set
 * on the router.
 * 
 * @see UMOInboundRouter
 * @see org.mule.api.routing.UMOInboundRouterCollection
 * @see org.mule.api.routing.UMORouterCollection
 */

public class SelectiveConsumer extends AbstractRouter implements UMOInboundRouter
{
    protected final Log logger = LogFactory.getLog(getClass());

    private volatile UMOFilter filter;
    private volatile boolean transformFirst = true;

    public boolean isMatch(UMOEvent event) throws MessagingException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Attempting to route event: " + event.getId());
        }

        if (filter == null)
        {
            return true;
        }

        UMOMessage message = event.getMessage();

        if (transformFirst)
        {
            try
            {
                Object payload = event.transformMessage();
                message = new MuleMessage(payload, message);
            }
            catch (TransformerException e)
            {
                throw new RoutingException(
                    CoreMessages.transformFailedBeforeFilter(), event.getMessage(), 
                    event.getEndpoint(), e);
            }
        }

        boolean result = filter.accept(message);

        if (logger.isDebugEnabled())
        {
            logger.debug("Event " + event.getId() + (result ? " passed filter " : " did not pass filter ")
                            + filter.getClass().getName());
        }

        return result;
    }

    public UMOEvent[] process(UMOEvent event) throws MessagingException
    {
        if (this.isMatch(event))
        {
            return new UMOEvent[]{event};
        }
        else
        {
            return null;
        }
    }

    public UMOFilter getFilter()
    {
        return filter;
    }

    public void setFilter(UMOFilter filter)
    {
        this.filter = filter;
    }

    public boolean isTransformFirst()
    {
        return transformFirst;
    }

    public void setTransformFirst(boolean transformFirst)
    {
        this.transformFirst = transformFirst;
    }
}

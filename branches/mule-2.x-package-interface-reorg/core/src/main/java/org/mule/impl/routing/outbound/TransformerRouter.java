/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.routing.outbound;

import org.mule.api.MessagingException;
import org.mule.api.UMOMessage;
import org.mule.api.UMOSession;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.routing.RoutingException;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transformer.UMOTransformer;
import org.mule.impl.MuleMessage;
import org.mule.imple.config.i18n.CoreMessages;

/**
 * Simply applies a transformer before continuing on to the next router.
 * This can be useful with the {@link ChainingRouter}.
 */
public class TransformerRouter extends AbstractOutboundRouter
{
    private UMOTransformer transformer;

    public UMOMessage route(UMOMessage message, UMOSession session, boolean synchronous) throws MessagingException
    {
        if (transformer != null)
        {
            try
            {
                Object payload = transformer.transform(message.getPayload());
                message = new MuleMessage(payload, message);
            }
            catch (TransformerException e)
            {
                throw new RoutingException(
                    CoreMessages.transformFailedBeforeFilter(),
                    message, (UMOImmutableEndpoint)endpoints.get(0), e);
            }
        }
        return message;
    }

    public boolean isMatch(UMOMessage message) throws MessagingException
    {
        return true;
    }

    public UMOTransformer getTransformer()
    {
        return transformer;
    }

    public void setTransformer(UMOTransformer transformer)
    {
        this.transformer = transformer;
    }
}


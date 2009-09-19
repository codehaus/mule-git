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

import org.mule.api.MuleRuntimeException;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.transport.xmpp.i18n.XmppMessages;

/**
 * A factory that creates {@link XmppConversation} instances based on the endpoint's configuration.
 */
public class XmppConversationFactory
{
    public XmppConversation create(ImmutableEndpoint endpoint)
    {
        String type = endpoint.getEndpointURI().getHost();
        if (XmppConnector.CONVERSATION_TYPE_MESSAGE.equals(type))
        {
            return new XmppMessageConversation(endpoint);
        }
        else if (XmppConnector.CONVERSATION_TYPE_CHAT.equals(type))
        {
            return new XmppChatConversation(endpoint);
        }
        else if (XmppConnector.CONVERSATION_TYPE_MULTI_USER_CHAT.equals(type))
        {
            // TODO xmpp: create me
            return null;
        }
        else
        {
            throw new MuleRuntimeException(XmppMessages.invalidConversationType(type));
        }
    }
}



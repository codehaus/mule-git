/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl;

import org.mule.api.AbstractMuleException;
import org.mule.api.MuleMessage;
import org.mule.api.Session;
import org.mule.api.transport.SessionHandler;

/**
 * A session handler that ignores any session information
 */
public class NullSessionHandler implements SessionHandler
{
    public void retrieveSessionInfoFromMessage(MuleMessage message, Session session) throws AbstractMuleException
    {
        // noop
    }

    public void storeSessionInfoToMessage(Session session, MuleMessage message) throws AbstractMuleException
    {
        // noop
    }

    /**
     * The property name of the session id to use when creating the Mule session. by
     * default the property name "ID" will be used. If no property was set on the
     * session called "ID" a session id will be automatically generated
     * 
     * @return the property name of the session id that is set on the session
     */
    public String getSessionIDKey()
    {
        return "ID";
    }
}

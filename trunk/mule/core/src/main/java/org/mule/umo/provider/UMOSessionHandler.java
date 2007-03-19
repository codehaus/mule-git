/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.umo.provider;

import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;

/**
 * An interface used for reading and writing session information to and from the
 * current message.
 */
public interface UMOSessionHandler
{

    void storeSessionInfoToMessage(UMOSession session, UMOMessage message) throws UMOException;

    void retrieveSessionInfoFromMessage(UMOMessage message, UMOSession session) throws UMOException;

    /**
     * The property name of the session id to use when creating the Mule session. by
     * default the property name "ID" will be used. If no property was set on the
     * session called "ID" a session id will be automatically generated
     * 
     * @return the property name of the session id that is set on the session
     */
    String getSessionIDKey();
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.soap.xfire;

import org.mule.api.Session;



/**
 * Mules session wrapper for XFire
 */
public class XFireMuleSession implements org.codehaus.xfire.transport.Session
{
    Session session;

    public XFireMuleSession(Session session)
    {
        if (session == null)
        {
            throw new IllegalArgumentException("Session");
        }
        this.session = session;
    }

    /**
     * Get a variable from the session by the key.
     * 
     * @param key
     * @return Value
     */
    public Object get(Object key)
    {
        return session.getProperty(key);
    }

    /**
     * Put a variable into the session with a key.
     * 
     * @param key
     * @param value
     */
    public void put(Object key, Object value)
    {
        session.setProperty(key, value);
    }
}

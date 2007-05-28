/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms.activemq;

import org.mule.providers.ConnectException;
import org.mule.providers.jms.JmsConnector;

import java.lang.reflect.Method;

import javax.jms.Connection;

/**
 * ActiveMQ 4.x-specific JMS connector.
 */
public class ActiveMqJmsConnector extends JmsConnector
{
    /**
     * Constructs a new ActiveMqJmsConnector.
     */
    public ActiveMqJmsConnector()
    {
        setEagerConsumer(false);
        // TODO MULE-1409 better support for ActiveMQ 4.x temp destinations
    }

    /**
     * Will additionally try to cleanup the ActiveMq connection, otherwise there's a deadlock on shutdown.
     */
    protected void doDisconnect() throws ConnectException
    {
        try
        {
            final Connection conn = getConnection();
            if (conn != null)
            {
                final Class clazz = conn.getClass();
                final Method cleanupMethod = clazz.getMethod("cleanup", null);
                if (cleanupMethod != null)
                {
                    try
                    {
                        cleanupMethod.invoke(conn, null);
                    }
                    finally
                    {
                        conn.close();
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new ConnectException(e, this);
        }
        finally
        {
            setConnection(null);
        }
    }
}

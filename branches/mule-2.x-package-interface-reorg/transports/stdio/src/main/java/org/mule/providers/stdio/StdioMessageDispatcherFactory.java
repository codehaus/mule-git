/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.stdio;

import org.mule.api.UMOException;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.transport.UMOMessageDispatcher;
import org.mule.impl.transport.AbstractMessageDispatcherFactory;

/**
 * <code>StdioMessageDispatcherFactory</code> creates a Stream dispatcher suitable
 * for writing to fixed streams such as System.in or System.out.
 */
public class StdioMessageDispatcherFactory extends AbstractMessageDispatcherFactory
{
    /*
     * (non-Javadoc)
     * 
     * @see org.mule.api.transport.UMOMessageDispatcherFactory#create(org.mule.api.transport.UMOConnector)
     */
    public UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException
    {
        return new StdioMessageDispatcher(endpoint);
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jdbc;

import org.mule.api.UMOException;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.transport.UMOMessageRequester;
import org.mule.impl.transport.AbstractMessageRequesterFactory;

/**
 * Creates JdbcMessageDispatchers.
 */
public class JdbcMessageRequesterFactory extends AbstractMessageRequesterFactory
{

    /*
     * (non-Javadoc)
     *
     * @see org.mule.api.transport.UMOMessageDispatcherFactory#create(org.mule.api.transport.UMOConnector)
     */
    public UMOMessageRequester create(UMOImmutableEndpoint endpoint) throws UMOException
    {
        return new JdbcMessageRequester(endpoint);
    }

}
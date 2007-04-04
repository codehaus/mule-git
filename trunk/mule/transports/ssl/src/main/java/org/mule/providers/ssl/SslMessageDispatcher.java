/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ssl;

import org.mule.providers.tcp.TcpMessageDispatcher;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

public class SslMessageDispatcher extends TcpMessageDispatcher
{

    public SslMessageDispatcher(UMOImmutableEndpoint endpoint)
    {
        super(endpoint);
    }

    /**
     * {@link TcpMessageDispatcher} opens and closes the socket at this point to check for
     * a clear connection.  We do not do this for SSL because: (i) it is expensive; (ii)
     * the advantages are doubtful and (iii) it throws an ugly (although apparently harmless)
     * exception.
     *
     * @throws Exception Never
     */
    // @Override
    protected void doConnect() throws Exception
    {
        // no-op
    }

}

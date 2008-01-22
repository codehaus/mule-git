/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.udp;

import org.mule.api.UMOException;
import org.mule.api.endpoint.UMOImmutableEndpoint;
import org.mule.api.transport.UMOMessageRequester;
import org.mule.impl.transport.AbstractMessageRequesterFactory;

/**
 * Creates a dispatcher for receiving UDP packets.
 */

public class UdpMessageRequesterFactory extends AbstractMessageRequesterFactory
{
    public UMOMessageRequester create(UMOImmutableEndpoint endpoint) throws UMOException
    {
        return new UdpMessageRequester(endpoint);
    }
}
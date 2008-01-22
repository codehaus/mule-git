/*
 * $Id: XFireMessageDispatcherFactory.java 4350 2006-12-20 16:34:49Z holger $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.cxf;

import org.mule.api.AbstractMuleException;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.MessageDispatcher;
import org.mule.impl.transport.AbstractMessageDispatcherFactory;

/**
 * Creates a CXF Message dispatcher used for making requests using the CXF client.
 */
public class CxfMessageDispatcherFactory extends AbstractMessageDispatcherFactory
{
    public MessageDispatcher create(ImmutableEndpoint endpoint) throws AbstractMuleException
    {
        return new CxfMessageDispatcher(endpoint);
    }
}

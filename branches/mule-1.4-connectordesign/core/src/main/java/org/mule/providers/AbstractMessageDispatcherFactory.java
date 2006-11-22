/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers;

import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOMessageDispatcherFactory;

public abstract class AbstractMessageDispatcherFactory implements UMOMessageDispatcherFactory
{
    // TODO can we use a backpointer into the connector?

    public AbstractMessageDispatcherFactory()
    {
        super();
    }

    public final void activateObject(Object key, Object obj) throws Exception
    {
        this.activate((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public final void destroyObject(Object key, Object obj) throws Exception
    {
        this.destroy((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public final Object makeObject(Object key) throws Exception
    {
        return this.create((UMOImmutableEndpoint)key);
    }

    public final void passivateObject(Object key, Object obj) throws Exception
    {
        this.passivate((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public final boolean validateObject(Object key, Object obj)
    {
        return this.validate((UMOImmutableEndpoint)key, (UMOMessageDispatcher)obj);
    }

    public abstract UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException;

    public void activate(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher) throws UMOException
    {
        // template method
    }

    public void destroy(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher)
    {
        // template method
    }

    public void passivate(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher)
    {
        // template method
    }

    public boolean validate(UMOImmutableEndpoint endpoint, UMOMessageDispatcher dispatcher)
    {
        return true;
    }

}

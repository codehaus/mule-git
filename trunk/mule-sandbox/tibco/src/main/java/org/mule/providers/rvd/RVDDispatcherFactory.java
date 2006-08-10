/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.rvd;

import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.UMOMessageDispatcher;
import org.mule.umo.provider.UMOMessageDispatcherFactory;


/**
 * Simply spins out a dispatcher
 *
 * @author <a href="mailto:ross.paul@mlb.com">Ross Paul</a>
 * @version $Revsion: $
 */
public class RVDDispatcherFactory implements UMOMessageDispatcherFactory
{
    public UMOMessageDispatcher create(UMOImmutableEndpoint endpoint) throws UMOException {
        return new RVDDispatcher(endpoint);
    }
}

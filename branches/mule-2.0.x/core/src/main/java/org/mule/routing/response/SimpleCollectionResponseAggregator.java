/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.routing.response;

import org.mule.DefaultMessageCollection;
import org.mule.api.MuleMessage;
import org.mule.api.MuleMessageCollection;
import org.mule.api.MuleEvent;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.routing.RoutingException;
import org.mule.routing.inbound.EventGroup;
import org.mule.routing.EventCorrelatorCallback;
import org.mule.routing.SingleCorrelatorCallback;
import org.mule.routing.CollectionCorrelatorCallback;

/**
 * A simple aggregator that will keep collecting events until a timeout is reached.  It will then return
 * a {@link org.mule.api.MuleMessageCollection} message.
 *
 * @see org.mule.api.MuleMessageCollection
 */
public class SimpleCollectionResponseAggregator extends ResponseCorrelationAggregator
{
    protected EventCorrelatorCallback getCorrelatorCallback()
    {
        return new CollectionCorrelatorCallback();
    }
}

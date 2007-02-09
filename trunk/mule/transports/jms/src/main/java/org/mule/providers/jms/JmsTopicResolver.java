/*
* $Id$
* --------------------------------------------------------------------------------------
* Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
*
* The software in this package is published under the terms of the MuleSource MPL
* license, a copy of which has been included with this distribution in the
* LICENSE.txt file.
*/

package org.mule.providers.jms;

import org.mule.umo.endpoint.UMOImmutableEndpoint;

import javax.jms.Destination;

public interface JmsTopicResolver
{
    boolean isTopic(UMOImmutableEndpoint endpoint);
    boolean isTopic(Destination destination);
}


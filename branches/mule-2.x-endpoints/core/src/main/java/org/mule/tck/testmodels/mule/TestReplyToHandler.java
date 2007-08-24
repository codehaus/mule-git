/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tck.testmodels.mule;

import org.mule.providers.DefaultReplyToHandler;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.transformer.UMOTransformer;

public class TestReplyToHandler extends DefaultReplyToHandler
{

    public TestReplyToHandler(UMOTransformer transformer)
    {
        super(transformer);
    }

    public synchronized UMOEndpoint getEndpoint(UMOEvent event, String endpointUri) throws UMOException
    {
        return super.getEndpoint(event, endpointUri);
    }
    
    

}



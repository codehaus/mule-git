/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.components.simple;

import org.mule.umo.UMOEventContext;
import org.mule.umo.lifecycle.Callable;

/**
 * <code>NullComponent</code> is a component that is used as a placeholder.
 * This implementation will throw an exception if a message is received for it.
 * @deprecated This component used to be used in conjection with the Forwarding consumer inbound
 * router.  You can now use the BridgeComponent that takes care of configuring the inbound router
 * for you.
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision$
 */
public class NullComponent implements Callable
{
    public Object onCall(UMOEventContext context) throws Exception
    {
        throw new UnsupportedOperationException("This component cannot receive messages. Component is: "
                + context.getComponentDescriptor().getName());
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.foo;

import org.mule.tck.providers.AbstractMessageAdapterTestCase;
import org.mule.umo.provider.UMOMessageAdapter;
import org.mule.umo.MessagingException;

public class FooMessageAdapterTestCase extends AbstractMessageAdapterTestCase
{
    public Object getValidMessage() throws Exception
    {
    	//Todo Create a valid message for your transport
    	throw new UnsupportedOperationException("getValidMessage");
    }

    public UMOMessageAdapter createAdapter(Object payload) throws MessagingException
    {
        return new FooMessageAdapter(payload);
    }
}

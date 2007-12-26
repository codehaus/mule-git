/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.model.direct;

import org.mule.impl.MuleDescriptor;
import org.mule.impl.model.AbstractComponentTestCase;
import org.mule.umo.UMODescriptor;

public class DirectComponentTestCase extends AbstractComponentTestCase
{

    protected void doSetUp() throws Exception
    {
        UMODescriptor descriptor = new MuleDescriptor("direct");
        descriptor.setImplementation(new Object());
        component = new DirectModel().createComponent(descriptor);
    }

    protected void doTearDown() throws Exception
    {
        component = null;
    }

}

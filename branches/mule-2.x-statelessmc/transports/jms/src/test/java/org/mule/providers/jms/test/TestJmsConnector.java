/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms.test;

import org.mule.providers.jms.JmsConnector;
import org.mule.util.object.SimpleObjectFactory;
import org.mule.util.object.ObjectFactory;
import org.mule.umo.lifecycle.InitialisationException;

public class TestJmsConnector extends JmsConnector
{

    public TestJmsConnector() throws InitialisationException
    {
        ObjectFactory factory = new SimpleObjectFactory(TestConnectionFactory.class);
        factory.initialise();
        setConnectionFactory(factory);
    }

}

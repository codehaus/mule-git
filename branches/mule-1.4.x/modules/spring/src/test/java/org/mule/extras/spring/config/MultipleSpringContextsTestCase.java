/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;

import org.mule.MuleManager;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.manager.UMOContainerContext;
import org.mule.umo.manager.UMOManager;

/**
 * Test more than one Spring container in the same Mule config file.
 */
public class MultipleSpringContextsTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "multiple-spring-contexts-mule.xml";
    }

    public void testMultipleSpringContexts() throws Exception
    {
        // initialize the manager
        UMOManager manager = MuleManager.getInstance();

        UMOContainerContext context = manager.getContainerContext();
        assertNotNull(context);
        Object bowl1 = context.getComponent("fruitBowl");
        assertNotNull(bowl1);
        Object bowl2 = context.getComponent("fruitBowl2");
        assertNotNull(bowl2);
    }
}

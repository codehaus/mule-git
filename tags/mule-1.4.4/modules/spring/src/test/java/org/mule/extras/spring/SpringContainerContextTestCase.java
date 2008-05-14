/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring;

import org.mule.tck.model.AbstractContainerContextTestCase;
import org.mule.umo.manager.ObjectNotFoundException;
import org.mule.umo.manager.UMOContainerContext;

/**
 * Tests the Spring container.
 */
public class SpringContainerContextTestCase extends AbstractContainerContextTestCase
{
    SpringContainerContext context;

    public UMOContainerContext getContainerContext()
    {
        return context;
    }

    protected void doSetUp() throws Exception
    {
        context = new SpringContainerContext();
        context.setConfigResources(getConfigResources());
    }

    public String getConfigResources()
    {
        return "test-application-context.xml";
    }

    public void testContainerContext() throws Exception
    {
        UMOContainerContext container = getContainerContext();
        container.initialise();
        assertNotNull(container);

        try
        {
            container.getComponent(null);
            fail("Should throw ObjectNotFoundException for null key");
        }
        catch (ObjectNotFoundException e)
        {
            // expected
        }

        try
        {
            container.getComponent("abcdefg123456!�$%^n");
            fail("Should throw ObjectNotFoundException for a key that doesn't exist");
        }
        catch (ObjectNotFoundException e)
        {
            // expected
        }

        try
        {
            Object result = container.getComponent("apple");
            assertNotNull("Component should exist in container", result);
        }
        catch (ObjectNotFoundException e)
        {
            fail("Component should exist in the container");
        }
    }

    protected String getFruitBowlComponentName()
    {
        return "fruitBowl";
    }
}

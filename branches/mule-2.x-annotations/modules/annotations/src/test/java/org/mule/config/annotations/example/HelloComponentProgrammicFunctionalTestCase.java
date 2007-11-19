/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.annotations.example;

import org.mule.extras.client.MuleClient;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOMessage;
import org.mule.util.IOUtils;

import java.util.Properties;
import java.io.IOException;

/**
 * Tests registering an annotated component with the registry programmatically and using
 * property placeholders in the annotations
 */
public class HelloComponentProgrammicFunctionalTestCase extends AbstractMuleTestCase
{

    @Override
    protected Properties getStartUpProperties()
    {
        //these will be made avaialble at start up
        Properties p = new Properties();
        try
        {
            p.load(IOUtils.getResourceAsStream("annotation-test.properties", getClass()));
            return p;
        }
        catch (IOException e)
        {
            fail(e.getMessage());
            return null;
        }
    }

    public void testHelloComponent() throws Exception
    {
        managementContext.getRegistry().registerObject("helloService", new AnnotatedHelloComponent());
        managementContext.getRegistry().registerObject("greeter", new GreetingComponent());

        //TODO we need object that are added at runtime to assume the same lifecycle as the ManagementContext
        managementContext.getRegistry().lookupComponent("helloService").start();
        managementContext.getRegistry().lookupComponent("greeter").start();

        MuleClient client = new MuleClient();

        UMOMessage message = client.send("vm://hello.service", "Ross", null);

        assertNotNull(message);
        assertEquals("Hello Ross", message.getPayloadAsString());
    }
}
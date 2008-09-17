/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.models;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.model.seda.SedaModel;
import org.mule.routing.inbound.InboundRouterCollection;
import org.mule.umo.UMOException;
import org.mule.umo.model.UMOModel;

import java.beans.ExceptionListener;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class MuleProxyTestCase extends TestCase
{
    private boolean exceptionThrown;

    /**
     * With the unpatched code, this test will fail because of an NPE when
     * DefaultMuleProxy tries to access the non-existent object pool.
     */
    public void testNoComponentPool() throws Exception
    {
        // Create a new Mule instance and client.
        MuleClient client = new MuleClient(true);

        // Configure the SEDA model so that it uses a singleton proxy for
        // all requests.
        SedaModel model = createTestModel();
        model.setEnablePooling(false);
        model.setComponentPerRequest(false);

        // Create a test component that listens to a VM queue.
        TestComponent comp = createTestComponent(model);
        
        // Send a message to the component via the test queue;
        // this must be done asynchronously in order to cause an exception.
        client.dispatch("vm://test.queue", new Integer(99), null);

        // Give the message time to propagate.
        Thread.sleep(4000);

        // Fail if we timed out.
        assertTrue(comp.isCalled());

        // Fail if an exception was thrown;
        // this should assert with the unpatched code.
        assertFalse(exceptionThrown);
    }

    public void testComponentPerRequest() throws Exception
    {
        // Create a new Mule instance and client.
        MuleClient client = new MuleClient(true);

        // Configure the SEDA model so that it creates a new proxy per request.
        SedaModel model = createTestModel();
        model.setEnablePooling(false);
        model.setComponentPerRequest(true);

        // Create a test component that listens to a VM queue.
        TestComponent comp = createTestComponent(model);

        // Send a message to the component via the test queue;
        // this must be done asynchronously in order to cause an exception.
        client.dispatch("vm://test.queue", new Integer(99), null);

        // Give the message time to propagate.
        Thread.sleep(4000);

        // Fail if we timed out;
        // this should assert with the unpatched code since the interceptor
        // fails before the component is called.
        assertTrue(comp.isCalled());

        // Fail if an exception was thrown.
        assertFalse(exceptionThrown);
    }

    // Creates and registers the test model with the Mule Instance;
    // the model will fail the JUnit test if an exception occurs.
    private SedaModel createTestModel() throws UMOException
    {
        SedaModel model = new SedaModel();
        model.setName("TestModel");
        exceptionThrown = false;
        model.setExceptionListener(new ExceptionListener()
        {
            public void exceptionThrown(Exception ex)
            {
                ex.printStackTrace();
                exceptionThrown = true;
            }
        });

        MuleManager.getInstance().unregisterModel("TestModel");
        MuleManager.getInstance().registerModel(model);
        return model;
    }

    // Registers a UMO that monitors the test queue and has a dummy interceptor.
    private TestComponent createTestComponent(UMOModel model) throws UMOException
    {
        InboundRouterCollection router = new InboundRouterCollection();
        router.addEndpoint(new MuleEndpoint("vm://test.queue", true));

        List interceptors = new ArrayList();
        interceptors.add(new TestInterceptor());

        TestComponent comp = new TestComponent();

        MuleDescriptor desc = new MuleDescriptor("TestComponent");
        desc.setInboundRouter(router);
        desc.setInterceptors(interceptors);
        desc.setImplementationInstance(comp);

        model.registerComponent(desc);

        return comp;
    }
}

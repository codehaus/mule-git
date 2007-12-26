/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ra;

import org.mule.MuleManager;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.model.ModelFactory;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

public class JcaComponentTestCase extends AbstractMuleTestCase // AbstractComponentTestCase
{

    // Cannot extend AbstractComponentTestCase because of inconsistent behaviour. See
    // MULE-2843

    private UMOComponent component;

    private TestJCAWorkManager workManager;

    protected void doSetUp() throws Exception
    {
        // Create and register JcaModel
        workManager = new TestJCAWorkManager();
        JcaModel jcaModel = (JcaModel) ModelFactory.createModel(JcaModel.JCA_MODEL_TYPE);
        jcaModel.setWorkManager(new DelegateWorkManager(workManager));
        MuleManager.getInstance().registerModel(jcaModel);

        // Create, register, initialise and start JcaComponent
        String name = "JcaComponent#";
        MuleDescriptor descriptor = new MuleDescriptor(name);
        descriptor.setModelName(jcaModel.getName());
        component = jcaModel.registerComponent(descriptor);
        assertNotNull(component);
    }

    protected void doTearDown() throws Exception
    {
        workManager = null;
        component = null;
    }

    public void testSendEvent() throws Exception
    {
        component.initialise();
        component.start();
        UMOEndpoint endpoint = getTestEndpoint("jcaInFlowEndpoint", UMOImmutableEndpoint.ENDPOINT_TYPE_RECEIVER);
        UMOEvent event = getTestEvent("Message", endpoint);

        try
        {
            component.sendEvent(event);
            fail("Exception expected, JcaComponent does not support sendEvent()");
        }
        catch (Exception e)
        {
        }
    }

    public void testDispatchEvent() throws Exception
    {
        component.initialise();
        component.start();
        UMOEndpoint endpoint = getTestEndpoint("jcaInFlowEndpoint", UMOImmutableEndpoint.ENDPOINT_TYPE_RECEIVER);
        UMOEvent event = getTestEvent("Message", endpoint);

        component.dispatchEvent(event);
        assertEquals(1, workManager.getScheduledWorkList().size());
        assertEquals(0, workManager.getStartWorkList().size());
        assertEquals(0, workManager.getDoWorkList().size());
    }

    public void testPause()
    {
        try
        {
            component.pause();
            fail("Exception expected, JcaComponent does not support pause()");
        }
        catch (UMOException e)
        {

        }
    }

    public void testResume()
    {
        try
        {
            component.resume();
            fail("Exception expected, JcaComponent does not support resume()");
        }
        catch (UMOException e)
        {
        }
    }

}

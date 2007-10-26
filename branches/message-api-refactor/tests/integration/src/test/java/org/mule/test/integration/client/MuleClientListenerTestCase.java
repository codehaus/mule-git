/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.client;


import org.mule.extras.client.MuleClient;
import org.mule.impl.endpoint.EndpointURIEndpointBuilder;
import org.mule.impl.model.seda.SedaComponent;
import org.mule.routing.inbound.InboundRouterCollection;
import org.mule.tck.FunctionalTestCase;
import org.mule.test.integration.service.TestReceiver;
import org.mule.transformers.simple.ByteArrayToString;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpointBuilder;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.NoReceiverForEndpointException;
import org.mule.util.object.SingletonObjectFactory;


public class MuleClientListenerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "org/mule/test/integration/client/mule-client-listener-config.xml";
    }

    public void doTestRegisterListener(String urlString, boolean canSendWithoutReceiver) throws Exception
    {
        MuleClient client = new MuleClient();
        client.getConfiguration().setDefaultSynchronousEndpoints(true);
        client.getConfiguration().setDefaultRemoteSync(true);

        if (!canSendWithoutReceiver)
        {
            try
            {
                client.send(urlString, "Test Client Send message", null);
                fail("There is no receiver for this endpointUri");
            }
            catch (Exception e)
            {
                assertTrue(e.getCause() instanceof NoReceiverForEndpointException);
            }
        }

        TestReceiver receiver = new TestReceiver();
        // we need to code the component creation here, which isn't ideal, see
        // MULE-1060
        String name = "myComponent";
        SedaComponent descriptor = new SedaComponent();
        descriptor.setName(name);
        descriptor.setServiceFactory(new SingletonObjectFactory(receiver));
        managementContext.getRegistry().registerComponent(descriptor, managementContext);

        UMOEndpointBuilder endpointBuilder = new EndpointURIEndpointBuilder(urlString, managementContext);
        endpointBuilder.addTransformer(new ByteArrayToString());
        UMOImmutableEndpoint endpoint = managementContext.getRegistry().lookupEndpointFactory().getInboundEndpoint(
            endpointBuilder, managementContext);
        descriptor.setInboundRouter(new InboundRouterCollection());
        descriptor.getInboundRouter().addEndpoint(endpoint);

        // No longer a valid assetion
        //assertTrue(managementContext.getRegistry().lookupModel("main").isComponentRegistered(name));

        UMOMessage message = client.send(urlString, "Test Client Send message", null);
        assertNotNull(message);
        assertEquals("Received: Test Client Send message", message.getPayloadAsString());
        client.unregisterComponent(name);

        // No longer a valid assetion
        // assertTrue(!managementContext.getRegistry().lookupModel("main").isComponentRegistered(name));

        if (!canSendWithoutReceiver)
        {
            try
            {
                message = client.send(urlString, "Test Client Send message", null);
                fail("There is no receiver for this endpointUri");
            }
            catch (Exception e)
            {
                assertTrue(e.getCause() instanceof NoReceiverForEndpointException);
            }
        }
    }

    public void testRegisterListenerVm() throws Exception
    {
        doTestRegisterListener("vm://test.queue", false);
    }

    public void testRegisterListenerTcp() throws Exception
    {
        doTestRegisterListener("tcp://localhost:56324", true);
    }

}

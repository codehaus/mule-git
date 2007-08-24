/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.config;

import org.mule.impl.MuleDescriptor;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.MuleSession;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.testmodels.fruit.Apple;
import org.mule.tck.testmodels.mule.TestInboundTransformer;
import org.mule.tck.testmodels.mule.TestMuleProxy;
import org.mule.tck.testmodels.mule.TestOutboundTransformer;
import org.mule.tck.testmodels.mule.TestReplyToHandler;
import org.mule.tck.testmodels.mule.TestResponseTransformer;
import org.mule.tck.testmodels.mule.TestSedaComponent;
import org.mule.umo.UMODescriptor;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.model.UMOModel;
import org.mule.umo.routing.UMOOutboundRouter;

public class EndpointsTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        return "endpoints.xml";
    }

    /**
     * Test retrieval of global endpoints from the registry and their type and transformers
     */
    public void testGlobalEndpointLookup()
    {
        UMOEndpoint globalAppleEndpoint = managementContext.getRegistry().lookupEndpoint("appleInEndpoint");
        UMOEndpoint globalAppleEndpoint2 = managementContext.getRegistry().lookupEndpoint("appleInEndpoint");
        UMOEndpoint globalAppleEndpoint3 = managementContext.getRegistry().lookupEndpoint("appleInEndpoint");
        
        //Instance of global endpoints looked up in registry are unqiue
        assertNotSame(globalAppleEndpoint, globalAppleEndpoint2);
        assertNotSame(globalAppleEndpoint, globalAppleEndpoint3);
        assertNotSame(globalAppleEndpoint2, globalAppleEndpoint3);
        
        // Type: What type should a global endpoint have in the registry? ENDPOINT_TYPE_GLOBAL?
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_GLOBAL,globalAppleEndpoint.getType());

        // Transformers
        assertEquals(TestInboundTransformer.class, globalAppleEndpoint.getTransformer().getClass());
        assertEquals(TestResponseTransformer.class, globalAppleEndpoint.getResponseTransformer().getClass());
    }

    
    /**
     * Logical endpoints looked up via the registry have incorrect type and incorrect transformers!!
     */
    public void testLogicalEndpointLookup()
    {
        UMOEndpoint appleComponentInboundEndpoint = managementContext.getRegistry().lookupEndpoint("appleComponentInboundEndpoint");
        UMOEndpoint appleComponentOutboundEndpoint = managementContext.getRegistry().lookupEndpoint("appleComponentOutboundEndpoint");
        
        // Outbound Endpoint type has "RECIEVER" type and conncector's default inbound transformer
        // FAILS assertEquals(UMOEndpoint.ENDPOINT_TYPE_RECEIVER, appleComponentInboundEndpoint.getType());
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_RECEIVER, appleComponentInboundEndpoint.getType());
        assertEquals(TestInboundTransformer.class, appleComponentInboundEndpoint.getTransformer().getClass());
        assertEquals(TestResponseTransformer.class, appleComponentInboundEndpoint.getResponseTransformer()
            .getClass());
        
        // Outbound Endpoint type has "RECIEVER" type and conncector's default outbound transformer
        // FAILS assertEquals(UMOEndpoint.ENDPOINT_TYPE_SENDER, appleComponentOutboundEndpoint.getType());
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_SENDER, appleComponentOutboundEndpoint.getType());
        assertEquals(TestOutboundTransformer.class, appleComponentOutboundEndpoint.getTransformer().getClass());
        assertEquals(TestResponseTransformer.class, appleComponentOutboundEndpoint.getResponseTransformer()
            .getClass());
    
    }
    
    /**
     * Test logical inbound endpoint created from a global endpoint
     */
    public void testLogicalInboundEndpointFromGlobal()
    {
        UMOEndpoint globalAppleEndpoint = managementContext.getRegistry().lookupEndpoint("appleInEndpoint");

        UMODescriptor appleService = managementContext.getRegistry().lookupService("appleComponent");
        UMOEndpoint logicalInboudAppleEndpoint = (UMOEndpoint) appleService.getInboundRouter()
            .getEndpoints()
            .get(0);

        // Logical endpoint is a unqiue instance, different to instance as global endpoint
        assertNotSame(globalAppleEndpoint, logicalInboudAppleEndpoint);
     
        // Logical endpointUri and global endpointURI's are equal
        assertTrue(globalAppleEndpoint.getEndpointURI().equals(logicalInboudAppleEndpoint.getEndpointURI()));

        // Outbound Endpoint type has "RECIEVER" type and conncector's default inbound transformer
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_RECEIVER, logicalInboudAppleEndpoint.getType());
        assertEquals(TestInboundTransformer.class, logicalInboudAppleEndpoint.getTransformer().getClass());
        assertEquals(TestResponseTransformer.class, logicalInboudAppleEndpoint.getResponseTransformer()
            .getClass());
    }
    
    /**
     * Test logical inbound endpoint
     */
    public void testLogicalInboundEndpoint()
    {
        UMODescriptor appleService = managementContext.getRegistry().lookupService("appleComponent");
        UMOEndpoint logicalInboudAppleEndpoint = (UMOEndpoint) appleService.getInboundRouter()
            .getEndpoints()
            .get(1);

        // Outbound Endpoint type has "RECIEVER" type and conncector's default inbound transformer
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_RECEIVER, logicalInboudAppleEndpoint.getType());
        assertEquals(TestInboundTransformer.class, logicalInboudAppleEndpoint.getTransformer().getClass());
        assertEquals(TestResponseTransformer.class, logicalInboudAppleEndpoint.getResponseTransformer()
            .getClass());

    }

    /**
     * Test logical outbound endpoint created from a global endpoint
     */
    public void testLogicalOutboundEndpointFromGlobal()
    {
        UMOEndpoint globalAppleEndpoint = managementContext.getRegistry().lookupEndpoint("appleInEndpoint");

        UMODescriptor orangeService = managementContext.getRegistry().lookupService("orangeComponent");
        UMODescriptor appleService = managementContext.getRegistry().lookupService("appleComponent");

        UMOEndpoint logicalOutboundAppleEndpoint = (UMOEndpoint) ((UMOOutboundRouter) orangeService.getOutboundRouter()
            .getRouters()
            .get(0)).getEndpoints().get(0);

        // Logical endpoint is a unqiue instance, different to instance as global endpoint
        assertNotSame(globalAppleEndpoint, logicalOutboundAppleEndpoint);

        // Logical endpointUri and global endpointURI's are equal
        assertTrue(globalAppleEndpoint.getEndpointURI().equals(logicalOutboundAppleEndpoint.getEndpointURI()));

        // Logical endpoints created from same global endpoint are distinct instances
        UMOEndpoint logicalInboudAppleEndpoint = (UMOEndpoint) appleService.getInboundRouter()
            .getEndpoints()
            .get(0);
        assertNotSame(logicalInboudAppleEndpoint, logicalOutboundAppleEndpoint);

        // Outbound Endpoint type has "SENDER" type and conncector's default outbound transformer
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_SENDER, logicalOutboundAppleEndpoint.getType());
        assertEquals(TestOutboundTransformer.class, logicalOutboundAppleEndpoint.getTransformer().getClass());
        assertEquals(TestResponseTransformer.class, logicalOutboundAppleEndpoint.getResponseTransformer()
            .getClass());

    }
    
    /**
     * Test logical outbound endpoint created from a global endpoint
     */
    public void testLogicalOutboundEndpoint()
    {

        UMODescriptor appleService = managementContext.getRegistry().lookupService("appleComponent");

        UMOEndpoint logicalOutboundAppleEndpoint = (UMOEndpoint) ((UMOOutboundRouter) appleService.getOutboundRouter()
            .getRouters()
            .get(0)).getEndpoints().get(0);

        // Outbound Endpoint type has "SENDER" type and conncector's default outbound transformer
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_SENDER, logicalOutboundAppleEndpoint.getType());
        assertEquals(TestOutboundTransformer.class, logicalOutboundAppleEndpoint.getTransformer().getClass());
        assertEquals(TestResponseTransformer.class, logicalOutboundAppleEndpoint.getResponseTransformer()
            .getClass());

    }

    /**
     * Test logical inbound endpoint created from a global endpoint
     * @throws Exception
     */
    public void testLogicalResponseEndpoint() throws Exception
    {
        UMOEndpoint globalAppleResponseEndpoint = managementContext.getRegistry().lookupEndpoint(
            "appleResponseEndpoint");
        UMODescriptor appleService = managementContext.getRegistry().lookupService("appleComponent");
        UMOEndpoint logicalResponseAppleQueueEndpoint = (UMOEndpoint) appleService.getResponseRouter()
            .getEndpoints()
            .get(0);

        // Logical endpoint is a unqiue instance, different to instance as global endpoint
        assertNotSame(globalAppleResponseEndpoint, logicalResponseAppleQueueEndpoint);
        // Logical endpointUri and global endpointURI's are equal
        assertTrue(globalAppleResponseEndpoint.getEndpointURI().equals(
            logicalResponseAppleQueueEndpoint.getEndpointURI()));
        
        // Logical endpoints created from same global endpoint are distinct instances
        UMOEndpoint logicalInboundAppleEndpoint = (UMOEndpoint) appleService.getInboundRouter()
            .getEndpoints()
            .get(0);
        assertNotSame(logicalInboundAppleEndpoint, logicalResponseAppleQueueEndpoint);
        UMODescriptor orangeService = managementContext.getRegistry().lookupService("orangeComponent");
        UMOEndpoint logicalOutboundAppleEndpoint = (UMOEndpoint) ((UMOOutboundRouter) orangeService.getOutboundRouter()
                        .getRouters()
                        .get(0)).getEndpoints().get(0);
        assertNotSame(logicalOutboundAppleEndpoint, logicalResponseAppleQueueEndpoint);

        // Outbound Endpoint type has "RESPONSE" type and conncector's default inbound transformer
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_RESPONSE, logicalResponseAppleQueueEndpoint.getType());
        assertEquals(TestInboundTransformer.class, logicalResponseAppleQueueEndpoint.getTransformer()
            .getClass());
        assertEquals(TestResponseTransformer.class,
            logicalResponseAppleQueueEndpoint.getResponseTransformer().getClass());

    }

    /**
     * Test transformer used on reply-to handler
     * @throws Exception
     */
    public void testLogicalReplyToEndpoint() throws Exception
    {
        UMOEndpoint otherEndpoint = managementContext.getRegistry().lookupEndpoint("otherEndpoint");
        MuleMessage testMessage = new MuleMessage("testMessage");
        testMessage.setReplyTo("test://replyEndpoint");
        UMOModel model = managementContext.getRegistry().lookupModel("main");
        TestSedaComponent appleComponent = (TestSedaComponent) model.getComponent("appleComponent");
        MuleDescriptor appleService = (MuleDescriptor) managementContext.getRegistry().lookupService(
            "appleComponent");
        TestMuleProxy proxy = (TestMuleProxy) appleComponent.createComponentProxy(new Apple(), appleService,
            model, null);
        TestReplyToHandler replyToHandler= (TestReplyToHandler) proxy.getReplyToHandler(testMessage, otherEndpoint);
        UMOEndpoint replyToEndpoint=replyToHandler.getEndpoint(new MuleEvent(testMessage, otherEndpoint,new MuleSession(appleComponent),true), "test://replyEndpoint");
  
        //ReplyTo method in Proxy uses ResponseTransformer
        assertEquals(TestResponseTransformer.class, replyToHandler.getTransformer().getClass());
        
        //ReplyToHandler endpoint uses OutboundTrasformer
        assertEquals(UMOEndpoint.ENDPOINT_TYPE_SENDER, replyToEndpoint.getType());
        assertEquals(TestOutboundTransformer.class, replyToEndpoint.getTransformer().getClass());
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.email.connectors;

import org.mule.config.MuleProperties;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.impl.model.seda.SedaComponent;
import org.mule.impl.model.seda.SedaModel;
import org.mule.providers.email.transformers.EmailMessageToString;
import org.mule.routing.outbound.OutboundPassThroughRouter;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOEventContext;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.routing.UMOOutboundRouter;
import org.mule.util.object.ObjectFactory;
import org.mule.util.object.SimpleObjectFactory;

import java.util.HashMap;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/**
 * Given an endpoint ({@link #getTestEndpointURI()}) this waits for up to 10 seconds,
 * hoping to receive the message stored in the mail server.  It also runs the unit tests
 * defined way down in {@link org.mule.tck.providers.AbstractConnectorTestCase}.
 */
public abstract class AbstractReceivingMailConnectorTestCase extends AbstractMailConnectorFunctionalTestCase
{

    public static final int POLL_PERIOD_MS = 1000; 
    public static final int WAIT_PERIOD_MS = 3 * POLL_PERIOD_MS;

    protected AbstractReceivingMailConnectorTestCase(String connectorName)
    {
        super(true, connectorName);
    }

    public void testReceiver() throws Exception
    {
        repeatTest("doTestReceiver");
    }

    public void doTestReceiver() throws Exception
    {
        final CountDownLatch countDown = new CountDownLatch(1);

        HashMap props = new HashMap();
        props.put("eventCallback", new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component)
            {
                try 
                {
                    logger.debug("woot - event received");
                    logger.debug("context: " + context);
                    logger.debug("component: " + component);
                    assertMessageOk(context.getMessage().getPayload());
                    countDown.countDown();
                } 
                catch (Exception e) 
                {
                    // counter will not be incremented
                    logger.error(e.getMessage(), e);
                }
            }
        });

//        managementContext.getRegistry().registerConnector(createConnector(true), managementContext);
//        
//        UMOEndpoint endpoint = new MuleEndpoint(getTestEndpointURI(), false);
//
//        UMOComponent component = getTestComponent(uniqueName("testComponent"), FunctionalTestComponent.class, props);
//        // TODO Simplify this API for adding an outbound endpoint.
//        ((OutboundPassThroughRouter) component.getOutboundRouter().getRouters().get(0)).addEndpoint(endpoint);
//        managementContext.getRegistry().registerComponent(component, managementContext);

        SedaModel model = new SedaModel();
        model.setManagementContext(managementContext);
        managementContext.applyLifecycle(model);
        
        UMOComponent component = new SedaComponent();
        component.setName(uniqueName("testComponent"));
        component.setModel(model);
        ObjectFactory of = new SimpleObjectFactory(FunctionalTestComponent.class, props);
        of.initialise();
        component.setServiceFactory(of);
        component.setManagementContext(managementContext);

        UMOConnector connector = createConnector(true);
        connector.setManagementContext(managementContext);        
       // managementContext.applyLifecycle(connector);
        managementContext.getRegistry().registerConnector(connector, managementContext);

        UMOEndpoint endpoint = new MuleEndpoint(getTestEndpointURI(), false);
        endpoint.setConnector(connector);
        
        managementContext.applyLifecycle(component);

        UMOOutboundRouter router = new OutboundPassThroughRouter();
        router.addEndpoint(endpoint);
        component.getOutboundRouter().addRouter(router);        

        
        logger.debug("waiting for count down");
        assertTrue(countDown.await(WAIT_PERIOD_MS, TimeUnit.MILLISECONDS));
    }

    protected static Map newEmailToStringServiceOverrides()
    {
        Map serviceOverrides = new HashMap();
        serviceOverrides.put(MuleProperties.CONNECTOR_INBOUND_TRANSFORMER,
                EmailMessageToString.class.getName());
        return serviceOverrides;
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm;

import java.util.Map;

import javax.resource.spi.work.Work;

import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

/**
 * Generates an incoming Mule event from an executing workflow process.
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class ProcessMessageReceiver extends AbstractMessageReceiver {

    private ProcessConnector connector = null;

    public ProcessMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint)
            throws InitialisationException {
        super(connector, component, endpoint);
        this.connector = (ProcessConnector) connector;
    }

    public UMOMessage sendEvent(String endpoint, Object payload, Map messageProperties) throws UMOException {
        logger.debug("Executing process is sending an event (synchronously) to Mule endpoint = " + endpoint);

        UMOMessage message;
        if (payload instanceof UMOMessage) {
            message = (UMOMessage) payload;
        } else {
            message = new MuleMessage(connector.getMessageAdapter(payload));
        }
        message.addProperties(messageProperties);

        if (connector.isLocalEndpointsOnly()) {
            message.setStringProperty(ProcessConnector.PROPERTY_ENDPOINT, endpoint);
            return routeMessage(message, /*synchronous*/true);
        }
        else {
            return connector.getMuleClient().send(endpoint, message);
        }
    }

    public void dispatchEvent(String endpoint, Object payload, Map messageProperties) throws UMOException {
        logger.debug("Executing process is dispatching an event (asynchronously) to Mule endpoint = " + endpoint);
        try {
            getWorkManager().scheduleWork(new Worker(endpoint, payload, messageProperties));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private class Worker implements Work {
        private String endpoint;
        private Object payload;
        private Map messageProperties;

        public Worker(String endpoint, Object payload, Map messageProperties) {
            this.endpoint = endpoint;
            this.payload = payload;
            this.messageProperties = messageProperties;
        }

        public void run() {
            try {
                UMOMessage message;
                if (payload instanceof UMOMessage) {
                    message = (UMOMessage) payload;
                } else {
                    message = new MuleMessage(connector.getMessageAdapter(payload));
                }
                message.addProperties(messageProperties);

                if (connector.isLocalEndpointsOnly()) {
                    message.setStringProperty(ProcessConnector.PROPERTY_ENDPOINT, endpoint);
                    routeMessage(message, /*synchronous*/false);
                }
                else {
                    connector.getMuleClient().dispatch(endpoint, message);
                }
            } catch (Exception e) {
                getConnector().handleException(e);
            }
        }

        public void release() { /*nop*/ }
    }

    public void doConnect() throws Exception { /*nop*/ }
    public void doDisconnect() throws Exception { /*nop*/ }
}

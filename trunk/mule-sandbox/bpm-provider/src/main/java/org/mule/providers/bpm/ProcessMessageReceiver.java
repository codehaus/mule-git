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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.providers.AbstractMessageReceiver;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.EndpointException;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;

/**
 * Generates an incoming Mule event from an executing workflow process.
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class ProcessMessageReceiver extends AbstractMessageReceiver implements EventRouter {

	private ProcessConnector connector = null;

	public ProcessMessageReceiver(UMOConnector connector, UMOComponent component, UMOEndpoint endpoint)
			throws InitialisationException {
		
		super(connector, component, endpoint);	
		this.connector = (ProcessConnector) connector;

		// Set a reference back to this object for the jBpm ActionHandler to use for 
		// generating events.
		this.connector.getJbpmSessionFactory().setEventRouter(this);
	}

	protected void doDispose() {
	}

	public void doStart() throws UMOException {
		try {
		} catch (Exception e) {
			throw new EndpointException(new Message(Messages.FAILED_TO_START_X,
					"Workflow receiver"), e);
		}
	}

	public void doConnect() throws Exception {
	}

	public void doDisconnect() throws Exception {

	}   
	
	public UMOMessage sendEvent(String url, Object payload, Map messageProperties) throws UMOException {
		log.debug("Executing process has generated a Mule event, url = " + url);
		return connector.getMuleClient().send(url, payload, messageProperties, ProcessConnector.MULE_CLIENT_TIMEOUT);
	}

	private static Log log = LogFactory.getLog(ProcessMessageReceiver.class);
}

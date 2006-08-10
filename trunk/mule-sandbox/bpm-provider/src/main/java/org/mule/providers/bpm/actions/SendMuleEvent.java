/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;
import org.jbpm.db.JbpmSession;
import org.jbpm.graph.exe.ExecutionContext;
import org.mule.config.ConfigurationException;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.Message;
import org.mule.providers.bpm.EventRouter;
import org.mule.providers.bpm.MuleJbpmSessionFactory;
import org.mule.providers.bpm.ProcessConnector;

/**
 * Sends a Mule message to the specified URL
 *
 * @param url - the Mule endpoint
 * @param transformers - any transformers to be applied
 * @param payload - the payload of the message
 * @param payloadSource - process variable from which to generate the message payload, defaults to {@link ProcessConnector.PROCESS_VARIABLE_DATA}
 * @param messageProperties - any properties to be applied to the message
 */
public class SendMuleEvent extends LoggingActionHandler {

    String url = null;
    String transformers = null;
    String payload = null; // TODO This should be an Object
    String payloadSource = ProcessConnector.PROCESS_VARIABLE_DATA;
    Map messageProperties = null;

    private Object payloadObject;

    public void execute(ExecutionContext executionContext) throws Exception {
        super.execute(executionContext);

        if (transformers != null) {
            url += "?transformers=" + transformers;
        }
        if (payload == null && payloadSource != null) {
            payloadObject = executionContext.getVariable(payloadSource);
        } else {
            payloadObject = payload;
        }
        if (payloadObject == null) {
            throw new IllegalArgumentException("Payload for message is null.  Payload source is \"" + payloadSource + "\"");
        }
        messageProperties = new HashMap();
        messageProperties.put(ProcessConnector.PROPERTY_PROCESS_TYPE, executionContext.getProcessDefinition().getName());
        messageProperties.put(ProcessConnector.PROPERTY_PROCESS_ID, new Long(executionContext.getProcessInstance().getId()));
        messageProperties.put(MuleProperties.MULE_CORRELATION_ID_PROPERTY, new Long(executionContext.getProcessInstance().getId()).toString());
        messageProperties.put(ProcessConnector.PROPERTY_PROCESS_STARTED, executionContext.getProcessInstance().getStart());

        JbpmSession session = JbpmSession.getCurrentJbpmSession();
           MuleJbpmSessionFactory factory =
               (MuleJbpmSessionFactory) session.getJbpmSessionFactory();
        EventRouter router = factory.getEventRouter();

        Transaction processTransaction = session.getTransaction();
        try {
            if (router != null) {
                router.sendEvent(url, payloadObject, messageProperties);
            } else {
                throw new ConfigurationException(Message.createStaticMessage("No endpoint has been configured to receive events from the BPM Connector."));
            }
            if (processTransaction != null) { processTransaction.commit(); }
        } catch (Exception e) {
            if (processTransaction != null) { processTransaction.rollback(); }
            throw e;
        }
    }

    private static Log log = LogFactory.getLog(SendMuleEvent.class);
}

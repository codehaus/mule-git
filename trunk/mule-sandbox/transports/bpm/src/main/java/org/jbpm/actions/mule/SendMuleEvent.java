package org.jbpm.actions.mule;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.actions.LoggingActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.mule.config.ConfigurationException;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.Message;
import org.mule.impl.MuleEvent;
import org.mule.impl.RequestContext;
import org.mule.providers.bpm.ProcessConnector;
import org.mule.providers.bpm.ProcessMessageDispatcher;
import org.mule.providers.bpm.ProcessMessageReceiver;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOMessage;

/**
 * Sends a Mule message to the specified URL
 *
 * @param endpoint - the Mule endpoint
 * @param transformers - any transformers to be applied
 * @param payload - the payload of the message
 * @param payloadSource - process variable from which to generate the message payload, defaults to {@link ProcessConnector.PROCESS_VARIABLE_DATA}
 * @param messageProperties - any properties to be applied to the message
 */
public class SendMuleEvent extends LoggingActionHandler {

    boolean synchronous = true;
    String endpoint = null;
    String transformers = null;
    String payload = null; // TODO This should be an Object
    String payloadSource = ProcessConnector.PROCESS_VARIABLE_DATA;
    Map messageProperties = null;

    private Object payloadObject;

    public void execute(ExecutionContext executionContext) throws Exception {
        super.execute(executionContext);

        if (transformers != null) {
            endpoint += "?transformers=" + transformers;
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

        // Get a handle to the BPM connector from the current event.
        UMOEvent event = RequestContext.getEvent();
        ProcessConnector connector = (ProcessConnector) event.getEndpoint().getConnector();
        if (connector == null) { throw new ConfigurationException(Message.createStaticMessage("Unable to locate connector for the current event.")); }

        // Look up a receiver with the name of the process.
        ProcessMessageReceiver receiver =
            (ProcessMessageReceiver) connector.lookupReceiver(event.getEndpoint().getEndpointURI().getAddress());
        if (receiver == null) {
            // The global receiver allows an endpoint of type "bpm://*" without specifying a process name.  This
            // could be useful for dynamically generating the name of the process to send to/receive from.
            if (connector.isAllowGlobalReceiver()) {
                receiver = (ProcessMessageReceiver) connector.lookupReceiver(ProcessConnector.GLOBAL_RECEIVER);
                if (receiver == null) {
                    throw new ConfigurationException(Message.createStaticMessage("No global process receiver found"));
                }
            } else {
                throw new ConfigurationException(Message.createStaticMessage("No receiver found for " + event.getEndpoint().getEndpointURI().getAddress()));
            }
        }

        if (synchronous) {
            // Send the process-generated Mule message.
            UMOMessage response = receiver.sendEvent(endpoint, payloadObject, messageProperties);

            // Look up a dispatcher with the name of the process.
            ProcessMessageDispatcher dispatcher =
                (ProcessMessageDispatcher) connector.lookupDispatcher(event.getEndpoint().getEndpointURI().getAddress());
            if (dispatcher != null) {
                // Send the response message back to the process.
                dispatcher.doSend(new MuleEvent(response, event));
            } else {
                throw new ConfigurationException(Message.createStaticMessage("No dispatcher found for " + event.getEndpoint().getEndpointURI().getAddress()));
            }
        }
        else {
            // Dispatch the process-generated Mule message.
            receiver.dispatchEvent(endpoint, payloadObject, messageProperties);
        }
    }
}

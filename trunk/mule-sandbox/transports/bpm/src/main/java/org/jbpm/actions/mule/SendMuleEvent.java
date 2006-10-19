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

        String processName = executionContext.getProcessDefinition().getName();
        long processId = executionContext.getProcessInstance().getId();

        // Look up a receiver which matches this process.
        ProcessMessageReceiver receiver =
            connector.lookupReceiver(processName, new Long(processId));
        if (receiver == null) {
            throw new ConfigurationException(Message.createStaticMessage("No corresponding receiver found for processName = " + processName + ", processId = " + processId));
        }

        if (synchronous) {
            // Send the process-generated Mule message synchronously.
            UMOMessage response = receiver.generateSynchronousEvent(endpoint, payloadObject, messageProperties);

            // Look up the dispatcher which generated the current event.
            ProcessMessageDispatcher dispatcher = (ProcessMessageDispatcher)
                connector.lookupDispatcher(event.getEndpoint().getEndpointURI().getAddress());
            if (dispatcher != null) {
                // Feed the synchronous response message back into the process.
                dispatcher.doSend(new MuleEvent(response, event));
            } else {
                throw new ConfigurationException(Message.createStaticMessage("No corresponding dispatcher found for processName = " + processName + ", processId = " + processId));
            }
        }
        else {
            // Dispatch the process-generated Mule message asynchronously.
            receiver.generateAsynchronousEvent(endpoint, payloadObject, messageProperties);
        }
    }
}

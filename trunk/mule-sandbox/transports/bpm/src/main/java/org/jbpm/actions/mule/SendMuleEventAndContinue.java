package org.jbpm.actions.mule;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.mule.providers.bpm.ProcessConnector;

/**
 * Sends a Mule message to the specified URL and continues execution to the next state.
 * This class assumes the current state has only one leaving transition.
 *
 * @param url - the Mule endpoint
 * @param transformers - any transformers to be applied
 * @param payload - the payload of the message
 * @param payloadSource - process variable from which to generate the message payload, defaults to {@link ProcessConnector.PROCESS_VARIABLE_DATA}
 * @param messageProperties - any properties to be applied to the message
 */
public class SendMuleEventAndContinue extends SendMuleEvent {

    // TODO These properties should be inherited from the parent class but are not due
    // to a known issue in jBpm 3.0.0
    String url = null;
    String transformers = null;
    String payload = null; // TODO This should be an Object
    String payloadSource = ProcessConnector.PROCESS_VARIABLE_DATA;
    Map messageProperties = null;

    public void execute(ExecutionContext executionContext) throws Exception {
        super.execute(executionContext);
        executionContext.leaveNode();
    }

    private static Log log = LogFactory.getLog(SendMuleEventAndContinue.class);
}

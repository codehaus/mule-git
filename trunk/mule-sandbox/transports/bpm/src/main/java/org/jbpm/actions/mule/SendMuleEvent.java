package org.jbpm.actions.mule;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.jbpm.actions.LoggingActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.msg.mule.MuleMessageService;
import org.mule.config.MuleProperties;
import org.mule.providers.bpm.ProcessConnector;
import org.mule.util.StringUtils;

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
    Object payload = null;
    String payloadSource = null;
    Map properties = null;

    private Object payloadObject;

    public void execute(ExecutionContext executionContext) throws Exception {
        super.execute(executionContext);
        MuleMessageService mule =
            (MuleMessageService) executionContext.getJbpmContext().getServices().getMessageService();

        if (transformers != null) {
            endpoint += "?transformers=" + transformers;
        }

        if (payload == null) {
            String source = payloadSource;
            if (source == null) {
                source = ProcessConnector.PROCESS_VARIABLE_DATA;
            }

            // The payloadSource may be specified using JavaBean notation (e.g.,
            // "myObject.myStuff.myField" would first retrieve the process variable
            // "myObject" and then call .getMyStuff().getMyField()
            String[] tokens = StringUtils.split(source, ".", 2);
            payloadObject = executionContext.getVariable(tokens[0]);
            if (tokens.length > 1) {
                JXPathContext context = JXPathContext.newContext(payloadObject);
                payloadObject = context.getValue(tokens[1]);
            }
        } else {
            payloadObject = payload;
        }
        if (payloadObject == null) {
            throw new IllegalArgumentException("Payload for message is null.  Payload source is \"" + payloadSource + "\"");
        }

        Map props = new HashMap();
        props.put(ProcessConnector.PROPERTY_PROCESS_TYPE, executionContext.getProcessDefinition().getName());
        props.put(ProcessConnector.PROPERTY_PROCESS_ID, new Long(executionContext.getProcessInstance().getId()));
        props.put(MuleProperties.MULE_CORRELATION_ID_PROPERTY, new Long(executionContext.getProcessInstance().getId()).toString());
        props.put(ProcessConnector.PROPERTY_PROCESS_STARTED, executionContext.getProcessInstance().getStart());
        if (properties != null) {
            props.putAll(properties);
        }

        mule.generateMessage(endpoint, payloadObject, props, synchronous);
    }
}

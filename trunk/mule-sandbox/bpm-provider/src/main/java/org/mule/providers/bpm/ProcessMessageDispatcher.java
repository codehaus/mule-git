package org.mule.providers.bpm;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.providers.NullPayload;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

/**
 * Initiates or advances a workflow process from an outgoing Mule event.
 *
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class ProcessMessageDispatcher extends AbstractMessageDispatcher {

    private ProcessConnector connector;

    public ProcessMessageDispatcher(UMOImmutableEndpoint endpoint) {
        super(endpoint);
        this.connector = (ProcessConnector) endpoint.getConnector();
    }

    public void doDispose() {}
    protected void doConnect(UMOImmutableEndpoint arg0) throws Exception {}
    protected void doDisconnect() throws Exception {}

    public void doDispatch(UMOEvent event) throws Exception {
        doSend(event);
    }

    /**
     * @return id of the new ProcessInstance if a new process is created, otherwise null.
     */
    public UMOMessage doSend(UMOEvent event) throws Exception {

        // Get parameters
        String processType =
            (String) event.getProperty(ProcessConnector.PROPERTY_PROCESS_TYPE, /*exhaustiveSearch*/true);
        long processId =
            objectToLong(event.getProperty(ProcessConnector.PROPERTY_PROCESS_ID, /*exhaustiveSearch*/true));

        String action =
            (String) event.getMessage().getProperty(ProcessConnector.PROPERTY_ACTION);
        String transition =
            (String) event.getMessage().getProperty(ProcessConnector.PROPERTY_TRANSITION);

        // Decode the URI, for example:
        // 		bpm:/testProcess/4561?action=advance
        String path = event.getEndpoint().getEndpointURI().getPath();
        String[] pathTokens = StringUtils.split(path, "/");
        if (pathTokens.length >= 1) {
            processType = pathTokens[0];
        }
        if (pathTokens.length >= 2) {
            processId = NumberUtils.toLong(pathTokens[1]);
        }

        // Pass the message's payload in as a process variable.
        Map variables = new HashMap();
        Object payload = null;
        if (event != null) {
            payload = event.getTransformedMessage();
            if (payload != null && !(payload instanceof NullPayload)) {
                variables.put(ProcessConnector.PROCESS_VARIABLE_INCOMING, payload);
            }
        }

        // This event is not associated to any existing process so we create a new one.
        if ((action != null && action.equals(ProcessConnector.ACTION_START))
                || processId == -1) {
            ProcessInstance process;
            if (processType != null) {
                process = JBpmUtil.startProcess(connector.getJbpmSessionFactory(),
                                                processType, variables);
            }
            else throw new IllegalArgumentException("Process type must be specified when starting a new process.");

                 // Return the id of the newly-created process.
            UMOMessage message = event.getMessage();
               message.setLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, process.getId());
            return message;
        }
        else if (action != null && action.equals(ProcessConnector.ACTION_UPDATE)){
            if (processId != -1) {
                JBpmUtil.updateProcess(connector.getJbpmSessionFactory(),
                                        processId, variables);
            }
            else throw new IllegalArgumentException("Process id must be specified when updating an existing process.");
            return null;
        }
        // The process instance already exists, so we just need to advance the process
        // one step.
        else {
            if (processId != -1) {
                JBpmUtil.advanceProcess(connector.getJbpmSessionFactory(),
                                        processId, variables, transition);
            }
            else throw new IllegalArgumentException("Process id must be specified when advancing an existing process.");
            return null;
        }
    }

    protected UMOMessage doReceive(UMOImmutableEndpoint endpoint, long timeout) throws Exception {
        throw new UnsupportedOperationException("Receive is not implemented by the Workflow provider");
    }


    public Object getDelegateSession() throws UMOException {
        return null;
    }

    // TODO This method should be in org.mule.util.Utility but for some reason it causes
    // problems if I reference it from there.
    public static long objectToLong(Object obj) {
        if (obj == null) {
            return -1;
        } else if (obj instanceof String) {
            return NumberUtils.toLong((String) obj);
        } else if (obj instanceof Long) {
            return ((Long) obj).longValue();
        } else return -1;
    }

    private static Log log = LogFactory.getLog(ProcessMessageDispatcher.class);
}

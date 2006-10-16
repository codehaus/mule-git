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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mule.config.i18n.Message;
import org.mule.impl.MuleMessage;
import org.mule.providers.AbstractMessageDispatcher;
import org.mule.providers.NullPayload;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.provider.DispatchException;
import org.mule.util.PropertiesUtils;

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

    /**
     * Performs a synchronous action on the BPMS.
     * @return an object representing the new state of the process
     */
    public UMOMessage doSend(UMOEvent event) throws Exception {
        Object process = processAction(event);

        if (process != null) {
            return new MuleMessage(process);
        } else {
            throw new DispatchException(Message.createStaticMessage("Synchronous process invocation must return the new process state."), event.getMessage(), event.getEndpoint());
        }
    }

    /**
     * Performs an asynchronous action on the BPMS.
     */
    public void doDispatch(UMOEvent event) throws Exception {
        processAction(event);
    }

    protected Object processAction(UMOEvent event) throws Exception {
        // An object representing the new state of the process
        Object process = null;

        // Create a map of process variables based on the message properties.
        Map processVariables = new HashMap();
        if (event != null) {
            processVariables.putAll(PropertiesUtils.getMessageProperties(event.getMessage()));

            // Pass the message's payload in as a special process variable.
            Object payload = event.getTransformedMessage();
            if (payload != null && !(payload instanceof NullPayload)) {
                processVariables.put(ProcessConnector.PROCESS_VARIABLE_INCOMING, payload);
            }
        }

        // Retrieve the parameters
        Object processType = event.getProperty(ProcessConnector.PROPERTY_PROCESS_TYPE, /*exhaustiveSearch*/true);
        processVariables.remove(ProcessConnector.PROPERTY_PROCESS_TYPE);

        Object processId = event.getProperty(ProcessConnector.PROPERTY_PROCESS_ID, /*exhaustiveSearch*/true);
        processVariables.remove(ProcessConnector.PROPERTY_PROCESS_ID);

        // Default action is "advance"
        String action = event.getMessage().getStringProperty(ProcessConnector.PROPERTY_ACTION,
                                                             ProcessConnector.ACTION_ADVANCE);
        processVariables.remove(ProcessConnector.PROPERTY_ACTION);

        Object transition = event.getMessage().getProperty(ProcessConnector.PROPERTY_TRANSITION);
        processVariables.remove(ProcessConnector.PROPERTY_TRANSITION);

        // Decode the URI, for example:
        //      bpm:/testProcess/4561?action=advance
        // TODO Replace this with an EndpointBuilder
        String path = event.getEndpoint().getEndpointURI().getHost();
        String[] pathTokens = StringUtils.split(path, "/");
        if (pathTokens.length >= 1) {
            processType = pathTokens[0];
        }
        if (pathTokens.length >= 2) {
            processId = pathTokens[1];
        }

        ////////////////////////////////////////////////////////////////////////

        // Start a new process.
        if (processId == null || action.equals(ProcessConnector.ACTION_START)) {
            if (processType != null) {
                process = connector.getBpms().startProcess(processType, processVariables);
                if ((process != null) && logger.isInfoEnabled()) {
                    logger.info("New process started, ID = " + connector.getBpms().getId(process));
                }
            } else {
                throw new IllegalArgumentException("Process type is missing, cannot start a new process.");
            }
        }

        // Don't advance the process, just update the process variables.
        else if (action.equals(ProcessConnector.ACTION_UPDATE)){
            if (processId != null) {
                process = connector.getBpms().updateProcess(processId, processVariables);
                if ((process != null) && logger.isInfoEnabled()) {
                    logger.info("Process variables updated, ID = " + connector.getBpms().getId(process));
                }
            } else {
                throw new IllegalArgumentException("Process ID is missing, cannot update process.");
            }
        }

        // Abort the running process (end abnormally).
        else if (action.equals(ProcessConnector.ACTION_ABORT)){
            if (processId != null) {
                connector.getBpms().abortProcess(processId);
                process = new NullPayload();
                logger.info("Process aborted, ID = " + processId);
            } else {
                throw new IllegalArgumentException("Process ID is missing, cannot abort process.");
            }
        }

        // Advance the already-running process one step.
        else {
            if (processId != null) {
                process = connector.getBpms().advanceProcess(processId, transition, processVariables);
                if ((process != null) && logger.isInfoEnabled()) {
                    logger.info("Process advanced, ID = " + connector.getBpms().getId(process) + ", new state = " + connector.getBpms().getState(process));
                }
            } else {
                throw new IllegalArgumentException("Process ID is missing, cannot advance process.");
            }
        }

        return process;
    }

    protected UMOMessage doReceive(UMOImmutableEndpoint endpoint, long timeout) throws Exception {
        throw new UnsupportedOperationException("doReceive() is not implemented by the ProcessMessageDispatcher");
    }

    public Object getDelegateSession() throws UMOException { return null; }
    protected void doConnect(UMOImmutableEndpoint arg0) throws Exception { /*nop*/ }
    protected void doDisconnect() throws Exception { /*nop*/ }
    public void doDispose() { /*nop*/ }
}

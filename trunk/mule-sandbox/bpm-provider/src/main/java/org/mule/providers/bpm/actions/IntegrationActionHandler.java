package org.mule.providers.bpm.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.mule.providers.bpm.ProcessConnector;

public abstract class IntegrationActionHandler extends LoggingActionHandler {

    private Object incoming;

    public void execute(ExecutionContext executionContext) throws Exception {
        super.execute(executionContext);
        incoming = executionContext.getVariable(ProcessConnector.PROCESS_VARIABLE_INCOMING);
    }

    protected Object getIncomingMessage() {
        return incoming;
    }

    protected transient Log logger = LogFactory.getLog(getClass());
}

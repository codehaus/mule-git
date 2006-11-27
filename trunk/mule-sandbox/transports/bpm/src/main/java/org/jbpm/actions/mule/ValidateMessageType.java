package org.jbpm.actions.mule;

import org.jbpm.JbpmException;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Throws an exception if the incoming message's class is not as expected.
 * @param expectedType the expected class type
 * @param strict if true, the class must match exactly, otherwise it can be a subclass
 * @throws JbpmException
 *
 * <action class="org.jbpm.actions.mule.ValidateType">
 *      <expectedType>com.mycompany.MyClass</expectedType>
 * </action>
 */
public class ValidateMessageType extends IntegrationActionHandler {

    protected Class expectedType;
    protected boolean strict = false;

    public void execute(ExecutionContext executionContext) throws Exception {
        super.execute(executionContext);
        Object message = getIncomingMessage();
        if (message == null) {
            throw new JbpmException("Incoming message is null.");
        }

        boolean match;
        if (strict) {
            match = message.getClass().equals(expectedType);
        } else {
            match = expectedType.isAssignableFrom(message.getClass());
        }
        if (match == false) {
            throw new JbpmException("Incoming message type is " + message.getClass() + ", expected type is " + expectedType);
        }
    }
}

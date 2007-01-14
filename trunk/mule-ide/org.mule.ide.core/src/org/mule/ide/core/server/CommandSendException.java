package org.mule.ide.core.server;

/**
 * Exception thrown when a command can not be sent to a server/port.
 * 
 * @author Derek Adams
 */
public class CommandSendException extends Exception {

	private static final long serialVersionUID = -6983752410460049359L;

	public CommandSendException(String message) {
        super(message);
    }
}
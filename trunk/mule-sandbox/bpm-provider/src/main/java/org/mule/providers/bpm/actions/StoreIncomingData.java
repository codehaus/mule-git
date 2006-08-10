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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.mule.providers.bpm.ProcessConnector;

/**
 * Stores the incoming message payload into the specified variable.
 *
 * <action class="org.mule.providers.bpm.actions.StoreIncomingData">
 *      <variable>foo</variable>
 * </action>
 */
public class StoreIncomingData extends IntegrationActionHandler {

    protected String variable = ProcessConnector.PROCESS_VARIABLE_DATA;

    public void execute(ExecutionContext executionContext) throws Exception {
        super.execute(executionContext);
        executionContext.setVariable(variable, transform(getIncomingMessage()));
    }

    /**
     * This method may be overriden in order to store the incoming data as a different
     * type.
     *
     * @param incomingData - the message that has arrived
     * @return the object to be stored as a process variable
     */
    protected Object transform(Object incomingData) throws Exception {
        return incomingData;
    }

    private static Log log = LogFactory.getLog(StoreIncomingData.class);
}

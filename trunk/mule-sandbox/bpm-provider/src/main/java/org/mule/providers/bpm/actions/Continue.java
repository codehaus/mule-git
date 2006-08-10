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
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Simply continues the process execution (moves on to the next state).
 */
public class Continue implements ActionHandler  {

    public void execute(ExecutionContext executionContext) throws Exception {
        executionContext.leaveNode();
    }

    private static Log log = LogFactory.getLog(Continue.class);
}

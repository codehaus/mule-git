/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm.osworkflow.conditions;

import org.mule.providers.bpm.ProcessConnector;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Compares the current incoming message to an expected value.
 * 
 *  <condition type="class">
 *    <arg name="class.name">org.mule.providers.bpm.osworkflow.conditions.ValidateMessage</arg>
 *    <arg name="expectedValue">Message in a bottle.</arg>
 *  </condition>
 */
public class ValidateMessage implements Condition
{
    protected static transient Log logger = LogFactory.getLog(ValidateMessage.class);
    
    public boolean passesCondition(Map transientVars, Map args, PropertySet ps) throws WorkflowException
    {
        String expectedValue = (String) args.get("expectedValue");

        Object incomingMessage = ps.getObject(ProcessConnector.PROCESS_VARIABLE_INCOMING);
        boolean verdict = expectedValue.equals(incomingMessage);
        logger.debug("Validating message against expected value '" + expectedValue + "', verdict = " + verdict);
        return verdict;
    }

}



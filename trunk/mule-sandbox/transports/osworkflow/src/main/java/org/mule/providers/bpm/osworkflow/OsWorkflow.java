/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm.osworkflow;

import org.mule.providers.bpm.BPMS;
import org.mule.providers.bpm.MessageService;
import org.mule.util.NumberUtils;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * OSWorkflow's implementation of Mule's generic BPMS interface.
 * This class should be set as the "bpms" property of the BPM Connector:
 * 
 *   <connector name="OSWorkflowConnector" className="org.mule.providers.bpm.ProcessConnector">
 *       <properties>
 *           <spring-property name="bpms">
 *              <ref local="osworkflow" />
 *           </spring-property>
 *       </properties>
 *   </connector>
 *
 *   <bean id="osworkflow" class="org.mule.providers.bpm.osworkflow.OsWorkflow"/>
 *   
 * The "process" object is simply the workflow ID as a Long.
 */
public class OsWorkflow implements BPMS
{
    protected static transient Log logger = LogFactory.getLog(OsWorkflow.class);

    protected Workflow workflowInterface = null;
    protected String user = "mule";

    // ///////////////////////////////////////////////////////////////////////////
    // Lifecycle methods
    // ///////////////////////////////////////////////////////////////////////////

    /**
     * Creates the Mule wrapper for OSWorkflow using a default configuration. 
     */
    public OsWorkflow() 
    {
        workflowInterface = new BasicWorkflow(user);
    }
    
    /**
     * Creates the Mule wrapper for OSWorkflow based on an already-existing configuration. 
     */
    public OsWorkflow(Workflow workflowInterface)
    {
        setWorkflowInterface(workflowInterface);
    }

    public void setMessageService(MessageService msgService)
    {
        workflowInterface.setConfiguration(new MuleConfiguration(msgService));
    }
    
    // ///////////////////////////////////////////////////////////////////////////
    // Process manipulation
    // ///////////////////////////////////////////////////////////////////////////

    /**
     * Start a new workflow.
     * @param processType - the workflow name
     * @param transition - the initial-action # (defaults to 1)
     * @param processVariables - user inputs
     * @return ID of the new workflow
     */
    public Object startProcess(Object processType, Object transition, Map processVariables) throws Exception
    {
        int initialAction = 1;
        if (transition != null)
        {
            initialAction = NumberUtils.toInt(transition);
        }
        
        long workflowId = 
            workflowInterface.initialize((String) processType, initialAction, processVariables);
        return new Long(workflowId);
    }

    /**
     * Perform an action on an existing workflow.
     * @param processId - the workflow ID
     * @param transition - the action # (only required if more than one action is available)
     * @param processVariables - user inputs
     * @return void
     */
    public Object advanceProcess(Object processId, Object transition, Map processVariables) throws Exception
    {
        int actionId;
        if (transition != null)
        {
            actionId = NumberUtils.toInt(transition);
        }
        else
        {
            int[] availableActions = 
                workflowInterface.getAvailableActions(NumberUtils.toLong(processId), new Hashtable());
            if (availableActions.length == 1)
            {
                actionId = availableActions[0];
            }
            else if (availableActions.length > 1)
            {
                throw new WorkflowException("More than one action is available from the current state but no transition has been specified.");
            }
            else 
            {
                throw new WorkflowException("There are no actions available from the current state.");
            }
        }

        workflowInterface.doAction(NumberUtils.toLong(processId), actionId, processVariables);
        return processId;
    }

    /**
     * Not implemented
     */
    public Object updateProcess(Object processId, Map processVariables) throws Exception
    {
        return null;
    }

    /**
     * Not implemented
     */
    public void abortProcess(Object processId) throws Exception
    {
        // nop
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Process status / lookup
    // ///////////////////////////////////////////////////////////////////////////

    /**
     * Not yet implemented
     */
    public Object lookupProcess(Object processId) throws Exception
    {
        // TODO
        //return workflowInterface.query(new WorkflowExpressionQuery(???));
        return null;
    }

    public Object getId(Object process) throws Exception
    {
        // The "process" object is simply the workflow ID as a Long.
        return process;
    }

    /**
     * @return the state ID of the specified workflow.
     */
    public Object getState(Object process) throws Exception
    {
        return new Integer(workflowInterface.getEntryState(NumberUtils.toLong(process)));
    }

    public boolean hasEnded(Object process) throws Exception
    {
        int[] availableActions = 
            workflowInterface.getAvailableActions(NumberUtils.toLong(process), new Hashtable());
        return (availableActions.length == 0);
    }

    /**
     * Not implemented
     */
    public boolean isProcess(Object obj) throws Exception
    {
        return false;
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Getters and setters
    // ///////////////////////////////////////////////////////////////////////////

    public Workflow getWorkflowInterface()
    {
        return workflowInterface;
    }

    public void setWorkflowInterface(Workflow workflowInterface)
    {
        this.workflowInterface = workflowInterface;
    }
}



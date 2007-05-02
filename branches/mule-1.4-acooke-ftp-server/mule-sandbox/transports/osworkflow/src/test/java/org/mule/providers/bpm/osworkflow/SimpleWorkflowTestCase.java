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

import org.mule.tck.AbstractMuleTestCase;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.config.DefaultConfiguration;
import com.opensymphony.workflow.spi.Step;

import java.util.Collection;
import java.util.Hashtable;

public class SimpleWorkflowTestCase extends AbstractMuleTestCase
{
    public void testWorkFlow() throws Exception
    {
        Workflow workflow = new BasicWorkflow("testuser");
        DefaultConfiguration config = new DefaultConfiguration();
        workflow.setConfiguration(config);

        long workflowId = workflow.initialize("simple", 1, null);

        Collection currentSteps = workflow.getCurrentSteps(workflowId);
        // verify we only have one current step
        assertEquals("Unexpected number of current steps", 1, currentSteps.size());
        // verify it's step 1
        Step currentStep = (Step)currentSteps.iterator().next();
        assertEquals("Unexpected current step", 1, currentStep.getStepId());

        int[] availableActions = workflow.getAvailableActions(workflowId, new Hashtable());
        // verify we only have one available action
        assertEquals("Unexpected number of available actions", 1, availableActions.length);
        // verify it's action 1
        assertEquals("Unexpected available action", 2, availableActions[0]);

        workflow.doAction(workflowId, 2, null);
    }
}

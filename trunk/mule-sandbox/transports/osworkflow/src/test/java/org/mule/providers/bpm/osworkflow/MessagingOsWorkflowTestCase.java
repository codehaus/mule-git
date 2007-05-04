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

import org.mule.extras.client.MuleClient;
import org.mule.providers.bpm.BPMS;
import org.mule.providers.bpm.tests.AbstractBpmTestCase;
import org.mule.umo.UMOMessage;
import org.mule.util.NumberUtils;

/**
 * Tests the connector against OSWorkflow with a process which generates a Mule message and 
 * processes its response.
 */
public class MessagingOsWorkflowTestCase extends AbstractBpmTestCase {

    protected String getConfigResources() {
        return "mule-osworkflow-config.xml";
    }

    public void testProcess() throws Exception {
        UMOMessage response;
        Object process;
        BPMS bpms = connector.getBpms();
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm://message", "data", null);
            process = response.getPayload();

            long processId = NumberUtils.toLong(bpms.getId(process));
            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals(1, NumberUtils.toInt(bpms.getState(process)));

            // Advance the process one step.
            response = client.send("bpm://message/" + processId, null, null);
            assertEquals(2, NumberUtils.toInt(bpms.getState(process)));
            
            // Advance the process one more step.
            response = client.send("bpm://message/" + processId, null, null);

            // The process should have ended.
            assertTrue("Process should have ended", bpms.hasEnded(process));
        } finally {
            client.dispose();
        }
    }
}

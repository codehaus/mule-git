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

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.providers.bpm.BPMS;
import org.mule.providers.bpm.ProcessConnector;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.util.NumberUtils;

/**
 * Tests the connector against OSWorkflow with a simple process which generates a Mule message.
 */
public class MessagingOsWorkflowTestCase extends FunctionalTestCase {

    ProcessConnector connector;
    BPMS bpms;

    protected String getConfigResources() {
        return "mule-osworkflow-config.xml";
    }

    protected void setupManager() throws Exception {
        doSetupManager();
        super.setupManager();
    }

    protected void doSetupManager() throws Exception {
        // Configure the BPM connector programmatically so that we are able to pass it the OsWorkflow instance.
        connector = new ProcessConnector();
        bpms = new OsWorkflow();
        connector.setBpms(bpms);
        connector.setAllowGlobalReceiver(true);

        MuleManager.getInstance().registerConnector(connector);
    }

    public void testSimpleProcess() throws Exception {
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
            process = response.getPayload();

            // The process should have ended.
            assertTrue(bpms.hasEnded(process));
        } finally {
            client.dispose();
        }
    }

    public void testSimpleProcessWithParameters() throws Exception {
        UMOMessage response;
        Object process;
        BPMS bpms = connector.getBpms();
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm://?" +
                ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_START +
                "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=message", "data", null);
            process = response.getPayload();

            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);
            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals(1, NumberUtils.toInt(bpms.getState(process)));

            // Advance the process one step.
            response = client.send("bpm://?" +
                    ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_ADVANCE +
                    "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=message&" +
                    ProcessConnector.PROPERTY_PROCESS_ID + "=" + processId, "data", null);
            process = response.getPayload();

            // The process should have ended.
            assertTrue(bpms.hasEnded(process));
        } finally {
            client.dispose();
        }
    }
}

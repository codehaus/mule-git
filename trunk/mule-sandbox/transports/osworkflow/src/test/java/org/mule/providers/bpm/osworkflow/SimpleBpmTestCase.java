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
 * Tests the connector against BPM with 2 simple processes.
 */
public class SimpleBpmTestCase extends FunctionalTestCase {

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
        // Configure the BPM connector programmatically so that we are able to pass it the jBPM instance.
        connector = new ProcessConnector();
        bpms = new OsWorkflow();
        connector.setBpms(bpms);

        MuleManager.getInstance().registerConnector(connector);
    }

    public void testSimpleProcess() throws Exception {
        UMOMessage response;
        Object process;
        BPMS bpms = connector.getBpms();
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm://simple", "data", null);
            process = response.getPayload();

            long processId = NumberUtils.toLong(bpms.getId(process));
            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals(1, NumberUtils.toInt(bpms.getState(process)));

            // Advance the process one step.
            response = client.send("bpm://simple/" + processId, null, null);
            process = response.getPayload();

            // The process should be in the second wait state.
            assertEquals(1, NumberUtils.toInt(bpms.getState(process)));
            
            // Advance the process one more step.
            response = client.send("bpm://simple/" + processId, null, null);
            process = response.getPayload();
            
            // The process should have ended.
            assertEquals(2, NumberUtils.toInt(bpms.getState(process)));
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
                "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=simple", "data", null);
            process = response.getPayload();

            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);
            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals(new Long(1), bpms.getState(process));

            // Advance the process one step.
            response = client.send("bpm://?" +
                    ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_ADVANCE +
                    "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=simple&" +
                    ProcessConnector.PROPERTY_PROCESS_ID + "=" + processId, "data", null);
            process = response.getPayload();

            // The process should have ended.
            assertTrue(bpms.hasEnded(process));
        } finally {
            client.dispose();
        }
    }
}

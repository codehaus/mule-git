/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the BSD style
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.bpm;

import org.jbpm.msg.mule.Jbpm;
import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.extras.client.MuleClient;
import org.mule.extras.spring.config.SpringConfigurationBuilder;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOMessage;
import org.mule.util.NumberUtils;

public class SimpleJbpmSpringTestCase extends FunctionalTestCase {

    private ProcessConnector connector;
    private BPMS bpms;

    public SimpleJbpmSpringTestCase() {
        super();
        setDisposeManagerPerSuite(true);
    }

    protected ConfigurationBuilder getBuilder() throws Exception {
        return new SpringConfigurationBuilder();
    }

    protected String getConfigResources() {
        return "jbpm-spring-config.xml";
    }

    protected void doPostFunctionalSetUp() throws Exception {
        connector =
            (ProcessConnector) MuleManager.getInstance().lookupConnector("jBpmConnector");
        bpms = connector.getBpms();
        super.doPostFunctionalSetUp();
    }

    public void testSimpleProcess() throws Exception {
        // Deploy the process definition.
        ((Jbpm) bpms).deployProcess("dummyProcess.xml");

        UMOMessage response;
        Object process;
        BPMS bpms = connector.getBpms();
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm://dummyProcess", "data", null);
            process = response.getPayload();

            long processId = NumberUtils.toLong(bpms.getId(process));
            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals("dummyState", bpms.getState(process));

            // Advance the process one step.
            response = client.send("bpm://dummyProcess/" + processId, null, null);
            process = response.getPayload();

            // The process should have ended.
            assertTrue(bpms.hasEnded(process));
        } finally {
            client.dispose();
        }
    }

    public void testSimpleProcessWithParameters() throws Exception {
        // Deploy the process definition.
        ((Jbpm) bpms).deployProcess("dummyProcess.xml");

        UMOMessage response;
        Object process;
        BPMS bpms = connector.getBpms();
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm://?" +
                ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_START +
                "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=dummyProcess", "data", null);
            process = response.getPayload();

            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);
            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals("dummyState", bpms.getState(process));

            // Advance the process one step.
            response = client.send("bpm://?" +
                    ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_ADVANCE +
                    "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=dummyProcess&" +
                    ProcessConnector.PROPERTY_PROCESS_ID + "=" + processId, "data", null);
            process = response.getPayload();

            // The process should have ended.
            assertTrue(bpms.hasEnded(process));
        } finally {
            client.dispose();
        }
    }
}

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

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.mule.Jbpm;
import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.ConfigurationException;
import org.mule.config.builders.MuleXmlConfigurationBuilder;
import org.mule.config.i18n.Message;
import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;

/**
 * @author <a href="mailto:carlson@hotpop.com">Travis Carlson</a>
 */
public class ProcessTestCase extends TestCase {

    private ProcessConnector connector;
    private BPMS bpms;

    protected void setUp() throws Exception {
        super.setUp();
        ConfigurationBuilder configBuilder = new MuleXmlConfigurationBuilder();
        configBuilder.configure("mule-config.xml", null);
        connector =
            (ProcessConnector) MuleManager.getInstance().lookupConnector("jbpmConnector");
        if (connector == null) {
            throw new ConfigurationException(Message.createStaticMessage("Unable to lookup BPM Connector"));
        }
        bpms = connector.getBpms();
    }

    protected void tearDown() throws Exception {
        if (MuleManager.isInstanciated()) {
            MuleManager.getInstance().dispose();
        }
        super.tearDown();
    }

    public void testCreateSimpleProcess() throws Exception {

        // Deploy the process definition.
        ((Jbpm) bpms).deployProcess("dummyProcess.xml");

        UMOMessage response;
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm:/dummyProcess", "data", null);
            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);

            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals("dummyState", bpms.getState(bpms.lookupProcess(new Long(processId))));

            // Advance the process one step.
            response = client.send("bpm:/dummyProcess/" + processId, null, null);

            // The process should have ended.
            assertTrue(bpms.hasEnded(bpms.lookupProcess(new Long(processId))));
        } finally {
            client.dispose();
        }
    }

    public void testSimpleProcessWithParameters() throws Exception {

        // Deploy the process definition.
        ((Jbpm) bpms).deployProcess("dummyProcess.xml");

        UMOMessage response;
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm:/?" +
                ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_START +
                "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=dummyProcess", "data", null);
            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);

            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals("dummyState", bpms.getState(bpms.lookupProcess(new Long(processId))));

            // Advance the process one step.
            response = client.send("bpm:/?" +
                    ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_ADVANCE +
                    "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=dummyProcess&" +
                    ProcessConnector.PROPERTY_PROCESS_ID + "=" + processId, "data", null);

            // The process should have ended.
            assertTrue(bpms.hasEnded(bpms.lookupProcess(new Long(processId))));
        } finally {
            client.dispose();
        }
    }

    public void testSendMessageProcess() throws Exception {

        // Deploy the process definition.
        ((Jbpm) bpms).deployProcess("sendMessageProcess.xml");

        UMOMessage response;
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm:/sendMessageProcess", "data", null);
            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);

            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals("sendMessage", bpms.getState(bpms.lookupProcess(new Long(processId))));

            // Advance the process one step.
            response = client.send("bpm:/sendMessageProcess/" + processId, "data", null);

            // TODO Verify message was sent
            // expect ??

            // The process should have ended.
            assertTrue(bpms.hasEnded(bpms.lookupProcess(new Long(processId))));
        } finally {
            client.dispose();
        }
    }

    private static Log log = LogFactory.getLog(ProcessTestCase.class);
}

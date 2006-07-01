package org.mule.providers.bpm;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    protected void setUp() throws Exception {
        super.setUp();
        ConfigurationBuilder configBuilder = new MuleXmlConfigurationBuilder();
        configBuilder.configure("mule-config.xml");
        connector =
            (ProcessConnector) MuleManager.getInstance().lookupConnector("jbpmConnector");
        if (connector == null) {
            throw new ConfigurationException(Message.createStaticMessage("Unable to lookup BPM Connector"));
        }
    }

    protected void tearDown() throws Exception {
        if (MuleManager.isInstanciated()) {
            MuleManager.getInstance().dispose();
        }
        super.tearDown();
    }

    public void testCreateSimpleProcess() throws Exception {

        // Deploy the process definition.
        JBpmUtil.deployProcess(connector.getJbpmSessionFactory(), "dummyProcess.xml");

        UMOMessage response;
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm:/dummyProcess", "data", null);
            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);

            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals("dummyState", JBpmUtil.getState(connector.getJbpmSessionFactory(), processId));

            // Advance the process one step.
            response = client.send("bpm:/dummyProcess/" + processId, null, null);

            // The process should have ended.
            assertTrue(JBpmUtil.processHasEnded(connector.getJbpmSessionFactory(), processId));
        } finally {
            client.dispose();
        }
    }

    public void testSimpleProcessWithParameters() throws Exception {

        // Deploy the process definition.
        JBpmUtil.deployProcess(connector.getJbpmSessionFactory(), "dummyProcess.xml");

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
            assertEquals("dummyState", JBpmUtil.getState(connector.getJbpmSessionFactory(), processId));

            // Advance the process one step.
            response = client.send("bpm:/?" +
                    ProcessConnector.PROPERTY_ACTION + "=" + ProcessConnector.ACTION_ADVANCE +
                    "&" + ProcessConnector.PROPERTY_PROCESS_TYPE + "=dummyProcess&" +
                    ProcessConnector.PROPERTY_PROCESS_ID + "=" + processId, "data", null);

            // The process should have ended.
            assertEquals("end", JBpmUtil.getState(connector.getJbpmSessionFactory(), processId));
        } finally {
            client.dispose();
        }
    }

    public void testSendMessageProcess() throws Exception {

        // Deploy the process definition.
        JBpmUtil.deployProcess(connector.getJbpmSessionFactory(), "sendMessageProcess.xml");

        UMOMessage response;
        MuleClient client = new MuleClient();
        try {
            // Create a new process.
            response = client.send("bpm:/sendMessageProcess", "data", null);
            long processId =
                response.getLongProperty(ProcessConnector.PROPERTY_PROCESS_ID, -1);

            // The process should be started and in a wait state.
            assertFalse(processId == -1);
            assertEquals("sendMessage", JBpmUtil.getState(connector.getJbpmSessionFactory(), processId));

            // Advance the process one step.
            response = client.send("bpm:/sendMessageProcess/" + processId, "data", null);

            // TODO Verify message was sent
            // expect ??

            // The process should have ended.
            assertEquals("end", JBpmUtil.getState(connector.getJbpmSessionFactory(), processId));
        } finally {
            client.dispose();
        }
    }

    private static Log log = LogFactory.getLog(ProcessTestCase.class);
}

/* 
* $Header: /opt/cvsroot/framegroup/mqseries/main/test/java/org/mule/providers/mqseries/MQSeriesRemoteQMFunctionalTestCase.java,v 1.1 2005/11/29 20:42:41 rossmason Exp $
* $Revision: 1.1 $
* $Date: 2005/11/29 20:42:41 $
* ------------------------------------------------------------------------------------------------------
* 
* Copyright (c) SymphonySoft Limited. All rights reserved.
* http://www.symphonysoft.com
* 
* The software in this package is published under the terms of the BSD
* style license a copy of which has been included with this distribution in
* the LICENSE.txt file. 
*
*/
package org.mule.providers.mqseries;

import junit.framework.TestCase;
import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;
import org.mule.MuleManager;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.JMSC;

import javax.jms.JMSException;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.1 $
 */
public class MQSeriesRemoteQMFunctionalTestCase extends TestCase {

    public void testFunctional() throws Exception {

        MQQueueConnectionFactory f = null;
        String TEST_MSG = "tresting";
        MuleClient client = new MuleClient();
        MQSeriesConnector c = new MQSeriesConnector();
        try {
            f = new MQQueueConnectionFactory();
            f.setHostName("vmmachine"); //Change this to the name of the server running MQ
            f.setPort(6969);	//Change this accordingly too. Default is 1414
            f.setQueueManager("QM_vmmachine");             // Queue Manager with this name needs to be pre-configured!

            f.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
            f.createQueueConnection().start();
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            e.getLinkedException().printStackTrace();
        }

        c.setName("WSSeries");
        c.setHostname("vmmachine");
        c.setConnectionFactory(f);

        client.getManager().registerConnector(c);
        client.sendNoReceive("mqs://QM_vmmachine/MuleQueue", TEST_MSG, null);
        UMOMessage result = client.receive("mqs://QM_vmmachine/MuleQueue", 3000);
        assertNotNull(result);
        assertEquals(TEST_MSG, result.getPayloadAsString());    }

    public void testFunctionalRemoteQueue() throws Exception {

        MQQueueConnectionFactory dmz = null;
        MQQueueConnectionFactory corp = null;

        dmz = new MQQueueConnectionFactory();
        dmz.setHostName("VMMachine"); //AB
        dmz.setPort(6969);
        dmz.setQueueManager("QM_vmmachine");
        dmz.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
        //dmz.createQueueConnection().start();

        corp = new MQQueueConnectionFactory();
        corp.setHostName("VMMachine"); // AB
        corp.setPort(6969);
        corp.setQueueManager("QM_vmmachine");
        corp.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
        //corp.createQueueConnection().start();

        String TEST_MSG = "tresting";
        MuleClient client = new MuleClient();
        MQSeriesConnector dmzCnn = new MQSeriesConnector();
        dmzCnn.setName("dmz");
        dmzCnn.setHostname("VMMachine");
        dmzCnn.setConnectionFactory(dmz);
        client.getManager().registerConnector(dmzCnn);

        MQSeriesConnector corpCnn = new MQSeriesConnector();
        corpCnn.setName("corp");
        corpCnn.setHostname("VMMachine"); // AB
        corpCnn.setPort(6969);
        
        corpCnn.setConnectionFactory(corp);
        client.getManager().registerConnector(corpCnn);
        client.getManager().start();

        client.sendNoReceive("mqs://QM_vmmachine/MuleQueue", TEST_MSG, null);
        UMOMessage result = client.receive("mqs://QM_vmmachine/MuleQueue", 3000);
        assertNotNull(result);
        assertEquals(TEST_MSG, result.getPayloadAsString());

    }
}

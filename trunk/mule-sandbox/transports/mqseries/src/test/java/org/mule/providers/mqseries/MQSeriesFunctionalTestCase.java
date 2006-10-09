/* 
* $Header: /opt/cvsroot/framegroup/mqseries/main/test/java/org/mule/providers/mqseries/MQSeriesFunctionalTestCase.java,v 1.3 2005/11/29 20:42:41 rossmason Exp $
* $Revision: 1.3 $
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
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.JMSC;

import javax.jms.JMSException;

/**
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.3 $
 */
public class MQSeriesFunctionalTestCase extends TestCase {

    public void testFunctional() throws Exception {

//        MQQueueConnectionFactory f = null;
//        try
//        {
//            f = new MQQueueConnectionFactory();
//            f.setHostName("Little-Ernie");
//            f.setQueueManager("FFX.DMZ.QM");
//            f.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
//            f.setChannel("FFX.IN2OUT");
//            f.createQueueConnection().start();
//        } catch (JMSException e)
//        {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            e.getLinkedException().printStackTrace();
//        }

        String TEST_MSG = "tresting";
        MuleClient client = new MuleClient();
//        MQSeriesConnector c = new MQSeriesConnector();
//        c.setName("mqseries");
//        c.setConnectionFactory(f);
//        client.getManager().registerConnector(c);
        client.sendNoReceive("mqs://FFX.DMZ.QM/DMZ.RECEIVE", TEST_MSG, null);
        UMOMessage result = client.receive("mqs://FFX.DMZ.QM/DMZ.RECEIVE", 3000);
        assertNotNull(result);
        assertEquals(TEST_MSG, result.getPayloadAsString());

    }
}

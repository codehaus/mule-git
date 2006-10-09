/* 
* $Header: /opt/cvsroot/framegroup/mqseries/main/src/java/org/mule/providers/mqseries/DefaultExitHandler.java,v 1.3 2006/02/02 12:23:29 rossmason Exp $
* $Revision: 1.3 $
* $Date: 2006/02/02 12:23:29 $
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

import com.ibm.mq.MQSendExit;
import com.ibm.mq.MQSecurityExit;
import com.ibm.mq.MQReceiveExit;
import com.ibm.mq.MQChannelExit;
import com.ibm.mq.MQChannelDefinition;
import com.ibm.mq.MQException;
import org.mule.MuleManager;

/**
 * Relays MQSeries Exit events to the Mule Server event manager where Mule components, agents, et
 * can register their interest for these events
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.3 $
 */
public class DefaultExitHandler implements MQSendExit, MQReceiveExit, MQSecurityExit {

    public DefaultExitHandler(String resourceInfo) {
        System.out.println(resourceInfo);
    }

    public byte[] sendExit(MQChannelExit mqChannelExit, MQChannelDefinition mqChannelDefinition, byte[] bytes) {
        MuleManager.getInstance().fireNotification(new MQSeriesExitEvent(mqChannelExit, mqChannelDefinition, MQSeriesExitEvent.MQSERIES_SEND_EXIT));
        return new byte[0];
    }

    public byte[] receiveExit(MQChannelExit mqChannelExit, MQChannelDefinition mqChannelDefinition, byte[] bytes) {
        MuleManager.getInstance().fireNotification(new MQSeriesExitEvent(mqChannelExit, mqChannelDefinition, MQSeriesExitEvent.MQSERIES_RECEIVE_EXIT));
        return new byte[0];
    }

    public byte[] securityExit(MQChannelExit mqChannelExit, MQChannelDefinition mqChannelDefinition, byte[] bytes) {
        MuleManager.getInstance().fireNotification(new MQSeriesExitEvent(mqChannelExit, mqChannelDefinition, MQSeriesExitEvent.MQSERIES_SECURITY_EXIT));
        return new byte[0];
    }
}

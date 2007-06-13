/* 
* $Header: /opt/cvsroot/framegroup/mqseries/main/src/java/org/mule/providers/mqseries/MQSeriesExitEvent.java,v 1.2 2005/11/29 20:42:41 rossmason Exp $
* $Revision: 1.2 $
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

import org.mule.umo.provider.UMOConnectable;
import org.mule.impl.internal.notifications.CustomNotification;
import com.ibm.mq.MQChannelExit;
import com.ibm.mq.MQChannelDefinition;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.2 $
 */
public class MQSeriesExitEvent extends CustomNotification
 {
    public static final int MQSERIES_SEND_EXIT = CUSTOM_EVENT_ACTION_START_RANGE + 1;
    public static final int MQSERIES_RECEIVE_EXIT = CUSTOM_EVENT_ACTION_START_RANGE + 2;
    public static final int MQSERIES_SECURITY_EXIT = CUSTOM_EVENT_ACTION_START_RANGE + 3;
    private static final transient String[] ACTIONS = new String[]{"send", "receive", "security"};

    private MQChannelDefinition mqChannelDefinition;

    public MQSeriesExitEvent(MQChannelExit mqChannelExit, MQChannelDefinition mqChannelDefinition, int action) {
        super(mqChannelExit, action);
        this.mqChannelDefinition = mqChannelDefinition;
    }

    protected String[] getActionNames() {
        return ACTIONS;
    }

    public MQChannelDefinition getMqChannelDefinition() {
        return mqChannelDefinition;
    }
}

/*
 * $Header: /opt/cvsroot/framegroup/mqseries/main/src/java/org/mule/providers/mqseries/MQSeriesJmsSupport.java,v 1.1 2006/02/22 14:40:46 rossmason Exp $
 * $Revision: 1.1 $
 * $Date: 2006/02/22 14:40:46 $
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.mqseries;

import org.mule.providers.jms.Jms102bSupport;
import org.mule.providers.jms.JmsConnector;
import org.mule.providers.jms.JmsConstants;

import javax.naming.Context;

/**
 * todo document
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.1 $
 */
public class MQSeriesJmsSupport extends Jms102bSupport {

    public MQSeriesJmsSupport(JmsConnector jmsConnector, Context context, boolean b, boolean b1) {
        super(jmsConnector, context, b, b1);
    }

    public boolean supportsProperty(String property) {
        return !(property.equals(JmsConstants.JMS_REPLY_TO) && ((MQSeriesConnector)connector).isDisableReplyTo());
    }
}

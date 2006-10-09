/* 
 * $Header: /opt/cvsroot/framegroup/mqseries/main/src/java/org/mule/providers/mqseries/transformers/MQJmsMessageToObject.java,v 1.3 2006/02/21 12:45:09 rossmason Exp $
 * $Revision: 1.3 $
 * $Date: 2006/02/21 12:45:09 $
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
package org.mule.providers.mqseries.transformers;

import javax.jms.Message;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.umo.transformer.TransformerException;
import org.mule.umo.UMOEventContext;
import org.mule.providers.jms.transformers.AbstractJmsTransformer;
import org.mule.providers.jms.transformers.JMSMessageToObject;
import org.mule.impl.RequestContext;
import org.mule.config.i18n.Messages;
import com.ibm.mq.MQMessage;
import com.ibm.jms.JMSMessage;

/**
 * <code>JMSMessageToObject</code> Will convert a
 * <code>javax.jms.Message</code> or sub-type into an object by extracting the
 * message payload. Users of this transformer can set different return types on
 * the transform to control the way it behaves.
 * <ul>
 * <li>javax.jms.TextMessage - java.lang.String</li>
 * <li>javax.jms.ObjectMessage - java.lang.Object</li>
 * <li>javax.jms.BytesMessage - Byte[]. Note that the transformer will check if
 * the payload is compressed and automatically uncompress the message.</li>
 * <li>javax.jms.MapMessage - java.util.Map</li>
 * <li>javax.jms.StreamMessage - java.util.Vector of objects from the Stream
 * Message.</li>
 * </ul>
 * 
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 1.3 $
 */

public class MQJmsMessageToObject extends JMSMessageToObject
{
    public static final String JMS_FOLDER_PROPERTY = "MQS_JMS_FOLDER";
    public static final String MCD_FOLDER_PROPERTY = "MQS_MCD_FOLDER";
    public static final String USR_FOLDER_PROPERTY = "MQS_USR_FOLDER";

    public MQJmsMessageToObject() {
        super();
        registerSourceType(JMSMessage.class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        UMOEventContext context = RequestContext.getEventContext();
        if (context == null) {
            throw new TransformerException(new org.mule.config.i18n.Message(Messages.NO_CURRENT_EVENT_FOR_TRANSFORMER), this);
        }
        JMSMessage mqMessage = (JMSMessage)src;
        try {
            context.setProperty(JMS_FOLDER_PROPERTY, mqMessage._getJmsFolder());
            if(logger.isDebugEnabled()) logger.debug("Setting " + JMS_FOLDER_PROPERTY + " property to " + context.getProperty(JMS_FOLDER_PROPERTY));
        } catch (JMSException e) {
            throw new TransformerException(new org.mule.config.i18n.Message("mqs", 1, "<jms>"), this, e);
        }
        try {
            context.setProperty(USR_FOLDER_PROPERTY, mqMessage._getUsrFolder());
            if(logger.isDebugEnabled()) logger.debug("Setting " + USR_FOLDER_PROPERTY + " property to " + context.getProperty(USR_FOLDER_PROPERTY));
        } catch (JMSException e) {
            throw new TransformerException(new org.mule.config.i18n.Message("mqs", 1, "<usr>"), this, e);
        }
        try {
            context.setProperty(MCD_FOLDER_PROPERTY, mqMessage._getMcdFolder());
            if(logger.isDebugEnabled()) logger.debug("Setting " + MCD_FOLDER_PROPERTY + " property to " + context.getProperty(MCD_FOLDER_PROPERTY));
        } catch (JMSException e) {
            throw new TransformerException(new org.mule.config.i18n.Message("mqs", 1, "<mcd>"), this, e);
        }

        return super.doTransform(mqMessage, encoding);
    }

}

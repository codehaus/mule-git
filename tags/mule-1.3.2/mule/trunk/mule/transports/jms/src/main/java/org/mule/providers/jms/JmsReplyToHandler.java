/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms;

import org.mule.impl.model.AbstractComponent;
import org.mule.providers.DefaultReplyToHandler;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.provider.DispatchException;
import org.mule.umo.transformer.UMOTransformer;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.commons.lang.ObjectUtils;

/**
 * <code>JmsReplyToHandler</code> will process a JMS replyTo or hand off to the
 * default replyTo handler if the replyTo is a URL.
 */
public class JmsReplyToHandler extends DefaultReplyToHandler
{
    private final JmsConnector connector;

    public JmsReplyToHandler(JmsConnector connector, UMOTransformer transformer)
    {
        super(transformer);
        this.connector = connector;
    }

    public void processReplyTo(UMOEvent event, UMOMessage returnMessage, Object replyTo) throws UMOException
    {
        Destination replyToDestination = null;
        MessageProducer replyToProducer = null;
        Session session = null;
        try
        {
            // now we need to send the response
            if (replyTo instanceof Destination)
            {
                replyToDestination = (Destination)replyTo;
            }
            if (replyToDestination == null)
            {
                super.processReplyTo(event, returnMessage, replyTo);
                return;
            }
            Object payload = returnMessage.getPayload();
            if (getTransformer() != null)
            {
                getTransformer().setEndpoint(getEndpoint(event, "jms://temporary"));
                if (getTransformer().isSourceTypeSupported(payload.getClass()))
                {
                    payload = getTransformer().transform(payload);
                }
                else if (logger.isDebugEnabled())
                {
                    logger.debug("Transformer for replyTo Handler: " + getTransformer().toString()
                                 + " does not support source type: " + payload.getClass()
                                 + ". Not doing a transform");
                }
            }

            final boolean topic = connector.getTopicResolver().isTopic(replyToDestination);
            session = connector.getSession(false, topic);
            Message replyToMessage = JmsMessageUtils.toMessage(payload, session);

            replyToMessage.setJMSReplyTo(null);
            if (logger.isDebugEnabled())
            {
                logger.debug("Sending jms reply to: " + replyToDestination + "("
                             + replyToDestination.getClass().getName() + ")");
            }
            replyToProducer = connector.getJmsSupport().createProducer(session, replyToDestination, topic);

            // QoS support
            UMOMessage eventMsg = event.getMessage();
            String ttlString = (String)eventMsg.removeProperty(JmsConstants.TIME_TO_LIVE_PROPERTY);
            String priorityString = (String)eventMsg.removeProperty(JmsConstants.PRIORITY_PROPERTY);
            String persistentDeliveryString = (String)eventMsg.removeProperty(JmsConstants.PERSISTENT_DELIVERY_PROPERTY);

            if (ttlString == null && priorityString == null && persistentDeliveryString == null)
            {
                connector.getJmsSupport().send(replyToProducer, replyToMessage, topic);
            }
            else
            {
                long ttl = Message.DEFAULT_TIME_TO_LIVE;
                int priority = Message.DEFAULT_PRIORITY;
                boolean persistent = Message.DEFAULT_DELIVERY_MODE == DeliveryMode.PERSISTENT;

                if (ttlString != null)
                {
                    ttl = Long.parseLong(ttlString);
                }
                if (priorityString != null)
                {
                    priority = Integer.parseInt(priorityString);
                }
                if (persistentDeliveryString != null)
                {
                    persistent = Boolean.valueOf(persistentDeliveryString).booleanValue();
                }

                connector.getJmsSupport().send(replyToProducer, replyToMessage, persistent, priority, ttl,
                    topic);
            }

            // connector.getJmsSupport().send(replyToProducer, replyToMessage,
            // replyToDestination);
            logger.info("Reply Message sent to: " + replyToDestination);
            ((AbstractComponent)event.getComponent()).getStatistics().incSentReplyToEvent();
        }
        catch (Exception e)
        {
            throw new DispatchException(new org.mule.config.i18n.Message("jms", 8, ObjectUtils.toString(
                replyToDestination, "null")), returnMessage, null, e);
        }
        finally
        {
            connector.closeQuietly(replyToProducer);
            connector.closeQuietly(session);
        }
    }
}

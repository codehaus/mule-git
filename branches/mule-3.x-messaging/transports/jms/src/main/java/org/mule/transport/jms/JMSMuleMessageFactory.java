/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.transport.AbstractMuleMessageFactory;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public class JMSMuleMessageFactory extends AbstractMuleMessageFactory
{
    private String specification;

    public JMSMuleMessageFactory(MuleContext context)
    {
        super(context);
    }

    @Override
    protected Class<?>[] getSupportedTransportMessageTypes()
    {
        return new Class[]{Message.class};
    }

    @Override
    protected Object extractPayload(Object transportMessage) throws Exception
    {
        Message jmsMessage = (Message) transportMessage;

        // TODO MessageAdapterRemoval: what about encoding set on the endpoint?
        String encoding = muleContext.getConfiguration().getDefaultEncoding();
        return JmsMessageUtils.toObject(jmsMessage, specification, encoding);
    }

    @Override
    protected void addProperties(MuleMessage muleMessage, Object transportMessage)
    {
        Message jmsMessage = (Message) transportMessage;

        addCorrelationProperties(muleMessage, jmsMessage);
        addDeliveryModeProperty(muleMessage, jmsMessage);
        addDestinationProperty(muleMessage, jmsMessage);
        addExpirationProperty(muleMessage, jmsMessage);
        addMessageIdProperty(muleMessage, jmsMessage);
        addPriorityProperty(muleMessage, jmsMessage);
        addRedeliveredProperty(muleMessage, jmsMessage);
        addJMSReplyToPropertes(muleMessage, jmsMessage);
        addTimestampProperty(muleMessage, jmsMessage);
        addTypeProperty(muleMessage, jmsMessage);

        propagateJMSProperties(jmsMessage, muleMessage);
    }

    protected void propagateJMSProperties(Message jmsMessage, MuleMessage muleMessage)
    {
        try
        {
            Enumeration<?> e = jmsMessage.getPropertyNames();
            while (e.hasMoreElements())
            {
                String key = (String) e.nextElement();
                try
                {
                    Object value = jmsMessage.getObjectProperty(key);
                    if (value != null)
                    {
                        muleMessage.setProperty(key, value);
                    }
                }
                catch (JMSException e1)
                {
                    // ignored
                }
            }
        }
        catch (JMSException e1)
        {
            // ignored
        }
    }

    protected void addTypeProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            String value = jmsMessage.getJMSType();
            if (value != null)
            {
                muleMessage.setProperty(JmsConstants.JMS_TYPE, value);
            }
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addTimestampProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            long value = jmsMessage.getJMSTimestamp();
            muleMessage.setProperty(JmsConstants.JMS_TIMESTAMP, Long.valueOf(value));
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addJMSReplyToPropertes(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            Destination value = jmsMessage.getJMSReplyTo();
            if (value != null)
            {
                // Special handling of replyTo since it needs to go into the
                // invocation scope
                muleMessage.setReplyTo(value);
            }
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addRedeliveredProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            boolean value = jmsMessage.getJMSRedelivered();
            muleMessage.setProperty(JmsConstants.JMS_REDELIVERED, Boolean.valueOf(value));
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addPriorityProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            int value = jmsMessage.getJMSPriority();
            muleMessage.setProperty(JmsConstants.JMS_PRIORITY, Integer.valueOf(value));
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addMessageIdProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            String value = jmsMessage.getJMSMessageID();
            if (value != null)
            {
                muleMessage.setProperty(JmsConstants.JMS_MESSAGE_ID, value);
                muleMessage.setProperty(MuleProperties.MULE_MESSAGE_ID_PROPERTY, value);
            }
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addExpirationProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            long value = jmsMessage.getJMSExpiration();
            muleMessage.setProperty(JmsConstants.JMS_EXPIRATION, Long.valueOf(value));
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addDestinationProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            Destination value = jmsMessage.getJMSDestination();
            if (value != null)
            {
                muleMessage.setProperty(JmsConstants.JMS_DESTINATION, value);
            }
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addDeliveryModeProperty(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            int value = jmsMessage.getJMSDeliveryMode();
            muleMessage.setProperty(JmsConstants.JMS_DELIVERY_MODE, Integer.valueOf(value));
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    protected void addCorrelationProperties(MuleMessage muleMessage, Message jmsMessage)
    {
        try
        {
            String value = jmsMessage.getJMSCorrelationID();
            if (value != null)
            {
                muleMessage.setProperty(JmsConstants.JMS_CORRELATION_ID, value);
                // Map to the internal Mule property
                muleMessage.setCorrelationId(value);
            }
        }
        catch (JMSException e)
        {
            // ignored
        }
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }
}

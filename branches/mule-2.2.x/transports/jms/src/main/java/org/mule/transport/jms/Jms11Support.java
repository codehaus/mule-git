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

import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transaction.Transaction;
import org.mule.transaction.TransactionCoordination;
import org.mule.util.concurrent.WorkerThread;

import java.text.MessageFormat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>Jms11Support</code> is a template class to provide an abstraction to to
 * the JMS 1.1 API specification.
 */

public class Jms11Support implements JmsSupport
{

    /**
     * logger used by this class
     */
    protected final Log logger = LogFactory.getLog(getClass());

    protected JmsConnector connector;

    public Jms11Support(JmsConnector connector)
    {
        this.connector = connector;
    }

    public Connection createConnection(ConnectionFactory connectionFactory, 
                                       String username, 
                                       String password) throws JMSException
    {
        if (connectionFactory == null)
        {
            throw new IllegalArgumentException("connectionFactory cannot be null");
        }
        
        CreateConnectionThread thread = new CreateConnectionThread(connectionFactory, username, password);
        thread.start();
        try
        {
            thread.join(connector.getConnectionLostTimeout());
        }
        catch (InterruptedException e)
        {
            logger.warn("Timer interrupted: " + e.getMessage());
        }

        if (thread.isAlive())
        {
            throw new JMSException("Timed out while attempting to create a JMS connection");
        }
        else if (thread.getException() != null)
        {
            throw (JMSException) thread.getException();
        }
        else
        {
            return (Connection) thread.getResult();
        }
    }

    public Connection createConnection(ConnectionFactory connectionFactory) throws JMSException
    {
        return createConnection(connectionFactory, null, null);
    }

    class CreateConnectionThread extends WorkerThread
    {
        private ConnectionFactory connectionFactory;
        private String username;
        private String password;
        
        public CreateConnectionThread(ConnectionFactory connectionFactory, String username, String password)
        {
            super();
            this.connectionFactory = connectionFactory;
            this.username = username;
            this.password = password;
        }
        
        @Override
        protected void doWork() throws Exception
        {
            if (username != null)
            {
                setResult(connectionFactory.createConnection(username, password));
            }
            else
            {
                setResult(connectionFactory.createConnection());
            }
        }
    };
    
    public Session createSession(Connection connection,
                                 boolean topic,
                                 boolean transacted,
                                 int ackMode,
                                 boolean noLocal) throws JMSException
    {
        CreateSessionThread thread = new CreateSessionThread(connection, transacted, (transacted ? Session.SESSION_TRANSACTED : ackMode));

        // Creating the session in another thread does not work with XA for some reason
        Transaction tx = TransactionCoordination.getInstance().getTransaction();
        if (tx != null && tx.isXA())
        {
            // Just run in the same thread
            thread.run();
        }
        else
        {        
            thread.start();
            
            try
            {
                thread.join(connector.getConnectionLostTimeout());
            }
            catch (InterruptedException e)
            {
                logger.warn("Timer interrupted: " + e.getMessage());
            }
        }
        if (thread.isAlive())
        {
            throw new JMSException("Timed out while attempting to create a JMS session");
        }
        else if (thread.getException() != null)
        {
            throw (JMSException) thread.getException();
        }
        else
        {
            return (Session) thread.getResult();
        }
    }

    class CreateSessionThread extends WorkerThread
    {
        private Connection connection;
        private boolean transacted;
        private int acknowledgeMode;
        
        public CreateSessionThread(Connection connection, boolean transacted, int acknowledgeMode)
        {
            super();
            this.connection = connection;
            this.transacted = transacted;
            this.acknowledgeMode = acknowledgeMode;
        }
        
        @Override
        protected void doWork() throws Exception
        {
            setResult(connection.createSession(transacted, acknowledgeMode));
        }
    };
    
    public MessageProducer createProducer(Session session, Destination destination, boolean topic)
        throws JMSException
    {
        return session.createProducer(destination);
    }

    public MessageConsumer createConsumer(Session session, Destination destination, boolean topic, ImmutableEndpoint endpoint)
        throws JMSException
    {
        return createConsumer(session, destination, null, false, null, topic, endpoint);
    }

    public MessageConsumer createConsumer(Session session,
                                          Destination destination,
                                          String messageSelector,
                                          boolean noLocal,
                                          String durableName,
                                          boolean topic, ImmutableEndpoint endpoint) throws JMSException
    {
        if (durableName == null)
        {
            if (topic)
            {
                return session.createConsumer(destination, messageSelector, noLocal);
            }
            else
            {
                return session.createConsumer(destination, messageSelector);
            }
        }
        else
        {
            if (topic)
            {
                return session.createDurableSubscriber((Topic) destination, durableName, messageSelector,
                    noLocal);
            }
            else
            {
                throw new JMSException(
                    "A durable subscriber name was set but the destination was not a Topic");
            }
        }
    }

    public Destination createDestination(Session session, ImmutableEndpoint endpoint) throws JMSException
    {
        String address = endpoint.getEndpointURI().toString();
        if (address.contains(JmsConstants.TOPIC_PROPERTY + ":"))
        {
            // cut prefixes
            address = address.substring((connector.getProtocol() + "://" + JmsConstants.TOPIC_PROPERTY + ":").length());
            // cut any endpoint uri params, if any
            if (address.contains("?"))
            {
                address = address.substring(0, address.indexOf('?'));
            }
        }
        else
        {
            address = endpoint.getEndpointURI().getAddress();
        }
        return createDestination(session, address, connector.getTopicResolver().isTopic(endpoint), endpoint);
    }

    public Destination createDestination(Session session, String name, boolean topic, ImmutableEndpoint endpoint) throws JMSException
    {
        if (connector.isJndiDestinations())
        {
            try
            {
                Destination dest = getJndiDestination(name);
                if (dest != null)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug(MessageFormat.format("Destination {0} located in JNDI, will use it now", name));
                    }
                    return dest;
                }
                else
                {
                    throw new JMSException("JNDI destination not found with name: " + name);
                }
            }
            catch (JMSException e)
            {
                if (connector.isForceJndiDestinations())
                {
                    throw e;
                }
                else 
                {
                    logger.warn("Unable to look up JNDI destination " + name + ": " + e.getMessage());
                }
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Using non-JNDI destination " + name + ", will create one now");
        }

        if (session == null)
        {
            throw new IllegalArgumentException("Session cannot be null when creating a destination");
        }
        if (name == null)
        {
            throw new IllegalArgumentException("Destination name cannot be null when creating a destination");
        }

        if (topic)
        {
            return session.createTopic(name);
        }
        else
        {
            return session.createQueue(name);
        }
    }
    
    protected Destination getJndiDestination(String name) throws JMSException
    {
        Object temp;
        try
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(MessageFormat.format("Looking up {0} from JNDI", name));
            }
            temp = connector.lookupFromJndi(name);
        }
        catch (NamingException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug(e);
            }
            String message = MessageFormat.format("Failed to look up destination {0}. Reason: {1}",
                                                  name, e.getMessage());
            throw new JMSException(message);
        }
        
        if (temp != null)
        {
            if (temp instanceof Destination)
            {
                return (Destination) temp;
            }
        }
        return null;
    }

    public Destination createTemporaryDestination(Session session, boolean topic) throws JMSException
    {
        if (session == null)
        {
            throw new IllegalArgumentException("Session cannot be null when creating a destination");
        }

        if (topic)
        {
            return session.createTemporaryTopic();
        }
        else
        {
            return session.createTemporaryQueue();
        }
    }

    public void send(MessageProducer producer, Message message, boolean topic, ImmutableEndpoint endpoint) throws JMSException
    {
        send(producer, message, connector.isPersistentDelivery(), Message.DEFAULT_PRIORITY,
            Message.DEFAULT_TIME_TO_LIVE, topic, endpoint);
    }

    public void send(MessageProducer producer, Message message, Destination dest, boolean topic, ImmutableEndpoint endpoint)
        throws JMSException
    {
        send(producer, message, dest, connector.isPersistentDelivery(), Message.DEFAULT_PRIORITY,
            Message.DEFAULT_TIME_TO_LIVE, topic, endpoint);
    }

    public void send(MessageProducer producer,
                     Message message,
                     boolean persistent,
                     int priority,
                     long ttl,
                     boolean topic, ImmutableEndpoint endpoint) throws JMSException
    {
        producer.send(message, (persistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT),
            priority, ttl);
    }

    public void send(MessageProducer producer,
                     Message message,
                     Destination dest,
                     boolean persistent,
                     int priority,
                     long ttl,
                     boolean topic, ImmutableEndpoint endpoint) throws JMSException
    {
        producer.send(dest, message, (persistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT),
            priority, ttl);
    }

}

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

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.XAConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang.UnhandledException;
import org.mule.MuleManager;
import org.mule.MuleRuntimeException;
import org.mule.config.ExceptionHelper;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.impl.internal.notifications.ConnectionNotification;
import org.mule.impl.internal.notifications.ConnectionNotificationListener;
import org.mule.impl.internal.notifications.NotificationException;
import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.providers.ConnectException;
import org.mule.providers.ConnectionStrategy;
import org.mule.providers.FatalConnectException;
import org.mule.providers.ReplyToHandler;
import org.mule.providers.jms.xa.ConnectionFactoryWrapper;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.TransactionException;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOTransaction;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.endpoint.UMOImmutableEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.util.BeanUtils;
import org.mule.util.ClassUtils;

import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;

/**
 * <code>JmsConnector</code> is a JMS 1.0.2b compliant connector that can be
 * used by a Mule endpoint. The connector supports all Jms functionality
 * including, topics and queues, durable subscribers, acknowledgement modes,
 * loacal transactions
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @author Guillaume Nodet
 * @version $Revision$
 */

public class JmsConnector extends AbstractServiceEnabledConnector implements ConnectionNotificationListener
{
    /* Register the Jms Exception reader if this class gets loaded*/
    static {
        ExceptionHelper.registerExceptionReader(new JmsExceptionReader());
    }

    private String connectionFactoryJndiName;

    private ConnectionFactory connectionFactory;

    private String connectionFactoryClass;

    private String jndiInitialFactory;

    private String jndiProviderUrl;

    private int acknowledgementMode = Session.AUTO_ACKNOWLEDGE;

    private String clientId;

    private boolean durable;

    private boolean noLocal;

    private boolean persistentDelivery;

    private Map jndiProviderProperties;

    private Map connectionFactoryProperties;

    private Connection connection;

    private String specification = JmsConstants.JMS_SPECIFICATION_102B;

    private JmsSupport jmsSupport;

    private Context jndiContext;

    private boolean jndiDestinations = false;

    private boolean forceJndiDestinations = false;

    public String username = null;

    public String password = null;

    private int maxRedelivery = 0;

    private String redeliveryHandler = DefaultRedeliveryHandler.class.getName();

    private boolean cacheJmsSessions = false;

    private boolean recoverJmsConnections = true;

    public JmsConnector()
    {
        receivers = new ConcurrentHashMap();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.UMOConnector#create(java.util.HashMap)
     */
    public void doInitialise() throws InitialisationException
    {
        // when a connection to jms server is lost, we are recycling the connector
        // need to safely reinitialise these here for proper connection recovery
        if (dispatchers == null) {
            dispatchers = new ConcurrentHashMap();
        }
        if (receivers == null) {
            receivers = new ConcurrentHashMap();
        }

        super.doInitialise();
        try {
            MuleManager.getInstance().registerListener(this, getName());
        } catch (NotificationException nex) {
            throw new InitialisationException(nex, this);
        }
    }

    protected void initJndiContext() throws NamingException, InitialisationException
    {
        if (jndiContext == null) {
            Hashtable props = new Hashtable();

            if (jndiInitialFactory != null) {
                props.put(Context.INITIAL_CONTEXT_FACTORY, jndiInitialFactory);
            } else if (jndiProviderProperties == null || !jndiProviderProperties.containsKey(Context.INITIAL_CONTEXT_FACTORY)) {
                throw new InitialisationException(new Message(Messages.X_IS_NULL, "jndiInitialFactory"), this);
            }

            if (jndiProviderUrl != null) {
                props.put(Context.PROVIDER_URL, jndiProviderUrl);
            }

            if (jndiProviderProperties != null) {
                props.putAll(jndiProviderProperties);
            }
            jndiContext = new InitialContext(props);
        }
    }

    protected void setConnection(Connection connection)
    {
        this.connection = connection;
    }

     protected ConnectionFactory createConnectionFactory() throws InitialisationException, NamingException
    {

        Object temp = jndiContext.lookup(connectionFactoryJndiName);

        if (temp instanceof ConnectionFactory) {
            return (ConnectionFactory) temp;
        } else {
            throw new InitialisationException(new Message(Messages.JNDI_RESOURCE_X_NOT_FOUND, connectionFactoryJndiName),
                                              this);
        }
    }

    protected Connection createConnection() throws NamingException, JMSException, InitialisationException
    {
        Connection connection;
        if (connectionFactory == null) {
            connectionFactory = createConnectionFactory();
        }
        if (connectionFactory != null && connectionFactory instanceof XAConnectionFactory) {
            if (MuleManager.getInstance().getTransactionManager() != null) {
                connectionFactory = new ConnectionFactoryWrapper(connectionFactory,
                                                                 MuleManager.getInstance().getTransactionManager());
            }
        }

        if (username != null) {
            connection = jmsSupport.createConnection(connectionFactory, username, password);
        } else {
            connection = jmsSupport.createConnection(connectionFactory);
        }

        if (clientId != null) {
            connection.setClientID(getClientId());
        }

        // Register a JMS exception listener to detect failed connections.
        // Existing connection strategy will be used to recover.

        final ConnectionStrategy connectionStrategy = getConnectionStrategy();
        if (recoverJmsConnections &&
                connectionStrategy != null &&
                connection != null) {
            connection.setExceptionListener(new ExceptionListener() {
                public void onException(JMSException jmsException) {
                    logger.debug("About to recycle myself due to remote JMS connection shutdown.");
                    final JmsConnector jmsConnector = JmsConnector.this;
                    try {
                        jmsConnector.stopConnector();
                        jmsConnector.initialised.set(false);
                    } catch (UMOException e) {
                        logger.warn(e.getMessage(), e);
                    }

                    try {
                        connectionStrategy.connect(jmsConnector);
                        jmsConnector.initialise();
                        jmsConnector.startConnector();
                    } catch (FatalConnectException fcex) {
                        logger.fatal("Failed to reconnect to JMS server. I'm giving up.");
                    } catch (UMOException umoex) {
                        throw new UnhandledException("Failed to recover a connector.", umoex);
                    }
                }
            });
        }

        return connection;
    }


    public void doConnect() throws ConnectException
    {
        try {
            // have to instanciate it here, and not earlier in MuleXmlConfigurationBuilder, as
            // native factory may initiate immediate connections, and that is not what we
            // want if the descriptor's initial state is paused.
            if (connectionFactoryClass != null) {
                connectionFactory = (ConnectionFactory) ClassUtils.instanciateClass(connectionFactoryClass,
                                                                                    ClassUtils.NO_ARGS);
            }

            // If we have a connection factory, there is no need to initialise
            // the JndiContext
            if (connectionFactory == null || jndiInitialFactory != null) {
                initJndiContext();
            } else {
                // Set these to false so that the jndiContext
                // will not be used by the JmsSupport classes
                jndiDestinations = false;
                forceJndiDestinations = false;
            }

            if(jmsSupport==null) {
                if (JmsConstants.JMS_SPECIFICATION_102B.equals(specification)) {
                    jmsSupport = new Jms102bSupport(this, jndiContext, jndiDestinations, forceJndiDestinations);
                } else {
                    jmsSupport = new Jms11Support(this, jndiContext, jndiDestinations, forceJndiDestinations);
                }
            }
            if (connectionFactory == null) {
                connectionFactory = createConnectionFactory();
            }
            if (connectionFactoryProperties != null && !connectionFactoryProperties.isEmpty()) {
                // apply connection factory properties
                BeanUtils.populateWithoutFail(connectionFactory, connectionFactoryProperties, true);
            }
        } catch (Exception e) {
            throw new ConnectException(new Message(Messages.FAILED_TO_CREATE_X, "Jms Connector"), e, this);
        }

        try {
            connection = createConnection();
            if (started.get()) {
                connection.start();
            }
        } catch (Exception e) {
            throw new ConnectException(e, this);
        }
    }

    public void doDisconnect() throws ConnectException
    {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new ConnectException(e, this);
        } finally {
            //connectionFactory = null;
            connection = null;
        }
    }

    protected Object getReceiverKey(UMOComponent component, UMOEndpoint endpoint)
    {
        return component.getDescriptor().getName() + "~" + endpoint.getEndpointURI().getAddress();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.TransactionEnabledConnector#getSessionFactory(org.mule.umo.endpoint.UMOEndpoint)
     */
    public Object getSessionFactory(UMOEndpoint endpoint)
    {
        if (endpoint.getTransactionConfig() != null
                && endpoint.getTransactionConfig().getFactory() instanceof JmsClientAcknowledgeTransactionFactory) {
            throw new MuleRuntimeException(new org.mule.config.i18n.Message("jms", 9));
        } else {
            return connection;
        }
    }

    public Session getSessionFromTransaction()
    {
        UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
        if (tx != null) {
            if (tx.hasResource(connection)) {
                return (Session) tx.getResource(connection);
            }
        }
        return null;
    }

    public Session getSession(boolean transacted, boolean topic) throws JMSException
    {
        if(!isConnected()) {
            throw new JMSException("Not connected");
        }
        UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
        Session session = getSessionFromTransaction();
        if (session != null) {
            logger.debug("Retrieving jms session from current transaction");
            return session;
        }
        if(logger.isDebugEnabled()) {
            logger.debug("Retrieving new jms session from connection: topic=" + topic + ", transacted=" + (transacted || tx != null) + ", ack mode="+ acknowledgementMode + ", nolocal=" + noLocal);
        }

        session = jmsSupport.createSession(connection, topic, transacted || tx != null, acknowledgementMode, noLocal);
        if (tx != null) {
            logger.debug("Binding session to current transaction");
            try {
                tx.bindResource(connection, session);
            } catch (TransactionException e) {
                throw new RuntimeException("Could not bind session to current transaction", e);
            }
        }
        return session;
    }



    public void doStart() throws UMOException
    {
        if (connection != null) {
            try {
                connection.start();
            } catch (JMSException e) {
                throw new LifecycleException(new Message(Messages.FAILED_TO_START_X, "Jms Connection"), e);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.UMOConnector#getProtocol()
     */
    public String getProtocol()
    {
        return "jms";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.mule.providers.AbstractConnector#doDispose()
     */
    protected void doDispose()
    {
        super.doDispose();
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                logger.error("Jms connector failed to dispose properly: ", e);
            }
            connection = null;
        }
        if (jndiContext != null) {
            try {
                jndiContext.close();
            } catch (NamingException e) {
                logger.error("Jms connector failed to dispose properly: ", e);
            }
            // need this line to flag for reinitialization in ConnectionStrategy
            jndiContext = null;
        }
    }

    /**
     * @return Returns the acknowledgeMode.
     */
    public int getAcknowledgementMode()
    {
        return acknowledgementMode;
    }

    /**
     * @param acknowledgementMode The acknowledgementMode to set.
     */
    public void setAcknowledgementMode(int acknowledgementMode)
    {
        this.acknowledgementMode = acknowledgementMode;
    }

    /**
     * @return Returns the connectionFactoryJndiName.
     */
    public String getConnectionFactoryJndiName()
    {
        return connectionFactoryJndiName;
    }

    /**
     * @param connectionFactoryJndiName The connectionFactoryJndiName to set.
     */
    public void setConnectionFactoryJndiName(String connectionFactoryJndiName)
    {
        this.connectionFactoryJndiName = connectionFactoryJndiName;
    }

    /**
     * @return Returns the durable.
     */
    public boolean isDurable()
    {
        return durable;
    }

    /**
     * @param durable The durable to set.
     */
    public void setDurable(boolean durable)
    {
        this.durable = durable;
    }

    /**
     * @return Returns the noLocal.
     */
    public boolean isNoLocal()
    {
        return noLocal;
    }

    /**
     * @param noLocal The noLocal to set.
     */
    public void setNoLocal(boolean noLocal)
    {
        this.noLocal = noLocal;
    }

    /**
     * @return Returns the persistentDelivery.
     */
    public boolean isPersistentDelivery()
    {
        return persistentDelivery;
    }

    /**
     * @param persistentDelivery The persistentDelivery to set.
     */
    public void setPersistentDelivery(boolean persistentDelivery)
    {
        this.persistentDelivery = persistentDelivery;
    }


    /**
     * @return Returns the JNDI providerProperties.
     * @since 1.1
     */
    public Map getJndiProviderProperties()
    {
        return jndiProviderProperties;
    }

    /**
     * @param jndiProviderProperties The JNDI providerProperties to set.
     * @since 1.1
     */
    public void setJndiProviderProperties(final Map jndiProviderProperties)
    {
        this.jndiProviderProperties = jndiProviderProperties;
    }

    /**
     * @return Returns underlying connection factory properties.
     */
    public Map getConnectionFactoryProperties()
    {
        return connectionFactoryProperties;
    }

    /**
     * @param connectionFactoryProperties properties to be set
     *        on the underlying ConnectionFactory.
     */
    public void setConnectionFactoryProperties(final Map connectionFactoryProperties)
    {
        this.connectionFactoryProperties = connectionFactoryProperties;
    }

    public String getJndiInitialFactory()
    {
        return jndiInitialFactory;
    }

    public void setJndiInitialFactory(String jndiInitialFactory)
    {
        this.jndiInitialFactory = jndiInitialFactory;
    }

    public String getJndiProviderUrl()
    {
        return jndiProviderUrl;
    }

    public void setJndiProviderUrl(String jndiProviderUrl)
    {
        this.jndiProviderUrl = jndiProviderUrl;
    }

    public Session getSession(UMOImmutableEndpoint endpoint) throws Exception
    {
        String resourceInfo = endpoint.getEndpointURI().getResourceInfo();
        boolean topic = (resourceInfo != null && JmsConstants.TOPIC_PROPERTY.equalsIgnoreCase(resourceInfo));
        return getSession(endpoint.getTransactionConfig().isTransacted(), topic);
    }

    public ConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }


    public String getConnectionFactoryClass() {
        return connectionFactoryClass;
    }

    public void setConnectionFactoryClass(String connectionFactoryClass) {
        this.connectionFactoryClass = connectionFactoryClass;
    }

    public JmsSupport getJmsSupport()
    {
        return jmsSupport;
    }

    public void setJmsSupport(JmsSupport jmsSupport)
    {
        this.jmsSupport = jmsSupport;
    }

    public String getSpecification()
    {
        return specification;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public boolean isJndiDestinations()
    {
        return jndiDestinations;
    }

    public void setJndiDestinations(boolean jndiDestinations)
    {
        this.jndiDestinations = jndiDestinations;
    }

    public boolean isForceJndiDestinations()
    {
        return forceJndiDestinations;
    }

    public void setForceJndiDestinations(boolean forceJndiDestinations)
    {
        this.forceJndiDestinations = forceJndiDestinations;
    }

    public Context getJndiContext()
    {
        return jndiContext;
    }

    public void setJndiContext(Context jndiContext)
    {
        this.jndiContext = jndiContext;
    }

    public void setRecoverJmsConnections(boolean recover)
    {
        this.recoverJmsConnections = recover;
    }

    public boolean isRecoverJmsConnections()
    {
        return this.recoverJmsConnections;
    }

    protected RedeliveryHandler createRedeliveryHandler() throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, ClassNotFoundException
    {
        if (redeliveryHandler != null) {
            return (RedeliveryHandler) ClassUtils.instanciateClass(redeliveryHandler, ClassUtils.NO_ARGS);
        } else {
            return new DefaultRedeliveryHandler();
        }
    }

    public ReplyToHandler getReplyToHandler()
    {
        return new JmsReplyToHandler(this, defaultResponseTransformer);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return Returns the connection.
     */
    public Connection getConnection()
    {
        return connection;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public int getMaxRedelivery()
    {
        return maxRedelivery;
    }

    public void setMaxRedelivery(int maxRedelivery)
    {
        this.maxRedelivery = maxRedelivery;
    }

    public String getRedeliveryHandler()
    {
        return redeliveryHandler;
    }

    public void setRedeliveryHandler(String redeliveryHandler)
    {
        this.redeliveryHandler = redeliveryHandler;
    }

    public boolean isRemoteSyncEnabled() {
        return true;
    }

    public void onNotification(UMOServerNotification notification) {
        if(notification.getAction() == ConnectionNotification.CONNECTION_DISCONNECTED ||
                notification.getAction() == ConnectionNotification.CONNECTION_FAILED) {
            //Remove all dispatchers as any cached session will be invalidated
            disposeDispatchers();
            // TODO should we dispose receivers here as well (in case they are transactional)
            // gives a harmless NPE at AbstractConnector.connect(AbstractConnector.java:927)
            //disposeReceivers();
        }
    }

    public boolean isCacheJmsSessions() {
        return cacheJmsSessions;
    }

    public void setCacheJmsSessions(boolean cacheJmsSessions) {
        this.cacheJmsSessions = cacheJmsSessions;
    }

    /**
     * This method may be overridden in case a certain JMS implementation does not support all the standard JMS properties.
     */
    public boolean supportsProperty(String property) {
        return true;
    }

    /** This method may be overridden in order to apply pre-processing to the message
     * as soon as it arrives.
     * @param message - the incoming message
     * @param session - the JMS session
     * @return the preprocessed message
     */
    public javax.jms.Message preProcessMessage(javax.jms.Message message, Session session) throws Exception {
        return message;
    }

    /**
     * Closes the MessageProducer
     * @param producer
     * @throws JMSException
     */
    public void close(MessageProducer producer) throws JMSException {
        if (producer != null) {
            producer.close();
        }
    }

    /**
     * Closes the MessageProducer without throwing an exception
     * (an error message is logged instead).
     * @param producer
     */
    public void closeQuietly(MessageProducer producer) {
        try {
            close(producer);
        }
        catch (JMSException e) {
            logger.error("Failed to close jms message producer", e);
        }
    }

    /**
     * Closes the MessageConsumer
     * @param consumer
     * @throws JMSException
     */
    public void close(MessageConsumer consumer) throws JMSException {
        if (consumer != null) {
            consumer.close();
        }
    }

    /**
     * Closes the MessageConsumer without throwing an exception
     * (an error message is logged instead).
     * @param consumer
     */
    public void closeQuietly(MessageConsumer consumer) {
        try {
            close(consumer);
        }
        catch (JMSException e) {
            logger.error("Failed to close jms message consumer", e);
        }
    }

    /**
     * Closes the Session
     * @param session
     * @throws JMSException
     */
    public void close(Session session) throws JMSException {
        if (session != null) {
            session.close();
        }
    }

    /**
     * Closes the Session without throwing an exception
     * (an error message is logged instead).
     * @param session
     */
    public void closeQuietly(Session session) {
        try {
            close(session);
        }
        catch (JMSException e) {
            logger.error("Failed to close jms session consumer", e);
        }
    }

    /**
     * Closes the TemporaryQueue
     * @param tempQueue
     * @throws JMSException
     */
    public void close(TemporaryQueue tempQueue) throws JMSException {
        if (tempQueue != null) {
            tempQueue.delete();
        }
    }

    /**
     * Closes the TemporaryQueue without throwing an exception
     * (an error message is logged instead).
     * @param tempQueue
     */
    public void closeQuietly(TemporaryQueue tempQueue) {
        try {
            close(tempQueue);
        }
        catch (JMSException e) {
            if (logger.isErrorEnabled()) {
                String queueName = "";
                try {
                    queueName = tempQueue.getQueueName();
                } catch (JMSException innerEx) {
                    // ignore, we are just trying to get the queue name
                }
                logger.error("Faled to delete a temporary queue " + queueName, e);
            }
        }
    }

    /**
     * Closes the TemporaryTopic
     * @param tempTopic
     * @throws JMSException
     */
    public void close(TemporaryTopic tempTopic) throws JMSException {
        if (tempTopic != null) {
            tempTopic.delete();
        }
    }

    /**
     * Closes the TemporaryTopic without throwing an exception
     * (an error message is logged instead).
     * @param tempTopic
     */
    public void closeQuietly(TemporaryTopic tempTopic) {
        try {
            close(tempTopic);
        }
        catch (JMSException e) {
            if (logger.isErrorEnabled()) {
                String topicName = "";
                try {
                    topicName = tempTopic.getTopicName();
                } catch (JMSException innerEx) {
                    // ignore, we are just trying to get the topic name
                }
                logger.error("Faled to delete a temporary topic " + topicName, e);
            }
        }
    }
}

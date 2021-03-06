/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.mule.providers.jms;


import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;
import org.mule.MuleManager;
import org.mule.MuleRuntimeException;
import org.mule.config.i18n.Message;
import org.mule.config.i18n.Messages;
import org.mule.providers.AbstractServiceEnabledConnector;
import org.mule.providers.ReplyToHandler;
import org.mule.providers.jms.xa.ConnectionFactoryWrapper;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.TransactionException;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOTransaction;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.util.ClassHelper;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;


/**
 * <code>JmsConnector</code> is a JMS 1.0.2b compliant connector that can be used by a Mule
 * endpoint.  The connector supports all Jms functionality including, topics and queues, durable
 * subscribers, acknowledgement modes, loacal transactions
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @author Guillaume Nodet
 * @version $Revision$
 */


public class JmsConnector extends AbstractServiceEnabledConnector
{

    public static final String JMS_SELECTOR_PROPERTY = "selector";
    public static final String JMS_SPECIFICATION_102B = "1.0.2b";
    public static final String JMS_SPECIFICATION_11 = "1.1";

    private String connectionFactoryJndiName;

    private ConnectionFactory connectionFactory;

    private String jndiInitialFactory;

    private String jndiProviderUrl;

    private int acknowledgementMode = Session.AUTO_ACKNOWLEDGE;

    private String clientId;

    private boolean durable;

    private boolean noLocal;

    private boolean persistentDelivery;

    private Map providerProperties;

    private Connection connection;

    private String specification = JMS_SPECIFICATION_102B;

    private JmsSupport jmsSupport;

    private Context jndiContext;

    private boolean jndiDestinations = false;

    private boolean forceJndiDestinations = false;

    public String username = null;

    public String password = null;

    private int maxRedelivery = 0;

    private String redeliveryHandler = DefaultRedeliveryHandler.class.getName();

    public JmsConnector()
    {
        receivers = new ConcurrentHashMap();
    }

    /* (non-Javadoc)
     * @see org.mule.providers.UMOConnector#create(java.util.HashMap)
     */
    public void doInitialise() throws InitialisationException
    {
        super.doInitialise();
        try
        {
        	setDisposeDispatcherOnCompletion(true);
            //If we have a connection factory, there is no need to initialise
            //the JndiContext
            if(connectionFactory==null || (connectionFactory!=null && jndiInitialFactory!=null)) {
                initJndiContext();
            } else {
                //Set these to false so that the jndiContext
                //will not be used by the JmsSupport classes
                jndiDestinations = false;
                forceJndiDestinations = false;
            }

            if(JMS_SPECIFICATION_102B.equals(specification)) {
                jmsSupport = new Jms102bSupport(this, jndiContext, jndiDestinations, forceJndiDestinations);
            } else {
                jmsSupport = new Jms11Support(this, jndiContext, jndiDestinations, forceJndiDestinations);
            }
            connection = createConnection();
        } catch (Exception e)
        {
            throw new InitialisationException(new Message(Messages.FAILED_TO_CREATE_X, "Jms Connector"), e, this);
        }
    }

    protected void initJndiContext() throws NamingException, InitialisationException
    {
        if(jndiContext==null) {
            Hashtable props = new Hashtable();

            if (jndiInitialFactory != null) {
                props.put(Context.INITIAL_CONTEXT_FACTORY, jndiInitialFactory);
            } else if(providerProperties==null || !providerProperties.containsKey(Context.INITIAL_CONTEXT_FACTORY)) {
                throw new InitialisationException(new Message(Messages.X_IS_NULL, "jndiInitialFactory"), this);
            }

            if (jndiProviderUrl != null)
                props.put(Context.PROVIDER_URL, jndiProviderUrl);

            if (providerProperties != null)
            {
                props.putAll(providerProperties);
            }
            jndiContext = new InitialContext(props);
        }
    }

    protected void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected ConnectionFactory createConnectionFactory() throws InitialisationException, NamingException
    {

        Object temp =  jndiContext.lookup(connectionFactoryJndiName);

        if(temp instanceof ConnectionFactory)
        {
            return (ConnectionFactory)temp;
        } else {
            throw new InitialisationException(new Message(Messages.JNDI_RESOURCE_X_NOT_FOUND, connectionFactoryJndiName), this);
        }
    }

    protected Connection createConnection() throws NamingException, JMSException, InitialisationException
    {
        Connection connection = null;
        if(connectionFactory==null)
        {
            connectionFactory = createConnectionFactory();
        }
        if (connectionFactory != null && connectionFactory instanceof XAConnectionFactory) {
        	if (MuleManager.getInstance().getTransactionManager() != null) {
        		connectionFactory = new ConnectionFactoryWrapper((XAConnectionFactory) connectionFactory, MuleManager.getInstance().getTransactionManager());
        	}
        }

        if(username!=null) {
            connection = jmsSupport.createConnection(connectionFactory, username, password);
        } else {
            connection = jmsSupport.createConnection(connectionFactory);
        }

        if (clientId!=null) {
            connection.setClientID(getClientId());
        }
        return connection;
    }

    protected Object getReceiverKey(UMOComponent component, UMOEndpoint endpoint)
    {
        return component.getDescriptor().getName() + "~" + endpoint.getEndpointURI().getAddress();
    }

	/* (non-Javadoc)
	 * @see org.mule.providers.TransactionEnabledConnector#getSessionFactory(org.mule.umo.endpoint.UMOEndpoint)
	 */
    public Object getSessionFactory(UMOEndpoint endpoint) {
    	if (endpoint.getTransactionConfig() != null &&
    		endpoint.getTransactionConfig().getFactory() instanceof JmsClientAcknowledgeTransactionFactory) {
    		throw new MuleRuntimeException(new org.mule.config.i18n.Message("jms", 9));
    	} else {
    		return connection;
    	}
    }
    
    public Session getSession(boolean transacted) throws JMSException
    {
		UMOTransaction tx = TransactionCoordination.getInstance().getTransaction();
		if (tx != null) {
			if (tx.hasResource(connection)) {
				logger.debug("Retrieving jms session from current transaction");
				return (Session) tx.getResource(connection);
			}
		}
		logger.debug("Retrieving new jms session from connection");
    	Session session = jmsSupport.createSession(connection, transacted, acknowledgementMode, noLocal);
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

    public void stopConnector() throws UMOException
    {
        try
        {
            connection.stop();
        }
        catch (JMSException e)
        {
            throw new LifecycleException(new Message(Messages.FAILED_TO_STOP_X, "Jms Connection"), e);
        }
    }


    public void startConnector() throws UMOException
    {
        try
        {
            connection.start();
        }
        catch (JMSException e)
        {
            throw new LifecycleException(new Message(Messages.FAILED_TO_START_X, "Jms Connection"), e);
        }
    }

    /* (non-Javadoc)
     * @see org.mule.providers.UMOConnector#getProtocol()
     */
    public String getProtocol()
    {
        return "jms";
    }

    /* (non-Javadoc)
     * @see org.mule.providers.AbstractConnector#disposeConnector()
     */
    protected void disposeConnector()
    {
        try
        {
            if(connection!=null) connection.close();
        }
        catch (JMSException e)
        {
            logger.error("Jms connector failed to close properly: " + e);
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
     * @return Returns the endpointProperties.
     */
    public Map getProviderProperties()
    {
        return providerProperties;
    }


    /**
     * @param endpointProperties The endpointProperties to set.
     */
    public void setProviderProperties(Map endpointProperties)
    {
        this.providerProperties = endpointProperties;
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

    public Object getSession(UMOEndpoint endpoint) throws Exception
    {
        return getSession(endpoint.getTransactionConfig().isTransacted());
    }

    public ConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
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

    protected RedeliveryHandler createRedeliveryHandler() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        if(redeliveryHandler!=null) {
            return (RedeliveryHandler)ClassHelper.instanciateClass(redeliveryHandler, ClassHelper.NO_ARGS);
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
	public Connection getConnection() {
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

    public int getMaxRedelivery() {
        return maxRedelivery;
    }

    public void setMaxRedelivery(int maxRedelivery) {
        this.maxRedelivery = maxRedelivery;
    }

    public String getRedeliveryHandler() {
        return redeliveryHandler;
    }

    public void setRedeliveryHandler(String redeliveryHandler) {
        this.redeliveryHandler = redeliveryHandler;
    }
}

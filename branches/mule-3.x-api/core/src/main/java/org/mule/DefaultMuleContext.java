/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleRuntimeException;
import org.mule.api.config.MuleConfiguration;
import org.mule.api.config.MuleProperties;
import org.mule.api.config.ThreadingProfile;
import org.mule.api.context.MuleContextAware;
import org.mule.api.context.WorkManager;
import org.mule.api.context.notification.ServerNotification;
import org.mule.api.context.notification.ServerNotificationListener;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.LifecycleManager;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.registry.MuleRegistry;
import org.mule.api.registry.RegistrationException;
import org.mule.api.registry.Registry;
import org.mule.api.security.SecurityManager;
import org.mule.api.transaction.TransactionManagerFactory;
import org.mule.config.DefaultMuleConfiguration;
import org.mule.config.i18n.CoreMessages;
import org.mule.context.notification.MuleContextNotification;
import org.mule.context.notification.NotificationException;
import org.mule.context.notification.ServerNotificationManager;
import org.mule.expression.DefaultExpressionManager;
import org.mule.lifecycle.MuleContextLifecycleManager;
import org.mule.management.stats.AllStatistics;
import org.mule.registry.DefaultRegistryBroker;
import org.mule.registry.MuleRegistryHelper;
import org.mule.util.ServerShutdownSplashScreen;
import org.mule.util.ServerStartupSplashScreen;
import org.mule.util.SplashScreen;
import org.mule.util.queue.QueueManager;

import java.util.Collection;

import javax.resource.spi.work.WorkListener;
import javax.transaction.TransactionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultMuleContext implements MuleContext
{
    /**
     * logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(DefaultMuleContext.class);

    /**
     * Internal registry facade which delegates to other registries.
     */
    private DefaultRegistryBroker registryBroker;

    /**
     * Simplified Mule configuration interface
     */
    private MuleRegistry muleRegistryHelper;

    /**
     * stats used for management
     */
    private AllStatistics stats = new AllStatistics();

    private WorkManager workManager;

    private WorkListener workListener;

    /**
     * LifecycleManager for the MuleContext.  Note: this is NOT the same lifecycle manager
     * as the one in the Registry.
     */
    protected MuleContextLifecycleManager lifecycleManager;

    protected ServerNotificationManager notificationManager;

    private MuleConfiguration config;

    /**
     * the date in milliseconds from when the server was started
     */
    private long startDate;

    private ExpressionManager expressionManager;

    private static MuleContext staticInstance;

    private SplashScreen startupScreen;
    private SplashScreen shutdownScreen;

    public DefaultMuleContext(MuleConfiguration config,
                              WorkManager workManager,
                              WorkListener workListener,
                              MuleContextLifecycleManager lifecycleManager,
                              ServerNotificationManager notificationManager)
    {
        this.config = config;
        ((MuleContextAware) config).setMuleContext(this);
        this.workManager = workManager;
        this.workListener = workListener;
        this.lifecycleManager = lifecycleManager;
        this.notificationManager = notificationManager;
        this.notificationManager.setMuleContext(this);
        //there is no point having this object configurable
        this.expressionManager = new DefaultExpressionManager();

        registryBroker = createRegistryBroker();
        muleRegistryHelper = createRegistryHelper(registryBroker);

        //TODO URGENT remove - currently used by the MuleClient only
        staticInstance = this;
    }

    public static MuleContext getContext()
    {
        return staticInstance;
    }

    protected DefaultRegistryBroker createRegistryBroker()
    {
        return new DefaultRegistryBroker(this);
    }

    protected MuleRegistry createRegistryHelper(DefaultRegistryBroker registry)
    {
        return new MuleRegistryHelper(registry, this);
    }

    public void setSplash(SplashScreen startup, SplashScreen shutdown)
    {
        startupScreen = startup;
        shutdownScreen = shutdown;
    }

    protected void initSplashScreens()
    {
        if (startupScreen == null)
        {
            startupScreen = SplashScreen.getInstance(ServerStartupSplashScreen.class);
        }

        if (shutdownScreen == null)
        {
            shutdownScreen = SplashScreen.getInstance(ServerShutdownSplashScreen.class);
        }

        startupScreen.setHeader(this);
        startupScreen.setFooter(this);
        shutdownScreen.setHeader(this);
        shutdownScreen.setFooter(this);
    }

    public synchronized void initialise() throws InitialisationException
    {
        lifecycleManager.checkPhase(Initialisable.PHASE_NAME);

        if (getNotificationManager() == null)
        {
            throw new MuleRuntimeException(
                    CoreMessages.objectIsNull(MuleProperties.OBJECT_NOTIFICATION_MANAGER));
        }
        if (workManager == null)
        {
            throw new MuleRuntimeException(CoreMessages.objectIsNull("workManager"));
        }

        try
        {


            // Initialize internal registries
            muleRegistryHelper.initialise();

            //We need to start the work manager straight away since we need it to fire notifications
            if (workManager instanceof MuleContextAware)
            {
                MuleContextAware contextAware = (MuleContextAware) workManager;
                contextAware.setMuleContext(this);
            }

            workManager.start();
            getNotificationManager().start(workManager, workListener);
            fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_INITIALISING));
            getLifecycleManager().fireLifecycle(Initialisable.PHASE_NAME);

            fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_INITIALISED));

            initSplashScreens();
        }
        catch (Exception e)
        {
            throw new InitialisationException(e, this);
        }
    }

    public synchronized void start() throws MuleException
    {
        lifecycleManager.checkPhase(Startable.PHASE_NAME);

        if (getSecurityManager() == null)
        {
            throw new MuleRuntimeException(CoreMessages.objectIsNull("securityManager"));
        }
        if (getQueueManager() == null)
        {
            throw new MuleRuntimeException(CoreMessages.objectIsNull("queueManager"));
        }

        startDate = System.currentTimeMillis();

        fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_STARTING));
        getLifecycleManager().fireLifecycle(Startable.PHASE_NAME);


        fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_STARTED));

        if (logger.isInfoEnabled())
        {
            logger.info(startupScreen.toString());
        }
    }

    /**
     * Stops the <code>MuleContext</code> which stops all sessions and
     * connectors
     *
     * @throws MuleException if either any of the sessions or connectors fail to stop
     */
    public synchronized void stop() throws MuleException
    {
        lifecycleManager.checkPhase(Stoppable.PHASE_NAME);
        fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_STOPPING));
        lifecycleManager.fireLifecycle(Stoppable.PHASE_NAME);
        fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_STOPPED));
    }

    public synchronized void dispose()
    {
        lifecycleManager.checkPhase(Disposable.PHASE_NAME);

        fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_DISPOSING));

        try
        {
            getLifecycleManager().fireLifecycle(Disposable.PHASE_NAME);

            // THis is a little odd. I find the relationship between the MuleRegistry Helper and the registry broker, too much abstraction?
            muleRegistryHelper.dispose();
        }
        catch (Exception e)
        {
            logger.debug("Failed to cleanly dispose Mule: " + e.getMessage(), e);
        }

        notificationManager.fireNotification(new MuleContextNotification(this, MuleContextNotification.CONTEXT_DISPOSED));

        notificationManager.dispose();
        workManager.dispose();

        if ((getStartDate() > 0) && logger.isInfoEnabled())
        {
            logger.info(shutdownScreen.toString());
        }

        staticInstance = null;
        // SplashScreen holds static variables which need to be cleared in case we restart the server.
        SplashScreen.dispose();
    }


    /**
     * Determines if the server has been initialised
     *
     * @return true if the server has been initialised
     */
    public boolean isInitialised()
    {
        return lifecycleManager.getState().isInitialised();
    }

    /**
     * Determines if the server is being initialised
     *
     * @return true if the server is beening initialised
     */
    public boolean isInitialising()
    {
        return lifecycleManager.getState().isInitialising();
    }

    protected boolean isStopped()
    {
        return lifecycleManager.getState().isStopped();
    }

    protected boolean isStopping()
    {
        return lifecycleManager.getState().isStopping();
    }

    /**
     * Determines if the server has been started
     *
     * @return true if the server has been started
     */
    public boolean isStarted()
    {
        return lifecycleManager.isPhaseComplete(Startable.PHASE_NAME);
    }

    protected boolean isStarting()
    {
        return lifecycleManager.getState().isStarting();
    }

    public boolean isDisposed()
    {
        return lifecycleManager.getState().isDisposed();
    }

    public boolean isDisposing()
    {
        return lifecycleManager.getState().isDisposing();
    }

    public LifecycleManager getLifecycleManager()
    {
        return lifecycleManager;
    }

    /**
     * Gets all statisitcs for this instance
     *
     * @return all statisitcs for this instance
     */
    public AllStatistics getStatistics()
    {
        return stats;
    }

    public void registerListener(ServerNotificationListener l) throws NotificationException
    {
        registerListener(l, null);
    }

    public void registerListener(ServerNotificationListener l, String resourceIdentifier) throws NotificationException
    {
        ServerNotificationManager notificationManager = getNotificationManager();
        if (notificationManager == null)
        {
            throw new MuleRuntimeException(CoreMessages.serverNotificationManagerNotEnabled());
        }
        notificationManager.addListenerSubscription(l, resourceIdentifier);
    }

    public void unregisterListener(ServerNotificationListener l)
    {
        ServerNotificationManager notificationManager = getNotificationManager();
        if (notificationManager != null)
        {
            notificationManager.removeListener(l);
        }
    }

    /**
     * Fires a server notification to all registered
     * {@link org.mule.api.context.notification.CustomNotificationListener} notificationManager.
     *
     * @param notification the notification to fire. This must be of type
     *                     {@link org.mule.context.notification.CustomNotification} otherwise an
     *                     exception will be thrown.
     * @throws UnsupportedOperationException if the notification fired is not a
     *                                       {@link org.mule.context.notification.CustomNotification}
     */
    public void fireNotification(ServerNotification notification)
    {
        ServerNotificationManager notificationManager = getNotificationManager();
        if (notificationManager != null)
        {
            notificationManager.fireNotification(notification);
        }
        else if (logger.isDebugEnabled())
        {
            logger.debug("MuleEvent Manager is not enabled, ignoring notification: " + notification);
        }
    }

    /**
     * Sets the security manager used by this Mule instance to authenticate and
     * authorise incoming and outgoing event traffic and service invocations
     *
     * @param securityManager the security manager used by this Mule instance to
     *                        authenticate and authorise incoming and outgoing event traffic
     *                        and service invocations
     */
    public void setSecurityManager(SecurityManager securityManager) throws RegistrationException
    {
        checkLifecycleForPropertySet(MuleProperties.OBJECT_SECURITY_MANAGER, Initialisable.PHASE_NAME);
        registryBroker.registerObject(MuleProperties.OBJECT_SECURITY_MANAGER, securityManager);
    }

    /**
     * Gets the security manager used by this Mule instance to authenticate and
     * authorise incoming and outgoing event traffic and service invocations
     *
     * @return he security manager used by this Mule instance to authenticate
     *         and authorise incoming and outgoing event traffic and service
     *         invocations
     */
    public SecurityManager getSecurityManager()
    {
        SecurityManager securityManager = (SecurityManager) registryBroker.lookupObject(MuleProperties.OBJECT_SECURITY_MANAGER);
        if (securityManager == null)
        {
            Collection temp = registryBroker.lookupObjects(SecurityManager.class);
            if (temp.size() > 0)
            {
                securityManager = ((SecurityManager) temp.iterator().next());
            }
        }
        return securityManager;
    }

    /**
     * Obtains a workManager instance that can be used to schedule work in a
     * thread pool. This will be used primarially by Agents wanting to
     * schedule work. This work Manager must <b>never</b> be used by provider
     * implementations as they have their own workManager accible on the
     * connector.
     * <p/>
     * If a workManager has not been set by the time the
     * <code>initialise()</code> method has been called a default
     * <code>MuleWorkManager</code> will be created using the
     * <i>DefaultThreadingProfile</i> on the <code>MuleConfiguration</code>
     * object.
     *
     * @return a workManager instance used by the current MuleManager
     * @see org.mule.api.config.ThreadingProfile
     * @see DefaultMuleConfiguration
     */
    public WorkManager getWorkManager()
    {
        return workManager;
    }

    public WorkListener getWorkListener()
    {
        return workListener;
    }

    public QueueManager getQueueManager()
    {
        QueueManager queueManager = (QueueManager) registryBroker.lookupObject(MuleProperties.OBJECT_QUEUE_MANAGER);
        if (queueManager == null)
        {
            Collection temp = registryBroker.lookupObjects(QueueManager.class);
            if (temp.size() > 0)
            {
                queueManager = ((QueueManager) temp.iterator().next());
            }
        }
        return queueManager;
    }

    public void setQueueManager(QueueManager queueManager) throws RegistrationException
    {
        checkLifecycleForPropertySet(MuleProperties.OBJECT_QUEUE_MANAGER, Initialisable.PHASE_NAME);
        registryBroker.registerObject(MuleProperties.OBJECT_QUEUE_MANAGER, queueManager);
    }

    /**
     * @return the MuleConfiguration for this MuleManager. This object is immutable
     *         once the manager has initialised.
     */
    public MuleConfiguration getConfiguration()
    {

        return config;
        //return (MuleConfiguration) getRegistry().lookupObject(MuleProperties.OBJECT_MULE_CONFIGURATION);
    }

    public ServerNotificationManager getNotificationManager()
    {
        return notificationManager;
    }

    /**
     * Sets the Jta Transaction Manager to use with this Mule server instance
     *
     * @param manager the manager to use
     * @throws Exception
     */
    public void setTransactionManager(TransactionManager manager) throws RegistrationException
    {
        //checkLifecycleForPropertySet(MuleProperties.OBJECT_TRANSACTION_MANAGER, Initialisable.PHASE_NAME);
        registryBroker.registerObject(MuleProperties.OBJECT_TRANSACTION_MANAGER, manager);
    }

    /**
     * Returns the Jta transaction manager used by this Mule server instance. or
     * null if a transaction manager has not been set
     *
     * @return the Jta transaction manager used by this Mule server instance. or
     *         null if a transaction manager has not been set
     */
    public TransactionManager getTransactionManager()
    {
        TransactionManager transactionManager = (TransactionManager) registryBroker.lookupObject(MuleProperties.OBJECT_TRANSACTION_MANAGER);
        if (transactionManager == null)
        {
            Collection temp = registryBroker.lookupObjects(TransactionManagerFactory.class);
            if (temp.size() > 0)
            {
                try
                {
                    transactionManager = (((TransactionManagerFactory) temp.iterator().next()).create(config));
                }
                catch (Exception e)
                {
                    throw new MuleRuntimeException(CoreMessages.failedToCreate("transaction manager"), e);
                }
            }
            else
            {
                temp = registryBroker.lookupObjects(TransactionManager.class);
                if (temp.size() > 0)
                {
                    transactionManager = (((TransactionManager) temp.iterator().next()));
                }
            }
        }
        return transactionManager;
    }


    public void register() throws RegistrationException
    {
        throw new UnsupportedOperationException("register");
    }

    public void deregister() throws RegistrationException
    {
        throw new UnsupportedOperationException("deregister");
    }

    public String getRegistryId()
    {
        throw new UnsupportedOperationException("registryId");
    }


    protected void checkLifecycleForPropertySet(String propertyName, String phase) throws IllegalStateException
    {
        if (lifecycleManager.isPhaseComplete(phase))
        {
            throw new IllegalStateException("Cannot set property: '" + propertyName + "' once the server has been gone through the " + phase + " phase.");
        }
    }

    public MuleRegistry getRegistry()
    {
        return muleRegistryHelper;
    }

    public ThreadingProfile getDefaultMessageDispatcherThreadingProfile()
    {
        return (ThreadingProfile) getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_MESSAGE_DISPATCHER_THREADING_PROFILE);
    }

    public ThreadingProfile getDefaultMessageRequesterThreadingProfile()
    {
        return (ThreadingProfile) getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_MESSAGE_REQUESTER_THREADING_PROFILE);
    }

    public ThreadingProfile getDefaultMessageReceiverThreadingProfile()
    {
        return (ThreadingProfile) getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_MESSAGE_RECEIVER_THREADING_PROFILE);
    }

    public ThreadingProfile getDefaultServiceThreadingProfile()
    {
        return (ThreadingProfile) getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_SERVICE_THREADING_PROFILE);
    }

    public ThreadingProfile getDefaultThreadingProfile()
    {
        return (ThreadingProfile) getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_THREADING_PROFILE);
    }

    /**
     * Returns the long date when the server was started
     *
     * @return the long date when the server was started
     */
    public long getStartDate()
    {
        return startDate;
    }

    /**
     * Returns the Expression Manager configured for this instance of Mule
     *
     * @return the Expression Manager configured for this instance of Mule
     * @see org.mule.api.expression.ExpressionManager
     */
    public ExpressionManager getExpressionManager()
    {
        return expressionManager;
    }

    public void addRegistry(Registry registry)
    {
        registryBroker.addRegistry(registry);
    }

    public void removeRegistry(Registry registry)
    {
        registryBroker.removeRegistry(registry);
    }
}

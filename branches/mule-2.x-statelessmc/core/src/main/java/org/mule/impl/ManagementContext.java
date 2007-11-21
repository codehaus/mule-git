/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl;

import org.mule.MuleRuntimeException;
import org.mule.RegistryContext;
import org.mule.config.MuleManifest;
import org.mule.config.MuleProperties;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.internal.notifications.ManagerNotification;
import org.mule.impl.internal.notifications.NotificationException;
import org.mule.impl.internal.notifications.ServerNotificationManager;
import org.mule.management.stats.AllStatistics;
import org.mule.registry.Registry;
import org.mule.umo.UMOException;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.lifecycle.Disposable;
import org.mule.umo.lifecycle.FatalException;
import org.mule.umo.lifecycle.Initialisable;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.Startable;
import org.mule.umo.lifecycle.Stoppable;
import org.mule.umo.lifecycle.UMOLifecycleManager;
import org.mule.umo.manager.UMOAgent;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.manager.UMOServerNotificationListener;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.umo.security.UMOSecurityManager;
import org.mule.util.FileUtils;
import org.mule.util.StringMessageUtils;
import org.mule.util.StringUtils;
import org.mule.util.queue.QueueManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import javax.transaction.TransactionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO document
 */
public class ManagementContext implements UMOManagementContext
{
    /**
     * logger used by this class
     */
    private static transient Log logger = LogFactory.getLog(ManagementContext.class);

    private SystemInfo systemInfo = new SystemInfo();
    
    /**
     * stats used for management
     */
    private AllStatistics stats = new AllStatistics();

    protected Directories directories;

    public ManagementContext(UMOLifecycleManager lifecycleManager)
    {
//        if (lifecycleManager == null)
//        {
//            throw new NullPointerException(CoreMessages.objectIsNull("lifecycleManager").getMessage());
//        }
//        this.lifecycleManager = lifecycleManager;
        
        systemInfo.setStartDate(System.currentTimeMillis());
    }

    public void initialise() throws InitialisationException
    {
        getRegistry().getLifecycleManager().checkPhase(Initialisable.PHASE_NAME);
//        if (securityManager == null)
//        {
//            throw new NullPointerException(CoreMessages.objectIsNull("securityManager").getMessage());
//        }
//
//        if (notificationManager == null)
//        {
//            throw new NullPointerException(CoreMessages.objectIsNull("notificationManager").getMessage());
//        }
//
//        if (queueManager == null)
//        {
//            throw new NullPointerException(CoreMessages.objectIsNull("queueManager").getMessage());
//        }
//
//        if (workManager == null)
//        {
//            throw new NullPointerException(CoreMessages.objectIsNull("workManager").getMessage());
//        }
//
//        // TODO MULE-2162 MuleConfiguration belongs in the ManagementContext rather than the Registry
//        config = RegistryContext.getConfiguration();
//        if (config == null)
//        {
//            logger.info("A mule configuration object was not registered. Using default configuration");
//            config = new MuleConfiguration();
//        }

        try
        {
            setupIds();
            validateEncoding();
            validateOSEncoding();
            directories = new Directories(FileUtils.newFile(getRegistry().getConfiguration().getWorkingDirectory()));

            //We need to start the work manager straight away since we need it to fire notifications
            getRegistry().getWorkManager().start();
            getRegistry().getNotificationManager().start(getRegistry().getWorkManager());

            fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_INITIALISING));

            directories.createDirectories();
            getRegistry().getLifecycleManager().firePhase(Initialisable.PHASE_NAME);

            fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_INITIALISED));
        }
        catch (Exception e)
        {
            throw new InitialisationException(e, this);
        }
    }


    protected void setupIds() throws InitialisationException
    {
        systemInfo.setId(getRegistry().getConfiguration().getId());
        systemInfo.setClusterId(getRegistry().getConfiguration().getClusterId());
        systemInfo.setDomain(getRegistry().getConfiguration().getDomainId());

        if (systemInfo.getId() == null)
        {
            throw new InitialisationException(CoreMessages.objectIsNull("Instance ID"), this);
        }
        if (systemInfo.getClusterId() == null)
        {
            systemInfo.setClusterId(CoreMessages.notClustered().getMessage());
        }
        if (systemInfo.getDomain() == null)
        {
            try
            {
                systemInfo.setDomain(InetAddress.getLocalHost().getHostName());
            }
            catch (UnknownHostException e)
            {
                throw new InitialisationException(e, this);
            }
        }
        systemInfo.setSystemName(systemInfo.getDomain() + "." + systemInfo.getClusterId() + "." + systemInfo.getId());
    }

    public synchronized void start() throws UMOException
    {
        //getRegistry().getLifecycleManager().checkPhase(Startable.PHASE_NAME);
        if (!isStarted())
        {
            fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_STARTING));

            directories.deleteMarkedDirectories();

            getRegistry().getLifecycleManager().firePhase(Startable.PHASE_NAME);

            if (logger.isInfoEnabled())
            {
                logger.info(getStartSplash());
            }
            fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_STARTED));
        }
    }

    /**
     * Stops the <code>MuleManager</code> which stops all sessions and
     * connectors
     *
     * @throws UMOException if either any of the sessions or connectors fail to
     *                      stop
     */
    public synchronized void stop() throws UMOException
    {
        getRegistry().getLifecycleManager().checkPhase(Stoppable.PHASE_NAME);

        fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_STOPPING));
        getRegistry().getLifecycleManager().firePhase(Stoppable.PHASE_NAME);

        fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_STOPPED));
    }

    public void dispose()
    {
       //TODO getRegistry().getLifecycleManager().checkPhase(Disposable.PHASE_NAME);

        fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_DISPOSING));


        if (isDisposed())
        {
            return;
        }
        try
        {
            if (isStarted())
            {
                stop();
            }
        }
        catch (UMOException e)
        {
            logger.error("Failed to stop manager: " + e.getMessage(), e);
        }

        try
        {
            getRegistry().getLifecycleManager().firePhase(Disposable.PHASE_NAME);
        }
        catch (UMOException e)
        {
            logger.debug("Failed to cleanly dispose Mule: " + e.getMessage(), e);
        }

        fireNotification(new ManagerNotification(systemInfo, ManagerNotification.MANAGER_DISPOSED));

        if ((systemInfo.getStartDate() > 0) && logger.isInfoEnabled())
        {
            logger.info(getEndSplash());
        }
        //getRegistry().getLifecycleManager().reset();
    }


    /**
     * Determines if the server has been initialised
     *
     * @return true if the server has been initialised
     */
    public boolean isInitialised()
    {
        return getRegistry().getLifecycleManager().isPhaseComplete(Initialisable.PHASE_NAME);
    }

    /**
     * Determines if the server is being initialised
     *
     * @return true if the server is beening initialised
     */
    public boolean isInitialising()
    {
        return Disposable.PHASE_NAME.equals(getRegistry().getLifecycleManager().getExecutingPhase());
    }

    protected boolean isStopped()
    {
        return getRegistry().getLifecycleManager().isPhaseComplete(Stoppable.PHASE_NAME);
    }

    protected boolean isStopping()
    {
        return Stoppable.PHASE_NAME.equals(getRegistry().getLifecycleManager().getExecutingPhase());
    }

    /**
     * Determines if the server has been started
     *
     * @return true if the server has been started
     */
    public boolean isStarted()
    {
        return getRegistry().getLifecycleManager().isPhaseComplete(Startable.PHASE_NAME);
    }

    protected boolean isStarting()
    {
        return Startable.PHASE_NAME.equals(getRegistry().getLifecycleManager().getExecutingPhase());
    }

    public boolean isDisposed()
    {
        return getRegistry().getLifecycleManager().isPhaseComplete(Disposable.PHASE_NAME);
    }

    public boolean isDisposing()
    {
        return Disposable.PHASE_NAME.equals(getRegistry().getLifecycleManager().getExecutingPhase());
    }

    protected void validateEncoding() throws FatalException
    {
        String encoding = System.getProperty(MuleProperties.MULE_ENCODING_SYSTEM_PROPERTY);
        if (encoding == null)
        {
            encoding = getRegistry().getConfiguration().getDefaultEncoding();
            System.setProperty(MuleProperties.MULE_ENCODING_SYSTEM_PROPERTY, encoding);
        }
        else
        {
            getRegistry().getConfiguration().setDefaultEncoding(encoding);
        }
        //Check we have a valid and supported encoding
        if (!Charset.isSupported(getRegistry().getConfiguration().getDefaultEncoding()))
        {
            throw new FatalException(CoreMessages.propertyHasInvalidValue("encoding", getRegistry().getConfiguration().getDefaultEncoding()), this);
        }
    }

    protected void validateOSEncoding() throws FatalException
    {
        String encoding = System.getProperty(MuleProperties.MULE_OS_ENCODING_SYSTEM_PROPERTY);
        if (encoding == null)
        {
            encoding = getRegistry().getConfiguration().getDefaultOSEncoding();
            System.setProperty(MuleProperties.MULE_OS_ENCODING_SYSTEM_PROPERTY, encoding);
        }
        else
        {
            getRegistry().getConfiguration().setDefaultOSEncoding(encoding);
        }
        // Check we have a valid and supported encoding
        if (!Charset.isSupported(getRegistry().getConfiguration().getDefaultOSEncoding()))
        {
            throw new FatalException(CoreMessages.propertyHasInvalidValue("osEncoding",
                    getRegistry().getConfiguration().getDefaultOSEncoding()), this);
        }
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

    /**
     * Sets statistics on this instance
     *
     */
    public void setStatistics(AllStatistics stat)
    {
        this.stats = stat;
    }

    public Directories getDirectories()
    {
        return directories;
    }
    
    public SystemInfo getSystemInfo()
    {
        return systemInfo;
    }

    public void registerListener(UMOServerNotificationListener l) throws NotificationException
    {
        registerListener(l, null);
    }

    public void registerListener(UMOServerNotificationListener l, String resourceIdentifier) throws NotificationException
    {
        if (getRegistry().getNotificationManager() == null)
        {
            throw new MuleRuntimeException(CoreMessages.serverNotificationManagerNotEnabled());
        }
        getRegistry().getNotificationManager().registerListener(l, resourceIdentifier);
    }

    public void unregisterListener(UMOServerNotificationListener l)
    {
        if (getRegistry().getNotificationManager() != null)
        {
            getRegistry().getNotificationManager().unregisterListener(l);
        }
    }

    /**
     * Fires a server notification to all registered
     * {@link org.mule.impl.internal.notifications.CustomNotificationListener} notificationManager.
     *
     * @param notification the notification to fire. This must be of type
     *                     {@link org.mule.impl.internal.notifications.CustomNotification} otherwise an
     *                     exception will be thrown.
     * @throws UnsupportedOperationException if the notification fired is not a
     *                                       {@link org.mule.impl.internal.notifications.CustomNotification}
     */
    public void fireNotification(UMOServerNotification notification)
    {
        // if(notification instanceof CustomNotification) {
        if (getRegistry().getNotificationManager() != null)
        {
            getRegistry().getNotificationManager().fireEvent(notification);
        }
        else if (logger.isDebugEnabled())
        {
            logger.debug("Event Manager is not enabled, ignoring notification: " + notification);
        }
        // } else {
        // throw new UnsupportedOperationException(new
        // Message(Messages.ONLY_CUSTOM_EVENTS_CAN_BE_FIRED).getMessage());
        // }
    }


    /**
     * Returns a formatted string that is a summary of the configuration of the
     * server. This is the brock of information that gets displayed when the server
     * starts
     *
     * @return a string summary of the server information
     */
    private String getStartSplash()
    {
        String notset = CoreMessages.notSet().getMessage();

        // Mule Version, Timestamp, and Server ID
        List message = new ArrayList();
        Manifest mf = MuleManifest.getManifest();
        Map att = mf.getMainAttributes();
        if (att.values().size() > 0)
        {
            message.add(StringUtils.defaultString(MuleManifest.getProductDescription(), notset));
            message.add(CoreMessages.version().getMessage() + " Build: " 
                + StringUtils.defaultString(MuleManifest.getBuildNumber(), notset));

            message.add(StringUtils.defaultString(MuleManifest.getVendorName(), notset));
            message.add(StringUtils.defaultString(MuleManifest.getProductMoreInfo(), notset));
        }
        else
        {
            message.add(CoreMessages.versionNotSet().getMessage());
        }
        message.add(" ");
        message.add(CoreMessages.serverStartedAt(systemInfo.getStartDate()).getMessage());
        message.add("Server ID: " + systemInfo.getId());

        // JDK, OS, and Host
        message.add("JDK: " + System.getProperty("java.version") + " (" + System.getProperty("java.vm.info")
                + ")");
        String patch = System.getProperty("sun.os.patch.level", null);
        message.add("OS: " + System.getProperty("os.name")
                + (patch != null && !"unknown".equalsIgnoreCase(patch) ? " - " + patch : "") + " ("
                + System.getProperty("os.version") + ", " + System.getProperty("os.arch") + ")");
        try
        {
            InetAddress host = InetAddress.getLocalHost();
            message.add("Host: " + host.getHostName() + " (" + host.getHostAddress() + ")");
        }
        catch (UnknownHostException e)
        {
            // ignore
        }

        // Mule Agents
        message.add(" ");
        //List agents
        Collection agents = RegistryContext.getRegistry().getAgents();
        if (agents.size() == 0)
        {
            message.add(CoreMessages.agentsRunning().getMessage() + " "
                        + CoreMessages.none().getMessage());
        }
        else
        {
            message.add(CoreMessages.agentsRunning().getMessage());
            UMOAgent umoAgent;
            for (Iterator iterator = agents.iterator(); iterator.hasNext();)
            {
                umoAgent = (UMOAgent)iterator.next();
                message.add("  " + umoAgent.getDescription());
            }
        }
        return StringMessageUtils.getBoilerPlate(message, '*', 70);
    }

    private String getEndSplash()
    {
        List message = new ArrayList(2);
        long currentTime = System.currentTimeMillis();
        message.add(CoreMessages.shutdownNormally(new Date()).getMessage());
        long duration = 10;
        if (systemInfo.getStartDate() > 0)
        {
            duration = currentTime - systemInfo.getStartDate();
        }
        message.add(CoreMessages.serverWasUpForDuration(duration).getMessage());

        return StringMessageUtils.getBoilerPlate(message, '*', 78);
    }

    /**
     * Resolve and return a handle to the registry.
     * This should eventually be more intelligent (handle remote registries, clusters of Mule instances, etc.)  
     * For now the registry is just a local singleton.
     */
    public Registry getRegistry()
    {
        return RegistryContext.getRegistry();
    }
    
    public void applyLifecycle(Object object) throws UMOException
    {
        getRegistry().getLifecycleManager().applyLifecycle(object);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    // Registry Facade
    ////////////////////////////////////////////////////////////////////////////////////////////

    public UMOLifecycleManager getLifecycleManager()
    {
        return getRegistry().getLifecycleManager();
    }
    
    public UMOSecurityManager getSecurityManager()
    {
        return getRegistry().getSecurityManager();
    }

    public UMOWorkManager getWorkManager()
    {
        return getRegistry().getWorkManager();
    }

    public QueueManager getQueueManager()
    {
        return getRegistry().getQueueManager();
    }

    public ServerNotificationManager getNotificationManager()
    {
        return getRegistry().getNotificationManager();
    }

    public TransactionManager getTransactionManager()
    {
        return getRegistry().getTransactionManager();
    }
}

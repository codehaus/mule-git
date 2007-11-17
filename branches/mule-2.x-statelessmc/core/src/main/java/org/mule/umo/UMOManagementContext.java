/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.umo;

import org.mule.impl.Directories;
import org.mule.impl.internal.notifications.NotificationException;
import org.mule.impl.internal.notifications.ServerNotificationManager;
import org.mule.management.stats.AllStatistics;
import org.mule.registry.Registry;
import org.mule.umo.lifecycle.Lifecycle;
import org.mule.umo.lifecycle.UMOLifecycleManager;
import org.mule.umo.manager.UMOServerNotification;
import org.mule.umo.manager.UMOServerNotificationListener;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.umo.security.UMOSecurityManager;
import org.mule.util.queue.QueueManager;

import javax.transaction.TransactionManager;

/**
 * TODO document
 */
public interface UMOManagementContext extends Lifecycle
{
    String getSystemName();

    Directories getDirectories();

    /**
     * Determines if the server has been started
     *
     * @return true if the server has been started
     */
    boolean isStarted();

    /**
     * Determines if the server has been initialised
     *
     * @return true if the server has been initialised
     */
    boolean isInitialised();

    /**
     * Determines if the server is being initialised
     *
     * @return true if the server is beening initialised
     */
    boolean isInitialising();

    boolean isDisposed();

    boolean isDisposing();

    /**
     * Returns the long date when the server was started
     *
     * @return the long date when the server was started
     */
    long getStartDate();

    /**
     * Registers an intenal server event listener. The listener will be notified
     * when a particular event happens within the server. Typically this is not
     * an event in the same sense as an UMOEvent (although there is nothing
     * stopping the implementation of this class triggering listeners when a
     * UMOEvent is received).
     * <p/>
     * The types of notifications fired is entirely defined by the implementation of
     * this class
     *
     * @param l the listener to register
     */
    void registerListener(UMOServerNotificationListener l) throws NotificationException;

    /**
     * Registers an intenal server event listener. The listener will be notified
     * when a particular event happens within the server. Typically this is not
     * an event in the same sense as an UMOEvent (although there is nothing
     * stopping the implementation of this class triggering listeners when a
     * UMOEvent is received).
     * <p/>
     * The types of notifications fired is entirely defined by the implementation of
     * this class
     *
     * @param l                  the listener to register
     * @param resourceIdentifier a particular resource name for the given type
     *                           of listener For example, the resourceName could be the name of
     *                           a component if the listener was a ComponentNotificationListener
     */
    void registerListener(UMOServerNotificationListener l, String resourceIdentifier) throws NotificationException;

    /**
     * Unregisters a previously registered listener. If the listener has not
     * already been registered, this method should return without exception
     *
     * @param l the listener to unregister
     */
    void unregisterListener(UMOServerNotificationListener l);

    /**
     * Fires a server notification to all regiistered listeners
     *
     * @param notification the notification to fire
     */
    void fireNotification(UMOServerNotification notification);

    /**
     * Sets the unique Id for this Manager instance. this id can be used to
     * assign an identy to the manager so it can be identified in a network of
     * Mule nodes
     *
     * @param id the unique Id for this manager in the network
     */
    void setId(String id);

    /**
     * Gets the unique Id for this Manager instance. this id can be used to
     * assign an identy to the manager so it can be identified in a network of
     * Mule nodes
     *
     * @return the unique Id for this manager in the network
     */
    String getId();

    String getDomain();

    void setDomain(String domain);

    String getClusterId();

    void setClusterId(String clusterId);

    public AllStatistics getStatistics();

    public void setStatistics(AllStatistics stats);

    Registry getRegistry();
    
    void applyLifecycle(Object object) throws UMOException;

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Registry Facade
    ////////////////////////////////////////////////////////////////////////////////////////////

    UMOLifecycleManager getLifecycleManager();
    
    UMOSecurityManager getSecurityManager();

    UMOWorkManager getWorkManager();

    QueueManager getQueueManager();

    ServerNotificationManager getNotificationManager();

    TransactionManager getTransactionManager();
}

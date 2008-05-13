/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.impl.model.seda;

import org.mule.MuleManager;
import org.mule.MuleRuntimeException;
import org.mule.config.PoolingProfile;
import org.mule.config.QueueProfile;
import org.mule.config.ThreadingProfile;
import org.mule.config.i18n.CoreMessages;
import org.mule.impl.FailedToQueueEventException;
import org.mule.impl.MuleDescriptor;
import org.mule.impl.MuleEvent;
import org.mule.impl.model.AbstractComponent;
import org.mule.impl.model.DefaultMuleProxy;
import org.mule.impl.model.MuleProxy;
import org.mule.management.stats.ComponentStatistics;
import org.mule.management.stats.SedaComponentStatistics;
import org.mule.umo.ComponentException;
import org.mule.umo.UMOEvent;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.umo.manager.UMOWorkManager;
import org.mule.util.ObjectPool;
import org.mule.util.queue.QueueSession;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;

/**
 * A Seda component runs inside a Seda Model and is responsible for managing a Seda
 * Queue and thread pool for a Mule sevice component. In Seda terms this is
 * equivilent to a stage.
 */
public class SedaComponent extends AbstractComponent implements Work, WorkListener
{
    public static final String QUEUE_PROFILE_PROPERTY = "queueProfile";
    public static final String POOLING_PROFILE_PROPERTY = "poolingProfile";
    /**
     * Serial version/
     */
    private static final long serialVersionUID = 7711976708670893015L;

    /**
     * A pool of available Mule proxies. If component pooling has been disabled on the
     * SEDAModel, this pool will be null and the 'componentProxy' will be used.
     */
    protected ObjectPool proxyPool;

    /**
     * Is created only if component pooling is turned off on the SEDAModel. In this
     * scenario all requests are serviced by this component, unless
     * {@link #componentPerRequest} flag is set on the model.
     */
    protected MuleProxy componentProxy;

    protected UMOWorkManager workManager;

    protected String descriptorQueueName;

    /**
     * The time out used for taking from the Seda Queue.
     */
    protected int queueTimeout = 0;

    /**
     * Whether component objects should be pooled or a single instance should be
     * used.
     */
    protected boolean enablePooling = true;

    /**
     * If this is set to true a new component will be created for every request.
     */
    protected boolean componentPerRequest = false;

    /**
     * The pooling configuration used when initialising the component described by
     * this descriptor.
     */
    protected PoolingProfile poolingProfile;

    /**
     * The queuing profile for events received for this component.
     */
    protected QueueProfile queueProfile;

    /**
     * Creates a new SEDA component.
     * 
     * @param descriptor The descriptor of the component to creat
     * @param model the model in which the component is registered
     */
    public SedaComponent(MuleDescriptor descriptor, SedaModel model)
    {
        super(descriptor, model);

        descriptorQueueName = descriptor.getName() + ".component";
        queueTimeout = model.getQueueTimeout();
        enablePooling = model.isEnablePooling();
        componentPerRequest = model.isComponentPerRequest();
        poolingProfile = model.getPoolingProfile();
        queueProfile = model.getQueueProfile();
    }

    /**
     * Initialise the component. The component will first create a Mule UMO from the
     * UMODescriptor and then initialise a pool based on the attributes in the
     * UMODescriptor.
     * 
     * @throws org.mule.umo.lifecycle.InitialisationException if the component fails
     *             to initialise
     * @see org.mule.umo.UMODescriptor
     */
    public synchronized void doInitialise() throws InitialisationException
    {
        // Create thread pool
        ThreadingProfile tp = descriptor.getThreadingProfile();
        workManager = tp.createWorkManager(descriptor.getName());

        queueProfile = descriptor.getQueueProfile();
        if (queueProfile == null)
        {
            queueProfile = ((SedaModel) model).getQueueProfile();
        }

        poolingProfile = descriptor.getPoolingProfile();
        if (poolingProfile == null)
        {
            poolingProfile = ((SedaModel) model).getPoolingProfile();
        }

        try
        {
            // Setup event Queue (used for VM execution)
            queueProfile.configureQueue(descriptorQueueName);
        }
        catch (InitialisationException e)
        {
            throw e;
        }
        catch (Throwable e)
        {
            throw new InitialisationException(
                CoreMessages.objectFailedToInitialise("Component Queue"), e, this);
        }
    }

    protected ComponentStatistics createStatistics()
    {
        return new SedaComponentStatistics(getName(), descriptor.getThreadingProfile().getMaxThreadsActive(),
            poolingProfile.getMaxActive());
    }

    protected ObjectPool createPool() throws InitialisationException
    {
        return getPoolingProfile().getPoolFactory().createPool(descriptor, model,
            getPoolingProfile());
    }

    protected void initialisePool() throws InitialisationException
    {
        try
        {
            int initPolicy = getPoolingProfile().getInitialisationPolicy();
            if (initPolicy == PoolingProfile.INITIALISE_ALL)
            {
                int numToBorrow = getPoolingProfile().getMaxActive();
                List holderList = new ArrayList(numToBorrow);

                try
                {
                    for (int t = 0; t < numToBorrow; t++)
                    {
                        holderList.add(proxyPool.borrowObject());
                    }
                }
                finally
                {
                    for (int t = 0; t < holderList.size(); t++)
                    {
                        Object obj = holderList.get(t);
                        if (obj != null)
                        {
                            try
                            {
                                proxyPool.returnObject(obj);
                            }
                            finally
                            {
                                // ignore - nothing we can do
                            }
                        }
                    }
                }
            }
            else if (initPolicy == PoolingProfile.INITIALISE_ONE)
            {
                Object obj = null;
                try
                {
                    obj = proxyPool.borrowObject();
                }
                finally
                {
                    if (obj != null)
                    {
                        proxyPool.returnObject(obj);
                    }
                }
            }

            poolInitialised.set(true);
        }
        catch (Exception e)
        {
            throw new InitialisationException(
                CoreMessages.objectFailedToInitialise("Proxy Pool"), e, this);
        }
    }

    protected MuleProxy createComponentProxy() throws InitialisationException
    {
        try
        {
            Object component = lookupComponent();
            MuleProxy componentProxy = new DefaultMuleProxy(component, descriptor, model);
            ((SedaComponentStatistics) getStatistics()).setComponentPoolSize(-1);
            componentProxy.setStatistics(getStatistics());
            componentProxy.start();
            return componentProxy;
        }
        catch (UMOException e)
        {
            throw new InitialisationException(e, this);
        }
    }

    public void doForceStop() throws UMOException
    {
        doStop();
    }

    public void doStop() throws UMOException
    {
        if (MuleManager.getInstance().getQueueManager().getQueueSession().getQueue(
            descriptor.getName() + ".component").size() > 0)
        {
            try
            {
                stopping.whenFalse(null);
            }
            catch (InterruptedException e)
            {
                // we can ignore this
                // TODO MULE-863: Why?
            }
        }
        workManager.stop();
        if (proxyPool != null)
        {
            try
            {
                proxyPool.stop();
                proxyPool.clearPool();
            }
            catch (Exception e)
            {
                // TODO MULE-863: If this is an error, do something about it
                logger.error("Failed to stop component pool: " + e.getMessage(), e);
            }
            poolInitialised.set(false);
        }
        else if (componentProxy != null)
        {
            componentProxy.stop();
        }
    }

    public void doStart() throws UMOException
    {

        try
        {
            // Need to initialise the pool only after all listerner have
            // been registered and initialised so we need to delay until now
            if (!poolInitialised.get() && enablePooling)
            {
                proxyPool = this.createPool();
                this.initialisePool();
                proxyPool.start();
            }
            else if (!componentPerRequest)
            {
                componentProxy = createComponentProxy();
            }
            workManager.start();
            workManager.scheduleWork(this, WorkManager.INDEFINITE, null, this);
        }
        catch (Exception e)
        {
            throw new LifecycleException(
                CoreMessages.failedToStart("Component: " + descriptor.getName()), e, this);
        }
    }

    protected void doDispose()
    {

        try
        {
            // threadPool.awaitTerminationAfterShutdown();
            if (workManager != null)
            {
                workManager.dispose();
            }
        }
        catch (Exception e)
        {
            // TODO MULE-863: So what are we going to do about it?
            logger.error("Component Thread Pool did not close properly: " + e);
        }
        try
        {
            if (proxyPool != null)
            {
                proxyPool.clearPool();
            }
            else if (componentProxy != null)
            {
                componentProxy.dispose();
            }
        }
        catch (Exception e)
        {
            // TODO MULE-863: So what are we going to do about it?
            logger.error("Proxy Pool did not close properly: " + e);
        }
    }

    protected void doDispatch(UMOEvent event) throws UMOException
    {
        // Dispatching event to the component
        if (stats.isEnabled())
        {
            stats.incReceivedEventASync();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Component: " + descriptor.getName() + " has received asynchronous event on: "
                            + event.getEndpoint().getEndpointURI());
        }

        // Block until we can queue the next event
        try
        {
            enqueue(event);
            if (stats.isEnabled())
            {
                stats.incQueuedEvent();
            }
        }
        catch (Exception e)
        {
            FailedToQueueEventException e1 = 
                new FailedToQueueEventException(
                    CoreMessages.interruptedQueuingEventFor(this.getName()), 
                    event.getMessage(), this, e);
            handleException(e1);
        }

        if (logger.isTraceEnabled())
        {
            logger.trace("Event added to queue for: " + descriptor.getName());
        }
    }

    public UMOMessage doSend(UMOEvent event) throws UMOException
    {
        UMOMessage result = null;
        MuleProxy proxy = null;
        try
        {
            proxy = getProxy();
            if (logger.isDebugEnabled())
            {
                logger.debug(this + " : got proxy for " + event.getId() + " = " + proxy);
            }
            result = (UMOMessage) proxy.onCall(event);
        }
        catch (UMOException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ComponentException(event.getMessage(), this, e);
        }
        // Ensure that any proxy used for this request is released.
        finally
        {
            releaseProxy(proxy);
        }
        return result;
    }

    /**
     * @return the pool of Mule UMOs initialised in this component
     */
    ObjectPool getProxyPool()
    {
        return proxyPool;
    }

    public int getQueueSize()
    {
        QueueSession queueSession = MuleManager.getInstance().getQueueManager().getQueueSession();
        return queueSession.getQueue(descriptor.getName()).size();
    }

    /**
     * While the component isn't stopped this runs a continuous loop checking for new
     * events in the queue.
     */
    public void run()
    {
        MuleEvent event = null;
        MuleProxy proxy = null;
        QueueSession queueSession = MuleManager.getInstance().getQueueManager().getQueueSession();

        while (!stopped.get())
        {
            try
            {
                // Wait if the component is paused
                paused.whenFalse(null);

                // If we're doing a draining stop, read all events from the queue
                // before stopping
                if (stopping.get())
                {
                    if (queueSession == null || queueSession.getQueue(descriptorQueueName).size() == 0)
                    {
                        stopping.set(false);
                        break;
                    }
                }

                event = (MuleEvent) dequeue();
                if (event != null)
                {
                    if (stats.isEnabled())
                    {
                        stats.decQueuedEvent();
                    }

                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Component: " + descriptor.getName() + " dequeued event on: "
                                        + event.getEndpoint().getEndpointURI());
                    }

                    proxy = getProxy();
                    proxy.start();
                    proxy.onEvent(queueSession, event);
                    workManager.scheduleWork(proxy, WorkManager.INDEFINITE, null, this);
                }
            }
            catch (Exception e)
            {
                // The proxy did not get created and/or schedule, so ensure it gets
                // released.
                releaseProxy(proxy);

                if (e instanceof InterruptedException)
                {
                    stopping.set(false);
                    break;
                }
                else if (e instanceof NoSuchElementException)
                {
                    handleException(new ComponentException(CoreMessages.proxyPoolTimedOut(),
                        (event == null ? null : event.getMessage()), this, e));
                }
                else if (e instanceof UMOException)
                {
                    handleException(e);
                }
                else if (e instanceof WorkException)
                {
                    handleException(
                        new ComponentException(
                            CoreMessages.eventProcessingFailedFor(descriptor.getName()),
                            (event == null ? null : event.getMessage()), this, e));
                }
                else
                {
                    handleException(
                        new ComponentException(
                            CoreMessages.failedToGetPooledObject(),
                            (event == null ? null : event.getMessage()), this, e));
                }
            }
            finally
            {
                stopping.set(false);
                
                /* Removed: Since a componentPerRequest proxy is scheduled, it will be disposed by
                 * {@link #workCompleted(WorkEvent)} or {@link #workRejected(WorkEvent)}}.
                if (proxy != null && componentPerRequest)
                {
                    proxy.dispose();
                }
                */
            }
        }
    }

    /**
     * The proxy may be one of three types: 1. pooled 2. not pooled 3. per-request
     */
    protected MuleProxy getProxy() throws Exception
    {
        MuleProxy proxy;
        if (proxyPool != null)
        {
            proxy = (MuleProxy) proxyPool.borrowObject();
            ((SedaComponentStatistics) getStatistics()).setComponentPoolSize(proxyPool.getSize());
        }
        else if (componentPerRequest)
        {
            proxy = createComponentProxy();
        }
        else
        {
            proxy = componentProxy;
        }
        proxy.setStatistics(getStatistics());
        return proxy;
    }
    
    public void release()
    {
        stopping.set(false);
    }

    protected void enqueue(UMOEvent event) throws Exception
    {
        QueueSession session = MuleManager.getInstance().getQueueManager().getQueueSession();
        session.getQueue(descriptorQueueName).put(event);
    }

    protected UMOEvent dequeue() throws Exception
    {
        // Wait until an event is available
        QueueSession queueSession = MuleManager.getInstance().getQueueManager().getQueueSession();
        return (UMOEvent) queueSession.getQueue(descriptorQueueName).poll(queueTimeout);
    }

    public void workAccepted(WorkEvent event)
    {
        handleWorkException(event, "workAccepted");
    }

    /**
     * This method ensures that any component proxy associated with this rejected
     * work is released.
     *  
     * @see #workCompleted(WorkEvent)
     */
    public void workRejected(WorkEvent event)
    {
        handleWorkException(event, "workRejected");

        if (event.getWork() instanceof MuleProxy) 
        {
            releaseProxy((MuleProxy) event.getWork());
        }
    }

    public void workStarted(WorkEvent event)
    {
        handleWorkException(event, "workStarted");
    }

    /**
     * There are two units of work that call this method when they complete
     * (regardless of whether or not they incurred an exception):
     * 1) This component's queue listener that processes asynchronous events by
     *    scheduling a component proxy
     * 2) Each scheduled component proxy
     * <p>
     * Generally, #1 occurs each time the component stops and #2 occurs
     * at the end of each asynchronous event.
     * <p>
     * This method is responsible for handling any exceptions that occur for both
     * #1 and #2 and releasing the proxy from #2. 
     *    
     * @see WorkListener#workCompleted(WorkEvent)
     */
    public void workCompleted(WorkEvent event)
    {
        handleWorkException(event, "workCompleted");

        if (event.getWork() instanceof MuleProxy) 
        {
            releaseProxy((MuleProxy) event.getWork());
        }
    }

    protected void handleWorkException(WorkEvent event, String type)
    {
        Throwable e;

        if (event != null && event.getException() != null)
        {
            e = event.getException();
        }
        else
        {
            return;
        }

        if (event.getException().getCause() != null)
        {
            e = event.getException().getCause();
        }

        logger.error("Work caused exception on '" + type + "'. Work being executed was: "
                        + event.getWork().toString());

        if (e instanceof Exception)
        {
            handleException((Exception) e);
        }
        else
        {
            throw new MuleRuntimeException(
                CoreMessages.componentCausedErrorIs(this.getName()), e);
        }
    }

    public PoolingProfile getPoolingProfile()
    {
        return poolingProfile;
    }

    public void setPoolingProfile(PoolingProfile poolingProfile)
    {
        this.poolingProfile = poolingProfile;
    }

    public QueueProfile getQueueProfile()
    {
        return queueProfile;
    }

    public void setQueueProfile(QueueProfile queueProfile)
    {
        this.queueProfile = queueProfile;
    }

    /**
     * This is a helper method that catches any exceptions that occur while
     * releasing a component proxy and reports them to the component for
     * handling.
     * 
     * @param proxy -
     *            the proxy that has completed; may be null in which case
     *            nothing occurs
     * 
     * @see #doReleaseProxy(MuleProxy)
     */
    private void releaseProxy(MuleProxy proxy) 
    {
        if (proxy == null) 
        {
            return;
        }
        
        try 
        {
            doReleaseProxy(proxy);
        } 
        catch (Exception ex)
        {
            handleException(ex);
        }
        // else, keep the singleton proxy for future use.
    }
    
    /**
     * This method is called once when a proxy has finished processing a request
     * (regardless of success). For every call to
     * {@link #getProxy()), there will be a corresponding call to this method. 
     * The default behavior is to return the proxy to the pool, dispose of it,
     * or reuse it, depending on the configuration.
     * <p>
     * <b>NOTE:</b> The implementation of this method must be thread-safe.
     * 
     * @param proxy -
     *            the proxy that has completed; must be non-null.
     * 
     * @throws Exception -
     *             the proxy cannot be released.
     */
    protected void doReleaseProxy(MuleProxy proxy) throws Exception
    {
        assert (proxy != null);

        if (proxyPool != null) 
        {
            proxyPool.returnObject(proxy);
            ((SedaComponentStatistics) getStatistics()).setComponentPoolSize(proxyPool.getSize());
        } 
        else if (componentPerRequest) 
        {
            proxy.dispose();
        }
        // else, keep the singleton proxy for future use.
    }
}

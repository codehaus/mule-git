/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.management.mbean;

import org.mule.management.stats.ServiceStatistics;
import org.mule.management.stats.RouterStatistics;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>ServiceStats</code> TODO
 */
public class ServiceStats implements ServiceStatsMBean, MBeanRegistration
{

    /**
     * logger used by this class
     */
    private static Log LOGGER = LogFactory.getLog(ServiceStats.class);

    private MBeanServer server;

    private ObjectName name;
    private ObjectName inboundName;
    private ObjectName outboundName;

    private ServiceStatistics statistics;

    public ServiceStats(ServiceStatistics statistics)
    {
        this.statistics = statistics;
    }

    /**
     * 
     */
    public void clearStatistics()
    {
        statistics.clear();
    }

    /**
     * @return
     */
    public long getAsyncEventsReceived()
    {
        return statistics.getAsyncEventsReceived();
    }

    /**
     * @return
     */
    public long getAsyncEventsSent()
    {
        return statistics.getAsyncEventsSent();
    }

    /**
     * @return
     */
    public long getAverageExecutionTime()
    {
        return statistics.getAverageExecutionTime();
    }

    /**
     * @return
     */
    public long getAverageQueueSize()
    {
        return statistics.getAverageQueueSize();
    }

    /**
     * @return
     */
    public long getExecutedEvents()
    {
        return statistics.getExecutedEvents();
    }

    /**
     * @return
     */
    public long getExecutionErrors()
    {
        return statistics.getExecutionErrors();
    }

    /**
     * @return
     */
    public long getFatalErrors()
    {
        return statistics.getFatalErrors();
    }

    /**
     * @return
     */
    public long getMaxExecutionTime()
    {
        return statistics.getMaxExecutionTime();
    }

    /**
     * @return
     */
    public long getMaxQueueSize()
    {
        return statistics.getMaxQueueSize();
    }

    /**
     * @return
     */
    public long getMinExecutionTime()
    {
        return statistics.getMinExecutionTime();
    }

    /**
     * @return
     */
    public String getName()
    {
        return statistics.getName();
    }

    /**
     * @return
     */
    public long getQueuedEvents()
    {
        return statistics.getQueuedEvents();
    }

    /**
     * @return
     */
    public long getReplyToEventsSent()
    {
        return statistics.getReplyToEventsSent();
    }

    /**
     * @return
     */
    public long getSyncEventsReceived()
    {
        return statistics.getSyncEventsReceived();
    }

    /**
     * @return
     */
    public long getSyncEventsSent()
    {
        return statistics.getSyncEventsSent();
    }

    /**
     * @return
     */
    public long getTotalEventsReceived()
    {
        return statistics.getTotalEventsReceived();
    }

    /**
     * @return
     */
    public long getTotalEventsSent()
    {
        return statistics.getTotalEventsSent();
    }

    /**
     * @return
     */
    public long getTotalExecutionTime()
    {
        return statistics.getTotalExecutionTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.management.MBeanRegistration#preRegister(javax.management.MBeanServer,
     *      javax.management.ObjectName)
     */
    public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception
    {
        this.server = server;
        this.name = name;
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.management.MBeanRegistration#postRegister(java.lang.Boolean)
     */
    public void postRegister(Boolean registrationDone)
    {

        try
        {
            RouterStatistics is = this.statistics.getInboundRouterStat();
            if (is != null)
            {
                inboundName = new ObjectName(name.getDomain() + ":type=org.mule.Statistics,service="
                                             + statistics.getName() + ",router=inbound");
                // unregister old version if exists
                if (this.server.isRegistered(inboundName))
                {
                    this.server.unregisterMBean(inboundName);
                }
                this.server.registerMBean(new RouterStats(is), this.inboundName);
            }
            RouterStatistics os = this.statistics.getOutboundRouterStat();
            if (os != null)
            {
                outboundName = new ObjectName(name.getDomain() + ":type=org.mule.Statistics,service="
                                              + statistics.getName() + ",router=outbound");
                // unregister old version if exists
                if (this.server.isRegistered(outboundName))
                {
                    this.server.unregisterMBean(outboundName);
                }
                this.server.registerMBean(new RouterStats(os), this.outboundName);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Error post-registering MBean", e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.management.MBeanRegistration#preDeregister()
     */
    public void preDeregister() throws Exception
    {
        // nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.management.MBeanRegistration#postDeregister()
     */
    public void postDeregister()
    {
        try
        {
            if (this.server.isRegistered(inboundName))
            {
                this.server.unregisterMBean(inboundName);
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("Error unregistering ServiceStats child " + inboundName.getCanonicalName(), ex);
        }
        try
        {
            if (this.server.isRegistered(outboundName))
            {
                this.server.unregisterMBean(outboundName);
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("Error unregistering ServiceStats child " + inboundName.getCanonicalName(), ex);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.management.mbeans.ServiceStatsMBean#getInboundRouter()
     */
    public ObjectName getRouterInbound()
    {
        return this.inboundName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.management.mbeans.ServiceStatsMBean#getOutboundRouter()
     */
    public ObjectName getRouterOutbound()
    {
        return this.outboundName;
    }
}

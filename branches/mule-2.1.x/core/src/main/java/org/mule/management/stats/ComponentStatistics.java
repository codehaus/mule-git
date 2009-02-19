/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.stats;

import org.mule.api.management.stats.Statistics;
import org.mule.management.stats.printers.SimplePrinter;

import java.io.PrintWriter;

/**
 * 
 */
public class ComponentStatistics implements Statistics
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = -2086999226732861674L;

    private long minExecutionTime = 0;
    private long maxExecutionTime = 0;
    private long averageExecutionTime = 0;
    private long executedEvent = 0;
    private long totalExecTime = 0;
    private boolean enabled = false;
    private long intervalTime = 0;
    private long currentIntervalStartTime = 0;
    private boolean isStatIntervalTimeEnabled = false; 

    /**
     * The constructor added to initialize the interval time in ms that stats   
     * are measured for from the property statIntervalTime. If the property is 
     * not set or cannot be parsed, disable interval time and just compute 
     * stats from start of mule.
     * TODO: The code to create and use an interval time for measuring average execution 
     * time could be removed once a complete solution is available in MuleHQ to
     * monitor this
     */
    public ComponentStatistics() 
    {
        String intervalTimeString = System.getProperty("statIntervalTime");
        if (intervalTimeString == null) 
        {
            isStatIntervalTimeEnabled = false;
        } 
        else 
        {
            intervalTime = Integer.parseInt(intervalTimeString);
            isStatIntervalTimeEnabled = true;
        }
    }

    public void clear()
    {
        minExecutionTime = 0;
        maxExecutionTime = 0;
        executedEvent = 0;
        totalExecTime = 0;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void logSummary()
    {
        logSummary(new SimplePrinter(System.out));
    }

    public void logSummary(PrintWriter printer)
    {
        printer.print(this);
    }

    public void setEnabled(boolean b)
    {
        this.enabled = b;
    }

    public long getMaxExecutionTime()
    {
        return maxExecutionTime;
    }

    public long getMinExecutionTime()
    {
        return minExecutionTime;
    }

    public long getTotalExecutionTime()
    {
        return totalExecTime;
    }

    /*
     * executedEvents is since interval started
     */
    public long getExecutedEvents()
    {
        return executedEvent;
    }

    public synchronized void addExecutionTime(long time)
    {
        if (isStatIntervalTimeEnabled) 
        {
            long currentTime = System.currentTimeMillis();
            if (currentIntervalStartTime == 0)
            {
                currentIntervalStartTime = currentTime;
            }

            if ((currentTime - currentIntervalStartTime) > intervalTime)
            {
                clear();
                currentIntervalStartTime = currentTime;
            }
        }

        executedEvent++;

        totalExecTime += (time == 0 ? 1 : time);

        if (minExecutionTime == 0 || time < minExecutionTime)
        {
            minExecutionTime = time;
        }
        if (maxExecutionTime == 0 || time > maxExecutionTime)
        {
            maxExecutionTime = time;
        }
        averageExecutionTime = Math.round(totalExecTime / executedEvent);
    }

    public long getAverageExecutionTime()
    {
        return averageExecutionTime;
    }

}

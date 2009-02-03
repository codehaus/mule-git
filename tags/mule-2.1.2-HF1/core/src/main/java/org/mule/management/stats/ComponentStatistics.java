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

	private static final int DEFAULT_STAT_INTERVAL_TIME = 60000;

    private long minExecutionTime = 0;
    private long maxExecutionTime = 0;
    private long averageExecutionTime = 0;
    private long executedEvent = 0;
    private long totalExecTime = 0;
    private boolean enabled = false;
    private long intervalTime = 0;
    private long currentIntervalStartTime = 0;

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
    	long currentTime = System.currentTimeMillis();
    	if(currentIntervalStartTime == 0)
    	{
    		currentIntervalStartTime = currentTime;
    	}

    	// initialize the interval time that stats are measured for
    	if(intervalTime == 0)
    		try {
    			intervalTime = Integer.parseInt(System.getProperty("statIntervalTime", Integer.toString(DEFAULT_STAT_INTERVAL_TIME)));
    		} catch (NumberFormatException e) {
    			// if cannot parse the property, set to the default
    			intervalTime = DEFAULT_STAT_INTERVAL_TIME;
    		}

    	if((currentTime - currentIntervalStartTime) > intervalTime)
    	{
    		clear();
    		currentIntervalStartTime = currentTime;
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

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

/**
 * <code>ServiceStatsMBean</code> TODO
 */
public interface ServiceStatsMBean
{

    void clearStatistics();

    long getAverageExecutionTime();

    long getAverageQueueSize();

    long getMaxQueueSize();

    long getMaxExecutionTime();

    long getFatalErrors();

    long getMinExecutionTime();

    long getTotalExecutionTime();

    long getQueuedEvents();

    long getAsyncEventsReceived();

    long getSyncEventsReceived();

    long getReplyToEventsSent();

    long getSyncEventsSent();

    long getAsyncEventsSent();

    long getTotalEventsSent();

    long getTotalEventsReceived();

    long getExecutedEvents();

    long getExecutionErrors();
}

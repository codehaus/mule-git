/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.management.mbeans;

import com.yourkit.api.Controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YourKitProfilerService implements YourKitProfilerServiceMBean
{
    /**
     * logger used by this class
     */
    protected transient Log logger = LogFactory.getLog(getClass());

    private Controller controller;
    private boolean capturing = false;

    public YourKitProfilerService()
    {
        try
        {
            controller = new Controller();
        } catch (Exception e)
        {
            logger.error("Failed to instantiate controller", e);
        }
    }

    public String getHost()
    {                           
        return controller.getHost();
    }

    public int getPort()
    {
        return controller.getPort();
    }

    public String captureMemorySnapshot() throws Exception
    {
        return controller.captureMemorySnapshot();
    }

    public String captureSnapshot(long snapshotFlags) throws Exception
    {
        return controller.captureSnapshot(snapshotFlags);
    }

    public void startAllocationRecording(long mode) throws Exception
    {
        controller.startAllocationRecording(mode);
    }

    public void stopAllocationRecording() throws Exception
    {
        controller.stopAllocationRecording();
    }

    public void startCPUProfiling(long mode, String filters) throws Exception
    {
        controller.startCPUProfiling(mode, filters);
    }

    public void stopCPUProfiling() throws Exception
    {
        controller.stopCPUProfiling();
    }

    public void startMonitorProfiling() throws Exception
    {
        controller.startMonitorProfiling();
    }

    public void stopMonitorProfiling() throws Exception
    {
        controller.stopMonitorProfiling();
    }

    public void advanceGeneration(String description)
    {
        controller.advanceGeneration(description);
    }

    public long[] forceGC() throws Exception
    {
        return controller.forceGC();
    }

    public void startCapturingMemorySnapshotEverySeconds(final int seconds)
    {
        if(this.capturing) return;

        this.capturing = true;
        final Thread thread = new Thread(
                new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            while (capturing)
                            {
                                controller.captureMemorySnapshot();
                                Thread.sleep(seconds * 1000 /* millis in second */);
                            }
                        }
                        catch (Exception e)
                        {
                            logger.error("Failed to capture memory snapshot", e);
                        }
                    }
                }
        );
        thread.setDaemon(true); // let the application normally terminate
        thread.start();
    }

    public void stopCapturingMemorySnapshot()
    {
        this.capturing = false;
    }
}

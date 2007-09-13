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

    public String getHost()
    {                           
        return getController().getHost();
    }

    public int getPort()
    {
        return getController().getPort();
    }

    public String captureMemorySnapshot() throws Exception
    {
        return getController().captureMemorySnapshot();
    }

    public String captureSnapshot(long snapshotFlags) throws Exception
    {
        return getController().captureSnapshot(snapshotFlags);
    }

    public void startAllocationRecording(long mode) throws Exception
    {
        getController().startAllocationRecording(mode);
    }

    public void stopAllocationRecording() throws Exception
    {
        getController().stopAllocationRecording();
    }

    public void startCPUProfiling(long mode, String filters) throws Exception
    {
        getController().startCPUProfiling(mode, filters);
    }

    public void stopCPUProfiling() throws Exception
    {
        getController().stopCPUProfiling();
    }

    public void startMonitorProfiling() throws Exception
    {
        getController().startMonitorProfiling();
    }

    public void stopMonitorProfiling() throws Exception
    {
        getController().stopMonitorProfiling();
    }

    public void advanceGeneration(String description)
    {
        getController().advanceGeneration(description);
    }

    public long[] forceGC() throws Exception
    {
        return getController().forceGC();
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
                                getController().captureMemorySnapshot();
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

    private Controller getController()
    {
        try
        {
            if (controller == null) controller = new Controller();
        } catch (Exception e)
        {
            logger.error("Failed to instantiate controller", e);
        }
        return controller;
    }
}

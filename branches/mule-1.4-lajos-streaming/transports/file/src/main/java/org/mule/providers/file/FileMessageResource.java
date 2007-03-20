/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.file;

import org.mule.MuleException;
import org.mule.umo.provider.UMOMessageResource;
import org.mule.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 */
public class FileMessageResource implements UMOMessageResource
{
    /**
     * logger used by this class
     */
    private static Log logger = LogFactory.getLog(FileConnector.class);

    private File file;
    private FileLock lock;
    private boolean autoDelete = false;
    private FileChannel channel = null;

    public FileMessageResource(FileConnector connector, File file)
    {
        setFile(file);
        this.autoDelete = connector.isAutoDelete();
        acquireLock();
    }

    private void acquireLock()
    {
        try
        {
            channel = new RandomAccessFile(file, "rw").getChannel();
            // This has to be shared so that IOUtils can do the copy
            lock = channel.lock(0L, Long.MAX_VALUE, true);
        }
        catch (Exception e)
        {
            logger.error("Lock error", e);
        }
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public File getFile()
    {
        return file;
    }

    public void dispose()
    {
        if (file == null)
        {
            logger.error("Unable to dispose - file is null");
            return;
        }

        try 
        {
            if (lock == null)
            {
                logger.error("This resource may not be properly disposed - lock is null");
            }
            else
            {
                lock.release();
            }

            if (channel != null)
            {
                try
                {
                    // Close the file
                    channel.close();
                }
                catch (IOException e)
                {
                    logger.error("Error closing channel", e);
                }
            }

            if (autoDelete)
            {
                try 
                {
                    FileUtils.forceDelete(file);
                }
                catch (Exception e)
                {
                    logger.error("Unable to delete the file " + file.getAbsolutePath(), e);
                    // We can probably remove this later. This might never be
                    // called.
                    
                    // Try one more time
                    if (!file.delete())
                    {
                        // Give up. Let's have it deleted when Mule shuts down
                        file.deleteOnExit();
                        // We need to re-acquire the lock to prevent re-reading
                        acquireLock();
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Unable to dispose", e);
        }
    }

}

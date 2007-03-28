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
import org.mule.umo.provider.UMOMessageAdapter;
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
 * Example implementation of the UMOMessageResource. This one handles the cleanup
 * of a File after it has been read in the streaming model.
 */
public class FileMessageResource implements UMOMessageResource
{
    /**
     * logger used by this class
     */
    private static Log logger = LogFactory.getLog(FileMessageResource.class);

    /**
     * Represents the source file
     */
    private File file;

    /**
     * Lock on the file to make sure it is not read again while being read
     * for the first time
     */
    private FileLock lock;

    /**
     * Whether or not to delete the file after reading it
     */
    private boolean autoDelete = false;

    /**
     * FileChannel for the file
     */
    private FileChannel channel = null;

    /**
     * If applicable, the moveTo file object
     */
    private File destinationFile = null;

    /**
     * Constructor ... perhaps we don't have to pass in all these parameters
     */
    public FileMessageResource(FileConnector connector, FileMessageReceiver receiver, UMOMessageAdapter msgAdapter, File file)
    {
        setFile(file);
        this.autoDelete = connector.isAutoDelete();
        String moveDir = receiver.getMoveDir();
        String moveToPattern = receiver.getMovePattern();
        acquireLock();

        if (moveDir != null)
        {
            String destinationFileName = this.file.getName();

            if (moveToPattern != null)
            {
                destinationFileName = connector.getFilenameParser().getFilename(msgAdapter,
                    moveToPattern);
            }

            // don't use new File() directly, see MULE-1112
            destinationFile = FileUtils.newFile(moveDir, destinationFileName);
        }
    }

    /**
     * Get a rw lock on the file
     */
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

    /**
     * Clean up the resource.
     *
     * First release the lock. Then either move to the destination directory
     * or delete
     */
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

            if (destinationFile != null)
            {
                boolean fileWasMoved = FileUtils.moveFile(file, destinationFile);

                if (!fileWasMoved)
                {
                    // Not sure what to do here just yet ...
                }

            }
            else if (autoDelete)
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

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.boot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtils
{
    private static final Log logger = LogFactory.getLog(FileUtils.class);

    private FileUtils()
    {
        // utility class only
    }

    public static boolean renameFileHard(String srcFilePath, String destFilePath)
    {
        if (StringUtils.isNotBlank(srcFilePath) && StringUtils.isNotBlank(destFilePath))
        {
            return renameFileHard(new File(srcFilePath), new File(destFilePath));
        }
        else
        {
            return false;
        }
    }
    
    public static boolean renameFileHard(File srcFile, File destFile)
    {
        boolean isRenamed = false;
        if (srcFile != null && destFile != null)
        {
            logger.debug("Moving file " + srcFile.getAbsolutePath() + " to " + destFile.getAbsolutePath());
            try
            {
                if (srcFile.isFile())
                {
                    logger.debug("Trying to rename file");
                    FileInputStream in = null;
                    FileOutputStream out = null;
                    try
                    {
                        in = new FileInputStream(srcFile);
                        out = new FileOutputStream(destFile);
                        out.getChannel().transferFrom(in.getChannel(), 0, srcFile.length());
                        isRenamed = true;
                    }
                    catch (Exception e)
                    {
                        logger.debug(e);
                    }
                    finally
                    {
                        if (in != null)
                        {
                            try
                            {
                                in.close();
                            }
                            catch (Exception inNotClosed)
                            {
                                logger.debug(inNotClosed);
                            }
                        }
                        if (out != null)
                        {
                            try
                            {
                                out.close();
                            }
                            catch (Exception outNotClosed)
                            {
                                logger.debug(outNotClosed);
                            }
                        }
                    }
                    logger.debug("File renamed: " + isRenamed);
                    if (isRenamed)
                    {
                        removeFile(srcFile);
                    }
                    else
                    {
                        removeFile(destFile);
                    }
                }
                else
                {
                    logger.debug(srcFile.getAbsolutePath() + " is not a valid file");
                }
            }
            catch (Exception e)
            {
                logger.debug("Error moving file from " + srcFile.getAbsolutePath() + " to " + destFile.getAbsolutePath());
            }
        }
        return isRenamed;
    }

    public static boolean renameFile(String srcFilePath, String destFilePath)
    {
        if (StringUtils.isNotBlank(srcFilePath) && StringUtils.isNotBlank(destFilePath))
        {
            return renameFile(new File(srcFilePath), new File(destFilePath));
        }
        else
        {
            return false;
        }
    }
    
    public static boolean renameFile(File srcFile, File destFile)
    {
        boolean isRenamed = false;
        if (srcFile != null && destFile != null)
        {
            logger.debug("Moving file " + srcFile.getAbsolutePath() + " to " + destFile.getAbsolutePath());
            if (!destFile.exists())
            {
                try
                {
                    if (srcFile.isFile())
                    {
                        logger.debug("Trying to rename file");
                        isRenamed = srcFile.renameTo(destFile);
                        if (!isRenamed && srcFile.exists())
                        {
                            logger.debug("Trying hard copy, assuming partition crossing ...");
                            isRenamed = renameFileHard(srcFile, destFile);
                        }
                        logger.debug("File renamed: " + isRenamed);
                    }
                    else
                    {
                        logger.debug(srcFile.getAbsolutePath() + " is not a valid file");
                    }
                }
                catch (Exception e)
                {
                    logger.debug("Error moving file from " + srcFile.getAbsolutePath() + " to " + destFile.getAbsolutePath(), e);
                }
            }
            else
            {
                logger.debug("Error renaming file " + srcFile.getAbsolutePath() + ". Destination file " + destFile.getAbsolutePath() + " already exists.");
            }
        }
        else
        {
            logger.debug("Error renaming file. Source or destination file is null.");
        }
    
        return isRenamed;
    }


    public static boolean touchFile(File file)
    {
        boolean isSuccess = false;
        if (file != null)
        {
            logger.debug("Create or update file " + file.getAbsolutePath());
            try
            {
                // Update timestamp if file already exists
                if (file.createNewFile() == false)
                {
                    file.setLastModified((new Date()).getTime());
                }
                isSuccess = true;
            }
            catch (Exception e)
            {
                logger.debug("Error touching empty file " + file.getAbsolutePath());
            }
        }
        return isSuccess;
    }
    
    public static boolean touchFile(String filePath)
    {
        return touchFile(new File(filePath));
    }

    public static boolean createDir(String dirPath)
    {
        return createDir(new File(dirPath));
    }
    
    public static boolean createDir(File dir)
    {
        boolean isCreated = false;
        if (dir != null)
        {
            logger.debug("Creating dir: " + dir.getAbsolutePath());
            try
            {
                isCreated = dir.mkdir();
            }
            catch (Exception e)
            {
                logger.debug("Error creating dir " + dir.getAbsolutePath());
            }
        }
        return isCreated;
    }

    public static boolean removeDir(String dirPath)
    {
        return removeDir(dirPath, false);
    }

    public static boolean removeDir(String dirPath, boolean isRecursive)
    {
        return removeDir(new File(dirPath), isRecursive);
    }

    public static boolean removeDir(File dir)
    {
        return removeDir(dir, false);   
    }
    
    public static boolean removeDir(File dir, boolean isRecursive)
    {
        boolean isDeleted = false;
        if (dir != null && dir.isDirectory())
        {
            try
            {
                if (isRecursive)
                {
                    File files[] = dir.listFiles();
                    for (int i = 0; i < files.length; i++)
                    {
                        if (files[i].isDirectory())
                        {
                            removeDir(files[i], true);
                        }
                        else
                        {
                            removeFile(files[i]);
                        }
                    }
                }

                logger.debug("removing dir " + dir.getAbsolutePath());
                isDeleted = dir.delete();
            }
            catch (Exception e)
            {
                logger.debug("Error removing dir " + dir.getAbsolutePath() + " : " + e.getMessage());
            }
        }
        return isDeleted;
    }

    public static boolean removeFile(String filePath)
    {
        return removeFile(new File(filePath));
    }

    public static boolean removeFile(File file)
    {
        boolean isDeleted = false;
        if (file != null && file.isFile())
        {
            try
            {
                logger.debug("removing file " + file.getAbsolutePath());
                isDeleted = file.delete();
            }
            catch (Exception e)
            {
                logger.debug("Error removing file " + file.getAbsolutePath() + " : " + e.getMessage());
            }
        }
        return isDeleted;
    }

    public static boolean doesFileExist(String filePath)
    {
        return doesFileExist(new File(filePath));
    }

    public static boolean doesFileExist(File file)
    {
        return file.exists() && file.isFile();
    }
    

    public static boolean doesDirExist(String filePath)
    {
        return doesDirExist(new File(filePath));
    }    

    public static boolean doesDirExist(File dir)
    {
        return dir.exists() && dir.isDirectory();
    }
}

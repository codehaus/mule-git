/*
 * $Id:$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.boot.util;

import org.mule.tck.AbstractMuleTestCase;

import java.io.File;
import java.io.IOException;

/**
 * Test suite for file utilities. 
 */
public class FileUtilsTestCase extends AbstractMuleTestCase
{
    private File createTestFile(String filePath) throws IOException
    {
        return File.createTempFile(filePath, ".junit");
    }
    
    public void testRenameFile()
    {
        try
        {
            File source = createTestFile("source");
            File dest = createTestFile("dest");
    
            assertTrue(FileUtils.removeFile(dest));
            assertTrue(FileUtils.renameFile(source, dest));
            assertTrue(FileUtils.doesFileExist(dest));
            assertTrue(FileUtils.removeFile(dest));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testTouchFile()
    {
        try
        {
            File file = createTestFile("touch");

            assertTrue(FileUtils.touchFile(file));
            assertTrue(FileUtils.removeFile(file));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testCreateDir()
    {
        try
        {
            File dir = createTestFile("dir");

            assertTrue(FileUtils.removeFile(dir));
            assertTrue(FileUtils.createDir(dir));
            assertTrue(FileUtils.removeDir(dir));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testRemoveDir()
    {
        try
        {
            File dir = createTestFile("remove");
            
            assertTrue(FileUtils.removeFile(dir));
            assertTrue(FileUtils.createDir(dir));
            assertTrue(FileUtils.removeDir(dir));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testRemoveDirRecursivelyTrue()
    {
        try
        {
            File dir = createTestFile("remove");
            File subDir = new File(dir, "sub");
            
            assertTrue(FileUtils.removeFile(dir));
            assertTrue(FileUtils.createDir(dir));
            assertTrue(FileUtils.createDir(subDir));
            assertTrue(FileUtils.removeDir(dir, true));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testRemoveDirRecursivelyFalse()
    {
        try
        {
            File dir = createTestFile("remove");
            File subDir = new File(dir, "sub");
            
            assertTrue(FileUtils.removeFile(dir));
            assertTrue(FileUtils.createDir(dir));
            assertTrue(FileUtils.createDir(subDir));
            assertFalse(FileUtils.removeDir(dir, false));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    public void testRemoveFile()
    {
        try
        {
            File file = createTestFile("remove");
            
            assertTrue(FileUtils.removeFile(file));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testDoesDirExist()
    {
        try
        {
            File dir = createTestFile("doesDirExist");

            assertTrue(FileUtils.removeFile(dir));
            assertTrue(FileUtils.createDir(dir));
            assertTrue(FileUtils.doesDirExist(dir));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
    
    public void testDoesFileExist()
    {
        try
        {
            File file = createTestFile("doesFileExist");

            assertTrue(FileUtils.doesFileExist(file));
            assertTrue(FileUtils.removeFile(file));
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }
}

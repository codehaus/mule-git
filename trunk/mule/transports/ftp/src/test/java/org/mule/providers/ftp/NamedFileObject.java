/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.ftp;

import org.apache.ftpserver.ftplet.FileObject;

public abstract class NamedFileObject implements FileObject {

    private String name;

    public NamedFileObject(String name)
    {
        this.name = name;
    }

    public String getFullName()
    {
        return name;
    }

    public String getShortName()
    {
        return name;
    }

    public boolean isHidden()
    {
        return false;
    }

    public boolean isDirectory()
    {
        return true;
    }

    public boolean doesExist()
    {
        return true;
    }

    public boolean hasReadPermission()
    {
        return true;
    }

    public boolean hasWritePermission()
    {
        return true;
    }

    public boolean hasDeletePermission()
    {
        return false;
    }

    public String getOwnerName()
    {
        return null;
    }

    public String getGroupName()
    {
        return null;
    }

    public int getLinkCount()
    {
        return 0;
    }

    public long getLastModified()
    {
        return 0;
    }

    public long getSize()
    {
        return 0;
    }

    public boolean mkdir()
    {
        return false;
    }

    public boolean delete()
    {
        return false;
    }

    public boolean move(FileObject destination)
    {
        return false;
    }

}

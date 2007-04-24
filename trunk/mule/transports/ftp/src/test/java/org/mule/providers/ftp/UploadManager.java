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

import org.apache.ftpserver.ftplet.FileSystemManager;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.FtpException;

public class UploadManager implements FileSystemManager
{

    private FtpFunctionalTestCase.UploadView view;

    public void setViewFromSystemProperties(String key)
    {
        view = (FtpFunctionalTestCase.UploadView)System.getProperties().get(key);
        view.flagStarted();
    }

    public FileSystemView createFileSystemView(User user) throws FtpException
    {
        return view;
    }

}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule;

import org.mule.util.FileUtils;

import java.io.File;
import java.io.IOException;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.transaction.TransactionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ManagementContext
{

    protected transient Log logger = LogFactory.getLog(getClass());

    protected String jmxDomainName;
    protected File workingDir;
    protected TransactionManager transactionManager;
    protected MBeanServer mBeanServer;
    protected InitialContext namingContext;

    // protected Registry registry;

    // public Registry getRegistry() {
    // return registry;
    // }

    // public void setRegistry(Registry registry) {
    // this.registry = registry;
    // }

    public String getJmxDomainName()
    {
        return jmxDomainName;
    }

    public void setJmxDomainName(String jmxDomainName)
    {
        this.jmxDomainName = jmxDomainName;
    }

    public File getWorkingDir()
    {
        return workingDir;
    }

    public void setWorkingDir(File workingDir)
    {
        this.workingDir = workingDir;
    }

    public TransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    public void setTransactionManager(TransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    public MBeanServer getMBeanServer()
    {
        return mBeanServer;
    }

    public void setMBeanServer(MBeanServer mBeanServer)
    {
        this.mBeanServer = mBeanServer;
    }

    public InitialContext getNamingContext()
    {
        return namingContext;
    }

    public void setNamingContext(InitialContext namingContext)
    {
        this.namingContext = namingContext;
    }

    public ObjectName createMBeanName(String componentName, String type, String name)
    {
        try
        {
            StringBuffer sb = new StringBuffer();
            sb.append(getJmxDomainName()).append(':');
            if (componentName != null)
            {
                sb.append("component=").append(validateString(componentName));
                sb.append(',');
            }
            sb.append("type=").append(validateString(type));
            if (name != null)
            {
                sb.append(',');
                sb.append("name=").append(validateString(name));
            }
            return new ObjectName(sb.toString());
        }
        catch (MalformedObjectNameException e)
        {
            logger.error("Could not create component mbean name", e);
            return null;
        }
    }

    private String validateString(String str)
    {
        str = str.replace(':', '_');
        str = str.replace('/', '_');
        str = str.replace('\\', '_');
        return str;
    }

    public static final String TEMP_DIR = "temp";
    public static final String COMPONENTS_DIR = "components";
    public static final String LIBRARIES_DIR = "libraries";
    public static final String ASSEMBLIES_DIR = "assemblies";
    public static final String INSTALL_DIR = "install";
    public static final String DEPLOY_DIR = "deploy";
    public static final String PROCESSED_DIR = "processed";
    public static final String WORKSPACE_DIR = "workspace";

    private int counter;

    public synchronized File getNewTempDir(File rootDir)
    {
        while (true)
        {
            String s = Integer.toHexString(++counter);
            while (s.length() < 8)
            {
                s = "0" + s;
            }
            File f = FileUtils.newFile(rootDir, File.separator + TEMP_DIR + File.separator + s);
            if (!f.exists())
            {
                return f;
            }
        }
    }

    public File getComponentInstallDir(File rootDir, String name)
    {
        return FileUtils.newFile(rootDir, COMPONENTS_DIR + File.separator + validateString(name));
    }

    public File getComponentWorkspaceDir(File rootDir, String name)
    {
        return FileUtils.newFile(rootDir, WORKSPACE_DIR + File.separator + validateString(name));
    }

    public File getLibraryInstallDir(File rootDir, String name)
    {
        return FileUtils.newFile(rootDir, LIBRARIES_DIR + File.separator + validateString(name));
    }

    public File getAssemblyInstallDir(File rootDir, String name)
    {
        return FileUtils.newFile(rootDir, ASSEMBLIES_DIR + File.separator + validateString(name));
    }

    public static File getAutoInstallDir(File rootDir)
    {
        return FileUtils.newFile(rootDir, INSTALL_DIR);
    }

    public File getAutoInstallProcessedDir(File rootDir)
    {
        return FileUtils.newFile(rootDir, INSTALL_DIR + File.separator + PROCESSED_DIR);
    }

    public File getAutoDeployDir(File rootDir)
    {
        return FileUtils.newFile(rootDir, DEPLOY_DIR);
    }

    public File getAutoDeployProcessedDir(File rootDir)
    {
        return FileUtils.newFile(rootDir, DEPLOY_DIR + File.separator + PROCESSED_DIR);
    }

    public void deleteMarkedDirectories(File dir)
    {
        if (dir != null && dir.isDirectory())
        {
            if (FileUtils.newFile(dir, ".delete").isFile())
            {
                deleteDir(dir);
            }
            else
            {
                File[] children = dir.listFiles();
                for (int i = 0; i < children.length; i++)
                {
                    if (children[i].isDirectory())
                    {
                        deleteMarkedDirectories(children[i]);
                    }
                }
            }
        }
    }

    public void deleteDir(String dir)
    {
        deleteDir(FileUtils.newFile(dir));
    }

    public void deleteDir(File dir)
    {
        FileUtils.deleteTree(dir);
        if (dir.isDirectory())
        {
            try
            {
                FileUtils.newFile(dir, ".delete").createNewFile();
            }
            catch (IOException e)
            {
                logger.warn("Could not mark directory to be deleted", e);
            }
        }
    }

    public void createDirectories(File rootDir) throws IOException
    {
        FileUtils.createFile(rootDir.getAbsolutePath());
        FileUtils.createFile(new File(rootDir, COMPONENTS_DIR).getAbsolutePath());
        FileUtils.createFile(new File(rootDir, WORKSPACE_DIR).getAbsolutePath());
        FileUtils.createFile(new File(rootDir, LIBRARIES_DIR).getAbsolutePath());
        FileUtils.createFile(new File(rootDir, ASSEMBLIES_DIR).getAbsolutePath());
        FileUtils.createFile(getAutoInstallDir(rootDir).getAbsolutePath());
        FileUtils.createFile(getAutoDeployDir(rootDir).getAbsolutePath());
        FileUtils.createFile(getAutoDeployProcessedDir(rootDir).getAbsolutePath());
    }
}

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.boot;

import org.mule.util.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;
import org.tanukisoftware.wrapper.WrapperSimpleApp;

/**
 * Determine which is the main class to run and delegate control to the Java Service
 * Wrapper. <p/> MuleBootstrap class is responsible for constructing Mule's classpath
 * from the Mule home folder.
 * 
 * IMPORTANT NOTE:
 * <p>When using external functionality from packages outside of <code>./lib/boot</code>
 * you must not use them within <code>main(String args[])</code>. Although this will safely
 * work with Sun's VM it'll fail on e.g. JRockit. In general, it is recommended to test
 * against JRockit in addition to Sun's VM when making changes within this module.</p> 
 */
public final class MuleBootstrap
{
    private static final String MULE_MODULE_BOOT_POM_FILE_PATH = "META-INF/maven/org.mule.modules/mule-module-boot/pom.properties";
    
    public static final String MULE_SERVER_WRAPPER = "org.mule.modules.boot.MuleServerWrapper";
    public static final String MULE_VERSION_WRAPPER = "org.mule.modules.boot.VersionWrapper";
    
    public static final String CLI_OPTIONS[][] = {
        {"main", "true", "Main Class"},
        {"version", "false", "Show product and version information"}
    };
    
    private MuleBootstrap()
    {
        // utility class only
    }

    public static void main(String args[]) throws Exception
    {
        prepareBootstrapPhase();
        
        CommandLine commandLine = parseCommandLine(args);
        String[] remainingArgs = commandLine.getArgs();
        
        String mainClassName = commandLine.getOptionValue("main");
        if (commandLine.hasOption("version"))
        {
            mainClassName = MULE_VERSION_WRAPPER;
        }
        else if (mainClassName == null)
        {
            mainClassName = MULE_SERVER_WRAPPER;
        }
        
        if (mainClassName.equals(MULE_SERVER_WRAPPER) || mainClassName.equals(MULE_VERSION_WRAPPER))
        {
            WrapperManager.start((WrapperListener) Class.forName(mainClassName).newInstance(), remainingArgs);
        }
        else
        {
            wrapperMain(mainClassName, args);
        }    
    }
    
    private static void prepareBootstrapPhase() throws Exception
    {
        File muleHome = lookupMuleHome();
        File muleBase = lookupMuleBase();

        if (muleBase == null)
        {
            muleBase = muleHome;
        }

        MuleBootstrapUtils.addLocalJarFilesToClasspath(muleHome, muleBase);
        MuleBootstrapUtils.addExternalJarFilesToClasspath(muleHome, null);
        
        setSystemMuleVersion();
        requestLicenseAcceptance();           
    }
    
    private static File lookupMuleHome() throws Exception
    {
        File muleHome = null;
        String muleHomeVar = System.getProperty("mule.home");
        
        if (muleHomeVar != null && !muleHomeVar.trim().equals("") && !muleHomeVar.equals("%MULE_HOME%"))
        {
            muleHome = new File(muleHomeVar).getCanonicalFile();
        }

        if (muleHome == null || !muleHome.exists() || !muleHome.isDirectory())
        {
            throw new IllegalArgumentException("Either MULE_HOME is not set or does not contain a valid directory.");
        }
        return muleHome;
    }
    
    private static File lookupMuleBase() throws Exception
    {
        File muleBase = null;
        String muleBaseVar = System.getProperty("mule.base");
        
        if (muleBaseVar != null && !muleBaseVar.trim().equals("") && !muleBaseVar.equals("%MULE_BASE%"))
        {
            muleBase = new File(muleBaseVar).getCanonicalFile();
        }
        return muleBase;
    }

    private static void requestLicenseAcceptance() throws Exception
    {
        if (!LicenseHandler.isLicenseAccepted() && !LicenseHandler.getAcceptance())
        {
            WrapperManager.stop(-1);
        }        
    }
    
    private static void setSystemMuleVersion()
    {
        try
        {
            URL mavenPropertiesUrl = ClassUtils.getResource(MULE_MODULE_BOOT_POM_FILE_PATH, MuleServerWrapper.class);
            Properties mavenProperties = new Properties();
            mavenProperties.load(mavenPropertiesUrl.openStream());
            
            System.setProperty("mule.version", mavenProperties.getProperty("version"));
            System.setProperty("mule.reference.version", mavenProperties.getProperty("version") + '-' + (new Date()).getTime());
        }
        catch (Exception ignore)
        {
            // ignore
        }
    }    
    
    private static CommandLine parseCommandLine(String[] args) throws ParseException
    {
        Options options = new Options();
        for (int i = 0; i < CLI_OPTIONS.length; i++)
        {
            options.addOption(CLI_OPTIONS[i][0], "true".equalsIgnoreCase(CLI_OPTIONS[i][1]), CLI_OPTIONS[i][2]);
        }
        return new BasicParser().parse(options, args, true);
    }
    
    private static void wrapperMain(String mainClassName, String[] args) throws Exception
    {
        String[] appArgs = new String[args.length + 1];
        appArgs[0] = mainClassName;
        System.arraycopy(args, 0, appArgs, 1, args.length);
        System.out.println("Starting class " + mainClassName + "...");
        WrapperSimpleApp.main(appArgs);
    }    
}

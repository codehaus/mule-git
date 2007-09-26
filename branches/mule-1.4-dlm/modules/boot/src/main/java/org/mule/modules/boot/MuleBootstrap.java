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

import java.io.File;

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
    public static final String MAIN_CLASS_MULE_SERVER = "org.mule.modules.boot.MuleServerWrapper";

    private MuleBootstrap()
    {
        // utility class only
    }

    public static void main(String args[]) throws Exception
    {
        File muleHome = lookupMuleHome();
        File muleBase = lookupMuleBase();

        if (muleBase == null)
        {
            muleBase = muleHome;
        }

        MuleBootstrapUtils.addLocalJarFilesToClasspath(muleHome, muleBase);
        MuleBootstrapUtils.addExternalJarFilesToClasspath(muleHome, null);
        wrapperMain(MAIN_CLASS_MULE_SERVER, args);
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
    
    private static void wrapperMain(String mainClass, String[] args) throws Exception
    {
        String[] appArgs = new String[args.length + 1];
        appArgs[0] = mainClass;
        System.arraycopy(args, 0, appArgs, 1, args.length);
        WrapperSimpleApp.main(appArgs);
    }    
}

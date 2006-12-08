/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.boot;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;
import org.tanukisoftware.wrapper.WrapperSimpleApp;

/**
 * Determine which is the main class to run and delegate control to the Java Service
 * Wrapper.  If OSGi is not being used to boot with, configure the classpath based on 
 * the libraries in $MULE_HOME/lib/*
 * 
 * Note: this class is intentionally kept free of any external library dependencies and
 * therefore repeats a few utility methods.  
 */
public class MuleBootstrap 
{
	public static final String MAIN_CLASS_MULE_SERVER = "org.mule.modules.boot.MuleServerWrapper";
	public static final String MAIN_CLASS_OSGI_FRAMEWORK = "org.mule.modules.osgi.OsgiFrameworkWrapper";
	
    public static void main( String[] args ) throws Exception
    {
        String mainClassName = getCommandLineOption("-main", args);

        if (mainClassName == null || mainClassName.equals(MAIN_CLASS_MULE_SERVER))
        {
        	configureClasspath();
        	System.out.println("Starting the Mule Server...");
        	WrapperManager.start((WrapperListener) Class.forName(MAIN_CLASS_MULE_SERVER).newInstance(), args);
        }
        else if (mainClassName.equalsIgnoreCase("osgi") || mainClassName.equals(MAIN_CLASS_OSGI_FRAMEWORK))
        {
        	System.out.println("Starting the OSGi Framework...");
        	WrapperManager.start((WrapperListener) Class.forName(MAIN_CLASS_OSGI_FRAMEWORK).newInstance(), args);
        }
        else 
        {
	        // Add the main class name as the first argument to the Wrapper.
	        String[] appArgs = new String[args.length + 1];
	        appArgs[0] = mainClassName;
	        System.arraycopy(args, 0, appArgs, 1, args.length);
        	configureClasspath();
        	System.out.println("Starting class " + mainClassName + "...");
	        WrapperSimpleApp.main(appArgs);
        }
    }

    private static void configureClasspath() 
    	throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException
    {
        // Make sure MULE_HOME is set.
        File muleHome = null;

        String muleHomeVar = System.getProperty("mule.home");
        // Note: we can't use StringUtils.isBlank() here because we don't have that library yet.
        if (muleHomeVar != null && !muleHomeVar.trim().equals("") && !muleHomeVar.equals("%MULE_HOME%")) {
            muleHome = new File(muleHomeVar).getCanonicalFile();
        }
        if (muleHome == null || !muleHome.exists() || !muleHome.isDirectory()) {
            throw new IllegalArgumentException(
                "Either MULE_HOME is not set or does not contain a valid directory.");
        }

        File muleBase = null;

        String muleBaseVar = System.getProperty("mule.base");
        if (muleBaseVar != null && !muleBaseVar.trim().equals("") && !muleBaseVar.equals("%MULE_BASE%")) {
            muleBase = new File(muleBaseVar).getCanonicalFile();
        } else {
            muleBase = muleHome;
        }

        // Build up a list of libraries from $MULE_HOME/lib/* and add them to the classpath.
        DefaultMuleClassPathConfig classPath = new DefaultMuleClassPathConfig(muleHome, muleBase);
        addLibrariesToClasspath(classPath.getURLs());

        // One-time download to get libraries not included in the Mule distribution due
        // to silly licensing restrictions.
        // 
        // Now we will download these libraries to MULE_BASE/lib/user. In
        // a standard installation, MULE_BASE will be MULE_HOME.
        if (!isClassOnPath("javax.activation.DataSource")) {
            LibraryDownloader downloader = new LibraryDownloader(muleBase);
            addLibrariesToClasspath(downloader.downloadLibraries());
        }
    }

    private static void addLibrariesToClasspath(List urls)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        ClassLoader sys = ClassLoader.getSystemClassLoader();
        if (!(sys instanceof URLClassLoader))
        {
            throw new IllegalArgumentException(
                "PANIC: Mule has been started with an unsupported classloader: " + sys.getClass().getName()
                                + ". " + "Please report this error to user<at>mule<dot>codehaus<dot>org");
        }

        // system classloader is in this case the one that launched the application,
        // which is usually something like a JDK-vendor proprietary AppClassLoader
        URLClassLoader sysCl = (URLClassLoader)sys;

        /*
         * IMPORTANT NOTE: The more 'natural' way would be to create a custom
         * URLClassLoader and configure it, but then there's a chicken-and-egg
         * problem, as all classes MuleBootstrap depends on would have been loaded by
         * a parent classloader, and not ours. There's no straightforward way to
         * change this, and is documented in a Sun's classloader guide. The solution
         * would've involved overriding the ClassLoader.findClass() method and
         * modifying the semantics to be child-first, but that way we are calling for
         * trouble. Hacking the primordial classloader is a bit brutal, but works
         * perfectly in case of running from the command-line as a standalone app.
         * All Mule embedding options then delegate the classpath config to the
         * embedder (a developer embedding Mule in the app), thus classloaders are
         * not modified in those scenarios.
         */

        // get a Method ref from the normal class, but invoke on a proprietary parent
        // object,
        // as this method is usually protected in those classloaders
        Class refClass = URLClassLoader.class;
        Method methodAddUrl = refClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        methodAddUrl.setAccessible(true);
        for (Iterator it = urls.iterator(); it.hasNext();)
        {
            URL url = (URL)it.next();
            // System.out.println("Adding: " + url.toExternalForm());
            methodAddUrl.invoke(sysCl, new Object[]{url});
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following utility methods are included here in order to keep the bootloader 
    // free of any external library dependencies.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Imitates ClassUtils.isClassOnPath()
     */
    private static boolean isClassOnPath(String className) {
    	boolean found = false;   	
    	try {
    		found = (Thread.currentThread().getContextClassLoader().loadClass(className) != null);
    	} catch (ClassNotFoundException e) { }
    	if (!found) {
	    	try {
	    		found = (Class.forName(className) != null);
	    	} catch (ClassNotFoundException e) { }
    	}
    	if (!found) {
	    	try {
	    		found = (MuleBootstrap.class.getClassLoader().loadClass(className) != null);
	    	} catch (ClassNotFoundException e) { }
    	}
    	return found;
    }

    /**
     * Imitates SystemUtils.getCommandLineOption()
     */
    private static String getCommandLineOption(String option, String args[])
    {
        List options = Arrays.asList(args);
        if (options.contains(option))
        {
            int i = options.indexOf(option);
            if (i < options.size() - 1)
            {
                return options.get(i + 1).toString();
            }
        }
        return null;
    }
}

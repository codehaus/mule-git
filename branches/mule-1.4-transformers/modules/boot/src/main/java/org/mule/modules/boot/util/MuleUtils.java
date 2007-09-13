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

public final class MuleUtils
{
    private static final String MULE_LIB_FILENAME = "lib" + File.separator + "mule";
    private static final String MULE_HOME = System.getProperty("mule.home");
    
    public static final String MULE_LOCAL_JAR_FILENAME = "mule-local-install.jar";

    private MuleUtils()
    {
        // utility class only
    }
    
    public static File getMuleHomeFile()
    {
        return new File(MULE_HOME);
    }
    
    public static File getMuleLibDir()
    {   
        return new File(MULE_HOME + File.separator + MULE_LIB_FILENAME);
    }
    
    public static File getMuleLocalJarFile()
    {
        return new File(getMuleLibDir(), MULE_LOCAL_JAR_FILENAME);
    }
}

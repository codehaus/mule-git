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

import org.mule.MuleServer;
import org.mule.util.ClassUtils;

import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.tanukisoftware.wrapper.WrapperManager;

public final class MuleServerWrapper
{
    private static final Log logger = LogFactory.getLog(MuleServerWrapper.class);
    
    private static final String MULE_MODULE_BOOT_POM_FILE_PATH = "META-INF/maven/org.mule.modules/mule-module-boot/pom.properties";
    
    private MuleServerWrapper()
    {
        // utility class only
    }

    public static void main(String args[]) throws Exception
    {
        setSystemMuleVersion();
        requestLicenseAcceptance();
        MuleServer.main(args);
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
            logger.debug(ignore);
        }
    }
}

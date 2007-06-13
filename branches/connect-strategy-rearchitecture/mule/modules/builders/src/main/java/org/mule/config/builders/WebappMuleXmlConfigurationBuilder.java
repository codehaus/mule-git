/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.builders;

import org.mule.config.ConfigurationException;

import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletContext;

/**
 * <code>WebappMuleXmlConfigurationBuilder</code> will first try and load config
 * resources from the Servlet context. If this fails it fails back to the methods
 * used by the MuleXmlConfigurationBuilder.
 */
public class WebappMuleXmlConfigurationBuilder extends MuleXmlConfigurationBuilder
{
    private ServletContext context;
    
    /**
     * Classpath within the servlet context (e.g., "WEB-INF/classes").  Mule will attempt to load config 
     * files from here first, and then from the remaining classpath.
     */
    private String webappClasspath;

    public WebappMuleXmlConfigurationBuilder(ServletContext context, String webappClasspath) 
        throws ConfigurationException
    {
        super();
        this.context = context;
        this.webappClasspath = webappClasspath;
    }

    /**
     * Attempt to load any resource from the Servlet Context first, then from the classpath.
     */
    protected InputStream loadResource(String resource) throws ConfigurationException
    {
        String resourcePath = new File(webappClasspath, resource).getPath();
        logger.debug("Searching for resource " + resourcePath + " in Servlet Context.");
        InputStream is = context.getResourceAsStream(resourcePath);
        if (is == null)
        {
            logger.debug("Resource " + resourcePath + " not found in Servlet Context, loading from classpath");
            is = super.loadResource(resource);
        }
        return is;
    }
}

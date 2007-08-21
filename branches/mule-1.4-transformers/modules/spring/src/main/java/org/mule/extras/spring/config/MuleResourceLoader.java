/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * By default, a resource is loaded directly from the classpath.  Override this method to try loading it
 * from the file system first.
 */
public class MuleResourceLoader extends DefaultResourceLoader 
{
    protected transient Log logger = LogFactory.getLog(MuleResourceLoader.class);
    
    /**
     * By default, a resource is loaded directly from the classpath.  Override this method to try loading it
     * from the file system first.
     */
    //@Override
    protected Resource getResourceByPath(String path)
    {
        Resource r = new FileSystemResource(path);
        if (logger.isDebugEnabled())
        {
            logger.debug("Attempting to load resource from file system: " + ((FileSystemResource) r).getFile().getAbsolutePath());
        }
        if (r.exists())
        {
            return r;
        }
        else
        {
            logger.debug("Resource does not exist on file system, loading from classpath.");
            r = new ClassPathResource(path, getClassLoader());
            return r;
        }
    }
}

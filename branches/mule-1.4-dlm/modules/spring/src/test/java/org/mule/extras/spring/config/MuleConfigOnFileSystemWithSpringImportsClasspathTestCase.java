/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;


/**
 * Tests a Mule config from the file system (i.e., not from the classpath) which loads Spring configs 
 * via <code>&lt;import resource="classpath:file.xml"/&gt;</code> statements.  The Spring configs will be loaded from the classpath
 * instead of the same directory as the Mule config because of the "classpath:" prefix (Spring uses relative path 
 * by default).
 */
public class MuleConfigOnFileSystemWithSpringImportsClasspathTestCase extends MuleConfigOnFileSystemWithSpringImportsTestCase
{
    public String getConfigResources()
    {
        // TODO TC this is guaranteed to fail, when e.g. running in a reactor build fromt the root
        // update: it work on CI, but fails for a single run in IDE, maybe getting all resources
        // from classpath and copying them to the target folder for the test would be more robust?
        return "./src/test/resources/mule-config-with-imports-classpath.xml";
    }
}



/*
 * $Id: SpringAutowireConfigBuilderTestCase.java 3765 2006-10-31 19:38:26Z holger $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.config;


/**
 * Tests a single Spring container with more than one Spring config file.
 */
public class MultipleSpringConfigsTestCase extends MultipleSpringContextsTestCase
{
    public String getConfigResources()
    {
        return "spring-context-multiple-configs.xml";
    }
}

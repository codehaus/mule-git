/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.apache.felix.tools.maven2.bundleplugin;

import junit.framework.TestCase;

public class BundlePluginTestCase extends TestCase
{
    public void testVersionTransformation() {
        assertEquals("2.0", BundlePlugin.fixBundleVersion("2.0"));
        // Characters are only allowed in the 4th position so an extra zero should be added.
        assertEquals("2.0.0.SNAPSHOT", BundlePlugin.fixBundleVersion("2.0-SNAPSHOT"));
        assertEquals("2.0.0.REGISTRY", BundlePlugin.fixBundleVersion("2.0-REGISTRY"));        
    }
}



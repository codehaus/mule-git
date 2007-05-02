/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.schema.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.resource.Resource;
import org.mule.schema.util.MuleResourceFactoryImpl;

/**
 * Test marshalling data to/from XML using Ecore.
 */
public class MuleSchemaTests extends TestCase {

	public void testLoadSave() {
		try {
			Resource.Factory factory = new MuleResourceFactoryImpl();
			Resource resource = factory.createResource(null);
			InputStream stream = new FileInputStream("xml/mule-config.xml");
			resource.load(stream, Collections.EMPTY_MAP);
			assertTrue("Could not find resource via URI.", resource.isLoaded());
			resource.save(System.out, Collections.EMPTY_MAP);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
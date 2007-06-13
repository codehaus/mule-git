/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.core.distribution;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jdt.core.IClasspathEntry;

/**
 * Interface for inquiring about a Mule distribution 
 */
public interface IMuleDistribution {

	/**
	 * Returns the location of the Mule Distribution 
	 * 
	 * @return
	 */
	File getLocation();

	/**
	 * Validates the Mule distribution
	 * 
	 * @return Whether or not the Mule distribution is valid 
	 */
	boolean isValid();
	
	/**
	 * @return The Mule version number. 
	 */
	String getVersion();
	
	/**
	 * @return Array containing all samples distributed with this Mule distribution.
	 * May be empty, but never null.
	 */
	IMuleSample[] getSuppliedSamples();

	/**
	 * @return The bundle describing the core plugin. Is never null.
	 */
	IMuleBundle getCoreModule();
	
	/**
	 * Returns all modules found in this Mule distribution.
	 * Modules may contain components, routers, transformers, etc.
	 * 
	 * @return Array containing all the modules. May be empty, but never null.
	 */
	IMuleBundle[] getMuleModules();
	
	/**
	 * Returns all transports found in this Mule distribution.
	 * 
	 * @return Array containing all the transports. May be empty, but never null.
	 */
	IMuleBundle[] getMuleTransports();

	/**
	 * @param desiredBundles Array of bundles to compute dependencies for
	 * @return Possibly longer array containing the transitive closure of Mule bundles
	 * needed to support the "desiredBundles".
	 */
	IMuleBundle[] getTransitiveMuleDependencies(IMuleBundle[] desiredBundles);
	
	/**
	 * @param bundles The bundles to check for 3rd party dependencies
	 * @return Complete list of 3rd party files required on the classpath for supporting the
	 * array of Mule bundes passed in. 
	 */
	File[] getLibrariesDependencies(IMuleBundle[] bundles);
	
	/**
	 * @param selectedBundles Bundles to compute a classpath for
	 * @return Classpath entries for the desired Mule bundles and their third party
	 * libraries 
	 */
	public IClasspathEntry[] getClasspath(IMuleBundle[] selectedBundles);
	
	/**
	 * @param dtdName Name of the DTD to query for (mule-configuration.dtd or mule-spring-configuration.dtd) 
	 * @return An inputstream for the stream, or null
	 * 
	 * @throws IOException If the JAR file cannot be read
	 */
	InputStream getDTDContents(String dtdName) throws IOException;

}

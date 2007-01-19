/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ide.core.distribution;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
	
	URL[] getFullClasspath();
	
	String[] getDependencies(String moduleAndTransportNames[]);
	
	String[] getSuppliedModules();
	String[] getSuppliedTransports();
	
	InputStream getDTDContents(String dtdName) throws IOException;
	
	/**
	 * Call this after use, allowing cleanup of resources.
	 */
	void close();
}

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

abstract public class AbstractMuleDistribution implements IMuleDistribution {

	protected static final String CORE_BUNDLE_NAME = "core";
	protected static final String SPRING_MODULE_NAME = "module-spring";
	protected static final String MULE_BUNDLE_PREFIX = "mule-";

	private File location;
	private String version;
	
	public AbstractMuleDistribution(File location) {
		if (location == null || ! location.canRead()) throw new IllegalArgumentException("Bad location: " + location);
		this.location = location;
	}

	public void initialize() throws IOException {
		File coreJar = findCoreJarPath();
		JarFile jar = new JarFile(coreJar);
		try {
			Manifest manifest = new Manifest(jar.getInputStream(jar.getEntry("META-INF/MANIFEST.MF")));
			version = manifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
			if (version == null) {
				Properties pomProps = new Properties();
				pomProps.load(jar.getInputStream(jar.getEntry("META-INF/maven/org.mule/mule-core/pom.properties")));
				version = pomProps.getProperty("version");
			}
		} finally {
			jar.close();
		}
	}
	
	public File getLocation() {
		return location;
	}

	public String getVersion() {
		return version == null ? "N/A" : version;
	}

	public boolean isValid() {
		return version != null;
	}
	
	abstract protected File findCoreJarPath() throws FileNotFoundException;
	
	public InputStream getDTDContents(String dtdName) throws IOException {
		if ("mule-configuration.dtd".equals(dtdName)) {
			return getCoreJarStream("/mule-configuration.dtd");
		} else if ("mule-spring-configuration.dtd".equals(dtdName)) {
			return getSpringModuleStream("/mule-spring-configuration.dtd");
		}
		return null;
	}

	abstract protected InputStream getCoreJarStream(String relativePath) throws IOException;
	
	abstract protected InputStream getSpringModuleStream(String relativePath) throws IOException;
}

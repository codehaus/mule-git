package org.mule.ide.core.distribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

abstract public class AbstractMuleDistribution implements IMuleDistribution {

	private File location;
	protected Manifest manifest;
	private String version;
	
	public AbstractMuleDistribution(File location) {
		if (location == null || ! location.canRead()) throw new IllegalArgumentException("Bad location: " + location);
		this.location = location;
	}

	public void initialize() throws IOException {
		manifest = new Manifest(getCoreJarStream("META-INF/MANIFEST.MF"));
		version = manifest.getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
		if (version == null) {
			Properties pomProps = new Properties();
			pomProps.load(getCoreJarStream("META-INF/maven/org.mule/mule-core/pom.properties"));
			version = pomProps.getProperty("version");
		}
	}
	
	public File getLocation() {
		return location;
	}

	public String getVersion() {
		return version;
	}

	public boolean isValid() {
		try {
			File coreJar = getCoreJarPath();
			return coreJar.exists() && coreJar.isFile() && coreJar.canRead();
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	public InputStream getDTDContents(String dtdName) throws IOException {
		if ("mule-configuration.dtd".equals(dtdName)) {
			return getCoreJarStream("/mule-configuration.dtd");
		} else if ("mule-spring-configuration.dtd".equals(dtdName)) {
			return getSpringModuleStream("/mule-spring-configuration.dtd");
		}
		return null;
	}

	abstract protected File getCoreJarPath() throws FileNotFoundException;
	
	abstract protected File getSpringModuleJarPath() throws FileNotFoundException;

	abstract protected InputStream getCoreJarStream(String relativePath) throws IOException;
	
	abstract protected InputStream getSpringModuleStream(String relativePath) throws IOException;
}

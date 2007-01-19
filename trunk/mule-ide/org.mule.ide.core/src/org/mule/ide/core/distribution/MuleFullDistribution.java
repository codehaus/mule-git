package org.mule.ide.core.distribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;

public class MuleFullDistribution extends AbstractMuleDistribution {

	private JarFile muleCoreJar;
	private JarFile muleSpringJar;
	
	public MuleFullDistribution(File rootDirectory) {
		super(rootDirectory);
		if (! rootDirectory.isDirectory())
			throw new IllegalArgumentException("rootDirectory MUST be a directory");
	}

	public void close() {
		if (muleCoreJar != null) {
			try {
				muleCoreJar.close();
			} catch (IOException e) {
				// Safe to ignore as we never write to it 
			}
			muleCoreJar = null;
		}
		if (muleSpringJar != null) {
			try {
				muleSpringJar.close();
			} catch (IOException e) {
				// Safe to ignore as we never write to it 
			}
			muleSpringJar = null;
		}
	}

	public URL[] getFullClasspath() {
		return new URL[0];
	}

	public String[] getSuppliedModules() {
		return new String[0];
	}

	public String[] getSuppliedTransports() {
		return new String[0];
	}

	private File coreJarPath = null;
	
	protected File getCoreJarPath() throws FileNotFoundException {
		if (coreJarPath == null) {
			File cores[] = new File(getLocation(), "lib/mule/").listFiles(new FilenameFilter() {
				public boolean accept(File dir, String file) {
					return file.startsWith("mule-core-") && file.endsWith(".jar");
				}});
			if (cores == null || cores.length != 1) throw new FileNotFoundException("No Core jar");
			coreJarPath = cores[0];
		}
		return coreJarPath;
	}
	
	protected InputStream getCoreJarStream(String relativePath) throws IOException {
		if (muleCoreJar == null) {
			muleCoreJar = new JarFile(getCoreJarPath());
		}
		return muleCoreJar.getInputStream(muleCoreJar.getJarEntry(relativePath));
	}

	private File springJarPath = null;
	
	protected File getSpringModuleJarPath() throws FileNotFoundException {
		if (springJarPath == null) {
			File springs[] = new File(getLocation(), "lib/mule/").listFiles(new FilenameFilter() {
				public boolean accept(File dir, String file) {
					return file.startsWith("mule-module-spring-") && file.endsWith(".jar");
				}});
			if (springs == null || springs.length != 1) throw new FileNotFoundException("No Spring jar");
			springJarPath = springs[0];
		}
		return springJarPath;
	}

	@Override
	protected InputStream getSpringModuleStream(String relativePath) throws IOException {
		if (muleSpringJar == null) {
			muleSpringJar = new JarFile(getSpringModuleJarPath());
		}
		return muleSpringJar.getInputStream(muleSpringJar.getJarEntry(relativePath));
	}

	public String[] getDependencies(String[] moduleAndTransportNames) {
		return new String[0];
	}
}

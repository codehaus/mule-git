package org.mule.ide.core.distribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.mule.ide.core.MuleCorePlugin;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MuleFullDistribution extends AbstractMuleDistribution {
	private static final String MODULE_PREFIX = "module-";
	private static final String TRANSPORT_PREFIX = "transport-";

	private static final String MULE_PREFIX = "mule-";

	private Map nameToBundle = Collections.synchronizedMap(new HashMap());
	
	public MuleFullDistribution(File rootDirectory) {
		super(rootDirectory);
		if (! rootDirectory.isDirectory())
			throw new IllegalArgumentException("rootDirectory MUST be a directory");
	}
	
	protected InputStream getCoreJarStream(String relativePath) throws IOException {
		IFileBasedBundle coreBundle = lookupMuleModule(CORE_BUNDLE_NAME);
		return coreBundle.getArchiveStream(relativePath);
	}

	public IMuleBundle getCoreModule() {
		return getMuleModule(CORE_BUNDLE_NAME);
	}

	protected InputStream getSpringModuleStream(String relativePath) throws IOException {
		IFileBasedBundle springBundle = lookupMuleModule(SPRING_MODULE_NAME);
		return springBundle.getArchiveStream(relativePath);
	}

	public IClasspathEntry[] getClasspath(IMuleBundle[] bundles) {
		IMuleBundle allBundles[] = getTransitiveMuleDependencies(bundles);
		File[] dependentJars = getLibrariesDependencies(allBundles);
		int total = allBundles.length + dependentJars.length;
		int idx = 0;
		IClasspathEntry entries[] = new IClasspathEntry[total];
		for (int i=0; i < allBundles.length; ++i, ++idx) {
			IPath sourcePath = null;
			File source = allBundles[i].getSourcePath();
			if (source != null) sourcePath = new Path(source.getAbsolutePath());
			entries[idx] = JavaCore.newLibraryEntry(getMuleLibraryPath(allBundles[i].getName()), sourcePath, null);
		}
		for (int j=0; j < dependentJars.length; ++j, ++idx) {
			entries[idx] = JavaCore.newLibraryEntry(new Path(dependentJars[j].getAbsolutePath()), null, null);
		}
		return entries;
	}
	
	private IPath getMuleLibraryPath(String name) {
		return new Path(getMuleLibraryFile(name).getAbsolutePath());
	}

	private File getMuleLibraryFile(String name) {
		return new File(getLocation(),"lib/mule/mule-" + name + getMuleModuleSuffix());
	}

	private Map stemToFileMap = null;
	
	private String trimVersion(String jarName) {
		if (! jarName.endsWith(".jar")) return jarName;
		int pos = jarName.length() - 4;
		while (pos > 1 && Character.isDigit(jarName.charAt(pos-1)) || jarName.charAt(pos-1)=='.') pos--;
		
		return jarName.substring(0, pos);
	}
	
	private File getOptLibraryFile(String name) {
		if (stemToFileMap == null) {
			stemToFileMap = new HashMap();
			File optLibs[] = new File(getLocation(),"lib/opt").listFiles();
			for (int i=0; i < optLibs.length; ++i) stemToFileMap.put(trimVersion(optLibs[i].getName()), optLibs[i]);
			try
			{
				File userLibs[] = new File(getLocation(),"lib/user").listFiles();
				for (int i=0; i < userLibs.length; ++i) stemToFileMap.put(trimVersion(userLibs[i].getName()), userLibs[i]);
			}
			catch (Throwable t)
			{
				MuleCorePlugin.getDefault().logException("Can't read Mule distro 'user' library folder", t);
			}
		}
		return (File)stemToFileMap.get(trimVersion(name));
	}

	public void gatherSamples(File dir, Collection dest) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setNamespaceAware(true);
			File pomFile = new File(dir, "pom.xml");
			if (! pomFile.exists()) return;
			Document doc = dbf.newDocumentBuilder().parse(pomFile);
			
			NodeList nl = XMLUtils.queryDomNodes(doc, "project/dependencies/modules/module");
			if (nl.getLength() > 0) {
				// Has submodules....
				for (int i=0; i < nl.getLength(); ++i) {
					String subModule = XMLUtils.text(nl.item(i));
					gatherSamples(new File(dir, subModule), dest);
				}
			} else {
				final String name = XMLUtils.queryDomString(doc, "project/name");
				final String description = XMLUtils.queryDomString(doc, "project/description");
				
				NodeList depNodes = XMLUtils.queryDomNodes(doc, "project/dependencies/dependency/artifactId");
				Set dependencies = new HashSet();
				if (depNodes.getLength() > 0) {
					// Has submodules....
					for (int i=0; i < depNodes.getLength(); ++i) {
						String dependency = XMLUtils.text(depNodes.item(i));
						if (dependency.startsWith(MULE_PREFIX))
							dependencies.add(getMuleModule(dependency.substring(MULE_PREFIX.length())));
					}
				}
				dest.add(new FullJarSample(name, description, (IMuleBundle[]) dependencies.toArray(new IMuleBundle[dependencies.size()]), dir));
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IMuleSample[] getSuppliedSamples() {
		List muleSamples = new ArrayList();

		File[] allSamples = new File(getLocation(), "examples/maven").listFiles();
		for (int i = 0; i < allSamples.length; ++i) {
				gatherSamples(allSamples[i], muleSamples);
		}
		return (IMuleSample[]) muleSamples.toArray(new IMuleSample[muleSamples.size()]);
	}
	
	public File[] getLibrariesDependencies(IMuleBundle[] bundles) {
		Set libs = new TreeSet();
		for (int i = 0; i < bundles.length; ++i) {
			try {
				String[] optJars = bundles[i].getOtherDependencies();
				for (int j = 0; j < optJars.length; ++j) libs.add(optJars[j]);
			} catch (IOException ioe) { /* IGNORE */ }
		}
		// We now have all the names, now filter for version mismatches (getOptLibraryFile
		// handles this)
		Set files = new TreeSet();
		for (Iterator it = libs.iterator(); it.hasNext(); )
			files.add(getOptLibraryFile((String)it.next()));
		return (File[]) files.toArray(new File[files.size()]);
	}
	

	public IMuleBundle[] getMuleModules() {
		if (muleModules == null) {
			initializeBundles();
		}
		return muleModules;
	}
	
	IMuleBundle muleTransports[] = null;
	IMuleBundle muleModules[] = null;
	
	protected File findCoreJarPath() throws FileNotFoundException {
		File cores[] = new File(getLocation(), "lib/mule/").listFiles(new FilenameFilter() {
			public boolean accept(File dir, String file) {
				return file.startsWith("mule-core-") && file.endsWith(".jar");
			}});
		if (cores == null || cores.length != 1) throw new FileNotFoundException("No Core jar");
		return cores[0];
	}
	public void initializeBundles() {
		List transports = new LinkedList();
		List modules = new LinkedList();
	
		File[] allModules = new File(getLocation(), "lib/mule").listFiles();
		for (int i = 0; i < allModules.length; ++i) {
			String filePart = allModules[i].getName(); 
			if (filePart.startsWith("mule-transport-")) {
				transports.add(getMuleModule(fileToModule(filePart)));
			} else if (filePart.startsWith("mule-module-")) {
				modules.add(getMuleModule(fileToModule(filePart)));
			}
		}
		muleTransports = (IMuleBundle[]) transports.toArray(new IMuleBundle[transports.size()]);
		muleModules = (IMuleBundle[]) modules.toArray(new IMuleBundle[modules.size()]);
	}
	
	public IMuleBundle[] getTransitiveMuleDependencies(IMuleBundle[] bundles) {
		List q = new LinkedList();
		for (int i = 0; i < bundles.length; ++i) q.add(bundles[i]);
		
		Set libs = new HashSet();
		while (q.size() > 0) {
			IMuleBundle consider = (IMuleBundle)q.remove(0);
			if (libs.add(consider)) {
				try {
					IMuleBundle[] directDependencies = consider.getMuleDependencies();
					for (int i = 0; i < directDependencies.length; ++i) {
						if (! libs.contains(directDependencies[i]))
						{
							q.add(directDependencies[i]);
						}
					}
				} catch (IOException ioe) { /* IGNORE */ }
			}
		}
		return (IMuleBundle[])libs.toArray(new IMuleBundle[libs.size()]);
	}
	
	public IFileBasedBundle createMuleBundle(final String bundleName) {
		
		return new FullJarBundle(bundleName);
		
	}
	
	private String fileToModule(String fullName) {
		String suffix = getMuleModuleSuffix();
		if (suffix.length() + MULE_BUNDLE_PREFIX.length() >= fullName.length())
			throw new IllegalArgumentException(fullName + " can't be a valid Mule module name");
		return fullName.substring(MULE_BUNDLE_PREFIX.length(), fullName.length()-suffix.length());
	}
	
	private String getMuleModuleSuffix() {
		return  "-" + getVersion() + ".jar";
	}

	private IFileBasedBundle getMuleModule(String moduleName) {
		IFileBasedBundle bundle = (IFileBasedBundle)nameToBundle.get(moduleName);
		if (bundle == null) {
			bundle = createMuleBundle(moduleName);
			nameToBundle.put(moduleName, bundle);
		}
		return bundle;
	}
	
	private IFileBasedBundle lookupMuleModule(String moduleName) {
		return (IFileBasedBundle)nameToBundle.get(moduleName);
	}

	protected File getCoreJarPath() throws FileNotFoundException {
		return getMuleLibraryFile(CORE_BUNDLE_NAME);
	}

	public IMuleBundle[] getMuleTransports() {
		if (muleTransports == null) {
			initializeBundles();
		}
		return muleTransports;
	}
	
	interface IFileBasedBundle extends IMuleBundle {
		public InputStream getArchiveStream(String path) throws IOException;
	}

	class FullJarBundle implements IFileBasedBundle {

		private final String name;

		private List muleDependencies;

		private List otherDependencies;

		private FullJarBundle(String name) {
			this.name = name;
		}

		protected File getBundleJarFile() throws FileNotFoundException {
			File jarLocation = getMuleLibraryFile(name);
			if (! jarLocation.exists() || ! jarLocation.canRead()) throw new FileNotFoundException("Can't read expected JAR: " + jarLocation.getAbsolutePath());
			return jarLocation;
		}

		public String getName() {
			return name;
		}

		private void loadDependencies() throws IOException {
			muleDependencies = new ArrayList();
			otherDependencies = new ArrayList();
		
			Manifest mf = new Manifest(getArchiveStream("META-INF/MANIFEST.MF"));
			String deps = mf.getMainAttributes().getValue(Attributes.Name.CLASS_PATH);
			StringTokenizer st = new StringTokenizer(deps, " ");
			while (st.hasMoreTokens()) {
				String dep = st.nextToken();
				if (dep.startsWith(MULE_BUNDLE_PREFIX)) {
					muleDependencies.add(lookupMuleModule(fileToModule(dep)));
				} else {
					otherDependencies.add(dep);
				}
			}
		}

		public InputStream getArchiveStream(String path) throws IOException {
			final JarFile jar = new JarFile(getBundleJarFile());
			ZipEntry entry = jar.getEntry(path);
			if (entry == null) throw new FileNotFoundException("No '" + path + "' found in " + getBundleJarFile());
			
			InputStream jarInputStream = jar.getInputStream(entry);
			return new DecoratingInputStream(jarInputStream) {
				public void close() throws IOException {
					super.close();
					jar.close();
				}
			};
		}

		public IMuleBundle[] getMuleDependencies() throws IOException {
			if (muleDependencies == null)
			{
				loadDependencies();
			}
			return (IMuleBundle[]) muleDependencies.toArray(new IMuleBundle[muleDependencies.size()]);
		}

		public String[] getOtherDependencies() throws IOException {
			if (otherDependencies == null)
			{
				loadDependencies();
			}
			return (String[]) otherDependencies.toArray(new String[otherDependencies.size()]);
		}

		public boolean equals(Object obj) {
			if (! (obj instanceof IFileBasedBundle)) return false;
			return getName() == ((IFileBasedBundle)obj).getName();
		}

		public int hashCode() {
			return getName().hashCode();
		}

		public String toString() {
			return MULE_BUNDLE_PREFIX + getName() + getMuleModuleSuffix();
		}
		
		public File getSourcePath() {
			if (getName().equals(CORE_BUNDLE_NAME))
			{
				return new File(getLocation(), "src/core/target/mule-core-" + getVersion() + "-sources.jar");
			}
			else if (getName().startsWith(MODULE_PREFIX))
			{
				String moduleName = getName().substring(MODULE_PREFIX.length());
				return new File(getLocation(), "src/modules/" + moduleName + "/target/mule-" + getName() + "-" + getVersion() + "-sources.jar");
			}
			else if (getName().startsWith(TRANSPORT_PREFIX)) {
				String transportName = getName().substring(TRANSPORT_PREFIX.length());
				return new File(getLocation(), "src/transports/" + transportName + "/target/mule-" + getName() + "-" + getVersion() + "-sources.jar"); 
			}
			return null;
		}
	}
	
	class FullJarSample extends FullJarBundle implements IMuleSample {

		private String description;
		
		private IMuleBundle dependencies[];

		private File root;
		
		public FullJarSample(String name, String description, IMuleBundle dependencies[], File root) {
			super(name);
			this.description = description;
			this.dependencies = dependencies;
			this.root = root;
		}
		
		public String getDescription() {
			return this.description;
		}
		
		protected File getBundleJarFile() throws FileNotFoundException {
			throw new UnsupportedOperationException("FullJarSample does not live in a single bundle");
		}

		public IMuleBundle[] getMuleDependencies() throws IOException {
			return dependencies;
		}
		
		public String[] getOtherDependencies() throws IOException {
			return new String[0];
		}

		public File[] getConfigFiles() {
			File conf = new File(root, "conf");
			
			if (conf.exists() && conf.isDirectory()) {
				return conf.listFiles(); 
			}
			return new File[0];
		}

		public File[] getSourceFolders() {
			Collection c = new LinkedList();
			File srcJava = new File(root, "src/main/java");
			if (srcJava.exists()) {
				c.add(srcJava);
			}
			File srcResources = new File(root, "src/main/resources");
			if (srcResources.exists()) {
				c.add(srcResources);
			}
			return (File[]) c.toArray(new File[c.size()]);
		} 
		
	}
}

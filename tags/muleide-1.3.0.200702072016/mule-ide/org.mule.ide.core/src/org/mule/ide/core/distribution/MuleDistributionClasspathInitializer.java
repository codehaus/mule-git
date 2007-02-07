package org.mule.ide.core.distribution;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.mule.ide.core.MuleCorePreferences;

public class MuleDistributionClasspathInitializer extends ClasspathContainerInitializer {

	
	/**
	 * This algorithm matches the one in the UI - 
	 * 
	 * @param hint
	 * @return
	 */
	static public String pathify(String hint) {
		return hint.replace('\\', '_').replace(':', '_').replace('/', '_');
	}

	public void initialize(final IPath path, final IJavaProject project) throws CoreException {
		// Get the project and the required libs, find the Mule distro, add the libs
		
		// First, find the distribution location to work with (from the preferences) 
		String [] distributions = MuleCorePreferences.getDistributions();
		int defaultIndex = MuleCorePreferences.getDefaultDistribution();
		String candidate = null;
		if (defaultIndex >= 0 && defaultIndex < distributions.length) {
			candidate = distributions[defaultIndex]; 
		}
		
		// Now, see if the supplied classpath has specified the distribution (as "pathified" hint)
		if (path.segmentCount() > 2) {
			String hint = path.segment(2);
			if (hint != null) {
				hint = pathify(hint);
				for (int i=0; i<distributions.length; ++i) {
					if (hint.equals(pathify(distributions[i]))) candidate = distributions[i];
				}
				// TODO: Perhaps we should suppress the default/fallback if the hint is set?  
			}
		}
		// We should have a candidate either way.
		if (candidate == null) return; // Silently fail as expected
		// TODO: Mark the Mule nature as having a bad path or missing prefs
		
		IMuleDistribution distrib = null;
		distrib = MuleDistributionFactory.getInstance().createMuleDistribution(new File(candidate));
		
		if (distrib == null) return; // As per contract...
		// TODO: Mark the Mule prefs as having a bad path
		
		Set included = null;
		if (path != null && path.segmentCount() > 1) {
			included = commaStringToSet(path.segment(1));
		}
		List bundles = new LinkedList();
		bundles.add(distrib.getCoreModule());
		IMuleBundle modules[] = distrib.getMuleModules();
		for (int i=0; i < modules.length; ++i) {
			if (included == null || included.contains(modules[i].getName())) bundles.add(modules[i]);
		}
		IMuleBundle transports[] = distrib.getMuleTransports();
		for (int i=0; i < transports.length; ++i) {
			if (included == null || included.contains(transports[i].getName())) bundles.add(transports[i]);
		}
		IMuleBundle[] allRequiredBundles = distrib.getTransitiveMuleDependencies((IMuleBundle[])bundles.toArray(new IMuleBundle[bundles.size()]));
		
		final IClasspathEntry containerEntries[] = distrib.getClasspath(allRequiredBundles);
		
		IClasspathContainer container = new IClasspathContainer() {
			public IClasspathEntry[] getClasspathEntries() {
				return containerEntries;
			}
			public String getDescription() {
				return "Mule libraries for project ["+ project.getElementName()+"]";
			}
			public int getKind() {
				return K_APPLICATION; 
			}
			public IPath getPath() {
				return path;
			}
			public String toString() {
				return getDescription();
			}
		};
		JavaCore.setClasspathContainer(path, new IJavaProject[] { project }, new IClasspathContainer[] { container }, null);
	}

	private static Set commaStringToSet(String bundleSelectString2) {
		Set selection = new HashSet();
		StringTokenizer st = new StringTokenizer(bundleSelectString2, ",");
		while (st.hasMoreTokens()) selection.add(st.nextToken());
		return selection;
	}
	
	
	public boolean canUpdateClasspathContainer(IPath containerPath, IJavaProject project) {
		return super.canUpdateClasspathContainer(containerPath, project);
	}
	public void requestClasspathContainerUpdate(IPath containerPath, IJavaProject project, IClasspathContainer containerSuggestion) throws CoreException {
		super.requestClasspathContainerUpdate(containerPath, project,
				containerSuggestion);
	}
}

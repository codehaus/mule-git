package org.mule.ide.core.distribution;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IJavaProject;

public class MuleDistributionClasspath extends ClasspathContainerInitializer {

	@Override
	public void initialize(IPath arg0, IJavaProject arg1) throws CoreException {
		// Get the project and the required libs, find the Mule distro, add the libs
	}
	
}

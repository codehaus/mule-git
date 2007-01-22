package org.mule.ide.core.distribution;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class MuleDistributionClasspathInitializer extends ClasspathContainerInitializer {

	public void initialize(final IPath path, final IJavaProject project) throws CoreException {
		// Get the project and the required libs, find the Mule distro, add the libs
		
		
		
	    /**
	     * Binds a classpath container to a <code>IClasspathContainer</code> for a given project,
	     * or silently fails if unable to do so.
	     * <p>
	     * A container is identified by a container path, which must be formed of two segments.
	     * The first segment is used as a unique identifier (which this initializer did register onto), and
	     * the second segment can be used as an additional hint when performing the resolution.
	     * <p>
	     * The initializer is invoked if a container path needs to be resolved for a given project, and no
	     * value for it was recorded so far. The implementation of the initializer would typically set the 
	     * corresponding container using <code>JavaCore#setClasspathContainer</code>.
	     * <p>
	     * A container initialization can be indirectly performed while attempting to resolve a project
	     * classpath using <code>IJavaProject#getResolvedClasspath(</code>; or directly when using
	     * <code>JavaCore#getClasspathContainer</code>. During the initialization process, any attempt
	     * to further obtain the same container will simply return <code>null</code> so as to avoid an
	     * infinite regression of initializations.
	     * <p>
	     * A container initialization may also occur indirectly when setting a project classpath, as the operation
	     * needs to resolve the classpath for validation purpose. While the operation is in progress, a referenced 
	     * container initializer may be invoked. If the initializer further tries to access the referring project classpath, 
	     * it will not see the new assigned classpath until the operation has completed. Note that once the Java 
	     * change notification occurs (at the end of the operation), the model has been updated, and the project 
	     * classpath can be queried normally.
		 * <p>
		 * This method is called by the Java model to give the party that defined
		 * this particular kind of classpath container the chance to install
		 * classpath container objects that will be used to convert classpath
		 * container entries into simpler classpath entries. The method is typically
		 * called exactly once for a given Java project and classpath container
		 * entry. This method must not be called by other clients.
		 * <p>
		 * There are a wide variety of conditions under which this method may be
		 * invoked. To ensure that the implementation does not interfere with
		 * correct functioning of the Java model, the implementation should use
		 * only the following Java model APIs:
		 * <ul>
		 * <li>{@link JavaCore#setClasspathContainer(IPath, IJavaProject[], IClasspathContainer[], org.eclipse.core.runtime.IProgressMonitor)}</li>
		 * <li>{@link JavaCore#getClasspathContainer(IPath, IJavaProject)}</li>
		 * <li>{@link JavaCore#create(org.eclipse.core.resources.IWorkspaceRoot)}</li>
		 * <li>{@link JavaCore#create(org.eclipse.core.resources.IProject)}</li>
		 * <li>{@link IJavaModel#getJavaProjects()}</li>
		 * <li>Java element operations marked as "handle-only"</li>
		 * </ul>
		 * The effects of using other Java model APIs are unspecified.
		 * </p>
		 * 
	     * @param containerPath a two-segment path (ID/hint) identifying the container that needs 
	     * 	to be resolved
	     * @param project the Java project in which context the container is to be resolved.
	     *    This allows generic containers to be bound with project specific values.
	     * @throws CoreException if an exception occurs during the initialization
	     * 
	     * @see JavaCore#getClasspathContainer(IPath, IJavaProject)
	     * @see JavaCore#setClasspathContainer(IPath, IJavaProject[], IClasspathContainer[], org.eclipse.core.runtime.IProgressMonitor)
	     * @see IClasspathContainer
	     */
		IClasspathContainer cc = JavaCore.getClasspathContainer(path.uptoSegment(1), project);
		final IClasspathEntry containerEntries[] = new IClasspathEntry[] { JavaCore.newLibraryEntry(new Path("c:\\java\\mule-1.3.3\\lib\\opt\\commons-pool-1.3.jar"), null, null) }; 
		IClasspathContainer container = new IClasspathContainer() {
			public IClasspathEntry[] getClasspathEntries() {
				return containerEntries;
			}
			public String getDescription() {
				return "Mule librariesfor project ["+ project.getElementName()+"]";
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
	public boolean canUpdateClasspathContainer(IPath containerPath, IJavaProject project) {
		return super.canUpdateClasspathContainer(containerPath, project);
	}
	public void requestClasspathContainerUpdate(IPath containerPath, IJavaProject project, IClasspathContainer containerSuggestion) throws CoreException {
		super.requestClasspathContainerUpdate(containerPath, project,
				containerSuggestion);
	}
}

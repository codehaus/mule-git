/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ide.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.mule.ide.core.MuleClasspathUtils;
import org.mule.ide.core.MuleCorePlugin;
import org.mule.ide.core.exception.MuleModelException;
import org.mule.ide.ui.IMuleImages;
import org.mule.ide.ui.MulePlugin;

/**
 * Wizard for creating a new Mule project.
 */
public class AddMuleSupportWizard extends Wizard implements INewWizard {

	/** The workbench handle */
	private IWorkbench workbench;

	/** Page for choosing the right project to add support to */
	MuleProjectReferencePage referencePage;
	
	/** Page for choosing the support */
	private AddMuleSupportWizardPage wizardPage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {
		setWindowTitle("Add Mule Support to Existing Projects");
		setDefaultPageImageDescriptor(MulePlugin.getDefault().getImageRegistry().getDescriptor(
				IMuleImages.KEY_MULE_WIZARD_BANNER));
		
		referencePage = new MuleProjectReferencePage("muleProjectReferencePage");
		referencePage.setTitle("Add Mule Support");
		referencePage.setDescription("Select projects to convert to Mule projects");
		addPage(referencePage);
				
		wizardPage = new AddMuleSupportWizardPage();
		addPage(wizardPage);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		try {
			// Set up the Java project according to entries on Java page.
			getContainer().run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					
					IJavaProject[] projects = referencePage.getReferencedProjects();
					
					monitor.beginTask("Setting up Mule nature and classpath", 100 * projects.length);
					for (int i = 0; i < projects.length; ++i) {
						try {
							monitor.setTaskName("Setting Mule nature");
							MuleCorePlugin.getDefault().setMuleNature(projects[i].getProject(), true);
							monitor.worked(50);
							monitor.setTaskName("Setting Mule classpath");
							addMuleLibraries(projects[i], monitor);
					    } catch (CoreException e) {
					    	throw new InvocationTargetException(e);
						} finally {
					    	monitor.done();
						}
					}
				}});
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof CoreException) {
				MulePlugin.getDefault().showError("Unable to convert project." + e.getCause().getLocalizedMessage(),
						((CoreException) e.getCause()).getStatus());
			} else {
				MulePlugin.getDefault().showError("Unable to convert project: " + e.getCause().getMessage(), MulePlugin.getDefault().createStatus(IStatus.ERROR, "Error creating Mule project", e.getCause()));
			}
		} catch (InterruptedException e) {
			if (getWorkbench().getActiveWorkbenchWindow() != null) {
				MessageDialog.openInformation(getWorkbench().getActiveWorkbenchWindow().getShell(), "Interrupted", "Project creation interrupted");
			}
		}
		// Even if there was a problem, we return true, otherwise the user will be stuck in the wizard
		return true;
	}

	/**
	 * Add the Mule libraries to the project classpath.
	 * 
	 * @param muleProject the mule project
	 * @throws JavaModelException
	 */
	protected void addMuleLibraries(IJavaProject muleProject, IProgressMonitor mon) throws JavaModelException, MuleModelException {
		Collection selectedModules = wizardPage.getSelectedMuleModules();
		IClasspathEntry[] initial = muleProject.getRawClasspath();
		
		int existingMulePosition = -1;
		for (int i = 0; i<initial.length; ++i) {
			if (initial[i].getEntryKind() == IClasspathEntry.CPE_CONTAINER && initial[i].getPath().segment(0).equals(MuleCorePlugin.ID_MULE_CLASSPATH_CONTAINER)) {
				existingMulePosition = i;
				break;
			}
		}
		
		IClasspathEntry muleEntry = MuleClasspathUtils.createMuleClasspathContainer(wizardPage.getDistributionHint(), selectedModules);
		IClasspathEntry[] result;
		if (existingMulePosition != -1) {
			result = initial;
			result[existingMulePosition] = muleEntry; 
		} else {
			IClasspathEntry[] entries = new IClasspathEntry[] { muleEntry };
			result = new IClasspathEntry[initial.length + entries.length];
			System.arraycopy(initial, 0, result, 0, initial.length);
			System.arraycopy(entries, 0, result, initial.length, entries.length);
		}
		muleProject.setRawClasspath(result, new SubProgressMonitor(mon, 50, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		setNeedsProgressMonitor(true);
	}

	/**
	 * Get the workbench that hosts the wizard.
	 * 
	 * @return the workbench
	 */
	protected IWorkbench getWorkbench() {
		return workbench;
	}

	/**
	 * Set the workbench that hosts the wizard.
	 * 
	 * @param workbench the workbench
	 */
	protected void setWorkbench(IWorkbench workbench) {
		this.workbench = workbench;
	}
}
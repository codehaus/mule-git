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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.mule.ide.core.MuleCorePlugin;

/**
 * Wizard page for a wizard that uses a project resource.
 */
public class MuleProjectReferencePage extends WizardPage {
    // widgets
    private CheckboxTableViewer referenceProjectsViewer;

    private static final int PROJECT_LIST_MULTIPLIER = 15;

    /**
     * Creates a new project reference wizard page.
     *
     * @param pageName the name of this page
     */
    public MuleProjectReferencePage(String pageName) {
        super(pageName);
    }

    /** (non-Javadoc)
     * Method declared on IDialogPage.
     */
    public void createControl(Composite parent) {

        Font font = parent.getFont();

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setFont(font);

        Label referenceLabel = new Label(composite, SWT.NONE);
        referenceLabel.setText("&Projects to convert to Mule project");
        referenceLabel.setFont(font);

        referenceProjectsViewer = CheckboxTableViewer.newCheckList(composite,
                SWT.BORDER);
        referenceProjectsViewer.getTable().setFont(composite.getFont());
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;

        data.heightHint = getDefaultFontHeight(referenceProjectsViewer
                .getTable(), PROJECT_LIST_MULTIPLIER);
        referenceProjectsViewer.getTable().setLayoutData(data);
        referenceProjectsViewer.setLabelProvider(WorkbenchLabelProvider
                .getDecoratingWorkbenchLabelProvider());
        referenceProjectsViewer.setContentProvider(getContentProvider());
        referenceProjectsViewer.setInput(ResourcesPlugin.getWorkspace());
        referenceProjectsViewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				setPageComplete(referenceProjectsViewer.getCheckedElements().length > 0);
			}});
        setPageComplete(false);
        setControl(composite);
    }

    /**
     * Returns a content provider for the reference project
     * viewer. It will return all non-Mule Java projects in the workspace.
     *
     * @return the content provider
     */
    protected IStructuredContentProvider getContentProvider() {
        return new WorkbenchContentProvider() {
            public Object[] getChildren(Object element) {
                if (!(element instanceof IWorkspace)) {
					return new Object[0];
				}
                IJavaModel model = JavaCore.create(((IWorkspace) element).getRoot());
                List projects = new LinkedList();
                IJavaProject[] javaProjects;
				try {
					javaProjects = model.getJavaProjects();
				} catch (JavaModelException e) {
					throw new RuntimeException("Can't get Java projects", e);
				}
                for (int i=0; i<javaProjects.length; ++i) {
                	IJavaProject project = javaProjects[i];
                	// If the project already has the Mule nature, don't bother.
                	if (MuleCorePlugin.getDefault().hasMuleNature(project.getProject())) continue;
                	projects.add(project);
                }
                return projects.toArray(new Object[projects.size()]);
            }
        };
    }

    /**
     * Get the defualt widget height for the supplied control.
     * @return int
     * @param control - the control being queried about fonts
     * @param lines - the number of lines to be shown on the table.
     */
    private static int getDefaultFontHeight(Control control, int lines) {
        FontData[] viewerFontData = control.getFont().getFontData();
        int fontHeight = 10;

        //If we have no font data use our guess
        if (viewerFontData.length > 0) {
			fontHeight = viewerFontData[0].getHeight();
		}
        return lines * fontHeight;

    }
    
    /**
     * Returns the referenced projects selected by the user.
     *
     * @return the referenced projects
     */
    public IJavaProject[] getReferencedProjects() {
        Object[] elements = referenceProjectsViewer.getCheckedElements();
        IJavaProject[] projects = new IJavaProject[elements.length];
        System.arraycopy(elements, 0, projects, 0, elements.length);
        return projects;
    }
}

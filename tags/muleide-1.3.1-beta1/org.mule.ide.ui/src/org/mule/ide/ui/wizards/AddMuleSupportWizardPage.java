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

import java.util.Collection;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.mule.ide.core.distribution.IMuleDistribution;
import org.mule.ide.ui.project.MuleClasspathChooserPanel;

/**
 * Page of the wizard to add Mule support to existing Java project
 * 
 * @author Jesper Steen Møller
 */
public class AddMuleSupportWizardPage extends WizardPage {

	/** Widgets needed to choose the initial project content */
	private MuleClasspathChooserPanel classpathChooser;

	/** Naming constant for project page */
	private static final String PAGE_PROJECT = "muleWizardClasspathPage";

	public AddMuleSupportWizardPage() {
		super(PAGE_PROJECT);
		setTitle("Choose Mule Features");
		setDescription("Select a Mule distribution and modules for use in existing Java projects");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setFont(parent.getFont());

        initializeDialogUnits(parent);

        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		classpathChooser = new MuleClasspathChooserPanel(composite, SWT.NONE, false);
		classpathChooser.reset(null, "");
		classpathChooser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

//        setPageComplete(false);
        // Show description on opening
        setErrorMessage(null);
        setMessage(null);
        setControl(composite);
	}

	public IMuleDistribution getMuleDistribution() {
		return classpathChooser.getChosenDistribution();
	}

	public String getDistributionHint() {
		return classpathChooser.getDistributionHint();
	}

	public Collection getSelectedMuleModules() {
		return classpathChooser.getModuleSelection();
	}
}
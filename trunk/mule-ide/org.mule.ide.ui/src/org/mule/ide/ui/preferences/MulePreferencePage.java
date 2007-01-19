/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Jesper Steen Møller. All rights reserved.
 * http://www.selskabet.org/jesper/
 * 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.mule.ide.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.mule.ide.ui.MulePlugin;
import org.mule.ide.ui.panels.MuleClasspathPreferencesPanel;

/**
 * Preference page for Mule setttings.
 */
public class MulePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/** Widget for choosing classpath */
	private MuleClasspathPreferencesPanel classpathPanel;

	public MulePreferencePage() {
		setPreferenceStore(MulePlugin.getDefault().getPreferenceStore());
		setDescription("Default settings for Mule projects");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		classpathPanel = new MuleClasspathPreferencesPanel(this);
		classpathPanel.createControl(composite);
		classpathPanel.initializeFromPreferences();
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		classpathPanel.saveToPreferences();
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#setErrorMessage(java.lang.String)
	 */
	public void setErrorMessage(String newMessage) {
		setValid(newMessage == null);
		super.setErrorMessage(newMessage);
	}
}
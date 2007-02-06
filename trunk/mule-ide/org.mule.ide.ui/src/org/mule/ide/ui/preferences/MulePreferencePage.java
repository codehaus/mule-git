/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.mule.ide.ui.MulePlugin;
import org.mule.ide.ui.panels.MuleDistributionPreferencePanel;

/**
 * Preference page for Mule setttings.
 */
public class MulePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/** Widget for choosing classpath */
	private MuleDistributionPreferencePanel classpathPanel;

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
		classpathPanel = new MuleDistributionPreferencePanel(this, parent, SWT.NONE);
		classpathPanel.initializeFromPreferences();
		return classpathPanel;
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
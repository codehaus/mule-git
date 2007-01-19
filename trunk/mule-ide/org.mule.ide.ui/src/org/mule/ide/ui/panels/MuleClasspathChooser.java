package org.mule.ide.ui.panels;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Widgets needed to choose the location from which Mule libraries are loaded.
 * 
 * @author Derek Adams
 */
public class MuleClasspathChooser {
	
	public DialogPage parentPage;
	
	public MuleClasspathChooser(DialogPage page) {
		this.parentPage = page;
	}

	/**
	 * Initialize the values from the preferences store.
	 */
	public void initializeFromPreferences() {
	}


	/**
	 * Create the widgets on a parent composite.
	 * 
	 * @param parent the parent composite
	 * @return the created composite
	 */
	public Composite createControl(Composite parent) {
		Group cpGroup = new Group(parent, SWT.NONE);
		cpGroup.setText("Choose where Eclipse will look for Mule libraries");
		return cpGroup;
	}

}
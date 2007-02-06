package org.mule.ide.ui.project;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.mule.ide.core.MuleClasspathUtils;

public class MuleClasspathContainerPage extends WizardPage implements
		IClasspathContainerPage {

	private IClasspathEntry selection;

	public MuleClasspathContainerPage() {
		super("Mule Classpath");
		this.setTitle("Mule Distribution and Modules");
		this.setDescription("Please choose the Mule distribution to use for this project, and choose which modules and transports to include");
	}
	
	public boolean finish() {
		String hint = chooserPanel.getDistributionHint();
		selection = MuleClasspathUtils.createMuleClasspathContainer(hint, chooserPanel.getModuleSelection());
		
		return true;
	}

	public IClasspathEntry getSelection() {
		return selection;
	}

	public void setSelection(IClasspathEntry containerEntry) {
		this.selection = containerEntry;
	}

	MuleClasspathChooserPanel chooserPanel; 
	
	public void createControl(Composite parent) {
		chooserPanel = new MuleClasspathChooserPanel(parent, SWT.NONE, false);
		setControl(chooserPanel);
		chooserPanel.initializeWidgets(getSelection().getPath());
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			chooserPanel.initializeWidgets(getSelection() != null ? getSelection().getPath() : null);
	}
}

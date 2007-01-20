package org.mule.ide.ui.project;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class MuleClasspathContainerPage extends WizardPage implements
		IClasspathContainerPage {

	private IClasspathEntry selection;

	public MuleClasspathContainerPage() {
		super("Mule Classpath");
	}

	public boolean finish() {
		if (selection == null) {
			IPath path = new Path("org.mule.ide.core.muleClasspath");
			path.append("what");
			selection = JavaCore.newContainerEntry(path);
		}
		
		return true;
	}

	public IClasspathEntry getSelection() {
		return selection;
	}

	public void setSelection(IClasspathEntry containerEntry) {
		this.selection = containerEntry;
	}

	public void createControl(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText("I was here");
		setControl(label);
	}

}

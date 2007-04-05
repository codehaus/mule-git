/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ide.ui.panels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mule.ide.core.distribution.IMuleDistribution;
import org.mule.ide.core.distribution.MuleDistributionFactory;
import org.mule.ide.ui.preferences.IPreferenceConstants;
import org.mule.ide.ui.preferences.MulePreferences;

/**
 * Panel used in the Mule Distribution preferences.
 *
 * @deprecated 1.3.0 Refactored into MuleDistributionsPreferencesPanel
 */
public class MuleClasspathPreferencesPanel {

	/** Button for selecting distro dir */
	private Button addDirButton;

	/** Button for selecting single jar file distro */
	private Button addSingleFileButton;

	/** Button for removing the selected root */
	private Button removeButton;

	/** Text field for showing the distribution's version */
	private Text textDistributionDescription;

	/** Text field for showing into about the distribution */
	private Text textDistributionVersion;

	private org.eclipse.swt.widgets.Table distros;

	private ArrayList pathList = new ArrayList();
	
	public DialogPage parentPage;
	
	private boolean hadDeprecatedValues = false;

	private int defaultIndex = -1;
	
	public MuleClasspathPreferencesPanel(DialogPage page) {
		this.parentPage = page;
	}

	/**
	 * Create the widgets on a parent composite.
	 * 
	 * @param parent the parent composite
	 * @return the created composite
	 */
	public Composite createControl(Composite parent) {
		Group cpGroup = new Group(parent, SWT.NONE);
		cpGroup.setText("Mule distributions");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		cpGroup.setLayout(layout);
		cpGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
		distros = new Table (cpGroup, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL);
		distros.setSize (100, 200);
		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_VERTICAL);
		data.verticalSpan = 4;
		distros.setLayoutData(data);
	
		distros.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				if (event.detail == SWT.CHECK) { 
					TableItem oldItem = distros.getItem(defaultIndex);
					if (oldItem != event.item) oldItem.setChecked(false);
					((TableItem)(event.item)).setChecked(true);
					setNewDefault(distros.indexOf((TableItem)(event.item)));
				} else {
					// Must be selection
					showMuleVersion(distros.indexOf((TableItem)(event.item)));
				}
			}
		});
		
		addDirButton = new Button(cpGroup, SWT.PUSH);
		addDirButton.setText("Add Mule &Directory");
		addDirButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		addDirButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				 addDirDistribution();
			}
		});
	
		addSingleFileButton = new Button(cpGroup, SWT.PUSH);
		addSingleFileButton.setText("Add Mule &JAR");
		addSingleFileButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		addSingleFileButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				 addJarDistribution();
			}
		});
		
		removeButton = new Button(cpGroup, SWT.PUSH);
		removeButton.setText("&Remove");
		removeButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		removeButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				 removeDistribution(distros.getSelectionIndex());
			}
		}); 
		textDistributionVersion = new Text(cpGroup, SWT.READ_ONLY);
		GridData data2 = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_VERTICAL);
		data2.horizontalSpan = 2;
		textDistributionVersion.setLayoutData(data2);
	
		textDistributionDescription = new Text(cpGroup, SWT.READ_ONLY);
		GridData data3 = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_VERTICAL);
		data3.horizontalSpan = 2;
		textDistributionDescription.setLayoutData(data3);
		
//		initializeFromPreferences();
		return cpGroup;
	}

	/**
	 * Initialize the values from the preferences store.
	 */
	public void initializeFromPreferences() {
		String cpType = MulePreferences.getDeprecatedClasspathChoice();
		if (IPreferenceConstants.MULE_CLASSPATH_TYPE_PLUGIN.equals(cpType)) {
			hadDeprecatedValues = true;
			parentPage.setMessage("Mule IDE no longer distributes Mule as part of the plugin. " + 
					"You must set the location of the external install for Mule to work.");
			initDistros(new String[0], -1);
		} else if (IPreferenceConstants.MULE_CLASSPATH_TYPE_EXTERNAL.equals(cpType)) {
			hadDeprecatedValues = true;
			String onlyMuleRoot = MulePreferences.getLegacyExternalMuleRoot();
			initDistros(new String[] {onlyMuleRoot}, -1);
		} else {
			initDistros(MulePreferences.getDistributions(), MulePreferences.getDefaultDistribution());
		}
	}

	private void initDistros(String[] distributions, int defaultDistribution) {
		pathList.clear();
		defaultIndex = defaultDistribution;
		distros.clearAll();
		for (int i = 0; i < distributions.length; ++i) {
			TableItem ti = new TableItem(distros, SWT.CHECK);
			ti.setChecked(i == defaultDistribution);
			ti.setText(distributions[i]);
			pathList.add(distributions[i]);
		}
		distros.select(defaultIndex);
	}

	public boolean showMuleVersion(int idx) {
		
		if (idx >= 0 && idx < pathList.size()) {
			IMuleDistribution dist = getValidDistribution((String)pathList.get(idx));
			if (dist != null) {
				this.textDistributionVersion.setText(dist.getVersion());
				this.textDistributionDescription.setText(dist.getLocation().toString());
				return true;
			} else {
				this.textDistributionVersion.setText("Error");
				this.textDistributionDescription.setText("The selected Mule distribution cannot be used with Mule IDE");
				return false;
			}
		} else {
			this.textDistributionVersion.setText("");
			this.textDistributionDescription.setText("");
		}
		return true;
	}
	
	/**
	 * Save the values into the preference store.
	 */
	public void saveToPreferences() {
		if (hadDeprecatedValues) {
			MulePreferences.clearDeprecatedValues();
			hadDeprecatedValues = false;
		}
		MulePreferences.setDistributions((String[])(pathList.toArray(new String[pathList.size()])), defaultIndex);
	}

	/**
	 * Browse for the external root.
	 */
	protected void addDirDistribution() {
		DirectoryDialog dialog = new DirectoryDialog(parentPage.getShell());
		dialog.setText("Choose the root of an unpacked Mule Distribution");
		tryAddDistribution(dialog.open());
	}
	
	/**
	 * Browse for the external root.
	 */
	protected void addJarDistribution() {
		FileDialog dialog = new FileDialog(parentPage.getShell());
		dialog.setText("Choose a Mule single-file distribution (JAR or RAR)");
		dialog.setFilterNames (new String [] {"Java Archive", "Resource Adapter Archive"});
		dialog.setFilterExtensions (new String [] {"*.jar", "*.rar"});
		tryAddDistribution(dialog.open());
	}

	private boolean checkDistribution(String filepath) {
		IMuleDistribution dist = getValidDistribution(filepath);
		return dist != null;
	}
	
	private void tryAddDistribution(String filepath) {
		if (filepath == null) return;

		try
		{
			File path = new File(filepath);
			filepath = path.getCanonicalPath();
			if (! path.exists()) filepath = null;
		}
		catch (IOException ioe)
		{
			// Do nothing
		}
		
		if (filepath == null)
		{
			MessageDialog.openError(parentPage.getShell(), "Mule Distribution not found", "No Mule Distribution found at:\r\n" + filepath);
			return;
		}
		
		if (pathList.contains(filepath)) {
			MessageDialog.openError(parentPage.getShell(), "Existing Mule Distribution", "This installation is already in the list");
			return;
		}
			
		if (checkDistribution(filepath)) {
			addDistributionRoot(filepath);
			parentPage.setMessage(null);
		} else {
			MessageDialog.openError(parentPage.getShell(), "Unrecognized Mule Distribution", "The Mule installation or distribution at this location cannot be recognized");
		}
	}

	private void addDistributionRoot(String filePath) {
		pathList.add(filePath);
		TableItem it = new TableItem(distros,SWT.NONE);
		it.setText(filePath);
		if (defaultIndex < 0)
		{
			setNewDefault(distros.indexOf(it));
		}
	}

	protected void removeDistribution(int idx) {
		if (idx >= 0 && idx < pathList.size()) {
			distros.remove(idx);
			pathList.remove(idx);
		}
		showMuleVersion(-1);
		if (defaultIndex == idx) {
			setNewDefault(-1);
		} else if (defaultIndex > idx) {
			defaultIndex--;
		}
	}

	private void setNewDefault(int idx) {
		// clear old checkmark
		if (idx != defaultIndex && defaultIndex > 0 && idx < distros.getItemCount()) distros.getItem(defaultIndex).setChecked(false);

		defaultIndex = idx;
		if (idx > 0 && idx < distros.getItemCount()) {
			distros.getItem(defaultIndex).setChecked(true);
		}
		distros.select(defaultIndex);
		boolean ok = showMuleVersion(defaultIndex);
		defaultIndex = idx;
		
		if (! ok)
		{
			parentPage.setErrorMessage("The Mule installation or distribution at this location cannot be recognized");
		}
		else
		{
			parentPage.setErrorMessage(null);
		}
	}
	
	private IMuleDistribution getValidDistribution(String path) {
		IMuleDistribution dist = MuleDistributionFactory.getInstance().createMuleDistribution(new File(path));
		if (! dist.isValid()) return null;
		
		return dist;
	}

}
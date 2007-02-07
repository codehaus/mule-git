package org.mule.ide.ui.panels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mule.ide.core.distribution.IMuleDistribution;
import org.mule.ide.core.distribution.MuleDistributionFactory;
import org.mule.ide.ui.preferences.IPreferenceConstants;
import org.mule.ide.ui.preferences.MulePreferences;

public class MuleDistributionPreferencePanel extends Composite {

	private Group distroGroup = null;
	private Table distros = null;
	private Button buttonAddDir = null;
	private Button buttonAddJar = null;
	private Button buttonRemoveDistro = null;
	private Group selectedDistroInfo = null;
	private Label label1 = null;
	private Text textDistributionDescription = null;
	private Label label2 = null;
	private Text textDistributionVersion = null;

	private ArrayList pathList = new ArrayList();  //  @jve:decl-index=0:
	public DialogPage parentPage;
	private boolean hadDeprecatedValues = false;

	private int defaultIndex = -1;
	
	public MuleDistributionPreferencePanel(DialogPage page, Composite parent, int style) {
		super(parent, style);
		this.parentPage = page;
		initialize();
	}

	private void initialize() {
		createDistroGroup();
		createSelectedDistroInfo();
		setSize(new Point(300, 200));
		setLayout(new GridLayout());
	}

	/**
	 * This method initializes distroGroup	
	 *
	 */
	private void createDistroGroup() {
		GridData gridData3 = new GridData();
		gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gridData11 = new GridData();
		gridData11.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gridData1 = new GridData();
		gridData1.grabExcessVerticalSpace = true;
		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		GridData gridData = new GridData();
		gridData.verticalSpan = 3;
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		distroGroup = new Group(this, SWT.NONE);
		distroGroup.setText("Mule &Distributions:");
		distroGroup.setLayout(gridLayout);
		distroGroup.setLayoutData(gridData1);
		distros = new Table(distroGroup, SWT.CHECK | SWT.BORDER);
		distros.setHeaderVisible(false);
		distros.setLayoutData(gridData);
		distros.setLinesVisible(false);
		distros.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
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
		buttonAddDir = new Button(distroGroup, SWT.NONE);
		buttonAddDir.setText("Add &Full Distribution");
		buttonAddDir.setLayoutData(gridData11);
		buttonAddDir
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						 addDirDistribution();
					}
				});
		buttonAddJar = new Button(distroGroup, SWT.NONE);
		buttonAddJar.setText("Add Mule &JAR/RAR");
		buttonAddJar.setLayoutData(gridData2);
		buttonAddJar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						 addJarDistribution();
					}
				});
		buttonRemoveDistro = new Button(distroGroup, SWT.NONE);
		buttonRemoveDistro.setText("&Remove");
		buttonRemoveDistro.setLayoutData(gridData3);
		buttonRemoveDistro
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						 removeDistribution(distros.getSelectionIndex());
					}
				});
	}

	/**
	 * This method initializes selectedDistroInfo	
	 *
	 */
	private void createSelectedDistroInfo() {
		GridData gridData6 = new GridData();
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		GridData gridData4 = new GridData();
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData4.grabExcessVerticalSpace = false;
		selectedDistroInfo = new Group(this, SWT.NONE);
		selectedDistroInfo.setLayoutData(gridData4);
		selectedDistroInfo.setLayout(gridLayout1);
		selectedDistroInfo.setText("Information:");
		label1 = new Label(selectedDistroInfo, SWT.NONE);
		label1.setText("Path:");
		textDistributionDescription = new Text(selectedDistroInfo, SWT.READ_ONLY);
		textDistributionDescription.setLayoutData(gridData5);
		label2 = new Label(selectedDistroInfo, SWT.NONE);
		label2.setText("Version:");
		textDistributionVersion = new Text(selectedDistroInfo, SWT.READ_ONLY);
		textDistributionVersion.setLayoutData(gridData6);
	}

	/**
	 * Browse for the external root.
	 */
	protected void addDirDistribution() {
		DirectoryDialog dialog = new DirectoryDialog(parentPage.getShell());
		dialog.setText("Choose the root of an unpacked Mule Distribution");
		tryAddDistribution(dialog.open());
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

	private IMuleDistribution getValidDistribution(String path) {
		IMuleDistribution dist = MuleDistributionFactory.getInstance().createMuleDistribution(new File(path));
		if (! dist.isValid()) return null;
		
		return dist;
	}
	

	private void initDistros(String[] distributions, int defaultDistribution) {
		defaultIndex = defaultDistribution;
		pathList.clear();
		distros.clearAll();
		for (int i = 0; i < distributions.length; ++i) {
			TableItem ti = new TableItem(distros, SWT.CHECK);
			ti.setChecked(i == defaultDistribution);
			ti.setText(distributions[i]);
			pathList.add(distributions[i]);
		}
		distros.select(defaultIndex);
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

	public boolean showMuleVersion(int idx) {
		
		if (idx >= 0 && idx < pathList.size()) {
			IMuleDistribution dist = getValidDistribution((String)pathList.get(idx));
			if (dist != null) {
				this.textDistributionVersion.setText("Version: " + dist.getVersion());
				this.textDistributionDescription.setText("Location: " + dist.getLocation().toString());
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

	private boolean checkDistribution(String filepath) {
		IMuleDistribution dist = getValidDistribution(filepath);
		return dist != null;
	}
}

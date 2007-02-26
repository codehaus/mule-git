package org.mule.ide.ui.project;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.mule.ide.core.MuleClasspathUtils;
import org.mule.ide.core.distribution.IMuleBundle;
import org.mule.ide.core.distribution.IMuleDistribution;
import org.mule.ide.core.distribution.MuleDistributionClasspathInitializer;
import org.mule.ide.core.distribution.MuleDistributionFactory;
import org.mule.ide.ui.preferences.IPreferenceConstants;
import org.mule.ide.ui.preferences.MulePreferences;

public class MuleClasspathChooserPanel extends Composite {

	private Group groupDistribution = null;
	private Group groupModule = null;
	private Button radioUseDefault = null;
	private Button radioSpecifyDistribution = null;
	private Combo comboDistribution = null;
	private Link clickLabelPrefs = null;
	private Table tableSelectedModules = null;
	private boolean omitModuleSelection = false;

	Map path2DistributionCache = new HashMap();  //  @jve:decl-index=0:
	private Button buttonSelectAll = null;
	private Button buttonDeSelectAll = null;

	String distributions[] = new String[] { };  //  @jve:decl-index=0:
	int selectedDistribution = -1, defaultDistribution = -1;

	public MuleClasspathChooserPanel(Composite parent, int style, boolean omitModuleSelection) {
		super(parent, style);
		this.omitModuleSelection = omitModuleSelection;
		initialize();
	}

	private void initialize() {
		this.setSize(new Point(356, 249));
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.verticalSpacing = 5;
		createGroupDistribution();
		this.setLayout(gridLayout1);
		if (! omitModuleSelection) {
			createGroupModule();
		}
	}
	
	private void initDistributions() {
		distributions = MulePreferences.getDistributions();
		defaultDistribution = MulePreferences.getDefaultDistribution();
		if (defaultDistribution >= distributions.length) defaultDistribution = -1;
	}
		
	private IMuleDistribution getDistribution(String path) {
		if (path2DistributionCache.containsKey(path))
			return (IMuleDistribution)path2DistributionCache.get(path);
		IMuleDistribution newDist = MuleDistributionFactory.getInstance().createMuleDistribution(new File(path));
		path2DistributionCache.put(path, newDist);
		return newDist;
	}
	
	void initCombo() {
		initDistributions();
		comboDistribution.removeAll();
		for (int i=0; i<distributions.length; ++i)
			comboDistribution.add(distributions[i]);
	}

	void setDistributionSelection(String hint) {
		radioUseDefault.setSelection(hint == null);
		radioSpecifyDistribution.setSelection(hint != null);
		
		comboDistribution.setEnabled(hint != null);
		int selected = defaultDistribution;
		if (selected >= distributions.length) selected = -1;
		if (hint != null) {
			hint = pathify(hint);
			for (int i=0; i<distributions.length; ++i) {
				if (hint.equals(pathify(distributions[i]))) selected = i;
			}
		}
		comboDistribution.select(selected);
		selectedDistribution = (hint == null) ? defaultDistribution : selected;
		
		String selectedPath = selected >= 0 ? distributions[selected] : null; 
		IMuleDistribution dist = selectedPath != null ? getDistribution(selectedPath) : null;
		fireDistributionChanged(dist);
	}

	private void resetBundleTable(IMuleDistribution newDist) {
		Set oldSel = getModuleSelection();
		initializeBundleTable(newDist);
		setModuleSelection(oldSel);
	}

	private void initializeBundleTable(IMuleDistribution dist) {
		tableSelectedModules.setRedraw(false);
		try {
			tableSelectedModules.removeAll();
			if (dist != null) {
				IMuleBundle modules[] = dist.getMuleModules();
				for (int i = 0; i < modules.length; ++i) {
					new TableItem(tableSelectedModules,SWT.CHECK).setText(modules[i].getName());
				}
				IMuleBundle transports[] = dist.getMuleTransports();
				for (int i = 0; i < transports.length; ++i) {
					new TableItem(tableSelectedModules,SWT.CHECK).setText(transports[i].getName());
				}
			} 
		} finally {
			tableSelectedModules.setRedraw(true);
		}
	}

	private void setModuleSelection(Set selectionToSet) {
		int itemCount = tableSelectedModules.getItemCount();
		for (int i = 0; i < itemCount; ++i) {
			TableItem item = tableSelectedModules.getItem(i);
			item.setChecked(selectionToSet.contains(item.getText()));
		}
	}

	private String pathify(String hint) {
		return MuleDistributionClasspathInitializer.pathify(hint);
	}

	public void initializeWidgets(IPath originalPath) {
		// element 0 in the originalPath is always the name of the Mule classpath container
		String hint = null;
		String bundleSelectString = ""; 
		if (originalPath != null && originalPath.segmentCount() > 2) {
			hint = originalPath.segment(2); 
		}
		if (originalPath != null && originalPath.segmentCount() > 1) {
			bundleSelectString = originalPath.segment(1);
		}
		reset(hint, bundleSelectString);
		
	}

	/**
	 * Reset the panel's contents to the chosen distribution and bundle selection
	 * 
	 * @param hint Hint from the 
	 * @param bundleSelectString
	 */
	public void reset(String hint, String bundleSelectString) {
		initCombo();
		initDistributions();
		setDistributionSelection(hint);
		if (! omitModuleSelection) {
			initializeBundleTable(getChosenDistribution());
			setModuleSelection(MuleClasspathUtils.commaStringToSet(bundleSelectString));
			
			if (listener == null) addDistributionChangeListener(new IDistributionChangeListener() {
				public void distributionChanged(IMuleDistribution newDist) {
					resetBundleTable(newDist);
				}
			});
		}
	}
	
	public Set getModuleSelection() {
		Set selection = new HashSet();
		int itemCount = tableSelectedModules.getItemCount();
		for (int i = 0; i < itemCount; ++i) {
			TableItem item = tableSelectedModules.getItem(i);
			if (item.getChecked()) selection.add(item.getText());
		}
		return selection;
	}
	
	/**
	 * This method initializes groupDistribution	
	 *
	 */
	private void createGroupDistribution() {
		GridData gridData21 = new GridData();
		gridData21.horizontalSpan = 2;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 0;
		GridData gdDistribution = new GridData();
		gdDistribution.grabExcessHorizontalSpace = true;
		gdDistribution.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gd1 = new GridData();
		gd1.grabExcessHorizontalSpace = false;
		groupDistribution = new Group(this, SWT.NONE);
		groupDistribution.setText("&Mule distribution path");
		groupDistribution.setLayoutData(gdDistribution);
		groupDistribution.setLayout(gridLayout);
		radioUseDefault = new Button(groupDistribution, SWT.RADIO);
		radioUseDefault.setText("&Use the default Mule distribution");
		radioUseDefault.setLayoutData(gd1);
		clickLabelPrefs = new Link(groupDistribution, SWT.NONE);
		clickLabelPrefs.setText("<a>Preferences</a>");
		clickLabelPrefs.setLayoutData(gridData);
		radioUseDefault
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						setDistributionSelection(null);
					}
				});
		radioSpecifyDistribution = new Button(groupDistribution, SWT.RADIO);
		radioSpecifyDistribution.setText("Use a &specific Mule distribution:");
		radioSpecifyDistribution.setLayoutData(gridData21);
		createComboDistribution();
		radioSpecifyDistribution
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						setDistributionSelection(distributions[defaultDistribution]);
					}
				});
		clickLabelPrefs.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String hint = selectedDistribution > 0 ? distributions[selectedDistribution] : null;
				PreferencesUtil.createPreferenceDialogOn(getShell(), IPreferenceConstants.DISTRIBUTION_PREFERENCES_ID, null, null).open();
				setDistributionSelection(hint); // Give the UI a chance to react to prefs changes...
			}
		});
	}

	/**
	 * This method initializes groupModule	
	 *
	 */
	private void createGroupModule() {
		GridData gridData4 = new GridData();
		gridData4.widthHint = -1;
		gridData4.verticalAlignment = GridData.BEGINNING;
		gridData4.horizontalAlignment = GridData.FILL;
		GridData gridData3 = new GridData();
		gridData3.widthHint = -1;
		gridData3.verticalAlignment = GridData.CENTER;
		gridData3.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.verticalSpan = 2;
		gridData1.heightHint = 100;
		gridData1.widthHint = 100;
		gridData1.verticalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData2.grabExcessVerticalSpace = true;
		groupModule = new Group(this, SWT.NONE);
		groupModule.setText("Mule modules and &transports");
		groupModule.setLayout(gridLayout2);
		groupModule.setLayoutData(gridData2);
		tableSelectedModules = new Table(groupModule, SWT.BORDER | SWT.CHECK);
		tableSelectedModules.setHeaderVisible(false);
		tableSelectedModules.setToolTipText("Check the modules and transport to include in the Mule classpath for this project");
		tableSelectedModules.setLayoutData(gridData1);
		tableSelectedModules.setLinesVisible(false);
		buttonDeSelectAll = new Button(groupModule, SWT.NONE);
		buttonDeSelectAll.setText("Select &All");
		buttonDeSelectAll.setLayoutData(gridData3);
		buttonDeSelectAll
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						for (int i=0; i < tableSelectedModules.getItemCount(); ++i ) {
							tableSelectedModules.getItem(i).setChecked(true);
						}
					}
				});
		buttonSelectAll = new Button(groupModule, SWT.NONE);
		buttonSelectAll.setText("&Deselect All");
		buttonSelectAll.setLayoutData(gridData4);
		buttonSelectAll
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						for (int i=0; i < tableSelectedModules.getItemCount(); ++i ) {
							tableSelectedModules.getItem(i).setChecked(false);
						}
					}
				});
	}

	/**
	 * This method initializes comboDistribution	
	 *
	 */
	private void createComboDistribution() {
		GridData gd3 = new GridData();
		gd3.horizontalIndent = 16;
		gd3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gd3.widthHint = 200;
		gd3.horizontalSpan = 2;
		gd3.grabExcessHorizontalSpace = true;
		comboDistribution = new Combo(groupDistribution, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboDistribution.setText("");
		comboDistribution.setLayoutData(gd3);
		comboDistribution.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				setDistributionSelection(distributions[comboDistribution.getSelectionIndex()]);
			}
		});

	}

	public String getDistributionHint() {
		if (radioUseDefault.getSelection()) return null;
		return pathify(distributions[selectedDistribution]);
	}

	public interface IDistributionChangeListener {
		void distributionChanged(IMuleDistribution newDist);
	}	
	
	private IDistributionChangeListener listener = null;  //  @jve:decl-index=0:
	public void addDistributionChangeListener(IDistributionChangeListener newLstener) {
		if (this.listener != null) throw new IllegalStateException("listener already added");
		this.listener = newLstener;
	}
	
	void fireDistributionChanged(IMuleDistribution newMuleDistribution) {
		if (listener != null) listener.distributionChanged(newMuleDistribution);		
	}

	public IMuleDistribution getChosenDistribution() {
		int index = radioUseDefault.getSelection() ? comboDistribution.getSelectionIndex() : defaultDistribution;
		
		String selectedPath = index >= 0 ? distributions[index] : null; 
		return selectedPath != null ? getDistribution(selectedPath) : null;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"

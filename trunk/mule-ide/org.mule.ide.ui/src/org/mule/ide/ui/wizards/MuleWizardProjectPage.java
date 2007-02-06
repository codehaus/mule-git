package org.mule.ide.ui.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.mule.ide.core.distribution.IMuleBundle;
import org.mule.ide.core.distribution.IMuleDistribution;
import org.mule.ide.core.distribution.IMuleSample;
import org.mule.ide.ui.panels.ProjectContentChooser;
import org.mule.ide.ui.project.MuleClasspathChooserPanel;

/**
 * First page of the wizard to create a new Mule project
 * 
 * @author Derek Adams
 */
public class MuleWizardProjectPage extends WizardNewProjectCreationPage {

	/** Widgets needed to choose the initial project content */
	private ProjectContentChooser contentChooser;
	private MuleClasspathChooserPanel classpathChooser;

	private IMuleDistribution chosenDistribution;
	private IMuleSample[] allSamples;

	/** Naming constant for project page */
	private static final String PAGE_PROJECT = "muleWizardProjectPage";

	public MuleWizardProjectPage() {
		super(PAGE_PROJECT);
		setTitle("Mule Project");
		setDescription("Create a new Mule project.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite existing = (Composite) getControl();
		//existing.setLayout(new RowLayout(SWT.VERTICAL));
		// Top group - choose which distribution to use
		classpathChooser = new MuleClasspathChooserPanel(existing, SWT.NONE, true);
		classpathChooser.reset(null, "");
		classpathChooser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		// Bottom group - choose which distribution to use
		contentChooser = new ProjectContentChooser(existing, SWT.NONE);
		contentChooser.setChoice(ProjectContentChooser.LOAD_FROM_EMPTY);
		contentChooser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Make top pane
		classpathChooser.addDistributionChangeListener(new MuleClasspathChooserPanel.IDistributionChangeListener() {
			public void distributionChanged(IMuleDistribution newDist) {
				setDistributionSamples(newDist);
				chosenDistribution = newDist;
			}
		});
		setDistributionSamples(classpathChooser.getChosenDistribution());
	}

	/**
	 * Gets the chosen sample project name or null of "empty" or none chosen.
	 * 
	 * @return the sample project description or null
	 */
	public IMuleSample getSelectedSample() {
		int selected = contentChooser.getChosenSample();
		return (selected >= 0 && selected < allSamples.length) ? allSamples[selected] : null;
	}

	
	/**
	 * Sets the list of samples based on the selected distribution, or null
	 * 
	 * @param newDist The newly selected distribution
	 */
	private void setDistributionSamples(IMuleDistribution newDist) {
		if (newDist == null) {
			allSamples = new IMuleSample[0];
		} else {
			allSamples = newDist.getSuppliedSamples();

		}
		String newSampleList[] = new String[allSamples.length];
		if (allSamples != null) {
			for (int i=0; i < allSamples.length; ++i)
				newSampleList[i] = allSamples[i].getName();
		}
		contentChooser.setSampleList(newSampleList);
	}

	public IMuleDistribution getMuleDistribution() {
		return chosenDistribution;
	}

	public String getDistributionHint() {
		return classpathChooser.getDistributionHint();
	}
}
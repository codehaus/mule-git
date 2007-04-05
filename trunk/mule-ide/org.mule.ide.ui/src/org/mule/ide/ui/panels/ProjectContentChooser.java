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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Allows the choice of initial project content.
 * 
 * @author dadams, jmoller
 */
public class ProjectContentChooser extends Composite{

	/** Button for loading an empty project */
	private Button buttonEmpty;

	/** Button for loading from a sample project */
	private Button buttonSample;

	/** Dropdown that contains a list of sample projects */
	private Combo samples;

	/** One of the LOAD_FROM_* constants */
	private int choice;

	/** Constant that indicates to load from an empty project */
	public static final int LOAD_FROM_EMPTY = 0;

	/** Constant that indicates to load from a sample project */
	public static final int LOAD_FROM_SAMPLE = 1;

	public ProjectContentChooser(Composite parent, int style) {
		super(parent, style);
		initialize();
	}
	
	public void setSampleList(String[] sampleList) {
		if (sampleList == null || sampleList.length == 0) setChoice(LOAD_FROM_EMPTY);
		
		// Set new list into the widget, attempt to preserve any selection based on the string value
		if (samples != null && ! samples.isDisposed()) {
			int selectionIndex = samples.getSelectionIndex();
			String oldSelection = selectionIndex > 0 ? samples.getItem(selectionIndex) : null;
			samples.setItems(sampleList);
			for (int i = 0; i < sampleList.length; ++i) {
				if (sampleList[i].equals(oldSelection)) samples.select(i); 
			}
		}
	}
	
	public void initialize() {
		this.setLayout(new FillLayout());
		createGroup();
	}
	
	/**
	 * Create the widgets on a parent composite.
	 * 
	 * @param parent the parent composite
	 * @return the created composite
	 */
	public void createGroup() {
		Group group = new Group(this, SWT.NONE);
		group.setText("Choose the initial content of the project");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);
		buttonEmpty = new Button(group, SWT.RADIO);
		buttonEmpty.setText("&Empty project");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		buttonEmpty.setLayoutData(data);
		buttonEmpty.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				setChoice(LOAD_FROM_EMPTY);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				setChoice(LOAD_FROM_EMPTY);
			}
		});

		
		buttonSample = new Button(group, SWT.RADIO);
		buttonSample.setText("S&ample project");
		buttonSample.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		buttonSample.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				setChoice(LOAD_FROM_SAMPLE);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				setChoice(LOAD_FROM_SAMPLE);
			}
		});

		samples = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
		samples.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	/**
	 * Set the "load from" choice.
	 * 
	 * @param choice the choice of LOAD_FROM_* constant
	 */
	public void setChoice(int choice) {
		this.choice = choice;
		if (isDisposed()) return;

		if (choice == LOAD_FROM_EMPTY) {
			buttonEmpty.setSelection(true);
			buttonSample.setSelection(false);
			samples.setEnabled(false);
		} else {
			buttonEmpty.setSelection(false);
			buttonSample.setSelection(true);
			samples.setEnabled(true);
		}
	}

	/**
	 * Get the "load from" choice.
	 * 
	 * @return the choice of LOAD_FROM_* constant
	 */
	public int getChoice() {
		return choice;
	}

	/**
	 * Get the sample which is chosen.
	 * 
	 * @return the index of the sample, or null if empty project or no sample chosen
	 */
	public int getChosenSample() {
		return samples.getSelectionIndex();
	}
}
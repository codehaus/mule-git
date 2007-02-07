/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.mulemodel.presentation;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;

/**
 * This is a specialized editing domain that can be used by editors that have one or more design
 * pages that view a MOF model and a source page that contains an XML Model.
 */
public class StructuredTextEditingDomain extends AdapterFactoryEditingDomain implements IStructuredTextEditingDomain {
	protected IStructuredTextUndoManager undoManager;

	/**
	 * StructuredTextEditingDomain constructor comment.
	 * 
	 * @param adapterFactory
	 *            org.eclipse.emf.common.notify.AdapterFactory
	 * @param commandStack
	 *            CommandStack
	 */
	public StructuredTextEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack) {
		super(adapterFactory, commandStack);
	}

	public StructuredTextEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack, ResourceSet resourceSet) {
		super(adapterFactory, commandStack, resourceSet);
	}

	/**
	 * Execute a command within the editing domain.
	 */
	public void execute(Command command) {
		execute(command.getLabel(), command);
	}

	/**
	 * Execute a command within the editing domain.
	 */
	public void execute(String label, Command command) {
		executeViaUndoManager(label, command);
	}

	/**
	 * Execute a command directly on the command stack
	 */
	public void executeViaStack(Command command) {
		getCommandStack().execute(command);
	}

	/**
	 * Execute a command within the editing domain.
	 */
	public void executeViaUndoManager(String label, Command command) {
		if (command.canExecute()) {
			if (undoManager != null) {
				undoManager.beginRecording(this, label);
				command.execute();
				undoManager.endRecording(this);
			} else
				executeViaStack(command);
		}
	}

	public IStructuredTextUndoManager getUndoManager() {
		return undoManager;
	}

	public void setUndoManager(IStructuredTextUndoManager newUndoManager) {
		undoManager = newUndoManager;
		undoManager.setCommandStack(commandStack);
	}
}

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
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;

/**
 * This is a specialized editing domain that can be used by editors that have one or more design
 * pages that view a MOF model and a source page that contains an XML Model.
 */
public interface IStructuredTextEditingDomain extends EditingDomain {
	/**
	 * Execute a command within the editing domain.
	 */
	public void execute(Command command);

	/**
	 * Execute a command within the editing domain
	 */
	public void execute(String label, Command command);

	/**
	 * This returns the adapter factory used by this domain.
	 */
	public AdapterFactory getAdapterFactory();

	public IStructuredTextUndoManager getUndoManager();

	public void setUndoManager(IStructuredTextUndoManager newUndoManager);
}

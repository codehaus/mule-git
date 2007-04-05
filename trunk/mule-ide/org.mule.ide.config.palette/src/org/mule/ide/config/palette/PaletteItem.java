/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.palette;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.mule.ide.config.mulemodel.MuleFactory;

/*
 * The content provider class is responsible for
 * providing objects to the view. It can wrap
 * existing objects in adapters or simply return
 * objects as-is. These objects may be sensitive
 * to the current input of the view, or ignore
 * it and always show the same content 
 * (like Task List, for example).
 */

public class PaletteItem implements IAdaptable {
	protected String name;
	protected String className;
	protected String type;
	private ImageDescriptor imageDescriptor;

	private FolderItem parent;

	public PaletteItem(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setParent(FolderItem parent) {
		this.parent = parent;
	}

	public FolderItem getParent() {
		return parent;
	}

	public String toString() {
		return getName();
	}

	public Object getAdapter(Class key) {
		return null;
	}

	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}

	public void setImageDescriptor(ImageDescriptor image) {
		this.imageDescriptor = image;
	}
	
	public boolean mayDropOn(EObject obj) {
		return false;
	}
	
	public void performDropOn(EObject obj, MuleFactory factory) {
	}
}

package org.mule.ide.prototype.palette;

import org.eclipse.core.runtime.IAdaptable;

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
	private String name;

	private FolderItem parent;

	public PaletteItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
}

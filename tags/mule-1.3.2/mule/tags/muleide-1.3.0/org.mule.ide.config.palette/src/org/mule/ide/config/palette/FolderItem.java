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

import java.util.ArrayList;

public class FolderItem extends PaletteItem {
	private ArrayList children;

	public FolderItem(String name) {
		super(name);
		children = new ArrayList();
	}

	public void addChild(PaletteItem child) {
		children.add(child);
		child.setParent(this);
	}

	public void removeChild(PaletteItem child) {
		children.remove(child);
		child.setParent(null);
	}

	public PaletteItem[] getChildren() {
		return (PaletteItem[]) children.toArray(new PaletteItem[children.size()]);
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}
}

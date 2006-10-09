/*******************************************************************************
 * Copyright (c) 2002 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jens Lukowski/Innoopract - initial renaming/restructuring
 *     
 *******************************************************************************/
package org.mule.ide.prototype.palette;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * This class reads the plugin manifests and adds each specified gramamr
 * annotation file with the AnnotationProvider
 */
public class PaletteRegistryReader {
	protected static final String pluginId = "org.mule.ide.prototype.palette"; //$NON-NLS-1$
	protected static final String EXTENSION_POINT_ID = "paletteEntries"; //$NON-NLS-1$
	protected static final String FOLDER_TAG_NAME = "folder"; //$NON-NLS-1$
	protected static final String ATT_NAME = "name"; //$NON-NLS-1$

	private FolderItem rootPaletteItem;

	public PaletteRegistryReader() {
		this.rootPaletteItem = new FolderItem("Mule");
	}

	/**
	 * read from plugin registry and parse it.
	 */
	public void readRegistry() {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint point = extensionRegistry.getExtensionPoint(pluginId, EXTENSION_POINT_ID);
		if (point != null) {
			visitConfigElements(point.getConfigurationElements(), point,  rootPaletteItem);
		}
	}

	private void visitConfigElements(IConfigurationElement[] elements, IExtensionPoint point, FolderItem parent) {
		for (int i = 0; i < elements.length; i++) {
			readElement(elements[i], point, parent);
		}
	}

	/**
	 * readElement() - parse and deal with extension
	 */
	protected void readElement(IConfigurationElement element, IExtensionPoint point, FolderItem parent) {
		// Only includes folder at this stage 
		if (element.getName().equals(FOLDER_TAG_NAME)) {
			FolderItem pi = null;
			String name = element.getAttribute(ATT_NAME);
			if (name != null) {
				try {
//					String bundleId = element.getNamespace();
					pi = new FolderItem(name);
					parent.addChild(pi);
				}
				catch (Exception e) {
//					Log.logException("Problem adding example folders:" + folder, e); //$NON-NLS-1$
				}
			}
			visitConfigElements(element.getChildren(), point, pi);
		}
	}

	public FolderItem getRootPaletteItem() {
		return rootPaletteItem;
	}
}

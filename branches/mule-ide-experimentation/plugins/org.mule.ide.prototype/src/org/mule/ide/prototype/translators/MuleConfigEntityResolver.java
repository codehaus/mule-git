/*****************************************************************************
 * Copyright (c) 2005 StrutsBox.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    StrutsBox - initial API and implementation
 *****************************************************************************/
/*
 * $Id: StrutsConfigEntityResolver.java,v 1.3 2005/07/21 15:21:01 daniel_rohe Exp $
 */
package org.mule.ide.prototype.translators;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.mule.ide.prototype.mulemodel.MuleModelPlugin;
import org.mule.ide.prototype.mulemodel.MuleModelPlugin.Implementation;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The class <code>StrutsConfigEntityResolver</code> provides an entity
 * resolver that tries to load the Document Type Definitions of the Apache
 * Struts configuration files from the plugin directory.
 * 
 * @author Daniel
 */
public class MuleConfigEntityResolver implements EntityResolver {

	/*
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
	 *      java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		if (MuleConfigConstants.MULE_CONFIG_PUBLIC_ID_1_0.equals(publicId)) {
			Implementation plugin = MuleModelPlugin.getPlugin();
			if (plugin != null) {
				URL url = FileLocator.find(plugin.getBundle(), new Path("src/schemas/mule-configuration_1_0.dtd"), Collections.emptyMap()); //$NON-NLS-1$
				if (url != null) return new InputSource(url.openStream());
			}
		}
		return new InputSource(systemId);
	}

}

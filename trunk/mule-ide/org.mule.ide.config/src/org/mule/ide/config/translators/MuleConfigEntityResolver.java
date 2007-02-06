/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.translators;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.mule.ide.config.mulemodel.MuleModelPlugin;
import org.mule.ide.config.mulemodel.MuleModelPlugin.Implementation;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The class <code>MuleConfigEntityResolver</code> provides an entity
 * resolver that tries to load the Document Type Definitions of the Mule
 * configuration files from the plugin directory.
 */
public class MuleConfigEntityResolver implements EntityResolver {

	/*
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
	 *      java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		if (MuleConfigConstants.MULE_CONFIG_PUBLIC_ID_1_0.equals(publicId) || MuleConfigConstants.LEGACY_MULE_CONFIG_PUBLIC_ID_1_0.equals(publicId)) {
			Implementation plugin = MuleModelPlugin.getPlugin();
			if (plugin != null) {
				URL url = FileLocator.find(plugin.getBundle(), new Path("schemas/mule-configuration_1_0.dtd"), Collections.emptyMap()); //$NON-NLS-1$
				if (url != null) return new InputSource(url.openStream());
			}
		}
		return new InputSource(systemId);
	}

}

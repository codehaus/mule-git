/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.mulemodel.util;

import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;
import org.eclipse.wst.xml.core.internal.emf2xml.EMF2DOMSSERenderer;

public class EMF2DOMSSERendererEx extends EMF2DOMSSERenderer {

	
	protected EMF2DOMAdapter createRootDOMAdapter() {
		return new EMF2DOMSSEAdapterEx(getResource(), document, this, getResource().getRootTranslator());
	}
}

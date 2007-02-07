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

import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;

/**
 * @author schacher
 */
public class EMF2DOMSSERendererExFactory extends RendererFactory {

	public static final EMF2DOMSSERendererExFactory INSTANCE = new EMF2DOMSSERendererExFactory();

	public EMF2DOMSSERendererExFactory() {
		// Default constructor
	}


	public Renderer createRenderer() {
		return new EMF2DOMSSERendererEx();
	}
}
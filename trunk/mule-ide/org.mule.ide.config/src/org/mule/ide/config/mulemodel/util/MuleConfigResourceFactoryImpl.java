/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ide.config.mulemodel.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;

/**
 * The class <code>MuleConfigResourceFactoryImpl</code> provides a resource
 * factory for the MuleConfig model.
 * 
 * @author Jesper
 */
public class MuleConfigResourceFactoryImpl extends TranslatorResourceFactory {

	/**
	 * Creates a new <code>MuleConfigResourceFactoryImpl</code>.
	 */
	public MuleConfigResourceFactoryImpl() {
		super(EMF2DOMSSERendererExFactory.INSTANCE);
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory#createResource(org.eclipse.emf.common.util.URI,
	 *      org.eclipse.wst.common.internal.emf.resource.Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer aRenderer) {
		return new MuleConfigResourceImpl(uri, aRenderer);
	}

}

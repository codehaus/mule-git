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

import org.eclipse.emf.common.util.URI;
import org.eclipse.jem.util.emf.workbench.ProjectResourceSet;
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

	private ProjectResourceSet resourceSet;
	
	/**
	 * Creates a new <code>MuleConfigResourceFactoryImpl</code>.
	 */
	public MuleConfigResourceFactoryImpl(ProjectResourceSet resourceSet) {
		super(EMF2DOMSSERendererExFactory.INSTANCE);
		this.resourceSet = resourceSet;
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory#createResource(org.eclipse.emf.common.util.URI,
	 *      org.eclipse.wst.common.internal.emf.resource.Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer aRenderer) {
//    	((AbstractRendererImpl)aRenderer).setValidating(false);
		MuleConfigResourceImpl resource = new MuleConfigResourceImpl(uri, aRenderer);
        return resource;
	}
}

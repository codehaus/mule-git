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
 * $Id: StrutsConfigResourceFactoryImpl.java,v 1.3 2005/07/21 15:21:01 daniel_rohe Exp $
 */
package org.mule.ide.prototype.mulemodel.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMRendererFactoryDefaultHandler;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;

/**
 * The class <code>MuleConfigResourceFactoryImpl</code> provides a resource
 * factory for the MuleConfig model.
 * 
 * @author Daniel
 */
public class MuleConfigResourceFactoryImpl extends TranslatorResourceFactory {

	/**
	 * Creates a new <code>StrutsConfigResourceFactoryImpl</code>.
	 */
	public MuleConfigResourceFactoryImpl() {
		super(EMF2DOMRendererFactoryDefaultHandler.INSTANCE
				.getDefaultRendererFactory());
	}

	/*
	 * @see org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory#createResource(org.eclipse.emf.common.util.URI,
	 *      org.eclipse.wst.common.internal.emf.resource.Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer aRenderer) {
		return new MuleConfigResourceImpl(uri, aRenderer);
	}

}

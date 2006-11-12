/*
 * $Id: StrutsConfigResourceFactoryImpl.java,v 1.3 2005/07/21 15:21:01 daniel_rohe Exp $
 */
package org.mule.ide.prototype.mulemodel.util;

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
	 * Creates a new <code>StrutsConfigResourceFactoryImpl</code>.
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

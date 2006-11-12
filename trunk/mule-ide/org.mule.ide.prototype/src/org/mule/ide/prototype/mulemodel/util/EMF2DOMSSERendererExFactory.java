package org.mule.ide.prototype.mulemodel.util;

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
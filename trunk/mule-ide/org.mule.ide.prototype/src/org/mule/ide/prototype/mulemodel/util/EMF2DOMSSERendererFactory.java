package org.mule.ide.prototype.mulemodel.util;

import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;

/**
 * @author schacher
 */
public class EMF2DOMSSERendererFactory extends RendererFactory {

	public static final EMF2DOMSSERendererFactory INSTANCE = new EMF2DOMSSERendererFactory();

	public EMF2DOMSSERendererFactory() {
		// Default constructor
	}


	public Renderer createRenderer() {
		return new EMF2DOMSSERendererEx();
	}
}
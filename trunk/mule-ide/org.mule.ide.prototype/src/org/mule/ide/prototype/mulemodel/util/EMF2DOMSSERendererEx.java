package org.mule.ide.prototype.mulemodel.util;

import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;
import org.eclipse.wst.xml.core.internal.emf2xml.EMF2DOMSSEAdapter;
import org.eclipse.wst.xml.core.internal.emf2xml.EMF2DOMSSERenderer;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Node;

public class EMF2DOMSSERendererEx extends EMF2DOMSSERenderer {

	
	protected EMF2DOMAdapter createRootDOMAdapter() {
		return new EMF2DOMSSEAdapterEx(getResource(), document, this, getResource().getRootTranslator());
	}
}

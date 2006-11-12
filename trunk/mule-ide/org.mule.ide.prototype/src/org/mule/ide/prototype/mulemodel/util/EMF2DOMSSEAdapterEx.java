package org.mule.ide.prototype.mulemodel.util;

import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMRenderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.utilities.DOMUtilities;
import org.eclipse.wst.xml.core.internal.emf2xml.EMF2DOMSSEAdapter;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class EMF2DOMSSEAdapterEx extends EMF2DOMSSEAdapter {

	public EMF2DOMSSEAdapterEx(Node node, EMF2DOMRenderer renderer, Translator translator) {
		super(node, renderer, translator);
	}

	public EMF2DOMSSEAdapterEx(Notifier object, Node node, EMF2DOMRenderer renderer, Translator translator) {
		super(object, node, renderer, translator);
	}

	public EMF2DOMSSEAdapterEx(TranslatorResource resource, Document document, EMF2DOMRenderer renderer, Translator translator) {
		super(resource, document, renderer, translator);
	}
	
	/**
	 * Create an adapter for a child DOM node
	 * 
	 * @param node
	 *            org.w3c.dom.Node The node to create the adapter for.
	 */
	protected EMF2DOMAdapter primCreateAdapter(EObject mofObject, Translator childMap) {
		Element newNode = createNewNode(mofObject, childMap);
		return new EMF2DOMSSEAdapterEx(mofObject, newNode, fRenderer, childMap);
	}

	/**
	 * Create an adapter for a child DOM node
	 * 
	 * @param node
	 *            org.w3c.dom.Node The node to create the adapter for.
	 */
	protected EMF2DOMAdapter primCreateAdapter(Node node, Translator childMap) {
		return new EMF2DOMSSEAdapterEx(node, fRenderer, childMap);
	}


	/**
	 * Update a DOM subtree to reflect the mofObject and map passed in. The element(s) named by 'map' is 
	 * updated (or, for comments, the comment DOM node preceding node).
	 */
	protected void updateDOMSubtree(Translator map, Node node, EObject mofObject, Object attrValue) {

		if (map.featureExists(mofObject)) {
			if ((map.isEnumFeature() || map.isBooleanFeature()) && (map.isUnsettable() && !map.isSetMOFValue(mofObject)))
				attrValue = null;
		} else
			attrValue = map.extractStringValue(mofObject);

		// Create and/or update the DOM subtree
		if (attrValue != null) {
			Node parent = createDOMPath(node, map);
			if (map.isManagedByParent()) {
				// Handle the case where the mof value is not another
				// mof object (primitive)
				if (map.getDOMName(mofObject) != null && map.getDOMName(mofObject).startsWith("#")) //$NON-NLS-1$
					return;

				String valueAsString = map.convertValueToString(attrValue, mofObject);
				if (map.isComment()) {
					createOrUpdateDOMComment(map, parent, valueAsString);
				} else {
					Element child = map.isDOMTextValue() ? (Element) parent : findOrCreateNode(parent, map, map.getDOMName(mofObject));
					findOrCreateTextNode(child, map, valueAsString);
				}
			} else {
				// Handle the case were the mof value is a mof object.
				EObject mofValue = (EObject) attrValue;
				EMF2DOMAdapter valueAdapter = (EMF2DOMAdapter) EcoreUtil.getExistingAdapter(mofValue, EMF2DOMAdapter.ADAPTER_CLASS);
				if (valueAdapter != null)
					valueAdapter.updateDOM();
				else {
					removeDOMChildren(parent, map);
					EMF2DOMAdapter adapter = createAdapter(mofValue, map);
					List mofChildren = map.getMOFChildren(mofObject);
					List domChildren = getDOMChildren(parent, map);

					Node insertBeforeNode = findInsertBeforeNode(parent, map, mofChildren, 0, domChildren);
					DOMUtilities.insertBeforeNodeAndWhitespace(parent, adapter.getNode(), insertBeforeNode);
					boolean notificationFlag = adapter.isNotificationEnabled();
					adapter.setNotificationEnabled(false);
					try {
						indent(adapter.getNode(), map);
					} finally {
						adapter.setNotificationEnabled(notificationFlag);
					}
					adapter.updateDOM();
				}
			}
		} else {
			// The attribute value was set to null or unset. Remove any
			// existing DOM nodes.
			Node child = findDOMNode(node, map);
			if (child != null)
				removeDOMChild(child.getParentNode(), child);
		}
	}

	/**
	 * Set a comment for node into the DOM, looking for a comment preceding  
	 * 
	 * @param map
	 * @param node
	 * @param valueAsString
	 */
	private void createOrUpdateDOMComment(Translator map, Node node, String valueAsString) {
		Node prev = node.getPreviousSibling();
		while (prev != null && prev.getNodeType() != Node.ELEMENT_NODE && prev.getNodeType() != Node.COMMENT_NODE) {
			if (prev.getNodeType() == Node.TEXT_NODE && ! DOMUtilities.isWhitespace(prev)) break; 
			prev = prev.getPreviousSibling();
		}

		if (prev != null && prev.getNodeType() == Node.COMMENT_NODE) {
			// We found the comment node - carefully replace text if required
			Comment comment = (Comment)prev;
			if (! valueAsString.equals(comment.getData())) {
				comment.setData(valueAsString);
			}
		} else {
			// We need to create new comment, so do that and indent it accordingly
			Comment comment = node.getOwnerDocument().createComment(valueAsString);
			DOMUtilities.insertBeforeNodeAndWhitespace(node.getParentNode(), comment, node);
			indent(comment, map);
		}
	}

}

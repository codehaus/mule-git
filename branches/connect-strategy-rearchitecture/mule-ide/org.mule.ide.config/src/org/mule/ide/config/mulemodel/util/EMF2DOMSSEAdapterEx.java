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

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMRenderer;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
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

	/**
	 * Update all the children of the target MOF object in the relationship described by
	 * 
	 * @map.
	 */
	protected void primUpdateDOMMultiAttributeFeature(Translator map, Node node, EObject mofObject) {
		List mofChildren = map.getMOFChildren(mofObject);
		StringBuffer sb = new StringBuffer();
		
		for (Iterator it = mofChildren.iterator(); it.hasNext(); ) {
			Object attrValue = null;
			try {
				attrValue = map.getMOFValue((EObject)it.next());
			} catch (IDTranslator.NoResourceException ex) {
				//If the object has been removed from the resource,
				//No need to update
				continue;
			}
			if (attrValue == null) continue;
			
			if (sb.length() > 0)
				sb.append(' ');
			sb.append(attrValue);
		}
		Element e = (Element) node;
		if (mofObject != null && mofObject.eIsSet(map.getFeature()))
			e.setAttribute(map.getDOMName(mofObject), sb.toString());
		else
			e.removeAttribute(map.getDOMName(mofObject));

	}

	/**
	 * Update all the children of the target MOF object in the relationship described by
	 * 
	 * @map.
	 */
	protected void primUpdateDOMMultiFeature(Translator map, Node node, EObject mofObject) {

		if (map.isDOMAttribute()) {
			primUpdateDOMMultiAttributeFeature(map, node, mofObject);
		} else {
			List mofChildren = map.getMOFChildren(mofObject);
			List domChildren = getDOMChildren(node, map);
	
			primUpdateDOMMultiFeature(map, node, mofChildren, domChildren, mofObject);
		}
	}

	protected void primUpdateMOFAttributeMultiFeature(Translator map, Node node, EObject mofObject) {

		List mofChildren = map.getMOFChildren(mofObject);

		String attrVal = ((Element)node).getAttribute(map.getDOMName(mofObject));
		if (attrVal == null) attrVal = "";

		// Go though the referenced children to see if the corresponding
		// MOF Adapter children exists. If not, create the adapter.
		// Also handles reordering children that have moved.
		
		StringTokenizer tokenizer = new StringTokenizer(attrVal);
		int mofIndex = 0;
		int i = 0;
		for (;tokenizer.hasMoreTokens();++ i) {
			String value = tokenizer.nextToken();
			
			Object mof = map.convertStringToValue(value, mofObject);
			
			EMF2DOMAdapter adapter = i < mofChildren.size() ? getExistingAdapter((EObject) mofChildren.get(i)) : null;
			if (adapter != null && !adapter.isMOFProxy() && adapter.getEObject() == mof) {
				mofIndex++;
				continue;
			}

			if (adapter != null) {
				reorderIfNecessary((EList) mofChildren,(EObject) mof, mofIndex);
				mofIndex++;
			} else {
				boolean wasEnabled = fNotificationEnabled;
				try {
					//We don't want to push anything back to the child dom
					setNotificationEnabled(false);
					map.setMOFValue(getTarget(), mof, mofIndex);
				} finally {
					setNotificationEnabled(wasEnabled);
				}
				mofIndex++;
			}
		}
		// Remove any remaining adapters.
		for (; i < mofChildren.size(); i++) {
			removeMOFValue((EObject) mofChildren.get(i), map);
		}
	}

	/**
	 * Update all the children of the target MOF object in the relationship described by
	 * 
	 * @map.
	 * 
	 * @param map
	 *            com.ibm.etools.mof2dom.AttributeTranslator Describes the mapping from the MOF
	 *            attribute name to the DOM node name
	 */
	protected void primUpdateMOFMultiFeature(Translator map, Node node, EObject mofObject) {
		// If the feature is a collection of strings or ints, call a special
		// method that handles this.
		if (map.isDOMAttribute()) {
			primUpdateMOFAttributeMultiFeature(map, node, mofObject);
		} else if (map.isManagedByParent()) {
			updateMOFMultiPrimitiveFeature(map, node, mofObject);
		} else{
			List nodeChildren = getDOMChildren(node, map);
			List mofChildren = map.getMOFChildren(mofObject);
	
			primUpdateMOFMultiFeature(map, node, mofChildren, nodeChildren);
		}
	}

}

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.prototype.mulemodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.mule.ide.prototype.mulemodel.AbstractComponent;
import org.mule.ide.prototype.mulemodel.InboundRouter;
import org.mule.ide.prototype.mulemodel.MulePackage;
import org.mule.ide.prototype.mulemodel.OutboundRouter;
import org.mule.ide.prototype.mulemodel.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.AbstractComponentImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.AbstractComponentImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.AbstractComponentImpl#getOutboundRouter <em>Outbound Router</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.AbstractComponentImpl#getInboundRouter <em>Inbound Router</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.AbstractComponentImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.AbstractComponentImpl#getComponentProperties <em>Component Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractComponentImpl extends EObjectImpl implements AbstractComponent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected String implementation = IMPLEMENTATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutboundRouter() <em>Outbound Router</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutboundRouter()
	 * @generated
	 * @ordered
	 */
	protected EList outboundRouter = null;

	/**
	 * The cached value of the '{@link #getInboundRouter() <em>Inbound Router</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInboundRouter()
	 * @generated
	 * @ordered
	 */
	protected EList inboundRouter = null;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getComponentProperties() <em>Component Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentProperties()
	 * @generated
	 * @ordered
	 */
	protected Property componentProperties = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.ABSTRACT_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementation() {
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementation(String newImplementation) {
		String oldImplementation = implementation;
		implementation = newImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ABSTRACT_COMPONENT__IMPLEMENTATION, oldImplementation, implementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ABSTRACT_COMPONENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOutboundRouter() {
		if (outboundRouter == null) {
			outboundRouter = new EObjectContainmentEList(OutboundRouter.class, this, MulePackage.ABSTRACT_COMPONENT__OUTBOUND_ROUTER);
		}
		return outboundRouter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInboundRouter() {
		if (inboundRouter == null) {
			inboundRouter = new EObjectContainmentEList(InboundRouter.class, this, MulePackage.ABSTRACT_COMPONENT__INBOUND_ROUTER);
		}
		return inboundRouter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ABSTRACT_COMPONENT__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property getComponentProperties() {
		return componentProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponentProperties(Property newComponentProperties, NotificationChain msgs) {
		Property oldComponentProperties = componentProperties;
		componentProperties = newComponentProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES, oldComponentProperties, newComponentProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentProperties(Property newComponentProperties) {
		if (newComponentProperties != componentProperties) {
			NotificationChain msgs = null;
			if (componentProperties != null)
				msgs = ((InternalEObject)componentProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES, null, msgs);
			if (newComponentProperties != null)
				msgs = ((InternalEObject)newComponentProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES, null, msgs);
			msgs = basicSetComponentProperties(newComponentProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES, newComponentProperties, newComponentProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MulePackage.ABSTRACT_COMPONENT__OUTBOUND_ROUTER:
				return ((InternalEList)getOutboundRouter()).basicRemove(otherEnd, msgs);
			case MulePackage.ABSTRACT_COMPONENT__INBOUND_ROUTER:
				return ((InternalEList)getInboundRouter()).basicRemove(otherEnd, msgs);
			case MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES:
				return basicSetComponentProperties(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MulePackage.ABSTRACT_COMPONENT__IMPLEMENTATION:
				return getImplementation();
			case MulePackage.ABSTRACT_COMPONENT__NAME:
				return getName();
			case MulePackage.ABSTRACT_COMPONENT__OUTBOUND_ROUTER:
				return getOutboundRouter();
			case MulePackage.ABSTRACT_COMPONENT__INBOUND_ROUTER:
				return getInboundRouter();
			case MulePackage.ABSTRACT_COMPONENT__COMMENT:
				return getComment();
			case MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES:
				return getComponentProperties();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MulePackage.ABSTRACT_COMPONENT__IMPLEMENTATION:
				setImplementation((String)newValue);
				return;
			case MulePackage.ABSTRACT_COMPONENT__NAME:
				setName((String)newValue);
				return;
			case MulePackage.ABSTRACT_COMPONENT__OUTBOUND_ROUTER:
				getOutboundRouter().clear();
				getOutboundRouter().addAll((Collection)newValue);
				return;
			case MulePackage.ABSTRACT_COMPONENT__INBOUND_ROUTER:
				getInboundRouter().clear();
				getInboundRouter().addAll((Collection)newValue);
				return;
			case MulePackage.ABSTRACT_COMPONENT__COMMENT:
				setComment((String)newValue);
				return;
			case MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES:
				setComponentProperties((Property)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case MulePackage.ABSTRACT_COMPONENT__IMPLEMENTATION:
				setImplementation(IMPLEMENTATION_EDEFAULT);
				return;
			case MulePackage.ABSTRACT_COMPONENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MulePackage.ABSTRACT_COMPONENT__OUTBOUND_ROUTER:
				getOutboundRouter().clear();
				return;
			case MulePackage.ABSTRACT_COMPONENT__INBOUND_ROUTER:
				getInboundRouter().clear();
				return;
			case MulePackage.ABSTRACT_COMPONENT__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES:
				setComponentProperties((Property)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MulePackage.ABSTRACT_COMPONENT__IMPLEMENTATION:
				return IMPLEMENTATION_EDEFAULT == null ? implementation != null : !IMPLEMENTATION_EDEFAULT.equals(implementation);
			case MulePackage.ABSTRACT_COMPONENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MulePackage.ABSTRACT_COMPONENT__OUTBOUND_ROUTER:
				return outboundRouter != null && !outboundRouter.isEmpty();
			case MulePackage.ABSTRACT_COMPONENT__INBOUND_ROUTER:
				return inboundRouter != null && !inboundRouter.isEmpty();
			case MulePackage.ABSTRACT_COMPONENT__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case MulePackage.ABSTRACT_COMPONENT__COMPONENT_PROPERTIES:
				return componentProperties != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (implementation: "); //$NON-NLS-1$
		result.append(implementation);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} //AbstractComponentImpl
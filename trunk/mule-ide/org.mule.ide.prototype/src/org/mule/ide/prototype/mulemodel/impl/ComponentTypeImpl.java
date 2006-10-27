/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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

import org.mule.ide.prototype.mulemodel.ComponentType;
import org.mule.ide.prototype.mulemodel.InboundRouterType;
import org.mule.ide.prototype.mulemodel.Interceptor;
import org.mule.ide.prototype.mulemodel.MulePackage;
import org.mule.ide.prototype.mulemodel.OutboundRouterType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl#getOutboundRouter <em>Outbound Router</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl#getInterceptors <em>Interceptors</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl#getInboundRouter <em>Inbound Router</em>}</li>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentTypeImpl extends EObjectImpl implements ComponentType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

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
	 * The cached value of the '{@link #getInterceptors() <em>Interceptors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterceptors()
	 * @generated
	 * @ordered
	 */
	protected EList interceptors = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.COMPONENT_TYPE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.COMPONENT_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOutboundRouter() {
		if (outboundRouter == null) {
			outboundRouter = new EObjectContainmentEList(OutboundRouterType.class, this, MulePackage.COMPONENT_TYPE__OUTBOUND_ROUTER);
		}
		return outboundRouter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInterceptors() {
		if (interceptors == null) {
			interceptors = new EObjectContainmentEList(Interceptor.class, this, MulePackage.COMPONENT_TYPE__INTERCEPTORS);
		}
		return interceptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInboundRouter() {
		if (inboundRouter == null) {
			inboundRouter = new EObjectContainmentEList(InboundRouterType.class, this, MulePackage.COMPONENT_TYPE__INBOUND_ROUTER);
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
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.COMPONENT_TYPE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MulePackage.COMPONENT_TYPE__OUTBOUND_ROUTER:
				return ((InternalEList)getOutboundRouter()).basicRemove(otherEnd, msgs);
			case MulePackage.COMPONENT_TYPE__INTERCEPTORS:
				return ((InternalEList)getInterceptors()).basicRemove(otherEnd, msgs);
			case MulePackage.COMPONENT_TYPE__INBOUND_ROUTER:
				return ((InternalEList)getInboundRouter()).basicRemove(otherEnd, msgs);
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
			case MulePackage.COMPONENT_TYPE__NAME:
				return getName();
			case MulePackage.COMPONENT_TYPE__OUTBOUND_ROUTER:
				return getOutboundRouter();
			case MulePackage.COMPONENT_TYPE__INTERCEPTORS:
				return getInterceptors();
			case MulePackage.COMPONENT_TYPE__INBOUND_ROUTER:
				return getInboundRouter();
			case MulePackage.COMPONENT_TYPE__COMMENT:
				return getComment();
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
			case MulePackage.COMPONENT_TYPE__NAME:
				setName((String)newValue);
				return;
			case MulePackage.COMPONENT_TYPE__OUTBOUND_ROUTER:
				getOutboundRouter().clear();
				getOutboundRouter().addAll((Collection)newValue);
				return;
			case MulePackage.COMPONENT_TYPE__INTERCEPTORS:
				getInterceptors().clear();
				getInterceptors().addAll((Collection)newValue);
				return;
			case MulePackage.COMPONENT_TYPE__INBOUND_ROUTER:
				getInboundRouter().clear();
				getInboundRouter().addAll((Collection)newValue);
				return;
			case MulePackage.COMPONENT_TYPE__COMMENT:
				setComment((String)newValue);
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
			case MulePackage.COMPONENT_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MulePackage.COMPONENT_TYPE__OUTBOUND_ROUTER:
				getOutboundRouter().clear();
				return;
			case MulePackage.COMPONENT_TYPE__INTERCEPTORS:
				getInterceptors().clear();
				return;
			case MulePackage.COMPONENT_TYPE__INBOUND_ROUTER:
				getInboundRouter().clear();
				return;
			case MulePackage.COMPONENT_TYPE__COMMENT:
				setComment(COMMENT_EDEFAULT);
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
			case MulePackage.COMPONENT_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MulePackage.COMPONENT_TYPE__OUTBOUND_ROUTER:
				return outboundRouter != null && !outboundRouter.isEmpty();
			case MulePackage.COMPONENT_TYPE__INTERCEPTORS:
				return interceptors != null && !interceptors.isEmpty();
			case MulePackage.COMPONENT_TYPE__INBOUND_ROUTER:
				return inboundRouter != null && !inboundRouter.isEmpty();
			case MulePackage.COMPONENT_TYPE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} //ComponentTypeImpl
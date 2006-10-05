/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.prototype.mulemodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.mule.ide.prototype.mulemodel.EndpointType;
import org.mule.ide.prototype.mulemodel.InboundRouterType;
import org.mule.ide.prototype.mulemodel.MulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inbound Router Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.InboundRouterTypeImpl#getInboundEndpoint <em>Inbound Endpoint</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InboundRouterTypeImpl extends EObjectImpl implements InboundRouterType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getInboundEndpoint() <em>Inbound Endpoint</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInboundEndpoint()
	 * @generated
	 * @ordered
	 */
	protected EndpointType inboundEndpoint = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InboundRouterTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.INBOUND_ROUTER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getInboundEndpoint() {
		if (inboundEndpoint != null && inboundEndpoint.eIsProxy()) {
			InternalEObject oldInboundEndpoint = (InternalEObject)inboundEndpoint;
			inboundEndpoint = (EndpointType)eResolveProxy(oldInboundEndpoint);
			if (inboundEndpoint != oldInboundEndpoint) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MulePackage.INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT, oldInboundEndpoint, inboundEndpoint));
			}
		}
		return inboundEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType basicGetInboundEndpoint() {
		return inboundEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInboundEndpoint(EndpointType newInboundEndpoint) {
		EndpointType oldInboundEndpoint = inboundEndpoint;
		inboundEndpoint = newInboundEndpoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT, oldInboundEndpoint, inboundEndpoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MulePackage.INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT:
				if (resolve) return getInboundEndpoint();
				return basicGetInboundEndpoint();
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
			case MulePackage.INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT:
				setInboundEndpoint((EndpointType)newValue);
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
			case MulePackage.INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT:
				setInboundEndpoint((EndpointType)null);
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
			case MulePackage.INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT:
				return inboundEndpoint != null;
		}
		return super.eIsSet(featureID);
	}

} //InboundRouterTypeImpl
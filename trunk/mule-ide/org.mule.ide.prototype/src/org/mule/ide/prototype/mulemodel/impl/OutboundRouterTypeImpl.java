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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.mule.ide.prototype.mulemodel.EndpointType;
import org.mule.ide.prototype.mulemodel.MulePackage;
import org.mule.ide.prototype.mulemodel.OutboundRouterType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Outbound Router Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.OutboundRouterTypeImpl#getOutboundEndpoint <em>Outbound Endpoint</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OutboundRouterTypeImpl extends EObjectImpl implements OutboundRouterType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getOutboundEndpoint() <em>Outbound Endpoint</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutboundEndpoint()
	 * @generated
	 * @ordered
	 */
	protected EndpointType outboundEndpoint = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OutboundRouterTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.OUTBOUND_ROUTER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getOutboundEndpoint() {
		return outboundEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutboundEndpoint(EndpointType newOutboundEndpoint) {
		EndpointType oldOutboundEndpoint = outboundEndpoint;
		outboundEndpoint = newOutboundEndpoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.OUTBOUND_ROUTER_TYPE__OUTBOUND_ENDPOINT, oldOutboundEndpoint, outboundEndpoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MulePackage.OUTBOUND_ROUTER_TYPE__OUTBOUND_ENDPOINT:
				return getOutboundEndpoint();
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
			case MulePackage.OUTBOUND_ROUTER_TYPE__OUTBOUND_ENDPOINT:
				setOutboundEndpoint((EndpointType)newValue);
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
			case MulePackage.OUTBOUND_ROUTER_TYPE__OUTBOUND_ENDPOINT:
				setOutboundEndpoint((EndpointType)null);
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
			case MulePackage.OUTBOUND_ROUTER_TYPE__OUTBOUND_ENDPOINT:
				return outboundEndpoint != null;
		}
		return super.eIsSet(featureID);
	}

} //OutboundRouterTypeImpl
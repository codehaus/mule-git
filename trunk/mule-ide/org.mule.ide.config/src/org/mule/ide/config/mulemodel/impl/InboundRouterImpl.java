/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.mule.ide.config.mulemodel.Endpoint;
import org.mule.ide.config.mulemodel.InboundRouter;
import org.mule.ide.config.mulemodel.MulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inbound Router</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.InboundRouterImpl#getInboundEndpoint <em>Inbound Endpoint</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InboundRouterImpl extends RouterImpl implements InboundRouter {
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
	protected Endpoint inboundEndpoint = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InboundRouterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.INBOUND_ROUTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Endpoint getInboundEndpoint() {
		if (inboundEndpoint != null && inboundEndpoint.eIsProxy()) {
			InternalEObject oldInboundEndpoint = (InternalEObject)inboundEndpoint;
			inboundEndpoint = (Endpoint)eResolveProxy(oldInboundEndpoint);
			if (inboundEndpoint != oldInboundEndpoint) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MulePackage.INBOUND_ROUTER__INBOUND_ENDPOINT, oldInboundEndpoint, inboundEndpoint));
			}
		}
		return inboundEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Endpoint basicGetInboundEndpoint() {
		return inboundEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInboundEndpoint(Endpoint newInboundEndpoint) {
		Endpoint oldInboundEndpoint = inboundEndpoint;
		inboundEndpoint = newInboundEndpoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.INBOUND_ROUTER__INBOUND_ENDPOINT, oldInboundEndpoint, inboundEndpoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MulePackage.INBOUND_ROUTER__INBOUND_ENDPOINT:
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
			case MulePackage.INBOUND_ROUTER__INBOUND_ENDPOINT:
				setInboundEndpoint((Endpoint)newValue);
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
			case MulePackage.INBOUND_ROUTER__INBOUND_ENDPOINT:
				setInboundEndpoint((Endpoint)null);
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
			case MulePackage.INBOUND_ROUTER__INBOUND_ENDPOINT:
				return inboundEndpoint != null;
		}
		return super.eIsSet(featureID);
	}

} //InboundRouterImpl
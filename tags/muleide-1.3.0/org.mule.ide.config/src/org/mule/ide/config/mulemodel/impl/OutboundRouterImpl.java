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
import org.mule.ide.config.mulemodel.MulePackage;
import org.mule.ide.config.mulemodel.OutboundRouter;
import org.mule.ide.config.mulemodel.Transformer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Outbound Router</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.OutboundRouterImpl#getOutboundEndpoint <em>Outbound Endpoint</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.OutboundRouterImpl#getOutboundTransformer <em>Outbound Transformer</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OutboundRouterImpl extends RouterImpl implements OutboundRouter {
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
	protected Endpoint outboundEndpoint = null;

	/**
	 * The cached value of the '{@link #getOutboundTransformer() <em>Outbound Transformer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutboundTransformer()
	 * @generated
	 * @ordered
	 */
	protected Transformer outboundTransformer = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OutboundRouterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.OUTBOUND_ROUTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Endpoint getOutboundEndpoint() {
		return outboundEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutboundEndpoint(Endpoint newOutboundEndpoint) {
		Endpoint oldOutboundEndpoint = outboundEndpoint;
		outboundEndpoint = newOutboundEndpoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.OUTBOUND_ROUTER__OUTBOUND_ENDPOINT, oldOutboundEndpoint, outboundEndpoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transformer getOutboundTransformer() {
		if (outboundTransformer != null && outboundTransformer.eIsProxy()) {
			InternalEObject oldOutboundTransformer = (InternalEObject)outboundTransformer;
			outboundTransformer = (Transformer)eResolveProxy(oldOutboundTransformer);
			if (outboundTransformer != oldOutboundTransformer) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MulePackage.OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER, oldOutboundTransformer, outboundTransformer));
			}
		}
		return outboundTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transformer basicGetOutboundTransformer() {
		return outboundTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutboundTransformer(Transformer newOutboundTransformer) {
		Transformer oldOutboundTransformer = outboundTransformer;
		outboundTransformer = newOutboundTransformer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER, oldOutboundTransformer, outboundTransformer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_ENDPOINT:
				return getOutboundEndpoint();
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER:
				if (resolve) return getOutboundTransformer();
				return basicGetOutboundTransformer();
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
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_ENDPOINT:
				setOutboundEndpoint((Endpoint)newValue);
				return;
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER:
				setOutboundTransformer((Transformer)newValue);
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
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_ENDPOINT:
				setOutboundEndpoint((Endpoint)null);
				return;
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER:
				setOutboundTransformer((Transformer)null);
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
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_ENDPOINT:
				return outboundEndpoint != null;
			case MulePackage.OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER:
				return outboundTransformer != null;
		}
		return super.eIsSet(featureID);
	}

} //OutboundRouterImpl
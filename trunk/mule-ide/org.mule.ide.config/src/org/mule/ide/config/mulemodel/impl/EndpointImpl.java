/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.mule.ide.config.mulemodel.AbstractFilter;
import org.mule.ide.config.mulemodel.Connector;
import org.mule.ide.config.mulemodel.Endpoint;
import org.mule.ide.config.mulemodel.MulePackage;
import org.mule.ide.config.mulemodel.Transformer;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Endpoint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.EndpointImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.EndpointImpl#getConnector <em>Connector</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.EndpointImpl#getFilter <em>Filter</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.EndpointImpl#getTransformers <em>Transformers</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.EndpointImpl#getResponseTransformers <em>Response Transformers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EndpointImpl extends EObjectImpl implements Endpoint {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected String address = ADDRESS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConnector() <em>Connector</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnector()
	 * @generated
	 * @ordered
	 */
	protected Connector connector = null;

	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
	protected AbstractFilter filter = null;

	/**
	 * The cached value of the '{@link #getTransformers() <em>Transformers</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformers()
	 * @generated
	 * @ordered
	 */
	protected EList transformers = null;

	/**
	 * The cached value of the '{@link #getResponseTransformers() <em>Response Transformers</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponseTransformers()
	 * @generated
	 * @ordered
	 */
	protected EList responseTransformers = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EndpointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.ENDPOINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(String newAddress) {
		String oldAddress = address;
		address = newAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ENDPOINT__ADDRESS, oldAddress, address));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector getConnector() {
		if (connector != null && connector.eIsProxy()) {
			InternalEObject oldConnector = (InternalEObject)connector;
			connector = (Connector)eResolveProxy(oldConnector);
			if (connector != oldConnector) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MulePackage.ENDPOINT__CONNECTOR, oldConnector, connector));
			}
		}
		return connector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector basicGetConnector() {
		return connector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnector(Connector newConnector) {
		Connector oldConnector = connector;
		connector = newConnector;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ENDPOINT__CONNECTOR, oldConnector, connector));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractFilter getFilter() {
		if (filter != null && filter.eIsProxy()) {
			InternalEObject oldFilter = (InternalEObject)filter;
			filter = (AbstractFilter)eResolveProxy(oldFilter);
			if (filter != oldFilter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MulePackage.ENDPOINT__FILTER, oldFilter, filter));
			}
		}
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractFilter basicGetFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilter(AbstractFilter newFilter) {
		AbstractFilter oldFilter = filter;
		filter = newFilter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ENDPOINT__FILTER, oldFilter, filter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTransformers() {
		if (transformers == null) {
			transformers = new EObjectResolvingEList(Transformer.class, this, MulePackage.ENDPOINT__TRANSFORMERS);
		}
		return transformers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getResponseTransformers() {
		if (responseTransformers == null) {
			responseTransformers = new EObjectResolvingEList(Transformer.class, this, MulePackage.ENDPOINT__RESPONSE_TRANSFORMERS);
		}
		return responseTransformers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MulePackage.ENDPOINT__ADDRESS:
				return getAddress();
			case MulePackage.ENDPOINT__CONNECTOR:
				if (resolve) return getConnector();
				return basicGetConnector();
			case MulePackage.ENDPOINT__FILTER:
				if (resolve) return getFilter();
				return basicGetFilter();
			case MulePackage.ENDPOINT__TRANSFORMERS:
				return getTransformers();
			case MulePackage.ENDPOINT__RESPONSE_TRANSFORMERS:
				return getResponseTransformers();
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
			case MulePackage.ENDPOINT__ADDRESS:
				setAddress((String)newValue);
				return;
			case MulePackage.ENDPOINT__CONNECTOR:
				setConnector((Connector)newValue);
				return;
			case MulePackage.ENDPOINT__FILTER:
				setFilter((AbstractFilter)newValue);
				return;
			case MulePackage.ENDPOINT__TRANSFORMERS:
				getTransformers().clear();
				getTransformers().addAll((Collection)newValue);
				return;
			case MulePackage.ENDPOINT__RESPONSE_TRANSFORMERS:
				getResponseTransformers().clear();
				getResponseTransformers().addAll((Collection)newValue);
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
			case MulePackage.ENDPOINT__ADDRESS:
				setAddress(ADDRESS_EDEFAULT);
				return;
			case MulePackage.ENDPOINT__CONNECTOR:
				setConnector((Connector)null);
				return;
			case MulePackage.ENDPOINT__FILTER:
				setFilter((AbstractFilter)null);
				return;
			case MulePackage.ENDPOINT__TRANSFORMERS:
				getTransformers().clear();
				return;
			case MulePackage.ENDPOINT__RESPONSE_TRANSFORMERS:
				getResponseTransformers().clear();
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
			case MulePackage.ENDPOINT__ADDRESS:
				return ADDRESS_EDEFAULT == null ? address != null : !ADDRESS_EDEFAULT.equals(address);
			case MulePackage.ENDPOINT__CONNECTOR:
				return connector != null;
			case MulePackage.ENDPOINT__FILTER:
				return filter != null;
			case MulePackage.ENDPOINT__TRANSFORMERS:
				return transformers != null && !transformers.isEmpty();
			case MulePackage.ENDPOINT__RESPONSE_TRANSFORMERS:
				return responseTransformers != null && !responseTransformers.isEmpty();
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
		result.append(" (address: "); //$NON-NLS-1$
		result.append(address);
		result.append(')');
		return result.toString();
	}

} //EndpointImpl
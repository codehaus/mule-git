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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.mule.ide.prototype.mulemodel.GenericComponent;
import org.mule.ide.prototype.mulemodel.Interceptor;
import org.mule.ide.prototype.mulemodel.MulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.prototype.mulemodel.impl.GenericComponentImpl#getInterceptors <em>Interceptors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenericComponentImpl extends AbstractComponentImpl implements GenericComponent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.GENERIC_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInterceptors() {
		if (interceptors == null) {
			interceptors = new EObjectContainmentEList(Interceptor.class, this, MulePackage.GENERIC_COMPONENT__INTERCEPTORS);
		}
		return interceptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MulePackage.GENERIC_COMPONENT__INTERCEPTORS:
				return ((InternalEList)getInterceptors()).basicRemove(otherEnd, msgs);
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
			case MulePackage.GENERIC_COMPONENT__INTERCEPTORS:
				return getInterceptors();
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
			case MulePackage.GENERIC_COMPONENT__INTERCEPTORS:
				getInterceptors().clear();
				getInterceptors().addAll((Collection)newValue);
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
			case MulePackage.GENERIC_COMPONENT__INTERCEPTORS:
				getInterceptors().clear();
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
			case MulePackage.GENERIC_COMPONENT__INTERCEPTORS:
				return interceptors != null && !interceptors.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GenericComponentImpl
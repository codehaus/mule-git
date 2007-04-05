/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.mule.ide.config.mulemodel.AbstractFilter;
import org.mule.ide.config.mulemodel.MulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.AbstractFilterImpl#getNestedFilter <em>Nested Filter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractFilterImpl extends EObjectImpl implements AbstractFilter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getNestedFilter() <em>Nested Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestedFilter()
	 * @generated
	 * @ordered
	 */
	protected AbstractFilter nestedFilter = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.ABSTRACT_FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractFilter getNestedFilter() {
		return nestedFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNestedFilter(AbstractFilter newNestedFilter, NotificationChain msgs) {
		AbstractFilter oldNestedFilter = nestedFilter;
		nestedFilter = newNestedFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MulePackage.ABSTRACT_FILTER__NESTED_FILTER, oldNestedFilter, newNestedFilter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNestedFilter(AbstractFilter newNestedFilter) {
		if (newNestedFilter != nestedFilter) {
			NotificationChain msgs = null;
			if (nestedFilter != null)
				msgs = ((InternalEObject)nestedFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MulePackage.ABSTRACT_FILTER__NESTED_FILTER, null, msgs);
			if (newNestedFilter != null)
				msgs = ((InternalEObject)newNestedFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MulePackage.ABSTRACT_FILTER__NESTED_FILTER, null, msgs);
			msgs = basicSetNestedFilter(newNestedFilter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.ABSTRACT_FILTER__NESTED_FILTER, newNestedFilter, newNestedFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MulePackage.ABSTRACT_FILTER__NESTED_FILTER:
				return basicSetNestedFilter(null, msgs);
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
			case MulePackage.ABSTRACT_FILTER__NESTED_FILTER:
				return getNestedFilter();
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
			case MulePackage.ABSTRACT_FILTER__NESTED_FILTER:
				setNestedFilter((AbstractFilter)newValue);
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
			case MulePackage.ABSTRACT_FILTER__NESTED_FILTER:
				setNestedFilter((AbstractFilter)null);
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
			case MulePackage.ABSTRACT_FILTER__NESTED_FILTER:
				return nestedFilter != null;
		}
		return super.eIsSet(featureID);
	}

} //AbstractFilterImpl
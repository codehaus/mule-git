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

import org.mule.ide.config.mulemodel.AbstractFilter;
import org.mule.ide.config.mulemodel.BinaryFilter;
import org.mule.ide.config.mulemodel.MulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binary Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.BinaryFilterImpl#getLeftFilter <em>Left Filter</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.BinaryFilterImpl#getRightFilter <em>Right Filter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BinaryFilterImpl extends AbstractFilterImpl implements BinaryFilter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getLeftFilter() <em>Left Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftFilter()
	 * @generated
	 * @ordered
	 */
	protected AbstractFilter leftFilter = null;

	/**
	 * The cached value of the '{@link #getRightFilter() <em>Right Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRightFilter()
	 * @generated
	 * @ordered
	 */
	protected AbstractFilter rightFilter = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BinaryFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.BINARY_FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractFilter getLeftFilter() {
		return leftFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeftFilter(AbstractFilter newLeftFilter, NotificationChain msgs) {
		AbstractFilter oldLeftFilter = leftFilter;
		leftFilter = newLeftFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MulePackage.BINARY_FILTER__LEFT_FILTER, oldLeftFilter, newLeftFilter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeftFilter(AbstractFilter newLeftFilter) {
		if (newLeftFilter != leftFilter) {
			NotificationChain msgs = null;
			if (leftFilter != null)
				msgs = ((InternalEObject)leftFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MulePackage.BINARY_FILTER__LEFT_FILTER, null, msgs);
			if (newLeftFilter != null)
				msgs = ((InternalEObject)newLeftFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MulePackage.BINARY_FILTER__LEFT_FILTER, null, msgs);
			msgs = basicSetLeftFilter(newLeftFilter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.BINARY_FILTER__LEFT_FILTER, newLeftFilter, newLeftFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractFilter getRightFilter() {
		return rightFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRightFilter(AbstractFilter newRightFilter, NotificationChain msgs) {
		AbstractFilter oldRightFilter = rightFilter;
		rightFilter = newRightFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MulePackage.BINARY_FILTER__RIGHT_FILTER, oldRightFilter, newRightFilter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRightFilter(AbstractFilter newRightFilter) {
		if (newRightFilter != rightFilter) {
			NotificationChain msgs = null;
			if (rightFilter != null)
				msgs = ((InternalEObject)rightFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MulePackage.BINARY_FILTER__RIGHT_FILTER, null, msgs);
			if (newRightFilter != null)
				msgs = ((InternalEObject)newRightFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MulePackage.BINARY_FILTER__RIGHT_FILTER, null, msgs);
			msgs = basicSetRightFilter(newRightFilter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.BINARY_FILTER__RIGHT_FILTER, newRightFilter, newRightFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MulePackage.BINARY_FILTER__LEFT_FILTER:
				return basicSetLeftFilter(null, msgs);
			case MulePackage.BINARY_FILTER__RIGHT_FILTER:
				return basicSetRightFilter(null, msgs);
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
			case MulePackage.BINARY_FILTER__LEFT_FILTER:
				return getLeftFilter();
			case MulePackage.BINARY_FILTER__RIGHT_FILTER:
				return getRightFilter();
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
			case MulePackage.BINARY_FILTER__LEFT_FILTER:
				setLeftFilter((AbstractFilter)newValue);
				return;
			case MulePackage.BINARY_FILTER__RIGHT_FILTER:
				setRightFilter((AbstractFilter)newValue);
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
			case MulePackage.BINARY_FILTER__LEFT_FILTER:
				setLeftFilter((AbstractFilter)null);
				return;
			case MulePackage.BINARY_FILTER__RIGHT_FILTER:
				setRightFilter((AbstractFilter)null);
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
			case MulePackage.BINARY_FILTER__LEFT_FILTER:
				return leftFilter != null;
			case MulePackage.BINARY_FILTER__RIGHT_FILTER:
				return rightFilter != null;
		}
		return super.eIsSet(featureID);
	}

} //BinaryFilterImpl
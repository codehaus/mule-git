/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binary Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.BinaryFilter#getLeftFilter <em>Left Filter</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.BinaryFilter#getRightFilter <em>Right Filter</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getBinaryFilter()
 * @model
 * @generated
 */
public interface BinaryFilter extends AbstractFilter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Left Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left Filter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left Filter</em>' containment reference.
	 * @see #setLeftFilter(AbstractFilter)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getBinaryFilter_LeftFilter()
	 * @model containment="true"
	 * @generated
	 */
	AbstractFilter getLeftFilter();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.BinaryFilter#getLeftFilter <em>Left Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left Filter</em>' containment reference.
	 * @see #getLeftFilter()
	 * @generated
	 */
	void setLeftFilter(AbstractFilter value);

	/**
	 * Returns the value of the '<em><b>Right Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right Filter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right Filter</em>' containment reference.
	 * @see #setRightFilter(AbstractFilter)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getBinaryFilter_RightFilter()
	 * @model containment="true"
	 * @generated
	 */
	AbstractFilter getRightFilter();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.BinaryFilter#getRightFilter <em>Right Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right Filter</em>' containment reference.
	 * @see #getRightFilter()
	 * @generated
	 */
	void setRightFilter(AbstractFilter value);

} // BinaryFilter
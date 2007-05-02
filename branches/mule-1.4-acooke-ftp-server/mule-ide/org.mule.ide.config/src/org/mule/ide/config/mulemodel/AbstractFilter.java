/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.AbstractFilter#getNestedFilter <em>Nested Filter</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractFilter()
 * @model abstract="true"
 * @generated
 */
public interface AbstractFilter extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Nested Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nested Filter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nested Filter</em>' containment reference.
	 * @see #setNestedFilter(AbstractFilter)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractFilter_NestedFilter()
	 * @model containment="true"
	 * @generated
	 */
	AbstractFilter getNestedFilter();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.AbstractFilter#getNestedFilter <em>Nested Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nested Filter</em>' containment reference.
	 * @see #getNestedFilter()
	 * @generated
	 */
	void setNestedFilter(AbstractFilter value);

} // AbstractFilter
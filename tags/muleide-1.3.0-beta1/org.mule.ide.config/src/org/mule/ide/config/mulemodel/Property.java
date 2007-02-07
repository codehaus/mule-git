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
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.Property#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getProperty()
 * @model abstract="true"
 * @generated
 */
public interface Property extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getProperty_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.Property#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Property
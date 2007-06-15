/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.GenericComponent#getImplementation <em>Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getGenericComponent()
 * @model
 * @generated
 */
public interface GenericComponent extends AbstractComponent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation</em>' attribute.
	 * @see #setImplementation(String)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getGenericComponent_Implementation()
	 * @model
	 * @generated
	 */
	String getImplementation();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.GenericComponent#getImplementation <em>Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation</em>' attribute.
	 * @see #getImplementation()
	 * @generated
	 */
	void setImplementation(String value);

} // GenericComponent
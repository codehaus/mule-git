/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Endpoint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.GlobalEndpoint#getName <em>Name</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.GlobalEndpoint#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getGlobalEndpoint()
 * @model
 * @generated
 */
public interface GlobalEndpoint extends Endpoint {
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
	 * @see org.mule.ide.config.mulemodel.MulePackage#getGlobalEndpoint_Name()
	 * @model id="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.GlobalEndpoint#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getGlobalEndpoint_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.GlobalEndpoint#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

} // GlobalEndpoint
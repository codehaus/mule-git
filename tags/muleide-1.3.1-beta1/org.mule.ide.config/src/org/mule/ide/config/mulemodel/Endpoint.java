/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Endpoint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.Endpoint#getAddress <em>Address</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.Endpoint#getConnector <em>Connector</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.Endpoint#getFilter <em>Filter</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.Endpoint#getTransformers <em>Transformers</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.Endpoint#getResponseTransformers <em>Response Transformers</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getEndpoint()
 * @model abstract="true"
 * @generated
 */
public interface Endpoint extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' attribute.
	 * @see #setAddress(String)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getEndpoint_Address()
	 * @model required="true"
	 * @generated
	 */
	String getAddress();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.Endpoint#getAddress <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address</em>' attribute.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(String value);

	/**
	 * Returns the value of the '<em><b>Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connector</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connector</em>' reference.
	 * @see #setConnector(Connector)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getEndpoint_Connector()
	 * @model
	 * @generated
	 */
	Connector getConnector();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.Endpoint#getConnector <em>Connector</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connector</em>' reference.
	 * @see #getConnector()
	 * @generated
	 */
	void setConnector(Connector value);

	/**
	 * Returns the value of the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filter</em>' reference.
	 * @see #setFilter(AbstractFilter)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getEndpoint_Filter()
	 * @model
	 * @generated
	 */
	AbstractFilter getFilter();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.Endpoint#getFilter <em>Filter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter</em>' reference.
	 * @see #getFilter()
	 * @generated
	 */
	void setFilter(AbstractFilter value);

	/**
	 * Returns the value of the '<em><b>Transformers</b></em>' reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.Transformer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformers</em>' reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getEndpoint_Transformers()
	 * @model type="org.mule.ide.config.mulemodel.Transformer"
	 * @generated
	 */
	EList getTransformers();

	/**
	 * Returns the value of the '<em><b>Response Transformers</b></em>' reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.Transformer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Response Transformers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Response Transformers</em>' reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getEndpoint_ResponseTransformers()
	 * @model type="org.mule.ide.config.mulemodel.Transformer"
	 * @generated
	 */
	EList getResponseTransformers();

} // Endpoint
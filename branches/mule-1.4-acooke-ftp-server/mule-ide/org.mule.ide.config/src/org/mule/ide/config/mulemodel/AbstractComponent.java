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
 * A representation of the model object '<em><b>Abstract Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.AbstractComponent#getName <em>Name</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.AbstractComponent#getOutboundRouter <em>Outbound Router</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.AbstractComponent#getInboundRouter <em>Inbound Router</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.AbstractComponent#getComment <em>Comment</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.AbstractComponent#getComponentProperties <em>Component Properties</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.AbstractComponent#getInterceptors <em>Interceptors</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractComponent()
 * @model abstract="true"
 * @generated
 */
public interface AbstractComponent extends EObject {
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
	 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractComponent_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.AbstractComponent#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Outbound Router</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.OutboundRouter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outbound Router</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outbound Router</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractComponent_OutboundRouter()
	 * @model type="org.mule.ide.config.mulemodel.OutboundRouter" containment="true"
	 * @generated
	 */
	EList getOutboundRouter();

	/**
	 * Returns the value of the '<em><b>Inbound Router</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.InboundRouter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inbound Router</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inbound Router</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractComponent_InboundRouter()
	 * @model type="org.mule.ide.config.mulemodel.InboundRouter" containment="true"
	 * @generated
	 */
	EList getInboundRouter();

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
	 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractComponent_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.AbstractComponent#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Component Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Properties</em>' containment reference.
	 * @see #setComponentProperties(Property)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractComponent_ComponentProperties()
	 * @model containment="true"
	 * @generated
	 */
	Property getComponentProperties();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.AbstractComponent#getComponentProperties <em>Component Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Properties</em>' containment reference.
	 * @see #getComponentProperties()
	 * @generated
	 */
	void setComponentProperties(Property value);

	/**
	 * Returns the value of the '<em><b>Interceptors</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.Interceptor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interceptors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interceptors</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getAbstractComponent_Interceptors()
	 * @model type="org.mule.ide.config.mulemodel.Interceptor" containment="true"
	 * @generated
	 */
	EList getInterceptors();

} // AbstractComponent
/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */
package org.mule.ide.config.mulemodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getVersion <em>Version</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getDescription <em>Description</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getInterceptors <em>Interceptors</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getTransformers <em>Transformers</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getGlobalEndpoints <em>Global Endpoints</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.MuleConfig#getComponents <em>Components</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig()
 * @model
 * @generated
 */
public interface MuleConfig extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Global Endpoints</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.GlobalEndpoint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Global Endpoints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global Endpoints</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_GlobalEndpoints()
	 * @model type="org.mule.ide.config.mulemodel.GlobalEndpoint" containment="true"
	 * @generated
	 */
	EList getGlobalEndpoints();

	/**
	 * Returns the value of the '<em><b>Transformers</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.Transformer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformers</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformers</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_Transformers()
	 * @model type="org.mule.ide.config.mulemodel.Transformer" containment="true"
	 * @generated
	 */
	EList getTransformers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addComponent();

	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.AbstractComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_Components()
	 * @model type="org.mule.ide.config.mulemodel.AbstractComponent" containment="true"
	 * @generated
	 */
	EList getComponents();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.MuleConfig#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.MuleConfig#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(Properties)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_Properties()
	 * @model containment="true"
	 * @generated
	 */
	Properties getProperties();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.MuleConfig#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(Properties value);

	/**
	 * Returns the value of the '<em><b>Interceptors</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.InterceptorStack}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interceptors</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interceptors</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_Interceptors()
	 * @model type="org.mule.ide.config.mulemodel.InterceptorStack" containment="true"
	 * @generated
	 */
	EList getInterceptors();

	/**
	 * Returns the value of the '<em><b>Connectors</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.Connector}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connectors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connectors</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getMuleConfig_Connectors()
	 * @model type="org.mule.ide.config.mulemodel.Connector" containment="true"
	 * @generated
	 */
	EList getConnectors();

} // MuleConfig
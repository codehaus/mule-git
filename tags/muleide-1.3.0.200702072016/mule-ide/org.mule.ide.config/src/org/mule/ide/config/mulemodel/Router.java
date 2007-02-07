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
 * A representation of the model object '<em><b>Router</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.Router#getLocalEndpoints <em>Local Endpoints</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getRouter()
 * @model
 * @generated
 */
public interface Router extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Local Endpoints</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.config.mulemodel.LocalEndpoint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Endpoints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Endpoints</em>' containment reference list.
	 * @see org.mule.ide.config.mulemodel.MulePackage#getRouter_LocalEndpoints()
	 * @model type="org.mule.ide.config.mulemodel.LocalEndpoint" containment="true"
	 * @generated
	 */
	EList getLocalEndpoints();

} // Router
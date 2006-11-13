/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.prototype.mulemodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.prototype.mulemodel.GenericComponent#getInterceptors <em>Interceptors</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.prototype.mulemodel.MulePackage#getGenericComponent()
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
	 * Returns the value of the '<em><b>Interceptors</b></em>' containment reference list.
	 * The list contents are of type {@link org.mule.ide.prototype.mulemodel.Interceptor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interceptors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interceptors</em>' containment reference list.
	 * @see org.mule.ide.prototype.mulemodel.MulePackage#getGenericComponent_Interceptors()
	 * @model type="org.mule.ide.prototype.mulemodel.Interceptor" containment="true"
	 * @generated
	 */
	EList getInterceptors();

} // GenericComponent
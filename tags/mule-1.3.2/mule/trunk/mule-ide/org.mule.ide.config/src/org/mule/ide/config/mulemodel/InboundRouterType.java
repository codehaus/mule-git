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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inbound Router Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.InboundRouterType#getInboundEndpoint <em>Inbound Endpoint</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getInboundRouterType()
 * @model
 * @generated
 */
public interface InboundRouterType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Inbound Endpoint</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inbound Endpoint</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inbound Endpoint</em>' reference.
	 * @see #setInboundEndpoint(EndpointType)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getInboundRouterType_InboundEndpoint()
	 * @model
	 * @generated
	 */
	EndpointType getInboundEndpoint();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.InboundRouterType#getInboundEndpoint <em>Inbound Endpoint</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inbound Endpoint</em>' reference.
	 * @see #getInboundEndpoint()
	 * @generated
	 */
	void setInboundEndpoint(EndpointType value);

} // InboundRouterType
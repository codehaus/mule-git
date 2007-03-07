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
 * A representation of the model object '<em><b>Outbound Router Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.OutboundRouterType#getOutboundEndpoint <em>Outbound Endpoint</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getOutboundRouterType()
 * @model
 * @generated
 */
public interface OutboundRouterType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Outbound Endpoint</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outbound Endpoint</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outbound Endpoint</em>' reference.
	 * @see #setOutboundEndpoint(EndpointType)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getOutboundRouterType_OutboundEndpoint()
	 * @model resolveProxies="false"
	 * @generated
	 */
	EndpointType getOutboundEndpoint();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.OutboundRouterType#getOutboundEndpoint <em>Outbound Endpoint</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outbound Endpoint</em>' reference.
	 * @see #getOutboundEndpoint()
	 * @generated
	 */
	void setOutboundEndpoint(EndpointType value);

} // OutboundRouterType
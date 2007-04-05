/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inbound Router</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.InboundRouter#getInboundEndpoint <em>Inbound Endpoint</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getInboundRouter()
 * @model
 * @generated
 */
public interface InboundRouter extends Router {
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
	 * @see #setInboundEndpoint(Endpoint)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getInboundRouter_InboundEndpoint()
	 * @model
	 * @generated
	 */
	Endpoint getInboundEndpoint();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.InboundRouter#getInboundEndpoint <em>Inbound Endpoint</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inbound Endpoint</em>' reference.
	 * @see #getInboundEndpoint()
	 * @generated
	 */
	void setInboundEndpoint(Endpoint value);

} // InboundRouter
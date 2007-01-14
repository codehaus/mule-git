/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.config.mulemodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Outbound Router</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.OutboundRouter#getOutboundEndpoint <em>Outbound Endpoint</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.OutboundRouter#getOutboundTransformer <em>Outbound Transformer</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.ide.config.mulemodel.MulePackage#getOutboundRouter()
 * @model
 * @generated
 */
public interface OutboundRouter extends Router {
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
	 * @see #setOutboundEndpoint(Endpoint)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getOutboundRouter_OutboundEndpoint()
	 * @model resolveProxies="false"
	 * @generated
	 */
	Endpoint getOutboundEndpoint();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.OutboundRouter#getOutboundEndpoint <em>Outbound Endpoint</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outbound Endpoint</em>' reference.
	 * @see #getOutboundEndpoint()
	 * @generated
	 */
	void setOutboundEndpoint(Endpoint value);

	/**
	 * Returns the value of the '<em><b>Outbound Transformer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outbound Transformer</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outbound Transformer</em>' reference.
	 * @see #setOutboundTransformer(Transformer)
	 * @see org.mule.ide.config.mulemodel.MulePackage#getOutboundRouter_OutboundTransformer()
	 * @model
	 * @generated
	 */
	Transformer getOutboundTransformer();

	/**
	 * Sets the value of the '{@link org.mule.ide.config.mulemodel.OutboundRouter#getOutboundTransformer <em>Outbound Transformer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outbound Transformer</em>' reference.
	 * @see #getOutboundTransformer()
	 * @generated
	 */
	void setOutboundTransformer(Transformer value);

} // OutboundRouter
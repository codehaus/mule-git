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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.mule.ide.config.mulemodel.MulePackage
 * @generated
 */
public interface MuleFactory extends EFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MuleFactory eINSTANCE = org.mule.ide.config.mulemodel.impl.MuleFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Inbound Router</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inbound Router</em>'.
	 * @generated
	 */
	InboundRouter createInboundRouter();

	/**
	 * Returns a new object of class '<em>Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Config</em>'.
	 * @generated
	 */
	MuleConfig createMuleConfig();

	/**
	 * Returns a new object of class '<em>Outbound Router</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Outbound Router</em>'.
	 * @generated
	 */
	OutboundRouter createOutboundRouter();

	/**
	 * Returns a new object of class '<em>Interceptor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interceptor</em>'.
	 * @generated
	 */
	Interceptor createInterceptor();

	/**
	 * Returns a new object of class '<em>Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connector</em>'.
	 * @generated
	 */
	Connector createConnector();

	/**
	 * Returns a new object of class '<em>Properties</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Properties</em>'.
	 * @generated
	 */
	Properties createProperties();

	/**
	 * Returns a new object of class '<em>Text Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text Property</em>'.
	 * @generated
	 */
	TextProperty createTextProperty();

	/**
	 * Returns a new object of class '<em>List Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Property</em>'.
	 * @generated
	 */
	ListProperty createListProperty();

	/**
	 * Returns a new object of class '<em>Map Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Property</em>'.
	 * @generated
	 */
	MapProperty createMapProperty();

	/**
	 * Returns a new object of class '<em>Interceptor Stack</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interceptor Stack</em>'.
	 * @generated
	 */
	InterceptorStack createInterceptorStack();

	/**
	 * Returns a new object of class '<em>Bridge Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bridge Component</em>'.
	 * @generated
	 */
	BridgeComponent createBridgeComponent();

	/**
	 * Returns a new object of class '<em>Local Endpoint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Endpoint</em>'.
	 * @generated
	 */
	LocalEndpoint createLocalEndpoint();

	/**
	 * Returns a new object of class '<em>Global Endpoint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Endpoint</em>'.
	 * @generated
	 */
	GlobalEndpoint createGlobalEndpoint();

	/**
	 * Returns a new object of class '<em>Router</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Router</em>'.
	 * @generated
	 */
	Router createRouter();

	/**
	 * Returns a new object of class '<em>Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transformer</em>'.
	 * @generated
	 */
	Transformer createTransformer();

	/**
	 * Returns a new object of class '<em>Generic Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Component</em>'.
	 * @generated
	 */
	GenericComponent createGenericComponent();

	/**
	 * Returns a new object of class '<em>Generic Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Filter</em>'.
	 * @generated
	 */
	GenericFilter createGenericFilter();

	/**
	 * Returns a new object of class '<em>Binary Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Filter</em>'.
	 * @generated
	 */
	BinaryFilter createBinaryFilter();

	/**
	 * Returns a new object of class '<em>Xslt Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Xslt Filter</em>'.
	 * @generated
	 */
	XsltFilter createXsltFilter();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MulePackage getMulePackage();

} //MuleFactory

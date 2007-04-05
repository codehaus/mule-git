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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.mule.ide.config.mulemodel.MuleFactory
 * @model kind="package"
 * @generated
 */
public interface MulePackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "mulemodel"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://muleumo.org/model/"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.mule.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MulePackage eINSTANCE = org.mule.ide.config.mulemodel.impl.MulePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.RouterImpl <em>Router</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.RouterImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getRouter()
	 * @generated
	 */
	int ROUTER = 17;

	/**
	 * The feature id for the '<em><b>Local Endpoints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROUTER__LOCAL_ENDPOINTS = 0;

	/**
	 * The number of structural features of the '<em>Router</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROUTER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.InboundRouterImpl <em>Inbound Router</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.InboundRouterImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getInboundRouter()
	 * @generated
	 */
	int INBOUND_ROUTER = 0;

	/**
	 * The feature id for the '<em><b>Local Endpoints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INBOUND_ROUTER__LOCAL_ENDPOINTS = ROUTER__LOCAL_ENDPOINTS;

	/**
	 * The feature id for the '<em><b>Inbound Endpoint</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INBOUND_ROUTER__INBOUND_ENDPOINT = ROUTER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inbound Router</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INBOUND_ROUTER_FEATURE_COUNT = ROUTER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl <em>Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.MuleConfigImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getMuleConfig()
	 * @generated
	 */
	int MULE_CONFIG = 2;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.EndpointImpl <em>Endpoint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.EndpointImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getEndpoint()
	 * @generated
	 */
	int ENDPOINT = 3;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.OutboundRouterImpl <em>Outbound Router</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.OutboundRouterImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getOutboundRouter()
	 * @generated
	 */
	int OUTBOUND_ROUTER = 4;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.InterceptorImpl <em>Interceptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.InterceptorImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getInterceptor()
	 * @generated
	 */
	int INTERCEPTOR = 5;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.ConnectorImpl <em>Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.ConnectorImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getConnector()
	 * @generated
	 */
	int CONNECTOR = 6;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.PropertiesImpl <em>Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.PropertiesImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getProperties()
	 * @generated
	 */
	int PROPERTIES = 7;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.PropertyImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 8;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.TextPropertyImpl <em>Text Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.TextPropertyImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getTextProperty()
	 * @generated
	 */
	int TEXT_PROPERTY = 9;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.ListPropertyImpl <em>List Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.ListPropertyImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getListProperty()
	 * @generated
	 */
	int LIST_PROPERTY = 10;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.MapPropertyImpl <em>Map Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.MapPropertyImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getMapProperty()
	 * @generated
	 */
	int MAP_PROPERTY = 11;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.AbstractComponentImpl <em>Abstract Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.AbstractComponentImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getAbstractComponent()
	 * @generated
	 */
	int ABSTRACT_COMPONENT = 1;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.BridgeComponentImpl <em>Bridge Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.BridgeComponentImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getBridgeComponent()
	 * @generated
	 */
	int BRIDGE_COMPONENT = 14;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.LocalEndpointImpl <em>Local Endpoint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.LocalEndpointImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getLocalEndpoint()
	 * @generated
	 */
	int LOCAL_ENDPOINT = 15;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.GlobalEndpointImpl <em>Global Endpoint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.GlobalEndpointImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getGlobalEndpoint()
	 * @generated
	 */
	int GLOBAL_ENDPOINT = 16;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.TransformerImpl <em>Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.TransformerImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getTransformer()
	 * @generated
	 */
	int TRANSFORMER = 18;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.GenericComponentImpl <em>Generic Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.GenericComponentImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getGenericComponent()
	 * @generated
	 */
	int GENERIC_COMPONENT = 13;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.AbstractFilterImpl <em>Abstract Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.AbstractFilterImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getAbstractFilter()
	 * @generated
	 */
	int ABSTRACT_FILTER = 19;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.GenericFilterImpl <em>Generic Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.GenericFilterImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getGenericFilter()
	 * @generated
	 */
	int GENERIC_FILTER = 20;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.BinaryFilterImpl <em>Binary Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.BinaryFilterImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getBinaryFilter()
	 * @generated
	 */
	int BINARY_FILTER = 21;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.XsltFilterImpl <em>Xslt Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.XsltFilterImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getXsltFilter()
	 * @generated
	 */
	int XSLT_FILTER = 22;

	/**
	 * The meta object id for the '{@link org.mule.ide.config.mulemodel.impl.InterceptorStackImpl <em>Interceptor Stack</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.config.mulemodel.impl.InterceptorStackImpl
	 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getInterceptorStack()
	 * @generated
	 */
	int INTERCEPTOR_STACK = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Outbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__OUTBOUND_ROUTER = 1;

	/**
	 * The feature id for the '<em><b>Inbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__INBOUND_ROUTER = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__COMMENT = 3;

	/**
	 * The feature id for the '<em><b>Component Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__COMPONENT_PROPERTIES = 4;

	/**
	 * The feature id for the '<em><b>Interceptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__INTERCEPTORS = 5;

	/**
	 * The number of structural features of the '<em>Abstract Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__PROPERTIES = 2;

	/**
	 * The feature id for the '<em><b>Interceptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__INTERCEPTORS = 3;

	/**
	 * The feature id for the '<em><b>Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__CONNECTORS = 4;

	/**
	 * The feature id for the '<em><b>Transformers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__TRANSFORMERS = 5;

	/**
	 * The feature id for the '<em><b>Global Endpoints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__GLOBAL_ENDPOINTS = 6;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__COMPONENTS = 7;

	/**
	 * The number of structural features of the '<em>Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG_FEATURE_COUNT = 8;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT__ADDRESS = 0;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT__CONNECTOR = 1;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT__FILTER = 2;

	/**
	 * The feature id for the '<em><b>Transformers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT__TRANSFORMERS = 3;

	/**
	 * The feature id for the '<em><b>Response Transformers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT__RESPONSE_TRANSFORMERS = 4;

	/**
	 * The number of structural features of the '<em>Endpoint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Local Endpoints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTBOUND_ROUTER__LOCAL_ENDPOINTS = ROUTER__LOCAL_ENDPOINTS;

	/**
	 * The feature id for the '<em><b>Outbound Endpoint</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTBOUND_ROUTER__OUTBOUND_ENDPOINT = ROUTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outbound Transformer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER = ROUTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Outbound Router</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTBOUND_ROUTER_FEATURE_COUNT = ROUTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR__NAME = 0;

	/**
	 * The feature id for the '<em><b>Group Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR__GROUP_DEFINITION = 1;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR__CLASS_NAME = 2;

	/**
	 * The number of structural features of the '<em>Interceptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR_FEATURE_COUNT = 3;


	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__COMMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__NAME = 1;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__CLASS_NAME = 2;

	/**
	 * The number of structural features of the '<em>Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES__PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = 0;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_PROPERTY__NAME = PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_PROPERTY__VALUE = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_PROPERTY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_PROPERTY__NAME = PROPERTY__NAME;

	/**
	 * The number of structural features of the '<em>List Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_PROPERTY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_PROPERTY__NAME = PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_PROPERTY__PROPERTIES = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_PROPERTY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Interceptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR_STACK__INTERCEPTORS = 0;

	/**
	 * The number of structural features of the '<em>Interceptor Stack</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR_STACK_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT__NAME = ABSTRACT_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Outbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT__OUTBOUND_ROUTER = ABSTRACT_COMPONENT__OUTBOUND_ROUTER;

	/**
	 * The feature id for the '<em><b>Inbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT__INBOUND_ROUTER = ABSTRACT_COMPONENT__INBOUND_ROUTER;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT__COMMENT = ABSTRACT_COMPONENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Component Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT__COMPONENT_PROPERTIES = ABSTRACT_COMPONENT__COMPONENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Interceptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT__INTERCEPTORS = ABSTRACT_COMPONENT__INTERCEPTORS;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT__IMPLEMENTATION = ABSTRACT_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Generic Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_COMPONENT_FEATURE_COUNT = ABSTRACT_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRIDGE_COMPONENT__NAME = ABSTRACT_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Outbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRIDGE_COMPONENT__OUTBOUND_ROUTER = ABSTRACT_COMPONENT__OUTBOUND_ROUTER;

	/**
	 * The feature id for the '<em><b>Inbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRIDGE_COMPONENT__INBOUND_ROUTER = ABSTRACT_COMPONENT__INBOUND_ROUTER;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRIDGE_COMPONENT__COMMENT = ABSTRACT_COMPONENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Component Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRIDGE_COMPONENT__COMPONENT_PROPERTIES = ABSTRACT_COMPONENT__COMPONENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Interceptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRIDGE_COMPONENT__INTERCEPTORS = ABSTRACT_COMPONENT__INTERCEPTORS;

	/**
	 * The number of structural features of the '<em>Bridge Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRIDGE_COMPONENT_FEATURE_COUNT = ABSTRACT_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ENDPOINT__ADDRESS = ENDPOINT__ADDRESS;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ENDPOINT__CONNECTOR = ENDPOINT__CONNECTOR;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ENDPOINT__FILTER = ENDPOINT__FILTER;

	/**
	 * The feature id for the '<em><b>Transformers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ENDPOINT__TRANSFORMERS = ENDPOINT__TRANSFORMERS;

	/**
	 * The feature id for the '<em><b>Response Transformers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ENDPOINT__RESPONSE_TRANSFORMERS = ENDPOINT__RESPONSE_TRANSFORMERS;

	/**
	 * The number of structural features of the '<em>Local Endpoint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ENDPOINT_FEATURE_COUNT = ENDPOINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT__ADDRESS = ENDPOINT__ADDRESS;

	/**
	 * The feature id for the '<em><b>Connector</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT__CONNECTOR = ENDPOINT__CONNECTOR;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT__FILTER = ENDPOINT__FILTER;

	/**
	 * The feature id for the '<em><b>Transformers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT__TRANSFORMERS = ENDPOINT__TRANSFORMERS;

	/**
	 * The feature id for the '<em><b>Response Transformers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT__RESPONSE_TRANSFORMERS = ENDPOINT__RESPONSE_TRANSFORMERS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT__NAME = ENDPOINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT__COMMENT = ENDPOINT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Global Endpoint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_ENDPOINT_FEATURE_COUNT = ENDPOINT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMER__CLASS_NAME = 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMER__COMMENT = 1;

	/**
	 * The feature id for the '<em><b>Return Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMER__RETURN_CLASS = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMER__NAME = 3;

	/**
	 * The number of structural features of the '<em>Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMER_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Nested Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILTER__NESTED_FILTER = 0;

	/**
	 * The number of structural features of the '<em>Abstract Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILTER_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Nested Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_FILTER__NESTED_FILTER = ABSTRACT_FILTER__NESTED_FILTER;

	/**
	 * The number of structural features of the '<em>Generic Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_FILTER_FEATURE_COUNT = ABSTRACT_FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nested Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FILTER__NESTED_FILTER = ABSTRACT_FILTER__NESTED_FILTER;

	/**
	 * The feature id for the '<em><b>Left Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FILTER__LEFT_FILTER = ABSTRACT_FILTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FILTER__RIGHT_FILTER = ABSTRACT_FILTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binary Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_FILTER_FEATURE_COUNT = ABSTRACT_FILTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Nested Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FILTER__NESTED_FILTER = ABSTRACT_FILTER__NESTED_FILTER;

	/**
	 * The number of structural features of the '<em>Xslt Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XSLT_FILTER_FEATURE_COUNT = ABSTRACT_FILTER_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.InboundRouter <em>Inbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inbound Router</em>'.
	 * @see org.mule.ide.config.mulemodel.InboundRouter
	 * @generated
	 */
	EClass getInboundRouter();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.config.mulemodel.InboundRouter#getInboundEndpoint <em>Inbound Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Inbound Endpoint</em>'.
	 * @see org.mule.ide.config.mulemodel.InboundRouter#getInboundEndpoint()
	 * @see #getInboundRouter()
	 * @generated
	 */
	EReference getInboundRouter_InboundEndpoint();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.MuleConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig
	 * @generated
	 */
	EClass getMuleConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.MuleConfig#getGlobalEndpoints <em>Global Endpoints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Global Endpoints</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getGlobalEndpoints()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_GlobalEndpoints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.MuleConfig#getTransformers <em>Transformers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transformers</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getTransformers()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_Transformers();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.Endpoint <em>Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Endpoint</em>'.
	 * @see org.mule.ide.config.mulemodel.Endpoint
	 * @generated
	 */
	EClass getEndpoint();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Endpoint#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see org.mule.ide.config.mulemodel.Endpoint#getAddress()
	 * @see #getEndpoint()
	 * @generated
	 */
	EAttribute getEndpoint_Address();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.config.mulemodel.Endpoint#getConnector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Connector</em>'.
	 * @see org.mule.ide.config.mulemodel.Endpoint#getConnector()
	 * @see #getEndpoint()
	 * @generated
	 */
	EReference getEndpoint_Connector();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.config.mulemodel.Endpoint#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.Endpoint#getFilter()
	 * @see #getEndpoint()
	 * @generated
	 */
	EReference getEndpoint_Filter();

	/**
	 * Returns the meta object for the reference list '{@link org.mule.ide.config.mulemodel.Endpoint#getTransformers <em>Transformers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Transformers</em>'.
	 * @see org.mule.ide.config.mulemodel.Endpoint#getTransformers()
	 * @see #getEndpoint()
	 * @generated
	 */
	EReference getEndpoint_Transformers();

	/**
	 * Returns the meta object for the reference list '{@link org.mule.ide.config.mulemodel.Endpoint#getResponseTransformers <em>Response Transformers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Response Transformers</em>'.
	 * @see org.mule.ide.config.mulemodel.Endpoint#getResponseTransformers()
	 * @see #getEndpoint()
	 * @generated
	 */
	EReference getEndpoint_ResponseTransformers();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.OutboundRouter <em>Outbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Outbound Router</em>'.
	 * @see org.mule.ide.config.mulemodel.OutboundRouter
	 * @generated
	 */
	EClass getOutboundRouter();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.config.mulemodel.OutboundRouter#getOutboundEndpoint <em>Outbound Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Outbound Endpoint</em>'.
	 * @see org.mule.ide.config.mulemodel.OutboundRouter#getOutboundEndpoint()
	 * @see #getOutboundRouter()
	 * @generated
	 */
	EReference getOutboundRouter_OutboundEndpoint();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.config.mulemodel.OutboundRouter#getOutboundTransformer <em>Outbound Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Outbound Transformer</em>'.
	 * @see org.mule.ide.config.mulemodel.OutboundRouter#getOutboundTransformer()
	 * @see #getOutboundRouter()
	 * @generated
	 */
	EReference getOutboundRouter_OutboundTransformer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.MuleConfig#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getComponents()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_Components();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.MuleConfig#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getVersion()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EAttribute getMuleConfig_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.MuleConfig#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getDescription()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EAttribute getMuleConfig_Description();

	/**
	 * Returns the meta object for the containment reference '{@link org.mule.ide.config.mulemodel.MuleConfig#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getProperties()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.MuleConfig#getInterceptors <em>Interceptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interceptors</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getInterceptors()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_Interceptors();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.MuleConfig#getConnectors <em>Connectors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connectors</em>'.
	 * @see org.mule.ide.config.mulemodel.MuleConfig#getConnectors()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_Connectors();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.Interceptor <em>Interceptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interceptor</em>'.
	 * @see org.mule.ide.config.mulemodel.Interceptor
	 * @generated
	 */
	EClass getInterceptor();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Interceptor#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.config.mulemodel.Interceptor#getName()
	 * @see #getInterceptor()
	 * @generated
	 */
	EAttribute getInterceptor_Name();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.config.mulemodel.Interceptor#getGroupDefinition <em>Group Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Group Definition</em>'.
	 * @see org.mule.ide.config.mulemodel.Interceptor#getGroupDefinition()
	 * @see #getInterceptor()
	 * @generated
	 */
	EReference getInterceptor_GroupDefinition();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Interceptor#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.mule.ide.config.mulemodel.Interceptor#getClassName()
	 * @see #getInterceptor()
	 * @generated
	 */
	EAttribute getInterceptor_ClassName();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.Connector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector</em>'.
	 * @see org.mule.ide.config.mulemodel.Connector
	 * @generated
	 */
	EClass getConnector();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Connector#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.mule.ide.config.mulemodel.Connector#getComment()
	 * @see #getConnector()
	 * @generated
	 */
	EAttribute getConnector_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Connector#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.config.mulemodel.Connector#getName()
	 * @see #getConnector()
	 * @generated
	 */
	EAttribute getConnector_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Connector#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.mule.ide.config.mulemodel.Connector#getClassName()
	 * @see #getConnector()
	 * @generated
	 */
	EAttribute getConnector_ClassName();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.Properties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Properties</em>'.
	 * @see org.mule.ide.config.mulemodel.Properties
	 * @generated
	 */
	EClass getProperties();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.Properties#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.mule.ide.config.mulemodel.Properties#getProperties()
	 * @see #getProperties()
	 * @generated
	 */
	EReference getProperties_Properties();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.mule.ide.config.mulemodel.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.config.mulemodel.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.TextProperty <em>Text Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Property</em>'.
	 * @see org.mule.ide.config.mulemodel.TextProperty
	 * @generated
	 */
	EClass getTextProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.TextProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.mule.ide.config.mulemodel.TextProperty#getValue()
	 * @see #getTextProperty()
	 * @generated
	 */
	EAttribute getTextProperty_Value();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.ListProperty <em>List Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Property</em>'.
	 * @see org.mule.ide.config.mulemodel.ListProperty
	 * @generated
	 */
	EClass getListProperty();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.MapProperty <em>Map Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Property</em>'.
	 * @see org.mule.ide.config.mulemodel.MapProperty
	 * @generated
	 */
	EClass getMapProperty();

	/**
	 * Returns the meta object for the containment reference '{@link org.mule.ide.config.mulemodel.MapProperty#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Properties</em>'.
	 * @see org.mule.ide.config.mulemodel.MapProperty#getProperties()
	 * @see #getMapProperty()
	 * @generated
	 */
	EReference getMapProperty_Properties();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.InterceptorStack <em>Interceptor Stack</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interceptor Stack</em>'.
	 * @see org.mule.ide.config.mulemodel.InterceptorStack
	 * @generated
	 */
	EClass getInterceptorStack();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.InterceptorStack#getInterceptors <em>Interceptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interceptors</em>'.
	 * @see org.mule.ide.config.mulemodel.InterceptorStack#getInterceptors()
	 * @see #getInterceptorStack()
	 * @generated
	 */
	EReference getInterceptorStack_Interceptors();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.BridgeComponent <em>Bridge Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bridge Component</em>'.
	 * @see org.mule.ide.config.mulemodel.BridgeComponent
	 * @generated
	 */
	EClass getBridgeComponent();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.LocalEndpoint <em>Local Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Endpoint</em>'.
	 * @see org.mule.ide.config.mulemodel.LocalEndpoint
	 * @generated
	 */
	EClass getLocalEndpoint();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.GlobalEndpoint <em>Global Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Endpoint</em>'.
	 * @see org.mule.ide.config.mulemodel.GlobalEndpoint
	 * @generated
	 */
	EClass getGlobalEndpoint();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.GlobalEndpoint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.config.mulemodel.GlobalEndpoint#getName()
	 * @see #getGlobalEndpoint()
	 * @generated
	 */
	EAttribute getGlobalEndpoint_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.GlobalEndpoint#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.mule.ide.config.mulemodel.GlobalEndpoint#getComment()
	 * @see #getGlobalEndpoint()
	 * @generated
	 */
	EAttribute getGlobalEndpoint_Comment();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.Router <em>Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Router</em>'.
	 * @see org.mule.ide.config.mulemodel.Router
	 * @generated
	 */
	EClass getRouter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.Router#getLocalEndpoints <em>Local Endpoints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Local Endpoints</em>'.
	 * @see org.mule.ide.config.mulemodel.Router#getLocalEndpoints()
	 * @see #getRouter()
	 * @generated
	 */
	EReference getRouter_LocalEndpoints();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.Transformer <em>Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transformer</em>'.
	 * @see org.mule.ide.config.mulemodel.Transformer
	 * @generated
	 */
	EClass getTransformer();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Transformer#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.mule.ide.config.mulemodel.Transformer#getClassName()
	 * @see #getTransformer()
	 * @generated
	 */
	EAttribute getTransformer_ClassName();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Transformer#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.mule.ide.config.mulemodel.Transformer#getComment()
	 * @see #getTransformer()
	 * @generated
	 */
	EAttribute getTransformer_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Transformer#getReturnClass <em>Return Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Class</em>'.
	 * @see org.mule.ide.config.mulemodel.Transformer#getReturnClass()
	 * @see #getTransformer()
	 * @generated
	 */
	EAttribute getTransformer_ReturnClass();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.Transformer#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.config.mulemodel.Transformer#getName()
	 * @see #getTransformer()
	 * @generated
	 */
	EAttribute getTransformer_Name();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.AbstractComponent <em>Abstract Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Component</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractComponent
	 * @generated
	 */
	EClass getAbstractComponent();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.AbstractComponent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractComponent#getName()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.AbstractComponent#getOutboundRouter <em>Outbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outbound Router</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractComponent#getOutboundRouter()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_OutboundRouter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.AbstractComponent#getInboundRouter <em>Inbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inbound Router</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractComponent#getInboundRouter()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_InboundRouter();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.AbstractComponent#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractComponent#getComment()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_Comment();

	/**
	 * Returns the meta object for the containment reference '{@link org.mule.ide.config.mulemodel.AbstractComponent#getComponentProperties <em>Component Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Component Properties</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractComponent#getComponentProperties()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_ComponentProperties();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.config.mulemodel.AbstractComponent#getInterceptors <em>Interceptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interceptors</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractComponent#getInterceptors()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_Interceptors();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.GenericComponent <em>Generic Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Component</em>'.
	 * @see org.mule.ide.config.mulemodel.GenericComponent
	 * @generated
	 */
	EClass getGenericComponent();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.config.mulemodel.GenericComponent#getImplementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation</em>'.
	 * @see org.mule.ide.config.mulemodel.GenericComponent#getImplementation()
	 * @see #getGenericComponent()
	 * @generated
	 */
	EAttribute getGenericComponent_Implementation();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.AbstractFilter <em>Abstract Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractFilter
	 * @generated
	 */
	EClass getAbstractFilter();

	/**
	 * Returns the meta object for the containment reference '{@link org.mule.ide.config.mulemodel.AbstractFilter#getNestedFilter <em>Nested Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Nested Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.AbstractFilter#getNestedFilter()
	 * @see #getAbstractFilter()
	 * @generated
	 */
	EReference getAbstractFilter_NestedFilter();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.GenericFilter <em>Generic Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.GenericFilter
	 * @generated
	 */
	EClass getGenericFilter();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.BinaryFilter <em>Binary Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.BinaryFilter
	 * @generated
	 */
	EClass getBinaryFilter();

	/**
	 * Returns the meta object for the containment reference '{@link org.mule.ide.config.mulemodel.BinaryFilter#getLeftFilter <em>Left Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.BinaryFilter#getLeftFilter()
	 * @see #getBinaryFilter()
	 * @generated
	 */
	EReference getBinaryFilter_LeftFilter();

	/**
	 * Returns the meta object for the containment reference '{@link org.mule.ide.config.mulemodel.BinaryFilter#getRightFilter <em>Right Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.BinaryFilter#getRightFilter()
	 * @see #getBinaryFilter()
	 * @generated
	 */
	EReference getBinaryFilter_RightFilter();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.config.mulemodel.XsltFilter <em>Xslt Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Xslt Filter</em>'.
	 * @see org.mule.ide.config.mulemodel.XsltFilter
	 * @generated
	 */
	EClass getXsltFilter();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MuleFactory getMuleFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.InboundRouterImpl <em>Inbound Router</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.InboundRouterImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getInboundRouter()
		 * @generated
		 */
		EClass INBOUND_ROUTER = eINSTANCE.getInboundRouter();

		/**
		 * The meta object literal for the '<em><b>Inbound Endpoint</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INBOUND_ROUTER__INBOUND_ENDPOINT = eINSTANCE.getInboundRouter_InboundEndpoint();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl <em>Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.MuleConfigImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getMuleConfig()
		 * @generated
		 */
		EClass MULE_CONFIG = eINSTANCE.getMuleConfig();

		/**
		 * The meta object literal for the '<em><b>Global Endpoints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULE_CONFIG__GLOBAL_ENDPOINTS = eINSTANCE.getMuleConfig_GlobalEndpoints();

		/**
		 * The meta object literal for the '<em><b>Transformers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULE_CONFIG__TRANSFORMERS = eINSTANCE.getMuleConfig_Transformers();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.EndpointImpl <em>Endpoint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.EndpointImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getEndpoint()
		 * @generated
		 */
		EClass ENDPOINT = eINSTANCE.getEndpoint();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT__ADDRESS = eINSTANCE.getEndpoint_Address();

		/**
		 * The meta object literal for the '<em><b>Connector</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENDPOINT__CONNECTOR = eINSTANCE.getEndpoint_Connector();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENDPOINT__FILTER = eINSTANCE.getEndpoint_Filter();

		/**
		 * The meta object literal for the '<em><b>Transformers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENDPOINT__TRANSFORMERS = eINSTANCE.getEndpoint_Transformers();

		/**
		 * The meta object literal for the '<em><b>Response Transformers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENDPOINT__RESPONSE_TRANSFORMERS = eINSTANCE.getEndpoint_ResponseTransformers();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.OutboundRouterImpl <em>Outbound Router</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.OutboundRouterImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getOutboundRouter()
		 * @generated
		 */
		EClass OUTBOUND_ROUTER = eINSTANCE.getOutboundRouter();

		/**
		 * The meta object literal for the '<em><b>Outbound Endpoint</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OUTBOUND_ROUTER__OUTBOUND_ENDPOINT = eINSTANCE.getOutboundRouter_OutboundEndpoint();

		/**
		 * The meta object literal for the '<em><b>Outbound Transformer</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER = eINSTANCE.getOutboundRouter_OutboundTransformer();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULE_CONFIG__COMPONENTS = eINSTANCE.getMuleConfig_Components();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULE_CONFIG__VERSION = eINSTANCE.getMuleConfig_Version();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULE_CONFIG__DESCRIPTION = eINSTANCE.getMuleConfig_Description();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULE_CONFIG__PROPERTIES = eINSTANCE.getMuleConfig_Properties();

		/**
		 * The meta object literal for the '<em><b>Interceptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULE_CONFIG__INTERCEPTORS = eINSTANCE.getMuleConfig_Interceptors();

		/**
		 * The meta object literal for the '<em><b>Connectors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULE_CONFIG__CONNECTORS = eINSTANCE.getMuleConfig_Connectors();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.InterceptorImpl <em>Interceptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.InterceptorImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getInterceptor()
		 * @generated
		 */
		EClass INTERCEPTOR = eINSTANCE.getInterceptor();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERCEPTOR__NAME = eINSTANCE.getInterceptor_Name();

		/**
		 * The meta object literal for the '<em><b>Group Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERCEPTOR__GROUP_DEFINITION = eINSTANCE.getInterceptor_GroupDefinition();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERCEPTOR__CLASS_NAME = eINSTANCE.getInterceptor_ClassName();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.ConnectorImpl <em>Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.ConnectorImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getConnector()
		 * @generated
		 */
		EClass CONNECTOR = eINSTANCE.getConnector();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR__COMMENT = eINSTANCE.getConnector_Comment();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR__NAME = eINSTANCE.getConnector_Name();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR__CLASS_NAME = eINSTANCE.getConnector_ClassName();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.PropertiesImpl <em>Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.PropertiesImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getProperties()
		 * @generated
		 */
		EClass PROPERTIES = eINSTANCE.getProperties();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTIES__PROPERTIES = eINSTANCE.getProperties_Properties();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.PropertyImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.TextPropertyImpl <em>Text Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.TextPropertyImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getTextProperty()
		 * @generated
		 */
		EClass TEXT_PROPERTY = eINSTANCE.getTextProperty();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_PROPERTY__VALUE = eINSTANCE.getTextProperty_Value();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.ListPropertyImpl <em>List Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.ListPropertyImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getListProperty()
		 * @generated
		 */
		EClass LIST_PROPERTY = eINSTANCE.getListProperty();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.MapPropertyImpl <em>Map Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.MapPropertyImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getMapProperty()
		 * @generated
		 */
		EClass MAP_PROPERTY = eINSTANCE.getMapProperty();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_PROPERTY__PROPERTIES = eINSTANCE.getMapProperty_Properties();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.InterceptorStackImpl <em>Interceptor Stack</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.InterceptorStackImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getInterceptorStack()
		 * @generated
		 */
		EClass INTERCEPTOR_STACK = eINSTANCE.getInterceptorStack();

		/**
		 * The meta object literal for the '<em><b>Interceptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERCEPTOR_STACK__INTERCEPTORS = eINSTANCE.getInterceptorStack_Interceptors();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.BridgeComponentImpl <em>Bridge Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.BridgeComponentImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getBridgeComponent()
		 * @generated
		 */
		EClass BRIDGE_COMPONENT = eINSTANCE.getBridgeComponent();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.LocalEndpointImpl <em>Local Endpoint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.LocalEndpointImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getLocalEndpoint()
		 * @generated
		 */
		EClass LOCAL_ENDPOINT = eINSTANCE.getLocalEndpoint();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.GlobalEndpointImpl <em>Global Endpoint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.GlobalEndpointImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getGlobalEndpoint()
		 * @generated
		 */
		EClass GLOBAL_ENDPOINT = eINSTANCE.getGlobalEndpoint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_ENDPOINT__NAME = eINSTANCE.getGlobalEndpoint_Name();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_ENDPOINT__COMMENT = eINSTANCE.getGlobalEndpoint_Comment();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.RouterImpl <em>Router</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.RouterImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getRouter()
		 * @generated
		 */
		EClass ROUTER = eINSTANCE.getRouter();

		/**
		 * The meta object literal for the '<em><b>Local Endpoints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROUTER__LOCAL_ENDPOINTS = eINSTANCE.getRouter_LocalEndpoints();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.TransformerImpl <em>Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.TransformerImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getTransformer()
		 * @generated
		 */
		EClass TRANSFORMER = eINSTANCE.getTransformer();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMER__CLASS_NAME = eINSTANCE.getTransformer_ClassName();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMER__COMMENT = eINSTANCE.getTransformer_Comment();

		/**
		 * The meta object literal for the '<em><b>Return Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMER__RETURN_CLASS = eINSTANCE.getTransformer_ReturnClass();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMER__NAME = eINSTANCE.getTransformer_Name();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.AbstractComponentImpl <em>Abstract Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.AbstractComponentImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getAbstractComponent()
		 * @generated
		 */
		EClass ABSTRACT_COMPONENT = eINSTANCE.getAbstractComponent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__NAME = eINSTANCE.getAbstractComponent_Name();

		/**
		 * The meta object literal for the '<em><b>Outbound Router</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__OUTBOUND_ROUTER = eINSTANCE.getAbstractComponent_OutboundRouter();

		/**
		 * The meta object literal for the '<em><b>Inbound Router</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__INBOUND_ROUTER = eINSTANCE.getAbstractComponent_InboundRouter();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__COMMENT = eINSTANCE.getAbstractComponent_Comment();

		/**
		 * The meta object literal for the '<em><b>Component Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__COMPONENT_PROPERTIES = eINSTANCE.getAbstractComponent_ComponentProperties();

		/**
		 * The meta object literal for the '<em><b>Interceptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__INTERCEPTORS = eINSTANCE.getAbstractComponent_Interceptors();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.GenericComponentImpl <em>Generic Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.GenericComponentImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getGenericComponent()
		 * @generated
		 */
		EClass GENERIC_COMPONENT = eINSTANCE.getGenericComponent();

		/**
		 * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_COMPONENT__IMPLEMENTATION = eINSTANCE.getGenericComponent_Implementation();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.AbstractFilterImpl <em>Abstract Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.AbstractFilterImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getAbstractFilter()
		 * @generated
		 */
		EClass ABSTRACT_FILTER = eINSTANCE.getAbstractFilter();

		/**
		 * The meta object literal for the '<em><b>Nested Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_FILTER__NESTED_FILTER = eINSTANCE.getAbstractFilter_NestedFilter();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.GenericFilterImpl <em>Generic Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.GenericFilterImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getGenericFilter()
		 * @generated
		 */
		EClass GENERIC_FILTER = eINSTANCE.getGenericFilter();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.BinaryFilterImpl <em>Binary Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.BinaryFilterImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getBinaryFilter()
		 * @generated
		 */
		EClass BINARY_FILTER = eINSTANCE.getBinaryFilter();

		/**
		 * The meta object literal for the '<em><b>Left Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_FILTER__LEFT_FILTER = eINSTANCE.getBinaryFilter_LeftFilter();

		/**
		 * The meta object literal for the '<em><b>Right Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_FILTER__RIGHT_FILTER = eINSTANCE.getBinaryFilter_RightFilter();

		/**
		 * The meta object literal for the '{@link org.mule.ide.config.mulemodel.impl.XsltFilterImpl <em>Xslt Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.config.mulemodel.impl.XsltFilterImpl
		 * @see org.mule.ide.config.mulemodel.impl.MulePackageImpl#getXsltFilter()
		 * @generated
		 */
		EClass XSLT_FILTER = eINSTANCE.getXsltFilter();

	}

} //MulePackage

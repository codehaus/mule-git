/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */
package org.mule.ide.config.mulemodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.mule.ide.config.mulemodel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MuleFactoryImpl extends EFactoryImpl implements MuleFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MuleFactory init() {
		try {
			MuleFactory theMuleFactory = (MuleFactory)EPackage.Registry.INSTANCE.getEFactory("http://muleumo.org/model/"); //$NON-NLS-1$ 
			if (theMuleFactory != null) {
				return theMuleFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MuleFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuleFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MulePackage.INBOUND_ROUTER: return createInboundRouter();
			case MulePackage.MULE_CONFIG: return createMuleConfig();
			case MulePackage.OUTBOUND_ROUTER: return createOutboundRouter();
			case MulePackage.INTERCEPTOR: return createInterceptor();
			case MulePackage.CONNECTOR: return createConnector();
			case MulePackage.PROPERTIES: return createProperties();
			case MulePackage.TEXT_PROPERTY: return createTextProperty();
			case MulePackage.LIST_PROPERTY: return createListProperty();
			case MulePackage.MAP_PROPERTY: return createMapProperty();
			case MulePackage.INTERCEPTOR_STACK: return createInterceptorStack();
			case MulePackage.GENERIC_COMPONENT: return createGenericComponent();
			case MulePackage.BRIDGE_COMPONENT: return createBridgeComponent();
			case MulePackage.LOCAL_ENDPOINT: return createLocalEndpoint();
			case MulePackage.GLOBAL_ENDPOINT: return createGlobalEndpoint();
			case MulePackage.ROUTER: return createRouter();
			case MulePackage.TRANSFORMER: return createTransformer();
			case MulePackage.GENERIC_FILTER: return createGenericFilter();
			case MulePackage.BINARY_FILTER: return createBinaryFilter();
			case MulePackage.XSLT_FILTER: return createXsltFilter();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InboundRouter createInboundRouter() {
		InboundRouterImpl inboundRouter = new InboundRouterImpl();
		return inboundRouter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuleConfig createMuleConfig() {
		MuleConfigImpl muleConfig = new MuleConfigImpl();
		return muleConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OutboundRouter createOutboundRouter() {
		OutboundRouterImpl outboundRouter = new OutboundRouterImpl();
		return outboundRouter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interceptor createInterceptor() {
		InterceptorImpl interceptor = new InterceptorImpl();
		return interceptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector createConnector() {
		ConnectorImpl connector = new ConnectorImpl();
		return connector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Properties createProperties() {
		PropertiesImpl properties = new PropertiesImpl();
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextProperty createTextProperty() {
		TextPropertyImpl textProperty = new TextPropertyImpl();
		return textProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListProperty createListProperty() {
		ListPropertyImpl listProperty = new ListPropertyImpl();
		return listProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapProperty createMapProperty() {
		MapPropertyImpl mapProperty = new MapPropertyImpl();
		return mapProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterceptorStack createInterceptorStack() {
		InterceptorStackImpl interceptorStack = new InterceptorStackImpl();
		return interceptorStack;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BridgeComponent createBridgeComponent() {
		BridgeComponentImpl bridgeComponent = new BridgeComponentImpl();
		return bridgeComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalEndpoint createLocalEndpoint() {
		LocalEndpointImpl localEndpoint = new LocalEndpointImpl();
		return localEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalEndpoint createGlobalEndpoint() {
		GlobalEndpointImpl globalEndpoint = new GlobalEndpointImpl();
		return globalEndpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Router createRouter() {
		RouterImpl router = new RouterImpl();
		return router;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transformer createTransformer() {
		TransformerImpl transformer = new TransformerImpl();
		return transformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericComponent createGenericComponent() {
		GenericComponentImpl genericComponent = new GenericComponentImpl();
		return genericComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericFilter createGenericFilter() {
		GenericFilterImpl genericFilter = new GenericFilterImpl();
		return genericFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryFilter createBinaryFilter() {
		BinaryFilterImpl binaryFilter = new BinaryFilterImpl();
		return binaryFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XsltFilter createXsltFilter() {
		XsltFilterImpl xsltFilter = new XsltFilterImpl();
		return xsltFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MulePackage getMulePackage() {
		return (MulePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static MulePackage getPackage() {
		return MulePackage.eINSTANCE;
	}

} //MuleFactoryImpl

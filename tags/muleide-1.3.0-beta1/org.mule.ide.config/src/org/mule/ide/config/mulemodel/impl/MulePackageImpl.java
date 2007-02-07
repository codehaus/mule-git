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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.mule.ide.config.mulemodel.AbstractComponent;
import org.mule.ide.config.mulemodel.AbstractFilter;
import org.mule.ide.config.mulemodel.BinaryFilter;
import org.mule.ide.config.mulemodel.BridgeComponent;
import org.mule.ide.config.mulemodel.Connector;
import org.mule.ide.config.mulemodel.Endpoint;
import org.mule.ide.config.mulemodel.GenericComponent;
import org.mule.ide.config.mulemodel.GenericFilter;
import org.mule.ide.config.mulemodel.GlobalEndpoint;
import org.mule.ide.config.mulemodel.InboundRouter;
import org.mule.ide.config.mulemodel.Interceptor;
import org.mule.ide.config.mulemodel.InterceptorStack;
import org.mule.ide.config.mulemodel.ListProperty;
import org.mule.ide.config.mulemodel.LocalEndpoint;
import org.mule.ide.config.mulemodel.MapProperty;
import org.mule.ide.config.mulemodel.MuleConfig;
import org.mule.ide.config.mulemodel.MuleFactory;
import org.mule.ide.config.mulemodel.MulePackage;
import org.mule.ide.config.mulemodel.OutboundRouter;
import org.mule.ide.config.mulemodel.Properties;
import org.mule.ide.config.mulemodel.Property;
import org.mule.ide.config.mulemodel.Router;
import org.mule.ide.config.mulemodel.TextProperty;
import org.mule.ide.config.mulemodel.Transformer;
import org.mule.ide.config.mulemodel.XsltFilter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MulePackageImpl extends EPackageImpl implements MulePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inboundRouterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass muleConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass endpointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass outboundRouterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interceptorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interceptorStackEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bridgeComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localEndpointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalEndpointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass routerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xsltFilterEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.mule.ide.config.mulemodel.MulePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MulePackageImpl() {
		super(eNS_URI, MuleFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MulePackage init() {
		if (isInited) return (MulePackage)EPackage.Registry.INSTANCE.getEPackage(MulePackage.eNS_URI);

		// Obtain or create and register package
		MulePackageImpl theMulePackage = (MulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof MulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new MulePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMulePackage.createPackageContents();

		// Initialize created meta-data
		theMulePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMulePackage.freeze();

		return theMulePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInboundRouter() {
		return inboundRouterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInboundRouter_InboundEndpoint() {
		return (EReference)inboundRouterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMuleConfig() {
		return muleConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMuleConfig_GlobalEndpoints() {
		return (EReference)muleConfigEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMuleConfig_Transformers() {
		return (EReference)muleConfigEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEndpoint() {
		return endpointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEndpoint_Address() {
		return (EAttribute)endpointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEndpoint_Connector() {
		return (EReference)endpointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEndpoint_Filter() {
		return (EReference)endpointEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEndpoint_Transformers() {
		return (EReference)endpointEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEndpoint_ResponseTransformers() {
		return (EReference)endpointEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOutboundRouter() {
		return outboundRouterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOutboundRouter_OutboundEndpoint() {
		return (EReference)outboundRouterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOutboundRouter_OutboundTransformer() {
		return (EReference)outboundRouterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMuleConfig_Components() {
		return (EReference)muleConfigEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMuleConfig_Version() {
		return (EAttribute)muleConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMuleConfig_Description() {
		return (EAttribute)muleConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMuleConfig_Properties() {
		return (EReference)muleConfigEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMuleConfig_Interceptors() {
		return (EReference)muleConfigEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMuleConfig_Connectors() {
		return (EReference)muleConfigEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterceptor() {
		return interceptorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterceptor_Name() {
		return (EAttribute)interceptorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterceptor_GroupDefinition() {
		return (EReference)interceptorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterceptor_ClassName() {
		return (EAttribute)interceptorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnector() {
		return connectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnector_Comment() {
		return (EAttribute)connectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnector_Name() {
		return (EAttribute)connectorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnector_ClassName() {
		return (EAttribute)connectorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperties() {
		return propertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperties_Properties() {
		return (EReference)propertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Name() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextProperty() {
		return textPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextProperty_Value() {
		return (EAttribute)textPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListProperty() {
		return listPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMapProperty() {
		return mapPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMapProperty_Properties() {
		return (EReference)mapPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterceptorStack() {
		return interceptorStackEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterceptorStack_Interceptors() {
		return (EReference)interceptorStackEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBridgeComponent() {
		return bridgeComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalEndpoint() {
		return localEndpointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalEndpoint() {
		return globalEndpointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalEndpoint_Name() {
		return (EAttribute)globalEndpointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalEndpoint_Comment() {
		return (EAttribute)globalEndpointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRouter() {
		return routerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRouter_LocalEndpoints() {
		return (EReference)routerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransformer() {
		return transformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransformer_ClassName() {
		return (EAttribute)transformerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransformer_Comment() {
		return (EAttribute)transformerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransformer_ReturnClass() {
		return (EAttribute)transformerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransformer_Name() {
		return (EAttribute)transformerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractComponent() {
		return abstractComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_Name() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_OutboundRouter() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_InboundRouter() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_Comment() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_ComponentProperties() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_Interceptors() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericComponent() {
		return genericComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericComponent_Implementation() {
		return (EAttribute)genericComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractFilter() {
		return abstractFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractFilter_NestedFilter() {
		return (EReference)abstractFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericFilter() {
		return genericFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryFilter() {
		return binaryFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryFilter_LeftFilter() {
		return (EReference)binaryFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryFilter_RightFilter() {
		return (EReference)binaryFilterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getXsltFilter() {
		return xsltFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuleFactory getMuleFactory() {
		return (MuleFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		inboundRouterEClass = createEClass(INBOUND_ROUTER);
		createEReference(inboundRouterEClass, INBOUND_ROUTER__INBOUND_ENDPOINT);

		abstractComponentEClass = createEClass(ABSTRACT_COMPONENT);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__NAME);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__OUTBOUND_ROUTER);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__INBOUND_ROUTER);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__COMMENT);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__COMPONENT_PROPERTIES);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__INTERCEPTORS);

		muleConfigEClass = createEClass(MULE_CONFIG);
		createEAttribute(muleConfigEClass, MULE_CONFIG__VERSION);
		createEAttribute(muleConfigEClass, MULE_CONFIG__DESCRIPTION);
		createEReference(muleConfigEClass, MULE_CONFIG__PROPERTIES);
		createEReference(muleConfigEClass, MULE_CONFIG__INTERCEPTORS);
		createEReference(muleConfigEClass, MULE_CONFIG__CONNECTORS);
		createEReference(muleConfigEClass, MULE_CONFIG__TRANSFORMERS);
		createEReference(muleConfigEClass, MULE_CONFIG__GLOBAL_ENDPOINTS);
		createEReference(muleConfigEClass, MULE_CONFIG__COMPONENTS);

		endpointEClass = createEClass(ENDPOINT);
		createEAttribute(endpointEClass, ENDPOINT__ADDRESS);
		createEReference(endpointEClass, ENDPOINT__CONNECTOR);
		createEReference(endpointEClass, ENDPOINT__FILTER);
		createEReference(endpointEClass, ENDPOINT__TRANSFORMERS);
		createEReference(endpointEClass, ENDPOINT__RESPONSE_TRANSFORMERS);

		outboundRouterEClass = createEClass(OUTBOUND_ROUTER);
		createEReference(outboundRouterEClass, OUTBOUND_ROUTER__OUTBOUND_ENDPOINT);
		createEReference(outboundRouterEClass, OUTBOUND_ROUTER__OUTBOUND_TRANSFORMER);

		interceptorEClass = createEClass(INTERCEPTOR);
		createEAttribute(interceptorEClass, INTERCEPTOR__NAME);
		createEReference(interceptorEClass, INTERCEPTOR__GROUP_DEFINITION);
		createEAttribute(interceptorEClass, INTERCEPTOR__CLASS_NAME);

		connectorEClass = createEClass(CONNECTOR);
		createEAttribute(connectorEClass, CONNECTOR__COMMENT);
		createEAttribute(connectorEClass, CONNECTOR__NAME);
		createEAttribute(connectorEClass, CONNECTOR__CLASS_NAME);

		propertiesEClass = createEClass(PROPERTIES);
		createEReference(propertiesEClass, PROPERTIES__PROPERTIES);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__NAME);

		textPropertyEClass = createEClass(TEXT_PROPERTY);
		createEAttribute(textPropertyEClass, TEXT_PROPERTY__VALUE);

		listPropertyEClass = createEClass(LIST_PROPERTY);

		mapPropertyEClass = createEClass(MAP_PROPERTY);
		createEReference(mapPropertyEClass, MAP_PROPERTY__PROPERTIES);

		interceptorStackEClass = createEClass(INTERCEPTOR_STACK);
		createEReference(interceptorStackEClass, INTERCEPTOR_STACK__INTERCEPTORS);

		genericComponentEClass = createEClass(GENERIC_COMPONENT);
		createEAttribute(genericComponentEClass, GENERIC_COMPONENT__IMPLEMENTATION);

		bridgeComponentEClass = createEClass(BRIDGE_COMPONENT);

		localEndpointEClass = createEClass(LOCAL_ENDPOINT);

		globalEndpointEClass = createEClass(GLOBAL_ENDPOINT);
		createEAttribute(globalEndpointEClass, GLOBAL_ENDPOINT__NAME);
		createEAttribute(globalEndpointEClass, GLOBAL_ENDPOINT__COMMENT);

		routerEClass = createEClass(ROUTER);
		createEReference(routerEClass, ROUTER__LOCAL_ENDPOINTS);

		transformerEClass = createEClass(TRANSFORMER);
		createEAttribute(transformerEClass, TRANSFORMER__CLASS_NAME);
		createEAttribute(transformerEClass, TRANSFORMER__COMMENT);
		createEAttribute(transformerEClass, TRANSFORMER__RETURN_CLASS);
		createEAttribute(transformerEClass, TRANSFORMER__NAME);

		abstractFilterEClass = createEClass(ABSTRACT_FILTER);
		createEReference(abstractFilterEClass, ABSTRACT_FILTER__NESTED_FILTER);

		genericFilterEClass = createEClass(GENERIC_FILTER);

		binaryFilterEClass = createEClass(BINARY_FILTER);
		createEReference(binaryFilterEClass, BINARY_FILTER__LEFT_FILTER);
		createEReference(binaryFilterEClass, BINARY_FILTER__RIGHT_FILTER);

		xsltFilterEClass = createEClass(XSLT_FILTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Add supertypes to classes
		inboundRouterEClass.getESuperTypes().add(this.getRouter());
		outboundRouterEClass.getESuperTypes().add(this.getRouter());
		textPropertyEClass.getESuperTypes().add(this.getProperty());
		listPropertyEClass.getESuperTypes().add(this.getProperty());
		mapPropertyEClass.getESuperTypes().add(this.getProperty());
		genericComponentEClass.getESuperTypes().add(this.getAbstractComponent());
		bridgeComponentEClass.getESuperTypes().add(this.getAbstractComponent());
		localEndpointEClass.getESuperTypes().add(this.getEndpoint());
		globalEndpointEClass.getESuperTypes().add(this.getEndpoint());
		genericFilterEClass.getESuperTypes().add(this.getAbstractFilter());
		binaryFilterEClass.getESuperTypes().add(this.getAbstractFilter());
		xsltFilterEClass.getESuperTypes().add(this.getAbstractFilter());

		// Initialize classes and features; add operations and parameters
		initEClass(inboundRouterEClass, InboundRouter.class, "InboundRouter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInboundRouter_InboundEndpoint(), this.getEndpoint(), null, "inboundEndpoint", null, 0, 1, InboundRouter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(abstractComponentEClass, AbstractComponent.class, "AbstractComponent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAbstractComponent_Name(), ecorePackage.getEString(), "name", null, 1, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAbstractComponent_OutboundRouter(), this.getOutboundRouter(), null, "outboundRouter", null, 0, -1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAbstractComponent_InboundRouter(), this.getInboundRouter(), null, "inboundRouter", null, 0, -1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAbstractComponent_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAbstractComponent_ComponentProperties(), this.getProperty(), null, "componentProperties", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAbstractComponent_Interceptors(), this.getInterceptor(), null, "interceptors", "", 0, -1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(muleConfigEClass, MuleConfig.class, "MuleConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getMuleConfig_Version(), ecorePackage.getEString(), "version", null, 0, 1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getMuleConfig_Description(), ecorePackage.getEString(), "description", null, 0, 1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMuleConfig_Properties(), this.getProperties(), null, "properties", null, 0, 1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMuleConfig_Interceptors(), this.getInterceptorStack(), null, "interceptors", null, 0, -1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMuleConfig_Connectors(), this.getConnector(), null, "connectors", null, 0, -1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMuleConfig_Transformers(), this.getTransformer(), null, "transformers", null, 0, -1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMuleConfig_GlobalEndpoints(), this.getGlobalEndpoint(), null, "globalEndpoints", null, 0, -1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMuleConfig_Components(), this.getAbstractComponent(), null, "components", null, 0, -1, MuleConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(muleConfigEClass, null, "addComponent"); //$NON-NLS-1$

		initEClass(endpointEClass, Endpoint.class, "Endpoint", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getEndpoint_Address(), ecorePackage.getEString(), "address", null, 1, 1, Endpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getEndpoint_Connector(), this.getConnector(), null, "connector", null, 0, 1, Endpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getEndpoint_Filter(), this.getAbstractFilter(), null, "filter", null, 0, 1, Endpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getEndpoint_Transformers(), this.getTransformer(), null, "transformers", null, 0, -1, Endpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getEndpoint_ResponseTransformers(), this.getTransformer(), null, "responseTransformers", null, 0, -1, Endpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(outboundRouterEClass, OutboundRouter.class, "OutboundRouter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getOutboundRouter_OutboundEndpoint(), this.getEndpoint(), null, "outboundEndpoint", null, 0, 1, OutboundRouter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getOutboundRouter_OutboundTransformer(), this.getTransformer(), null, "outboundTransformer", null, 0, 1, OutboundRouter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(interceptorEClass, Interceptor.class, "Interceptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getInterceptor_Name(), ecorePackage.getEString(), "name", null, 1, 1, Interceptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterceptor_GroupDefinition(), this.getInterceptorStack(), null, "groupDefinition", null, 0, 1, Interceptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getInterceptor_ClassName(), ecorePackage.getEString(), "className", null, 0, 1, Interceptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(connectorEClass, Connector.class, "Connector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getConnector_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getConnector_Name(), ecorePackage.getEString(), "name", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getConnector_ClassName(), ecorePackage.getEString(), "className", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(propertiesEClass, Properties.class, "Properties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProperties_Properties(), this.getProperty(), null, "properties", null, 0, -1, Properties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(propertyEClass, Property.class, "Property", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getProperty_Name(), ecorePackage.getEString(), "name", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(textPropertyEClass, TextProperty.class, "TextProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTextProperty_Value(), ecorePackage.getEString(), "value", null, 0, 1, TextProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(listPropertyEClass, ListProperty.class, "ListProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(mapPropertyEClass, MapProperty.class, "MapProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMapProperty_Properties(), this.getProperties(), null, "properties", null, 0, 1, MapProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(interceptorStackEClass, InterceptorStack.class, "InterceptorStack", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInterceptorStack_Interceptors(), this.getInterceptor(), null, "interceptors", null, 0, -1, InterceptorStack.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(genericComponentEClass, GenericComponent.class, "GenericComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getGenericComponent_Implementation(), ecorePackage.getEString(), "implementation", null, 0, 1, GenericComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(bridgeComponentEClass, BridgeComponent.class, "BridgeComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(localEndpointEClass, LocalEndpoint.class, "LocalEndpoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(globalEndpointEClass, GlobalEndpoint.class, "GlobalEndpoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getGlobalEndpoint_Name(), ecorePackage.getEString(), "name", null, 0, 1, GlobalEndpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGlobalEndpoint_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, GlobalEndpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(routerEClass, Router.class, "Router", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRouter_LocalEndpoints(), this.getLocalEndpoint(), null, "localEndpoints", null, 0, -1, Router.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(transformerEClass, Transformer.class, "Transformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTransformer_ClassName(), ecorePackage.getEString(), "className", null, 0, 1, Transformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTransformer_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Transformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTransformer_ReturnClass(), ecorePackage.getEString(), "returnClass", null, 0, 1, Transformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getTransformer_Name(), ecorePackage.getEString(), "name", null, 0, 1, Transformer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(abstractFilterEClass, AbstractFilter.class, "AbstractFilter", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAbstractFilter_NestedFilter(), this.getAbstractFilter(), null, "nestedFilter", null, 0, 1, AbstractFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(genericFilterEClass, GenericFilter.class, "GenericFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(binaryFilterEClass, BinaryFilter.class, "BinaryFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBinaryFilter_LeftFilter(), this.getAbstractFilter(), null, "leftFilter", null, 0, 1, BinaryFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBinaryFilter_RightFilter(), this.getAbstractFilter(), null, "rightFilter", null, 0, 1, BinaryFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(xsltFilterEClass, XsltFilter.class, "XsltFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //MulePackageImpl

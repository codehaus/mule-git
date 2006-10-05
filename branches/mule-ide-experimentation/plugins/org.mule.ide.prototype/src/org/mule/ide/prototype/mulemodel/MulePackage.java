/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.prototype.mulemodel;

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
 * @see org.mule.ide.prototype.mulemodel.MuleFactory
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
	MulePackage eINSTANCE = org.mule.ide.prototype.mulemodel.impl.MulePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.mule.ide.prototype.mulemodel.impl.InboundRouterTypeImpl <em>Inbound Router Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.prototype.mulemodel.impl.InboundRouterTypeImpl
	 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getInboundRouterType()
	 * @generated
	 */
	int INBOUND_ROUTER_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Inbound Endpoint</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT = 0;

	/**
	 * The number of structural features of the '<em>Inbound Router Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INBOUND_ROUTER_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl <em>Component Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl
	 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getComponentType()
	 * @generated
	 */
	int COMPONENT_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Outbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__OUTBOUND_ROUTER = 1;

	/**
	 * The feature id for the '<em><b>Interceptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__INTERCEPTORS = 2;

	/**
	 * The feature id for the '<em><b>Inbound Router</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__INBOUND_ROUTER = 3;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE__COMMENT = 4;

	/**
	 * The number of structural features of the '<em>Component Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.mule.ide.prototype.mulemodel.impl.MuleConfigImpl <em>Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.prototype.mulemodel.impl.MuleConfigImpl
	 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getMuleConfig()
	 * @generated
	 */
	int MULE_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Global Endpoints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__GLOBAL_ENDPOINTS = 0;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__COMPONENTS = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__VERSION = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG__DESCRIPTION = 3;

	/**
	 * The number of structural features of the '<em>Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULE_CONFIG_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.mule.ide.prototype.mulemodel.impl.EndpointTypeImpl <em>Endpoint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.prototype.mulemodel.impl.EndpointTypeImpl
	 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getEndpointType()
	 * @generated
	 */
	int ENDPOINT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__ADDRESS = 1;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE__COMMENT = 2;

	/**
	 * The number of structural features of the '<em>Endpoint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.mule.ide.prototype.mulemodel.impl.OutboundRouterTypeImpl <em>Outbound Router Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.prototype.mulemodel.impl.OutboundRouterTypeImpl
	 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getOutboundRouterType()
	 * @generated
	 */
	int OUTBOUND_ROUTER_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Outbound Endpoint</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTBOUND_ROUTER_TYPE__OUTBOUND_ENDPOINT = 0;

	/**
	 * The number of structural features of the '<em>Outbound Router Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTBOUND_ROUTER_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.mule.ide.prototype.mulemodel.impl.InterceptorImpl <em>Interceptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.mule.ide.prototype.mulemodel.impl.InterceptorImpl
	 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getInterceptor()
	 * @generated
	 */
	int INTERCEPTOR = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR__NAME = 0;

	/**
	 * The number of structural features of the '<em>Interceptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERCEPTOR_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link org.mule.ide.prototype.mulemodel.InboundRouterType <em>Inbound Router Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inbound Router Type</em>'.
	 * @see org.mule.ide.prototype.mulemodel.InboundRouterType
	 * @generated
	 */
	EClass getInboundRouterType();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.prototype.mulemodel.InboundRouterType#getInboundEndpoint <em>Inbound Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Inbound Endpoint</em>'.
	 * @see org.mule.ide.prototype.mulemodel.InboundRouterType#getInboundEndpoint()
	 * @see #getInboundRouterType()
	 * @generated
	 */
	EReference getInboundRouterType_InboundEndpoint();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.prototype.mulemodel.ComponentType <em>Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Type</em>'.
	 * @see org.mule.ide.prototype.mulemodel.ComponentType
	 * @generated
	 */
	EClass getComponentType();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.ComponentType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.prototype.mulemodel.ComponentType#getName()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.prototype.mulemodel.ComponentType#getOutboundRouter <em>Outbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outbound Router</em>'.
	 * @see org.mule.ide.prototype.mulemodel.ComponentType#getOutboundRouter()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_OutboundRouter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.prototype.mulemodel.ComponentType#getInterceptors <em>Interceptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Interceptors</em>'.
	 * @see org.mule.ide.prototype.mulemodel.ComponentType#getInterceptors()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_Interceptors();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.prototype.mulemodel.ComponentType#getInboundRouter <em>Inbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inbound Router</em>'.
	 * @see org.mule.ide.prototype.mulemodel.ComponentType#getInboundRouter()
	 * @see #getComponentType()
	 * @generated
	 */
	EReference getComponentType_InboundRouter();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.ComponentType#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.mule.ide.prototype.mulemodel.ComponentType#getComment()
	 * @see #getComponentType()
	 * @generated
	 */
	EAttribute getComponentType_Comment();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.prototype.mulemodel.MuleConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config</em>'.
	 * @see org.mule.ide.prototype.mulemodel.MuleConfig
	 * @generated
	 */
	EClass getMuleConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.prototype.mulemodel.MuleConfig#getGlobalEndpoints <em>Global Endpoints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Global Endpoints</em>'.
	 * @see org.mule.ide.prototype.mulemodel.MuleConfig#getGlobalEndpoints()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_GlobalEndpoints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.mule.ide.prototype.mulemodel.MuleConfig#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see org.mule.ide.prototype.mulemodel.MuleConfig#getComponents()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EReference getMuleConfig_Components();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.MuleConfig#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.mule.ide.prototype.mulemodel.MuleConfig#getVersion()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EAttribute getMuleConfig_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.MuleConfig#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.mule.ide.prototype.mulemodel.MuleConfig#getDescription()
	 * @see #getMuleConfig()
	 * @generated
	 */
	EAttribute getMuleConfig_Description();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.prototype.mulemodel.EndpointType <em>Endpoint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Endpoint Type</em>'.
	 * @see org.mule.ide.prototype.mulemodel.EndpointType
	 * @generated
	 */
	EClass getEndpointType();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.EndpointType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.prototype.mulemodel.EndpointType#getName()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.EndpointType#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see org.mule.ide.prototype.mulemodel.EndpointType#getAddress()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_Address();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.EndpointType#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.mule.ide.prototype.mulemodel.EndpointType#getComment()
	 * @see #getEndpointType()
	 * @generated
	 */
	EAttribute getEndpointType_Comment();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.prototype.mulemodel.OutboundRouterType <em>Outbound Router Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Outbound Router Type</em>'.
	 * @see org.mule.ide.prototype.mulemodel.OutboundRouterType
	 * @generated
	 */
	EClass getOutboundRouterType();

	/**
	 * Returns the meta object for the reference '{@link org.mule.ide.prototype.mulemodel.OutboundRouterType#getOutboundEndpoint <em>Outbound Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Outbound Endpoint</em>'.
	 * @see org.mule.ide.prototype.mulemodel.OutboundRouterType#getOutboundEndpoint()
	 * @see #getOutboundRouterType()
	 * @generated
	 */
	EReference getOutboundRouterType_OutboundEndpoint();

	/**
	 * Returns the meta object for class '{@link org.mule.ide.prototype.mulemodel.Interceptor <em>Interceptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interceptor</em>'.
	 * @see org.mule.ide.prototype.mulemodel.Interceptor
	 * @generated
	 */
	EClass getInterceptor();

	/**
	 * Returns the meta object for the attribute '{@link org.mule.ide.prototype.mulemodel.Interceptor#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.mule.ide.prototype.mulemodel.Interceptor#getName()
	 * @see #getInterceptor()
	 * @generated
	 */
	EAttribute getInterceptor_Name();

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
		 * The meta object literal for the '{@link org.mule.ide.prototype.mulemodel.impl.InboundRouterTypeImpl <em>Inbound Router Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.prototype.mulemodel.impl.InboundRouterTypeImpl
		 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getInboundRouterType()
		 * @generated
		 */
		EClass INBOUND_ROUTER_TYPE = eINSTANCE.getInboundRouterType();

		/**
		 * The meta object literal for the '<em><b>Inbound Endpoint</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INBOUND_ROUTER_TYPE__INBOUND_ENDPOINT = eINSTANCE.getInboundRouterType_InboundEndpoint();

		/**
		 * The meta object literal for the '{@link org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl <em>Component Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.prototype.mulemodel.impl.ComponentTypeImpl
		 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getComponentType()
		 * @generated
		 */
		EClass COMPONENT_TYPE = eINSTANCE.getComponentType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__NAME = eINSTANCE.getComponentType_Name();

		/**
		 * The meta object literal for the '<em><b>Outbound Router</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__OUTBOUND_ROUTER = eINSTANCE.getComponentType_OutboundRouter();

		/**
		 * The meta object literal for the '<em><b>Interceptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__INTERCEPTORS = eINSTANCE.getComponentType_Interceptors();

		/**
		 * The meta object literal for the '<em><b>Inbound Router</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_TYPE__INBOUND_ROUTER = eINSTANCE.getComponentType_InboundRouter();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_TYPE__COMMENT = eINSTANCE.getComponentType_Comment();

		/**
		 * The meta object literal for the '{@link org.mule.ide.prototype.mulemodel.impl.MuleConfigImpl <em>Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.prototype.mulemodel.impl.MuleConfigImpl
		 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getMuleConfig()
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
		 * The meta object literal for the '{@link org.mule.ide.prototype.mulemodel.impl.EndpointTypeImpl <em>Endpoint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.prototype.mulemodel.impl.EndpointTypeImpl
		 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getEndpointType()
		 * @generated
		 */
		EClass ENDPOINT_TYPE = eINSTANCE.getEndpointType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__NAME = eINSTANCE.getEndpointType_Name();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__ADDRESS = eINSTANCE.getEndpointType_Address();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENDPOINT_TYPE__COMMENT = eINSTANCE.getEndpointType_Comment();

		/**
		 * The meta object literal for the '{@link org.mule.ide.prototype.mulemodel.impl.OutboundRouterTypeImpl <em>Outbound Router Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.prototype.mulemodel.impl.OutboundRouterTypeImpl
		 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getOutboundRouterType()
		 * @generated
		 */
		EClass OUTBOUND_ROUTER_TYPE = eINSTANCE.getOutboundRouterType();

		/**
		 * The meta object literal for the '<em><b>Outbound Endpoint</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OUTBOUND_ROUTER_TYPE__OUTBOUND_ENDPOINT = eINSTANCE.getOutboundRouterType_OutboundEndpoint();

		/**
		 * The meta object literal for the '{@link org.mule.ide.prototype.mulemodel.impl.InterceptorImpl <em>Interceptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.mule.ide.prototype.mulemodel.impl.InterceptorImpl
		 * @see org.mule.ide.prototype.mulemodel.impl.MulePackageImpl#getInterceptor()
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

	}

} //MulePackage

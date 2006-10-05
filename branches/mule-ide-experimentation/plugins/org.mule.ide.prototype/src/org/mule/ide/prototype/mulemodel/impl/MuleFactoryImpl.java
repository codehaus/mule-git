/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.ide.prototype.mulemodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.mule.ide.prototype.mulemodel.*;

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
			case MulePackage.INBOUND_ROUTER_TYPE: return createInboundRouterType();
			case MulePackage.COMPONENT_TYPE: return createComponentType();
			case MulePackage.MULE_CONFIG: return createMuleConfig();
			case MulePackage.ENDPOINT_TYPE: return createEndpointType();
			case MulePackage.OUTBOUND_ROUTER_TYPE: return createOutboundRouterType();
			case MulePackage.INTERCEPTOR: return createInterceptor();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InboundRouterType createInboundRouterType() {
		InboundRouterTypeImpl inboundRouterType = new InboundRouterTypeImpl();
		return inboundRouterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentType createComponentType() {
		ComponentTypeImpl componentType = new ComponentTypeImpl();
		return componentType;
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
	public EndpointType createEndpointType() {
		EndpointTypeImpl endpointType = new EndpointTypeImpl();
		return endpointType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OutboundRouterType createOutboundRouterType() {
		OutboundRouterTypeImpl outboundRouterType = new OutboundRouterTypeImpl();
		return outboundRouterType;
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

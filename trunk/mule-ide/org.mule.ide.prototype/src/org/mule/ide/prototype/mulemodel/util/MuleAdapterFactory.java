/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.ide.prototype.mulemodel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.mule.ide.prototype.mulemodel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.mule.ide.prototype.mulemodel.MulePackage
 * @generated
 */
public class MuleAdapterFactory extends AdapterFactoryImpl {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MulePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuleAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MulePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MuleSwitch modelSwitch =
		new MuleSwitch() {
			public Object caseInboundRouter(InboundRouter object) {
				return createInboundRouterAdapter();
			}
			public Object caseMuleConfig(MuleConfig object) {
				return createMuleConfigAdapter();
			}
			public Object caseEndpoint(Endpoint object) {
				return createEndpointAdapter();
			}
			public Object caseOutboundRouter(OutboundRouter object) {
				return createOutboundRouterAdapter();
			}
			public Object caseInterceptor(Interceptor object) {
				return createInterceptorAdapter();
			}
			public Object caseProperties(Properties object) {
				return createPropertiesAdapter();
			}
			public Object caseProperty(Property object) {
				return createPropertyAdapter();
			}
			public Object caseTextProperty(TextProperty object) {
				return createTextPropertyAdapter();
			}
			public Object caseListProperty(ListProperty object) {
				return createListPropertyAdapter();
			}
			public Object caseMapProperty(MapProperty object) {
				return createMapPropertyAdapter();
			}
			public Object caseInterceptorDefinition(InterceptorDefinition object) {
				return createInterceptorDefinitionAdapter();
			}
			public Object caseBridgeComponent(BridgeComponent object) {
				return createBridgeComponentAdapter();
			}
			public Object caseLocalEndpoint(LocalEndpoint object) {
				return createLocalEndpointAdapter();
			}
			public Object caseGlobalEndpoint(GlobalEndpoint object) {
				return createGlobalEndpointAdapter();
			}
			public Object caseRouter(Router object) {
				return createRouterAdapter();
			}
			public Object caseTransformer(Transformer object) {
				return createTransformerAdapter();
			}
			public Object caseAbstractComponent(AbstractComponent object) {
				return createAbstractComponentAdapter();
			}
			public Object caseGenericComponent(GenericComponent object) {
				return createGenericComponentAdapter();
			}
			public Object caseAbstractFilter(AbstractFilter object) {
				return createAbstractFilterAdapter();
			}
			public Object caseGenericFilter(GenericFilter object) {
				return createGenericFilterAdapter();
			}
			public Object caseBinaryFilter(BinaryFilter object) {
				return createBinaryFilterAdapter();
			}
			public Object caseXsltFilter(XsltFilter object) {
				return createXsltFilterAdapter();
			}
			public Object caseConnector(Connector object) {
				return createConnectorAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.InboundRouter <em>Inbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.InboundRouter
	 * @generated
	 */
	public Adapter createInboundRouterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.MuleConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.MuleConfig
	 * @generated
	 */
	public Adapter createMuleConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.Endpoint <em>Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.Endpoint
	 * @generated
	 */
	public Adapter createEndpointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.OutboundRouter <em>Outbound Router</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.OutboundRouter
	 * @generated
	 */
	public Adapter createOutboundRouterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.Interceptor <em>Interceptor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.Interceptor
	 * @generated
	 */
	public Adapter createInterceptorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.Connector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.Connector
	 * @generated
	 */
	public Adapter createConnectorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.Properties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.Properties
	 * @generated
	 */
	public Adapter createPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.TextProperty <em>Text Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.TextProperty
	 * @generated
	 */
	public Adapter createTextPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.ListProperty <em>List Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.ListProperty
	 * @generated
	 */
	public Adapter createListPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.MapProperty <em>Map Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.MapProperty
	 * @generated
	 */
	public Adapter createMapPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.InterceptorDefinition <em>Interceptor Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.InterceptorDefinition
	 * @generated
	 */
	public Adapter createInterceptorDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.BridgeComponent <em>Bridge Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.BridgeComponent
	 * @generated
	 */
	public Adapter createBridgeComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.LocalEndpoint <em>Local Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.LocalEndpoint
	 * @generated
	 */
	public Adapter createLocalEndpointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.GlobalEndpoint <em>Global Endpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.GlobalEndpoint
	 * @generated
	 */
	public Adapter createGlobalEndpointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.Router <em>Router</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.Router
	 * @generated
	 */
	public Adapter createRouterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.Transformer <em>Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.Transformer
	 * @generated
	 */
	public Adapter createTransformerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.AbstractComponent <em>Abstract Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.AbstractComponent
	 * @generated
	 */
	public Adapter createAbstractComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.GenericComponent <em>Generic Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.GenericComponent
	 * @generated
	 */
	public Adapter createGenericComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.AbstractFilter <em>Abstract Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.AbstractFilter
	 * @generated
	 */
	public Adapter createAbstractFilterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.GenericFilter <em>Generic Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.GenericFilter
	 * @generated
	 */
	public Adapter createGenericFilterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.BinaryFilter <em>Binary Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.BinaryFilter
	 * @generated
	 */
	public Adapter createBinaryFilterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.mule.ide.prototype.mulemodel.XsltFilter <em>Xslt Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.mule.ide.prototype.mulemodel.XsltFilter
	 * @generated
	 */
	public Adapter createXsltFilterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //MuleAdapterFactory

/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */
package org.mule.ide.config.mulemodel.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.mule.ide.config.mulemodel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.mule.ide.config.mulemodel.MulePackage
 * @generated
 */
public class MuleSwitch {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MulePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuleSwitch() {
		if (modelPackage == null) {
			modelPackage = MulePackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case MulePackage.INBOUND_ROUTER: {
				InboundRouter inboundRouter = (InboundRouter)theEObject;
				Object result = caseInboundRouter(inboundRouter);
				if (result == null) result = caseRouter(inboundRouter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.ABSTRACT_COMPONENT: {
				AbstractComponent abstractComponent = (AbstractComponent)theEObject;
				Object result = caseAbstractComponent(abstractComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.MULE_CONFIG: {
				MuleConfig muleConfig = (MuleConfig)theEObject;
				Object result = caseMuleConfig(muleConfig);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.ENDPOINT: {
				Endpoint endpoint = (Endpoint)theEObject;
				Object result = caseEndpoint(endpoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.OUTBOUND_ROUTER: {
				OutboundRouter outboundRouter = (OutboundRouter)theEObject;
				Object result = caseOutboundRouter(outboundRouter);
				if (result == null) result = caseRouter(outboundRouter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.INTERCEPTOR: {
				Interceptor interceptor = (Interceptor)theEObject;
				Object result = caseInterceptor(interceptor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.CONNECTOR: {
				Connector connector = (Connector)theEObject;
				Object result = caseConnector(connector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.PROPERTIES: {
				Properties properties = (Properties)theEObject;
				Object result = caseProperties(properties);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.PROPERTY: {
				Property property = (Property)theEObject;
				Object result = caseProperty(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.TEXT_PROPERTY: {
				TextProperty textProperty = (TextProperty)theEObject;
				Object result = caseTextProperty(textProperty);
				if (result == null) result = caseProperty(textProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.LIST_PROPERTY: {
				ListProperty listProperty = (ListProperty)theEObject;
				Object result = caseListProperty(listProperty);
				if (result == null) result = caseProperty(listProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.MAP_PROPERTY: {
				MapProperty mapProperty = (MapProperty)theEObject;
				Object result = caseMapProperty(mapProperty);
				if (result == null) result = caseProperty(mapProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.INTERCEPTOR_STACK: {
				InterceptorStack interceptorStack = (InterceptorStack)theEObject;
				Object result = caseInterceptorStack(interceptorStack);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.GENERIC_COMPONENT: {
				GenericComponent genericComponent = (GenericComponent)theEObject;
				Object result = caseGenericComponent(genericComponent);
				if (result == null) result = caseAbstractComponent(genericComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.BRIDGE_COMPONENT: {
				BridgeComponent bridgeComponent = (BridgeComponent)theEObject;
				Object result = caseBridgeComponent(bridgeComponent);
				if (result == null) result = caseAbstractComponent(bridgeComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.LOCAL_ENDPOINT: {
				LocalEndpoint localEndpoint = (LocalEndpoint)theEObject;
				Object result = caseLocalEndpoint(localEndpoint);
				if (result == null) result = caseEndpoint(localEndpoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.GLOBAL_ENDPOINT: {
				GlobalEndpoint globalEndpoint = (GlobalEndpoint)theEObject;
				Object result = caseGlobalEndpoint(globalEndpoint);
				if (result == null) result = caseEndpoint(globalEndpoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.ROUTER: {
				Router router = (Router)theEObject;
				Object result = caseRouter(router);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.TRANSFORMER: {
				Transformer transformer = (Transformer)theEObject;
				Object result = caseTransformer(transformer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.ABSTRACT_FILTER: {
				AbstractFilter abstractFilter = (AbstractFilter)theEObject;
				Object result = caseAbstractFilter(abstractFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.GENERIC_FILTER: {
				GenericFilter genericFilter = (GenericFilter)theEObject;
				Object result = caseGenericFilter(genericFilter);
				if (result == null) result = caseAbstractFilter(genericFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.BINARY_FILTER: {
				BinaryFilter binaryFilter = (BinaryFilter)theEObject;
				Object result = caseBinaryFilter(binaryFilter);
				if (result == null) result = caseAbstractFilter(binaryFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MulePackage.XSLT_FILTER: {
				XsltFilter xsltFilter = (XsltFilter)theEObject;
				Object result = caseXsltFilter(xsltFilter);
				if (result == null) result = caseAbstractFilter(xsltFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inbound Router</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inbound Router</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInboundRouter(InboundRouter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMuleConfig(MuleConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Endpoint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Endpoint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEndpoint(Endpoint object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Outbound Router</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Outbound Router</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOutboundRouter(OutboundRouter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interceptor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interceptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInterceptor(Interceptor object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConnector(Connector object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProperties(Properties object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Text Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Text Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTextProperty(TextProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>List Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>List Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseListProperty(ListProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Map Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Map Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMapProperty(MapProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interceptor Stack</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interceptor Stack</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInterceptorStack(InterceptorStack object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Bridge Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Bridge Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBridgeComponent(BridgeComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Local Endpoint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Local Endpoint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLocalEndpoint(LocalEndpoint object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Global Endpoint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Global Endpoint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGlobalEndpoint(GlobalEndpoint object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Router</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Router</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRouter(Router object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Transformer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTransformer(Transformer object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Abstract Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Abstract Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAbstractComponent(AbstractComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Generic Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Generic Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGenericComponent(GenericComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Abstract Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Abstract Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAbstractFilter(AbstractFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Generic Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Generic Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGenericFilter(GenericFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Binary Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Binary Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBinaryFilter(BinaryFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Xslt Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Xslt Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseXsltFilter(XsltFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object) {
		return null;
	}

} //MuleSwitch

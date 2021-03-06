/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.schema;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exception Strategy Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.mule.schema.ExceptionStrategyType#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.mule.schema.ExceptionStrategyType#getEndpoint <em>Endpoint</em>}</li>
 *   <li>{@link org.mule.schema.ExceptionStrategyType#getGlobalEndpoint <em>Global Endpoint</em>}</li>
 *   <li>{@link org.mule.schema.ExceptionStrategyType#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.mule.schema.ExceptionStrategyType#getClassName <em>Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.mule.schema.MulePackage#getExceptionStrategyType()
 * @model extendedMetaData="name='exception-strategyType' kind='mixed'"
 * @generated
 */
public interface ExceptionStrategyType extends EObject {
    /**
     * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mixed</em>' attribute list.
     * @see org.mule.schema.MulePackage#getExceptionStrategyType_Mixed()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='elementWildcard' name=':mixed'"
     * @generated
     */
    FeatureMap getMixed();

    /**
     * Returns the value of the '<em><b>Endpoint</b></em>' containment reference list.
     * The list contents are of type {@link org.mule.schema.EndpointType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Endpoint</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Endpoint</em>' containment reference list.
     * @see org.mule.schema.MulePackage#getExceptionStrategyType_Endpoint()
     * @model type="org.mule.schema.EndpointType" containment="true" resolveProxies="false" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='endpoint' namespace='##targetNamespace'"
     * @generated
     */
    EList getEndpoint();

    /**
     * Returns the value of the '<em><b>Global Endpoint</b></em>' containment reference list.
     * The list contents are of type {@link org.mule.schema.GlobalEndpointType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Global Endpoint</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Global Endpoint</em>' containment reference list.
     * @see org.mule.schema.MulePackage#getExceptionStrategyType_GlobalEndpoint()
     * @model type="org.mule.schema.GlobalEndpointType" containment="true" resolveProxies="false" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='global-endpoint' namespace='##targetNamespace'"
     * @generated
     */
    EList getGlobalEndpoint();

    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Properties</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Properties</em>' containment reference.
     * @see #setProperties(PropertiesType)
     * @see org.mule.schema.MulePackage#getExceptionStrategyType_Properties()
     * @model containment="true" resolveProxies="false" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
     * @generated
     */
    PropertiesType getProperties();

    /**
     * Sets the value of the '{@link org.mule.schema.ExceptionStrategyType#getProperties <em>Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Properties</em>' containment reference.
     * @see #getProperties()
     * @generated
     */
    void setProperties(PropertiesType value);

    /**
     * Returns the value of the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Class Name</em>' attribute.
     * @see #setClassName(String)
     * @see org.mule.schema.MulePackage#getExceptionStrategyType_ClassName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='className' namespace='##targetNamespace'"
     * @generated
     */
    String getClassName();

    /**
     * Sets the value of the '{@link org.mule.schema.ExceptionStrategyType#getClassName <em>Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Class Name</em>' attribute.
     * @see #getClassName()
     * @generated
     */
    void setClassName(String value);

} // ExceptionStrategyType

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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.mule.ide.config.mulemodel.AbstractComponent;
import org.mule.ide.config.mulemodel.Connector;
import org.mule.ide.config.mulemodel.GlobalEndpoint;
import org.mule.ide.config.mulemodel.InterceptorStack;
import org.mule.ide.config.mulemodel.MuleConfig;
import org.mule.ide.config.mulemodel.MulePackage;
import org.mule.ide.config.mulemodel.Properties;
import org.mule.ide.config.mulemodel.Transformer;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getInterceptors <em>Interceptors</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getConnectors <em>Connectors</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getTransformers <em>Transformers</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getGlobalEndpoints <em>Global Endpoints</em>}</li>
 *   <li>{@link org.mule.ide.config.mulemodel.impl.MuleConfigImpl#getComponents <em>Components</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MuleConfigImpl extends EObjectImpl implements MuleConfig {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected Properties properties = null;

	/**
	 * The cached value of the '{@link #getInterceptors() <em>Interceptors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterceptors()
	 * @generated
	 * @ordered
	 */
	protected EList interceptors = null;

	/**
	 * The cached value of the '{@link #getConnectors() <em>Connectors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList connectors = null;

	/**
	 * The cached value of the '{@link #getTransformers() <em>Transformers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformers()
	 * @generated
	 * @ordered
	 */
	protected EList transformers = null;

	/**
	 * The cached value of the '{@link #getGlobalEndpoints() <em>Global Endpoints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalEndpoints()
	 * @generated
	 * @ordered
	 */
	protected EList globalEndpoints = null;

	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList components = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MuleConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MulePackage.Literals.MULE_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getGlobalEndpoints() {
		if (globalEndpoints == null) {
			globalEndpoints = new EObjectContainmentEList(GlobalEndpoint.class, this, MulePackage.MULE_CONFIG__GLOBAL_ENDPOINTS);
		}
		return globalEndpoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTransformers() {
		if (transformers == null) {
			transformers = new EObjectContainmentEList(Transformer.class, this, MulePackage.MULE_CONFIG__TRANSFORMERS);
		}
		return transformers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addComponent() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList(AbstractComponent.class, this, MulePackage.MULE_CONFIG__COMPONENTS);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.MULE_CONFIG__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.MULE_CONFIG__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(Properties newProperties, NotificationChain msgs) {
		Properties oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MulePackage.MULE_CONFIG__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(Properties newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MulePackage.MULE_CONFIG__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MulePackage.MULE_CONFIG__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MulePackage.MULE_CONFIG__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInterceptors() {
		if (interceptors == null) {
			interceptors = new EObjectContainmentEList(InterceptorStack.class, this, MulePackage.MULE_CONFIG__INTERCEPTORS);
		}
		return interceptors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConnectors() {
		if (connectors == null) {
			connectors = new EObjectContainmentEList(Connector.class, this, MulePackage.MULE_CONFIG__CONNECTORS);
		}
		return connectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MulePackage.MULE_CONFIG__PROPERTIES:
				return basicSetProperties(null, msgs);
			case MulePackage.MULE_CONFIG__INTERCEPTORS:
				return ((InternalEList)getInterceptors()).basicRemove(otherEnd, msgs);
			case MulePackage.MULE_CONFIG__CONNECTORS:
				return ((InternalEList)getConnectors()).basicRemove(otherEnd, msgs);
			case MulePackage.MULE_CONFIG__TRANSFORMERS:
				return ((InternalEList)getTransformers()).basicRemove(otherEnd, msgs);
			case MulePackage.MULE_CONFIG__GLOBAL_ENDPOINTS:
				return ((InternalEList)getGlobalEndpoints()).basicRemove(otherEnd, msgs);
			case MulePackage.MULE_CONFIG__COMPONENTS:
				return ((InternalEList)getComponents()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MulePackage.MULE_CONFIG__VERSION:
				return getVersion();
			case MulePackage.MULE_CONFIG__DESCRIPTION:
				return getDescription();
			case MulePackage.MULE_CONFIG__PROPERTIES:
				return getProperties();
			case MulePackage.MULE_CONFIG__INTERCEPTORS:
				return getInterceptors();
			case MulePackage.MULE_CONFIG__CONNECTORS:
				return getConnectors();
			case MulePackage.MULE_CONFIG__TRANSFORMERS:
				return getTransformers();
			case MulePackage.MULE_CONFIG__GLOBAL_ENDPOINTS:
				return getGlobalEndpoints();
			case MulePackage.MULE_CONFIG__COMPONENTS:
				return getComponents();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MulePackage.MULE_CONFIG__VERSION:
				setVersion((String)newValue);
				return;
			case MulePackage.MULE_CONFIG__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case MulePackage.MULE_CONFIG__PROPERTIES:
				setProperties((Properties)newValue);
				return;
			case MulePackage.MULE_CONFIG__INTERCEPTORS:
				getInterceptors().clear();
				getInterceptors().addAll((Collection)newValue);
				return;
			case MulePackage.MULE_CONFIG__CONNECTORS:
				getConnectors().clear();
				getConnectors().addAll((Collection)newValue);
				return;
			case MulePackage.MULE_CONFIG__TRANSFORMERS:
				getTransformers().clear();
				getTransformers().addAll((Collection)newValue);
				return;
			case MulePackage.MULE_CONFIG__GLOBAL_ENDPOINTS:
				getGlobalEndpoints().clear();
				getGlobalEndpoints().addAll((Collection)newValue);
				return;
			case MulePackage.MULE_CONFIG__COMPONENTS:
				getComponents().clear();
				getComponents().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case MulePackage.MULE_CONFIG__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case MulePackage.MULE_CONFIG__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case MulePackage.MULE_CONFIG__PROPERTIES:
				setProperties((Properties)null);
				return;
			case MulePackage.MULE_CONFIG__INTERCEPTORS:
				getInterceptors().clear();
				return;
			case MulePackage.MULE_CONFIG__CONNECTORS:
				getConnectors().clear();
				return;
			case MulePackage.MULE_CONFIG__TRANSFORMERS:
				getTransformers().clear();
				return;
			case MulePackage.MULE_CONFIG__GLOBAL_ENDPOINTS:
				getGlobalEndpoints().clear();
				return;
			case MulePackage.MULE_CONFIG__COMPONENTS:
				getComponents().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MulePackage.MULE_CONFIG__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case MulePackage.MULE_CONFIG__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case MulePackage.MULE_CONFIG__PROPERTIES:
				return properties != null;
			case MulePackage.MULE_CONFIG__INTERCEPTORS:
				return interceptors != null && !interceptors.isEmpty();
			case MulePackage.MULE_CONFIG__CONNECTORS:
				return connectors != null && !connectors.isEmpty();
			case MulePackage.MULE_CONFIG__TRANSFORMERS:
				return transformers != null && !transformers.isEmpty();
			case MulePackage.MULE_CONFIG__GLOBAL_ENDPOINTS:
				return globalEndpoints != null && !globalEndpoints.isEmpty();
			case MulePackage.MULE_CONFIG__COMPONENTS:
				return components != null && !components.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (version: "); //$NON-NLS-1$
		result.append(version);
		result.append(", description: "); //$NON-NLS-1$
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //MuleConfigImpl
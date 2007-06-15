/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */
package org.mule.ide.config.mulemodel.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.mule.ide.config.mulemodel.MuleConfig;
import org.mule.ide.config.mulemodel.MuleFactory;
import org.mule.ide.config.mulemodel.MulePackage;

/**
 * This is the item provider adapter for a {@link org.mule.ide.config.mulemodel.MuleConfig} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MuleConfigItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com"; //$NON-NLS-1$

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuleConfigItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addVersionPropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MuleConfig_version_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_MuleConfig_version_feature", "_UI_MuleConfig_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 MulePackage.Literals.MULE_CONFIG__VERSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MuleConfig_description_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_MuleConfig_description_feature", "_UI_MuleConfig_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 MulePackage.Literals.MULE_CONFIG__DESCRIPTION,
				 true,
				 true,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(MulePackage.Literals.MULE_CONFIG__PROPERTIES);
			childrenFeatures.add(MulePackage.Literals.MULE_CONFIG__INTERCEPTORS);
			childrenFeatures.add(MulePackage.Literals.MULE_CONFIG__CONNECTORS);
			childrenFeatures.add(MulePackage.Literals.MULE_CONFIG__TRANSFORMERS);
			childrenFeatures.add(MulePackage.Literals.MULE_CONFIG__GLOBAL_ENDPOINTS);
			childrenFeatures.add(MulePackage.Literals.MULE_CONFIG__COMPONENTS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns MuleConfig.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MuleConfig")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((MuleConfig)object).getVersion();
		return label == null || label.length() == 0 ?
			getString("_UI_MuleConfig_type") : //$NON-NLS-1$
			getString("_UI_MuleConfig_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(MuleConfig.class)) {
			case MulePackage.MULE_CONFIG__VERSION:
			case MulePackage.MULE_CONFIG__DESCRIPTION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case MulePackage.MULE_CONFIG__PROPERTIES:
			case MulePackage.MULE_CONFIG__INTERCEPTORS:
			case MulePackage.MULE_CONFIG__CONNECTORS:
			case MulePackage.MULE_CONFIG__TRANSFORMERS:
			case MulePackage.MULE_CONFIG__GLOBAL_ENDPOINTS:
			case MulePackage.MULE_CONFIG__COMPONENTS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing all of the children that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(MulePackage.Literals.MULE_CONFIG__PROPERTIES,
				 MuleFactory.eINSTANCE.createProperties()));

		newChildDescriptors.add
			(createChildParameter
				(MulePackage.Literals.MULE_CONFIG__INTERCEPTORS,
				 MuleFactory.eINSTANCE.createInterceptorStack()));

		newChildDescriptors.add
			(createChildParameter
				(MulePackage.Literals.MULE_CONFIG__CONNECTORS,
				 MuleFactory.eINSTANCE.createConnector()));

		newChildDescriptors.add
			(createChildParameter
				(MulePackage.Literals.MULE_CONFIG__TRANSFORMERS,
				 MuleFactory.eINSTANCE.createTransformer()));

		newChildDescriptors.add
			(createChildParameter
				(MulePackage.Literals.MULE_CONFIG__GLOBAL_ENDPOINTS,
				 MuleFactory.eINSTANCE.createGlobalEndpoint()));

		newChildDescriptors.add
			(createChildParameter
				(MulePackage.Literals.MULE_CONFIG__COMPONENTS,
				 MuleFactory.eINSTANCE.createGenericComponent()));

		newChildDescriptors.add
			(createChildParameter
				(MulePackage.Literals.MULE_CONFIG__COMPONENTS,
				 MuleFactory.eINSTANCE.createBridgeComponent()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator() {
		return MuleEditPlugin.INSTANCE;
	}

}

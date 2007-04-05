/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.schema.util;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import org.eclipse.emf.ecore.xmi.XMLResource;

import org.mule.schema.MulePackage;

/**
 * <!-- begin-user-doc --> The <b>Resource Factory</b> associated with the package. <!--
 * end-user-doc -->
 * 
 * @see org.mule.schema.util.MuleResourceImpl
 * @generated
 */
public class MuleResourceFactoryImpl extends ResourceFactoryImpl {

	/** Namespace URL for Mule 1.2 schema */
	public static final String MULE_SCHEMA_1_2 = "http://mule.codehaus.org/schemas/mule/1.2";

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExtendedMetaData extendedMetaData;

	/**
	 * Creates an instance of the resource factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MuleResourceFactoryImpl() {
		super();
		extendedMetaData = new BasicExtendedMetaData(new EPackageRegistryImpl(
				EPackage.Registry.INSTANCE));
		extendedMetaData.putPackage(null, MulePackage.eINSTANCE);
		extendedMetaData.putPackage(MULE_SCHEMA_1_2, MulePackage.eINSTANCE);
	}

	/**
	 * Creates an instance of the resource. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Resource createResource(URI uri) {
		XMLResource result = new MuleResourceImpl(uri);
		result.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
		result.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);

		result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		result.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
				Boolean.TRUE);

		result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);
		return result;
	}

} // MuleResourceFactoryImpl

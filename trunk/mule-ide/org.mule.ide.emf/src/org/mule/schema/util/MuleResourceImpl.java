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

import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.mule.schema.util.MuleResourceFactoryImpl
 * @generated
 */
public class MuleResourceImpl extends XMLResourceImpl {
	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public MuleResourceImpl(URI uri) {
		super(uri);
	}

	/**
	 * Overrides the standard encoding
	 */
	protected void init()
	{
		super.init();
		encoding = "UTF-8";
	}

} //MuleResourceImpl

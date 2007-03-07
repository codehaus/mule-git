/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.config.translators;

/**
 * The class <code>StrutsConfigXmlMapping</code> provides mapping constants
 * for working with the Apache Struts configuration file.
 * 
 * @author Daniel
 */
public class MuleConfigXmlMapping {

	/**
	 * name of 'mule-configuration' XML element
	 */
	public static final String MULE_CONFIG = "mule-configuration"; //$NON-NLS-1$

	/**
	 * name of 'description' XML element
	 */
	public static final String DESCRIPTION = "description"; //$NON-NLS-1$

	/**
	 * name of 'global-endpoints' XML element
	 */
	public static final String GLOBAL_ENDPOINTS = "global-endpoints"; //$NON-NLS-1$

	/**
	 * name of 'transformer' XML element
	 */
	public static final String TRANSFORMER = "transformer"; //$NON-NLS-1$

	/**
	 * name of 'transformers' XML element
	 */
	public static final String TRANSFORMERS = "transformers"; //$NON-NLS-1$

	/**
	 * name of 'transformer' inside 'transformers' XML element
	 */
	public static final String TRANSFORMER_TRANSFORMER = "transformers/transformer"; //$NON-NLS-1$

	/**
	 * name of 'connector' XML element
	 */
	public static final String CONNECTOR = "connector"; //$NON-NLS-1$

	/**
	 * name of 'endpoint' XML element
	 */
	public static final String ENDPOINT = "endpoint"; //$NON-NLS-1$

	/**
	 * name of 'global-endpoints/endpoint' XML path
	 */
	public static final String GLOBAL_ENDPOINTS_ENDPOINT = "global-endpoints/endpoint"; //$NON-NLS-1$

	/**
	 * name of 'model' XML element
	 */
	public static final String MODEL = "model"; //$NON-NLS-1$

	/**
	 * name of 'mule-descriptor' XML element
	 */
	public static final String MULE_DESCRIPTOR = "mule-descriptor"; //$NON-NLS-1$

	/**
	 * name of 'model/mule-descriptor' XML element
	 */
	public static final String MODEL_MULE_DESCRIPTOR = "model/mule-descriptor"; //$NON-NLS-1$

	/**
	 * name of 'inbound-router' XML element
	 */
	public static final String INBOUND_ROUTER = "inbound-router"; //$NON-NLS-1$

	/**
	 * name of 'outbound-router' XML element
	 */
	public static final String OUTBOUND_ROUTER = "outbound-router"; //$NON-NLS-1$

	/**
	 * name of 'endpoint/address' XML element
	 */
	public static final String ENDPOINT_ADDRESS = "endpoint"; //$NON-NLS-1$

	/**
	 * name of 'type' XML attribute
	 */
	public static final String ATTR_TYPE = "type"; //$NON-NLS-1$

	/**
	 * special name denoting the comment preceding the node '#comment'
	 */
	public static final String COMMENT = "#comment"; //$NON-NLS-1$

	/**
	 * name of 'address' XML attribute
	 */
	public static final String ATTR_ADDRESS = "address"; //$NON-NLS-1$

	/**
	 * name of 'name' XML attribute
	 */
	public static final String ATTR_NAME = "name"; //$NON-NLS-1$

	/**
	 * name of 'implementation' XML attribute
	 */
	public static final String ATTR_IMPLEMENTATION = "implementation"; //$NON-NLS-1$
	
	/**
	 * name of 'className' XML attribute
	 */
	public static final String ATTR_CLASSNAME = "className"; //$NON-NLS-1$

	/**
	 * name of 'filter' XML element
	 */
	public static final String FILTER = "filter"; //$NON-NLS-1$

	/**
	 * Creates a new StrutsConfig XML mapping.
	 */
	private MuleConfigXmlMapping() {
		// do nothing
	}

}

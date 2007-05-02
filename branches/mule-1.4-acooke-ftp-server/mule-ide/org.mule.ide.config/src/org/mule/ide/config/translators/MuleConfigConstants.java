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
 * The class <code>MuleConfigConstants</code> provides constants used for
 * identifying Mule configuration files.
 * 
 * @author Jesper Steen Møller
 */
public class MuleConfigConstants {

	/**
	 * public identifier of Mule configuration version 1.0
	 */
	public static final String LEGACY_MULE_CONFIG_PUBLIC_ID_1_0 = "-//SymphonySoft //DTD mule-configuration XML V1.0//EN"; //$NON-NLS-1$

	/**
	 * public identifier of Mule configuration version 1.0
	 */
	public static final String MULE_CONFIG_PUBLIC_ID_1_0 = "-//MuleSource //DTD mule-configuration XML V1.0//EN"; //$NON-NLS-1$

	/**
	 * system identifier of Mule configuration version 1.0
	 */
	public static final String LEGACY_MULE_CONFIG_SYSTEM_ID_1_0 = "http://www.symphonysoft.com/dtds/mule/mule-configuration.dtd"; //$NON-NLS-1$

	/**
	 * system identifier of Mule configuration version 1.0
	 */
	public static final String MULE_CONFIG_SYSTEM_ID_1_0 = "http://mule.mulesource.org/dtds/mule-configuration.dtd"; //$NON-NLS-1$
	 
	/**
	 * identifier indicating Mule configuration version 1.0
	 */
	public static final int VERSION_1_0_ID = 10;

	/**
	 * string representation of Mule configuration version 1.0
	 */
	public static final String VERSION_1_0_TEXT = "1.0"; //$NON-NLS-1$
	
	/**
	 * Creates a new <code>MuleConfigConstants</code>.
	 */
	private MuleConfigConstants() {
		// do nothing
	}

}

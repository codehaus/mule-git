/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.launching;

public interface IMuleLaunchConfigurationConstants {

	/** Identifier for the Local Mule Server launch configuration type */
	public static final String ID_MULE_SERVER = MuleLaunchPlugin.PLUGIN_ID + ".localMuleServer";

	/** Attribute that holds the selected Mule project */
	static final String ATTR_MULE_EXEC_CLASS = "org.mule.MuleExecClass";

	/** Attribute that holds the selected Mule config set id */
	static final String ATTR_MULE_CONFIG_SET_ID = "org.mule.MuleConfigSetId";

	/** Default class to execute for a Mule launch */
	static final String DEFAULT_MULE_EXEC_CLASS = "org.mule.MuleServer";
}
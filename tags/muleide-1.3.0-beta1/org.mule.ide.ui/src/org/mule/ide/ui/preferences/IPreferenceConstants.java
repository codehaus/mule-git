/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */

package org.mule.ide.ui.preferences;

import org.mule.ide.core.ICorePreferenceConstants;

/**
 * Constant definitions for plug-in preferences relevant for UI
 */
public class IPreferenceConstants extends ICorePreferenceConstants {
	public static final String DISTRIBUTION_PREFERENCES_ID = "org.mule.ide.ui.preferences.MulePreferencePage";
	
	/** Value constant for MULE_CLASSPATH_TYPE. Use core plugin for classpath.
	 *
	 * @deprecated Since Mule IDE 1.3.0, the distribution is no longer bundled
	 * This is only preserved in order to migrate preferences
	 */
	public static final String MULE_CLASSPATH_TYPE = "muleClasspathType";

	/** 
	 * Value constant for MULE_CLASSPATH_TYPE. Use core plugin for classpath.
	 *
	 * @deprecated Since Mule IDE 1.3.0, the distribution is no longer bundled
	 * This is only preserved in order to migrate preferences
	 */
	public static final String MULE_CLASSPATH_TYPE_PLUGIN = "plugin";

	/**
	 * Value constant for MULE_CLASSPATH_TYPE. Use external classpath.
	 * 
	 * @deprecated Since Mule IDE 1.3.0, the distribution is no longer bundled
	 * This is only preserved in order to migrate preferences
	 */
	public static final String MULE_CLASSPATH_TYPE_EXTERNAL = "external";

	
	/** Location of external Mule installation root
	 *
	 * @deprecated Since Mule IDE 1.3.0, the distribution is no longer bundled
	 * This is only preserved in order to migrate preferences
	 */
	public static final String EXTERNAL_MULE_ROOT = "externalMuleRoot";

}
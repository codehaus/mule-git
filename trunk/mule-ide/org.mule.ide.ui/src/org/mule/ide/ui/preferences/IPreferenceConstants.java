/*
 * $Id$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) Jesper Steen Møller. All rights reserved.
 * http://www.selskabet.org/jesper/
 * 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.mule.ide.ui.preferences;

/**
 * Constant definitions for plug-in preferences
 */
public class IPreferenceConstants {

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

	/** Prefix for Mule installation roots */
	public static final String EXTERNAL_MULE_ROOT_PREFIX = "externalMuleRoot.";

	/** Location of external Mule installation root */
	public static final String EXTERNAL_MULE_ROOT_COUNT = "externalMuleRootCount";

	/** Number of the default Mule installation root */
	public static final String DEFAULT_EXTERNAL_MULE_ROOT = "defaultExternalMuleRoot";

}
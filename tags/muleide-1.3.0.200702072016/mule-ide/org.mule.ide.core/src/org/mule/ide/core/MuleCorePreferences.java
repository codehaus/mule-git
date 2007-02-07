/**
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * MULE_LICENSE.txt file.
 */
package org.mule.ide.core;

import org.mule.ide.core.ICorePreferenceConstants;

/**
 * Preferences for various Mule settings.
 * 
 * @author dadams, jsmoller
 */
public class MuleCorePreferences {

	public static String[] getDistributions() {
		int n = getIntPreference(ICorePreferenceConstants.EXTERNAL_MULE_ROOT_COUNT);
		String distributions[] = new String[n];
		for (int i = 0; i < n; ++i) {
			distributions[i] = getStringPreference(ICorePreferenceConstants.EXTERNAL_MULE_ROOT_PREFIX + i);
		}
		return distributions;
	}
	
	public static int getDefaultDistribution() {
		return getIntPreference(ICorePreferenceConstants.DEFAULT_EXTERNAL_MULE_ROOT);
		
	}

	/**
	 * Get an integer preference value from the preference service.
	 * 
	 * @param key the preference key
	 * @param defaultValue the fallback value
	 * @return the value or 0 if not found
	 */
	protected static int getIntPreference(String key) {
		return MuleCorePlugin.getDefault().getEclipsePreferences().getInt(key, 0);
	}

	/**
	 * Get a String preference value from the preference service.
	 * 
	 * @param key the preference key
	 * @return the value or null if not found
	 */
	protected static String getStringPreference(String key) {
		return MuleCorePlugin.getDefault().getEclipsePreferences().get(key, null);
	}

}

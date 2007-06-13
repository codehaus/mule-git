/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.ide.ui.preferences;

import org.mule.ide.core.ICorePreferenceConstants;
import org.mule.ide.ui.MulePlugin;

/**
 * Preferences for various Mule settings.
 * 
 * @author dadams, jsmoller
 */
public class MulePreferences {

	/**
	 * Gets the default location for an external Mule root folder.
	 * 
	 * @return the path or null if not set
	 */
	public static String getLegacyExternalMuleRoot() {
		return getStringPreference(IPreferenceConstants.EXTERNAL_MULE_ROOT);
	}

	/**
	 * Set the default location for an external Mule root folder.
	 * 
	 * @param root
	 */
	public static void clearDeprecatedValues() {
		clearPreferenceValue(IPreferenceConstants.EXTERNAL_MULE_ROOT);
		clearPreferenceValue(IPreferenceConstants.MULE_CLASSPATH_TYPE_PLUGIN);
		clearPreferenceValue(IPreferenceConstants.EXTERNAL_MULE_ROOT);
	}

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

	public static void setDistributions(String[] distributions, int selected) {
		int oldMax = getIntPreference(ICorePreferenceConstants.EXTERNAL_MULE_ROOT_COUNT);
		
		setIntPreference(ICorePreferenceConstants.EXTERNAL_MULE_ROOT_COUNT, distributions.length);
		int i = 0;
		for (; i < distributions.length; ++i) {
			setStringPreference(ICorePreferenceConstants.EXTERNAL_MULE_ROOT_PREFIX + i, distributions[i]);
		}
		for (; i < oldMax; ++i) {
			clearPreferenceValue(ICorePreferenceConstants.EXTERNAL_MULE_ROOT_PREFIX + i);
		}
		setIntPreference(ICorePreferenceConstants.DEFAULT_EXTERNAL_MULE_ROOT, selected);
	}
	
	/**
	 * Gets the default classpath type preference.
	 * 
	 * @return the preference value
	 */
	public static String getDeprecatedClasspathChoice() {
		return getStringPreference(IPreferenceConstants.MULE_CLASSPATH_TYPE);
	}

	/**
	 * Get an integer preference value from the preference service.
	 * 
	 * @param key the preference key
	 * @param defaultValue the fallback value
	 * @return the value or 0 if not found
	 */
	protected static int getIntPreference(String key) {
		return MulePlugin.getDefault().getPreferenceStore().getInt(key);
	}

	/**
	 * Set an integer preference value to the preference service.
	 * 
	 * @param key the preference key
	 * @param value the new value for the preference
	 */
	protected static void setIntPreference(String key, int value) {
		MulePlugin.getDefault().getPreferenceStore().setValue(key, value);
	}
	
	/**
	 * Get a String preference value from the preference service.
	 * 
	 * @param key the preference key
	 * @return the value or null if not found
	 */
	protected static String getStringPreference(String key) {
		return MulePlugin.getDefault().getPreferenceStore().getString(key);
	}

	/**
	 * Sets a string preference in instance scope.
	 * 
	 * @param key the preference key
	 * @param value the preference value
	 */
	protected static void setStringPreference(String key, String value) {
		MulePlugin.getDefault().getPreferenceStore().setValue(key, value);
	}

	/**
	 * Clear a string preference value in instance scope.
	 * 
	 * @param key the preference key
	 */
	protected static void clearPreferenceValue(String key) {
		MulePlugin.getDefault().getPreferenceStore().setToDefault(key);
	}
	
}

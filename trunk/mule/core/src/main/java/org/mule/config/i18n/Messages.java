/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.i18n;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>Messages</code> provides facilities for constructing <code>Message</code>
 * objects and access to core message constants.
 */
public class Messages implements CoreMessageConstants
{
    /**
     * logger used by this class
     */
    protected static final Log logger = LogFactory.getLog(Messages.class);

    public static final String DEFAULT_BUNDLE = "core";

    private static Map bundles = new HashMap();

    private static Object[] emptyArgs = new Object[]{};

    public static String get(int code)
    {
        return getString(DEFAULT_BUNDLE, code, emptyArgs);
    }

    public static String get(int code, Object[] args)
    {
        if (args == null)
        {
            args = Messages.emptyArgs;
        }
        return getString(DEFAULT_BUNDLE, code, args);
    }

    public static String get(int code, Object arg1)
    {
        if (arg1 == null)
        {
            arg1 = "null";
        }
        return getString(DEFAULT_BUNDLE, code, new Object[]{arg1});
    }

    public static String get(int code, Object arg1, Object arg2)
    {
        if (arg1 == null)
        {
            arg1 = "null";
        }
        if (arg2 == null)
        {
            arg2 = "null";
        }
        return getString(DEFAULT_BUNDLE, code, new Object[]{arg1, arg2});
    }

    public static String get(int code, Object arg1, Object arg2, Object arg3)
    {
        if (arg1 == null)
        {
            arg1 = "null";
        }
        if (arg2 == null)
        {
            arg2 = "null";
        }
        if (arg3 == null)
        {
            arg3 = "null";
        }
        return getString(DEFAULT_BUNDLE, code, new Object[]{arg1, arg2, arg3});
    }

    public static String get(String bundleName, int code)
    {
        return getString(bundleName, code, emptyArgs);
    }

    public static String get(String bundleName, int code, Object[] args)
    {
        if (args == null)
        {
            args = Messages.emptyArgs;
        }
        return getString(bundleName, code, args);
    }

    public static String get(String bundleName, int code, Object arg1)
    {
        if (arg1 == null)
        {
            arg1 = "null";
        }
        return getString(bundleName, code, new Object[]{arg1});
    }

    public static String get(String bundleName, int code, Object arg1, Object arg2)
    {
        if (arg1 == null)
        {
            arg1 = "null";
        }
        if (arg2 == null)
        {
            arg2 = "null";
        }
        return getString(bundleName, code, new Object[]{arg1, arg2});
    }

    public static String get(String bundleName, int code, Object arg1, Object arg2, Object arg3)
    {
        if (arg1 == null)
        {
            arg1 = "null";
        }
        if (arg2 == null)
        {
            arg2 = "null";
        }
        if (arg3 == null)
        {
            arg3 = "null";
        }
        return getString(bundleName, code, new Object[]{arg1, arg2, arg3});
    }

    public static String getString(String bundleName, int code, Object[] args)
    {
        // We will throw a MissingResourceException if the bundle name is invalid
        // This happens if the code references a bundle name that just doesn't exist
        ResourceBundle bundle = getBundle(bundleName);

        try
        {
            String m = bundle.getString(String.valueOf(code));
            if (m == null)
            {
                logger.error("Failed to find message for id " + code + " in resource bundle " + bundleName);
                return "";
            }

            return MessageFormat.format(m, args);
        }
        catch (MissingResourceException e)
        {
            logger.error("Failed to find message for id " + code + " in resource bundle " + bundleName);
            return "";
        }
    }

    protected static ResourceBundle getBundle(String bundleName) throws MissingResourceException
    {
        ResourceBundle bundle = (ResourceBundle)bundles.get(bundleName);
        if (bundle == null)
        {
            String path = "META-INF.services.org.mule.i18n." + bundleName + "-messages";
            logger.debug("Loading resource bundle: " + path + " for locale " +
                    Locale.getDefault());
            Locale locale = Locale.getDefault();
            bundle = ResourceBundle.getBundle(path, locale);
            bundles.put(bundleName, bundle);
        }
        return bundle;
    }

}

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
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class MessageFactory extends Object
{
    /**
     * logger used by this class
     */
    private static final Log logger = LogFactory.getLog(MessageFactory.class);

    private static final transient Object[] EMPTY_ARGS = new Object[]{};

    /**
     * Computes the bundle's full path 
     * (<code>META-INF/services/org/mule/i18n/&lt;bundleName&gt;-messages.properties</code>) from
     * <code>bundleName</code>.
     * 
     * @param bundleName Name of the bundle without the &quot;messages&quot; suffix and without
     *          file extension.
     */
    protected static String getBundlePath(String bundleName)
    {
        return "META-INF.services.org.mule.i18n." + bundleName + "-messages";
    }
    
    /**
     * Factory method to create a new {@link Message} instance that is filled with the formatted
     * message with id <code>code</code> from the resource bundle <code>bundleName</code>.
     * 
     * @param bundleName Name of the resource bundle for lookup
     * @param code numeric code of the message
     * @param arg
     */
    protected static Message createMessage(String bundlePath, int code, Object arg)
    {
        Object[] arguments = new Object[] {arg};
        String messageString = getString(bundlePath, code, arguments);
        return new Message(messageString, code, arguments);
    }
    
    /**
     * Factory method to create a new {@link Message} instance that is filled with the formatted
     * message with id <code>code</code> from the resource bundle <code>bundleName</code>.
     * 
     * @param bundleName Name of the resource bundle for lookup
     * @param code numeric code of the message
     * @param arg1
     * @param arg2
     */
    protected static Message createMessage(String bundlePath, int code, Object arg1, Object arg2)
    {
        Object[] arguments = new Object[] {arg1, arg2};
        String messageString = getString(bundlePath, code, arguments);
        return new Message(messageString, code, arguments);
    }
    
    /**
     * Factory method to create a new {@link Message} instance that is filled with the formatted
     * message with id <code>code</code> from the resource bundle <code>bundleName</code>.
     * 
     * @param bundleName Name of the resource bundle for lookup
     * @param code numeric code of the message
     * @param arg1
     * @param arg2
     * @param arg3
     */
    protected static Message createMessage(String bundlePath, int code, Object arg1, Object arg2, 
        Object arg3)
    {
        Object[] arguments = new Object[] {arg1, arg2, arg3};
        String messageString = getString(bundlePath, code, arguments);
        return new Message(messageString, code, arguments);
    }
    
    /**
     * Factory Method to create a new {@link Message} instance that is filled with the formatted
     * message with id <code>code</code> from the resource bundle <code>bundleName</code>.
     * 
     * @param bundleName
     * @param code
     */
    protected static Message createMessage(String bundlePath, int code)
    {
        String messageString = getString(bundlePath, code, null);
        return new Message(messageString, code, EMPTY_ARGS);
    }
    
    protected static String getString(String bundlePath, int code)
    {
        return getString(bundlePath, code, null);
    }
    
    protected static String getString(String bundlePath, int code, Object arg)
    {
        Object[] arguments = new Object[] {arg};
        return getString(bundlePath, code, arguments);
    }
    
    protected static String getString(String bundlePath, int code, Object arg1, Object arg2)
    {
        Object[] arguments = new Object[] {arg1, arg2};
        return getString(bundlePath, code, arguments);
    }

    private static String getString(String bundlePath, int code, Object[] args)
    {
        // We will throw a MissingResourceException if the bundle name is invalid
        // This happens if the code references a bundle name that just doesn't exist
        ResourceBundle bundle = getBundle(bundlePath);

        try
        {
            String m = bundle.getString(String.valueOf(code));
            if (m == null)
            {
                logger.error("Failed to find message for id " + code + " in resource bundle " + bundlePath);
                return "";
            }

            return MessageFormat.format(m, args);
        }
        catch (MissingResourceException e)
        {
            logger.error("Failed to find message for id " + code + " in resource bundle " + bundlePath);
            return "";
        }
    }

    /**
     * @throws MissingResourceException if resource is missing
     */
    private static ResourceBundle getBundle(String bundlePath)
    {
        Locale locale = Locale.getDefault();
        logger.debug("Loading resource bundle: " + bundlePath + " for locale " + locale);
        ResourceBundle bundle = ResourceBundle.getBundle(bundlePath, locale);
        return bundle;
    }    
}



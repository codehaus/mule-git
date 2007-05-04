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

    /**
     * The message code to use when client code does not specify one.
     */
    private static final int STATIC_ERROR_CODE = -1;

    private static final transient Object[] EMPTY_ARGS = new Object[]{};

    /**
     * Factory method to create a new {@link Message} instance that is filled with the formatted
     * message with id <code>code</code> from the resource bundle <code>bundleName</code>.
     * 
     * @param bundleName Name of the resource bundle for lookup
     * @param code numeric code of the message
     * @param arg
     */
    protected static Message createMessage(String bundleName, int code, Object arg)
    {
        Object[] arguments = new Object[] {arg};
        String messageString = getString(bundleName, code, arguments);
        return new Message(messageString, code, arguments);
    }
    
    /**
     * Factory Method to create a new {@link Message} instance that is filled with the formatted
     * message with id <code>code</code> from the resource bundle <code>bundleName</code>.
     * 
     * @param bundleName
     * @param code
     */
    protected static Message createMessage(String bundleName, int code)
    {
        String messageString = getString(bundleName, code, null);
        return new Message(messageString, code, EMPTY_ARGS);
    }

    private static String getString(String bundleName, int code, Object[] args)
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

    /**
     * @throws MissingResourceException if resource is missing
     */
    private static ResourceBundle getBundle(String bundleName)
    {
        String path = "META-INF.services.org.mule.i18n." + bundleName + "-messages";
        Locale locale = Locale.getDefault();
        logger.debug("Loading resource bundle: " + path + " for locale " + locale);
        ResourceBundle bundle = ResourceBundle.getBundle(path, locale);
        return bundle;
    }    
}



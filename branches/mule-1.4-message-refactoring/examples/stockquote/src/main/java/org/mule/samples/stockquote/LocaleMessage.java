/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.samples.stockquote;

import org.mule.config.i18n.MessageFactory;

/**
 * <code>LocaleMessage</code> is a convenience interface for retrieving
 * internationalised strings from resource bundles. The actual work is done by
 * the {@link MessageFactory} in core.
 */
public class LocaleMessage extends MessageFactory
{
    private static final String BUNDLE_PATH = getBundlePath("stockquote-example");

    public static String getStockQuoteMessage(Object[] args)
    {
        return getString(BUNDLE_PATH, 1, args);
    }
}
